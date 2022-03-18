# 第 6 章 ChannelHandler 和 ChannelPipeline

## 6.1 ChannelHandler 家族

<div style="text-align: center;">表 6-1 ChannelHandler 的生命周期状态</div>

 | 状态                  | 描述                                                                       |
 | :-------------------: | :-----------------------------------------:                                |
 | ChannelUnregistered   | Channel 已经被创建，但还未注册到 EventLoop                                 |
 | ChannelRegistered     | Channel 已经被注册到了 EventLoop                                           |
 | ChannelActive         | Channel 处于活动状态（已经连接到它的远程节点）。它现在可以接收和发送数据了 |
 | ChannelInactive       | Channel 没有连接到远程节点                                                 |

### 6.1.6 资源管理

为了诊断潜在的（资源泄露）问题，Netty 提供了 ResourceLeakDetector（其利用了 JDK 提供的 PhantomReference<T>），它将对应用程序的缓冲区分配大约 1% 的采样来检测内存泄漏。相关的开销非常小。

<div style="text-align: center;">表 6-5 泄漏检测级别</div>

| 级别                                                        | 描述                                                                                                      |
| :----------: | :---------------------------------------------------------: |
| DISABLED                                                    | 禁用泄漏检测，只有在详尽的测试后才应设置为这个值                                                          |
| SIMPLE                                                      | 使用 1% 的默认采样率检测并报告任何发现的泄漏。这是默认级别，适合绝大部分情况                              |
| ADVANCED                                                    | 使用默认的采样率，报告所发现的任何泄漏以及对应的消息被访问的位置                                          |
| PARANOID                                                    | 类似于 ADVANCED，但是其将会对每次（对消息的）访问都进行采样。这对性能将会有很大影响，应该只在调试阶段使用 |

如果一个消息被消费或丢弃了，并且没有传递给 ChannelPipeline 中的下一个 ChannelOutboundHandler，那么用户就有责任调用 ReferenceCountUtil.release()。如果消息到达了实际的传输层，那么当它被写入或者 Channel 关闭时，都将被自动释放。

## 6.2 ChannelPipeline 接口

每一个新建的 Channel 都将会被分配一个新的 ChannelPipeline。这项关联是永久性的，Channel 既不能附加另一个 ChannelPipeline，也不能分离其当前的。在 Netty 组件的生命周期中，这是一项固定的操作，不需要开发人员的任何干预。根据事件的起源，事件将会被 ChannelInboundHandler 处理。随后，通过调用 ChannelHandlerContext 实现，它将被转发给同一超类型的下一个 ChannelHandler。

在 ChannelPipeline 传播事件时，它会测试 ChannelPipeline 中的下一个 ChannelHandler 的类型是否和事件的运动方向相匹配。如果不匹配，ChannelPipeline 将跳过该 ChannelHandler 并前进到下一个，直到它找到和该事件所期望的方向相匹配的为止。当然，ChannelHandler 也可以同时实现 ChannelInboundHandler 接口和 ChannelOutboundHandler 接口。

### 6.2.1 修改 ChannelPipeline

ChannelHandler 可以通过添加、删除或转换其他的 ChannelHandler 来实时地修改 ChannelPipeline 的布局，它也可以将它自己从 ChannelPipeline 中移除。

## 6.3 ChannelHandlerContext 接口

ChannelHandlerContext 代表了 ChannelHandler 和 ChannelPipeline 之间的关联，每当有 ChannelHandler 添加到 ChannelPipeline 中时，都会创建 ChannelHandlerContext。ChannelHandlerContext 的主要功能是管理它所关联的 ChannelHandler 和在同一个 ChannelPipeline 中的其他 ChannelHandler 之间的交互。

ChannelHandlerContext 有很多的方法，其中一些方法也存在于 Channel 和ChannelPipeline 本身上，但是有一点重要的不同。如果调用 Channel 或 ChannelPipeline 上的这些方法，它们将沿着整个ChannelPipeline 进行传播。而调用位于 ChannelHandlerContext 上的相同方法，则将从当前所关联的 ChannelHandler 开始，并且只会传播给位于 ChannelPipeline 中的下一个能够处理该事件的 ChannelHandler。

