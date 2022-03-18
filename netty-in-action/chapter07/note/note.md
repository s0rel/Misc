# 第 7 章 EventLoop 和线程模型

## 7.2 EventLoop 接口

事件和任务是以先进先出的顺序执行的，这样可以保证字节内容总是按正确的顺序被处理，消除潜在的数据损坏的可能性。

### 7.2.1 Netty 4 中的 I/O 和事件处理

由 I/O 操作触发的事件将流经安装了一个或多个 ChannelHandler 的 ChannelPipeline。传播这些事件的方法调用可以随后被 ChannelHandler 所拦截，并且可以按需地处理事件。在 Netty 4 中，所有的 I/O 操作和事件都有已经被分配给了 EventLoop 的那个线程来处理（写操作是可以从外部的任意线程触发的）。

### 7.2.2 Netty 3 中的 I/O 操作

在以前的版本中所使用的线程模型只保证了入站事件会在所谓的 I/O 线程（对应于 Netty 4 中的EventLoop）中执行。所有的出站事件都由调用线程处理，其可能是 I/O 线程也可能是别的线程。开始看起来这似乎是个好主意，但是已经被发现是有问题的。因为需要在 ChannelHandler 中对出站事件进行仔细的同步。简而言之，不可能保证多个线程不会在同一时刻尝试访问出站事件。当出站事件触发了入站事件时，将会导致另一个负面影响。当 Channel.write() 方法导致异常时，需要生成并触发一个 exceptionCaught 事件。但是在 Netty 3 的模型中，由于这是一个入站事件，需要在调用线程中执行代码，然后将事件移交给 I/O 线程去执行，然后将带来额外的上下文切换。

Netty 4 中所采用的线程模型通过在同一个线程中处理某个给定的 EventLoop 中所产生的
所有事件解决了这个问题。这提供了一个更加简单的执行体系架构，并且消除了在多个 ChannelHandler 中进行同步的需要（除了任何可能需要在多个 Channel 中共享的）。

## 7.3 任务调度

### 7.3.2 使用 EventLoop 调度任务

ScheduledExecutorService 的实现具有局限性，例如，事实上作为线程池管理的一部分，将会有额外的线程创建。如果有大量任务被紧凑地调度，那么这将成为一个瓶颈。Netty 通过 Channel 的 EventLoop 实现任务调度解决了这一问题。

## 7.4 实现细节

### 7.4.1 线程管理

Netty 线程模型的卓越性能取决于当前执行的 Thread 的身份确定，这是通过调用 EventLoop 的 inEventLoop(Thread) 方法实现的。如果当前调用线程正式支撑 EventLoop 的线程，那么所提交的代码块将会被直接执行。否则，EventLoop 将调度该任务一遍稍后执行，并将它放入到内部队列中。当 EventLoop 下次处理它的事件时，它会执行队列中那些任务/事件。这也就解释了任何的 Thread 是如何与 Channel 直接交互而无需在 ChannelHandler 中进行额外同步的。注意，每个 EventLoop 都有它自己的任务队列，独立于任何其他的 EventLoop。

### 7.4.2 EventLoop/线程的分配

服务于 Channel 的 I/O 和事件的 EventLoop 包含在 EventLoopGroup 中，根据不同的传输实现，EventLoop 的创建和分配方式也不同。

**1. 异步传输**

异步传输实现只使用了少量的 EventLoop 以及和它们相关联的 Thread，而且在当前的线程模型中，它们可能会被多个 Channel 所共享。这使得可以通过尽可能少量的 Thread 来支撑大量的 Channel，而不是每个 Channel 分配一个 Thread。

在创建 EventLoopGroup 时就直接分配了 EventLoop 以及支撑它们的 Thread，以确保在需要时它们是可用的。EventLoopGroup 负责为每个新创建的 Channel 分配一个 EventLoop。在当前的实现中，使用顺序循环的方式进行分配以获取一个均衡的分布，并且相同的 EventLoop 可能会被分配给多个 Channel（这一点在将来的版本中可能会改变）。一旦一个 Channel 被分配给一个 EventLoop，它将在它的整个生命周期中都使用这个 EventLoop 以及相关联的 Thread，这样可以解决同步问题。

另外，需要注意的是 EventLoop 的分配方式对 ThreadLocal 的使用的影响。因为一个 EventLoop 通常会被用于支撑多个 Channel，所以对于所有相关联的 Channel 来说，ThreadLocal 将是一样的。这使得它对于实现状态跟踪等功能来说是个糟糕的选择。然而在一些无状态的上下文中，它仍然可以被用于在多个 Channel 之间共享一些重度的或代价昂贵的对象甚至是事件。
