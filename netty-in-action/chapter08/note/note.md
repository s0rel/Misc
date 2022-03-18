# 第 8 章 引导

## 8.1 Bootstrap 类

**为什么引导类时 Cloneable 的**

有时可能会需要创建多个具有类似配置或完全相同配置的 Channel。为了支持这种模式而又不为每个 Channel 都创建并配置一个新的引导类实例，AbstractBootstrap 被标记为了 Cloneable。在一个已经配置完成的引导实例上调用 clone() 方法将返回另一个可以立即使用的引导类实例。注意，这种方式智慧创建引导类实例的 EventLoopGroup 的一个浅拷贝，所以，后者将在所有克隆的 Channel 实例之间共享。这是可以接受的，因为通常这些克隆的 Channel 的生命周期都很短暂，一个典型的场景是 - 创建一个 Channel 以进行一次 HTTP 请求。

## 8.2 引导客户端和无连接协议

Bootstrap 类被用于客户端和无连接协议的应用程序中，负责为客户端和使用无连接协议的应用程序创建 Channel。

## 8.4 从 Channel 引导客户端

通过将已被接受的子 Channel 的 EventLoop 传递给 Bootstrap 的 group() 方法来共享 EventLoop。因为分配给 EventLoop 的所有 Channel 都使用同一个线程，所以这避免了额外的线程创建以及相关的上下文切换。

## 8.5 在引导过程中添加多个 ChannelHandler

如果应用程序使用了多个 ChannelHandler，请定义自己的 ChannelInitializer 实现来将它们安装到 ChannelPipeline 中。

## 8.6 使用 Netty 的 ChannelOption 和属性

在每个 Channel 创建时都手动配置它可能会变得相当乏味。幸运的是，不必这样做。相反，可以使用 option() 方法来将 ChannelOption 应用到引导。所提供的值将会被自动应用到引导所创建的所有 Channel。可用的 ChannelOption 包括了底层连接的详细信息，如 keep-alive 或者超时属性以及缓冲区设置。

 Netty 应用程序通常与组织的专有软件集成在一起，而像 Channel 这样的组件可能甚至会在正常的 Netty 生命周期之外被使用。在某些常用的属性和数据不可用时，Netty 提供了 AttributeMap 抽象（一个由 Channel 和引导类提供的集合）以及 AttributeKey<T>（一个用于插入和获取属性值的泛型类）。使用这些工具，便可以安全地将任何类型的数据项与客户端和服务器 Channel（包含 ServerChannel 的子 Channel） 相关联了。

## 8.8 关闭

引导使应用程序启动并且运行起来，但是迟早都需要优雅地地将它关闭。最重要的是，需要关闭 EventLoopGroup，它将处理任何挂起的事件和任务，并且随后释放所有活动的线程。这就是调用 EventLoopGroup.shutdownGracefully() 方法的作用。这个方法调用将会返回一个 Future，这个 Future 将在关闭完成时接收到通知。需要注意的是，shutdownGracefully() 方法也是一个异步的操作，所以必须阻塞等待直到它完成或者向所返回的 Future 注册一个监听器以在关闭完成时获得通知。

或者，也可以在调用 EventLoopGroup.shutdownGracefully() 方法之前显式地在所有活动的 Channel 上调用 Channel.close() 方法。但是在任何情况下，都请记得关闭 EventLoopGroup 本身。