当使用 ChannelHandlerContext 的 API 的时候，请牢记以下两点：
* ChannelHandlerContext 和 ChannelHandler 之间的关联是永远不会改变的，所以缓存对它的引用是安全的；
* 相对于其他类的同名方法 ChannelHandlerContext 的方法将产生更短的事件流，应该尽可能地利用这个特性来获得最大的性能。

###  6.3.1 使用 ChannelHandlerContext

为什么会想要从 ChannelPipeline 中的某个特定点开始传播事件呢？
* 为了减少将事件传经对它不感兴趣的 ChannelHandler 所带来的开销；
* 为了避免将事件传经那些可能会对它感兴趣的 ChannelHandler。

要想调用从某个特定的 ChannelHandler 开始的处理过程，必须获取到在 ChannelHandler 之前的 ChannelHandler 所关联的 ChannelHandlerContext。这个 ChannelHandlerContext 将调用和它所关联的 ChannelHandler 之后的 ChannelHandler。

### 6.3.2 ChannelHandler 和 ChannelHandlerContext 的高级用法

因为一个 ChannelHandler 可以从属于多个 ChannelPipeline，所以它也可以绑定到多个 ChannelHandlerContext 实例。对于这种用法（指在多个 ChannelPipeline 中共享同一个ChannelHandler），对应的 ChannelHandler 必须使用 @Sharable 注解标注；否则试图将它添加到多个 ChannelPipeline 时将会引发异常。显而易见，为了安全地被用于多个并发的 Channel，这样的 ChannelHandler 必须是线程安全的。

**为何要共享同一个 ChannelHandler**

在多个 ChannelPipeline 中安装同一个 ChannelHandler 的一个常见的原因是用于收集多个 Channel 的统计信息。

## 6.4 异常处理

### 6.4.1 处理入站异常

如果在处理入站事件的过程中有异常被抛出，那么它将从它在 ChannelInboundHandler 里被触发的那一点开始流经 ChannelPipeline。要想处理这种类型的入站异常，需要在 ChannelInboundHandler 实现中重写 exceptionCaught() 方法。因为异常将会继续按照入站方向流动（就像所有的入站事件一样），实现了异常处理逻辑的 ChannelInboundHandler 通常位于 ChannelPipeline 的最后。这确保了所有的入站异常都总是会被处理，无论它们可能会发生在 ChannelPipeline 中的什么位置。如果不实现任何处理入站异常的逻辑（或者没有消费该异常），那么 Netty 将会记录该异常没有被处理的事实，即 Netty 将会通过 Warning 级别的日志记录该异常到达了 ChannelPipeline 的尾端，但没有被处理，并尝试释放该异常。

### 6.4.2 处理出站异常

用于处理出站操作中正常完成以及异常的选项，基于以下的通知机制：
* 每个出站操作都将返回一个 ChannelFuture。注册到 ChannelFuture 的 ChannelFutureListener 将在操作完成时被通知该操作是成功了还是出错了；
* 几乎所有的 ChannelOutboundHandler 上的方法都会传入一个 ChannelPromise 的实例。作为 ChannelFuture 的子类，ChannelPromise 也可以被分配用于异步通知的监听器。但是 ChannelPromise 还具有提供立即通知的可写方法：setSuccess() 方法和 setFailure() 方法。

添加 ChannelFutureListener 有两种方式，一种是调用出站操作所返回的 ChannelFuture 上的 addListener() 方法，这种方式适用于需要细致的异常处理的情况；另一种方式是将 ChannelFutureListener 添加到即将作为参数传递给 ChannelOutboundHandler 方法的 ChannelPromise，这种方式适用于一般的异常处理情况，较为简单。

如果 ChannelOutboundHandler 本身抛出异常，此时 Netty 本身会通知任何已经注册到对应 ChannelPromise 的监听器。
