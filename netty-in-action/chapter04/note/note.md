# 第 4 章 传输

## 4.2 传输 API

传输 API 的核心是 Channel 接口，它被用于所有的所有的 I/O 操作。

每个 Channel 都将会被分配给一个 ChannelPipeline 和 ChannelConfig。ChannelConfig 包含了该 Channel
的所有配置设置，并且支持热更新。由于特定的传输可能具有独特的设置，所以它可能会实现一个 ChannelConfig 的子类型。

由于 Channel 是独一无二的，所以为了保证顺序将 Channel 声明为 java.lang.Comparable 的一个子接口。因此，如果两个不同的 Channel 实例都返回了相同的散列码，那么 AbstractChannel
中的 compareTo() 方法将会抛出一个 Error。

ChannelPipeline 持有所有将应用于入站和出站数据以及事件的 ChannelHandler 实例，这些 ChannelHandler 实现了应用程序用于处理状态变化以及数据处理的逻辑。

ChannelHandler 的典型用途包括：

* 将数据从一种格式转换为另一种格式；
* 提供异常的通知；
* 提供 Channel 变为活动或者非活动的通知；
* 提供当 Channel 注册到 EventLoop 或者从 EventLoop 注销时的通知；
* 提供有关用户自定义事件的通知。

**拦截过滤器**

ChannelPipeline 实现了拦截过滤器模式。

可以根据需要通过添加或者移除 ChannelHandler 实例来修改 ChannelPipeline。通过利用 Netty 的这项能力可以构建出高度灵活的程序。

Channel 活动的意义可能取决于底层的传输。例如，一个 Socket 传输一旦连接到了远程节点便是活动的，而一个 Datagram 传输一旦被打开便是活动的。

Netty 的 Channel 实现是线程安全的，因此可以存储一个到 Channel 的引用，并且每当需要向远程节点写数据时，都可以使用它，即使当时许多线程都在使用它。

## 4.3 内置的传输

Netty 内置了一些可开箱即用的传输，因为并不是它们所有的传输都支持每一种协议，所以你必须选择一个和你的应用程序所使用的协议相容的传输。

<div style="text-align: center;">表 4-2 Netty 所提供的传输</div>

| 名称     | 包                          | 描述                                                                                                                                |
| :-----:  | :----:                      | :--------------:                                                                                                                    |
| NIO      | io.netty.channel.socket.nio | 使用 java.nio.channels 包作为基础 - 基于选择器模式                                                                                  |
| Epoll    | io.netty.channel.epoll      | 由 JNI 驱动的 epoll() 和非阻塞 I/O。这个传输支持只有在 Linux 上可用的多种特性，如 SO_REUSEPORT，比 NIO 传输更快，而且是完全非阻塞的 |
| OIO      | io.netty.channel.socket.oio | 使用 java.net 包作为基础 - 使用阻塞流                                                                                               |
| Local    | io.netty.channel.local      | 可以在一个 VM 内部通过管道进行通信的本地传输                                                                                            |
| Embedded | io.netty.channel.embedded   | Embedded 传输，允许使用 ChannelHandler 而又不需要一个真正的基于网络的传输。这在测试 ChannelHandler 实现时非常有用                   |

### 4.3.1 NIO - 非阻塞 I/O

NIO 提供了一个所有 I/O 操作的全异步实现，它利用了自 NIO 子系统被引入 JDK 1.4 时便可用的基于选择器的 API。

选择器背后的基本概念是充当一个注册表，在那里将可以请求在 Channel 的状态发生变化时得到通知。可能的状态变化有：
* 新的 Channel 已被接受并且就绪；
* Channel 连接已经完成；
* Channel 有已经就绪的可供读取的数据；
* Channel 可用于写数据。


**零拷贝**

零拷贝是一种目前只有在使用 NIO 和 Epoll 传输时才可使用的特性。它使你可以快速高效地将数据从文件系统移动到网络接口，而不需要将其从内核空间复制到用户空间，其在像 FTP 或者 HTTP 这样的协议中可以显著提升性能。但是，并不是所有的操作系统都支持这一特性。特别地，它对于实现了数据加密或者压缩的文件系统是不可用的-只能传输文件的原始内容。反过来说，传输已被加密的文件则不是问题。

### 4.3.3. OIO - 旧的阻塞 I/O

Netty 能够使用和用于异步传输相同的 API 来支持 OIO 的原因：Netty 利用了 SO_TIMEOUT 这个 Socket 标志，它指定了等待一个 I/O 操作完成的最大毫秒数。如果操作在指定的时间间隔内没有完成，则会抛出一个 SocketTimeoutException。Netty 将捕获这个异常并继续处理循环。在 EventLoop 下一次运行时，它将再次尝试。这实际上也是类似于 Netty 这样的异步框架能够支持 OIO 的唯一方式。

### 4.3.4 用于 JVM 内部通信的 Local 传输

Netty 提供了一个 Local 传输，用于在同一个 JVM 中运行的客户端和服务器程序之间的异步通信。同样，这个传输也支持对于所有 Netty 传输实现都共同的 API。

在这个传输中，和服务器 Channel 相关联的 SocketAddress 并没有绑定物理网络地址；相反，只要服务器还在运行，它就会被存储在注册表里，并在 Channel 关闭时注销。因为这个传输并不接受真正的网络流量，所以它并不能够和其他传输实现进行互操作。因此，客户端希望连接到（在同一个 JVM 中）使用了这个传输的服务器端时也必须使用它。除了这个限制，它的使用方式和其他传输一模一样。

### 4.3.5 Embedded 传输

Netty 提供了一种额外的传输，使得可以将一组 ChannelHandler 作为帮助器类嵌入到其他的 ChannelHandler 内部。通过这种方式，可以扩展一个 ChannelHandler 的功能，而又不需要修改其内部代码。
