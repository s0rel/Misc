# 第 2 章 你的第一款 Netty 应用程序

## 2.3 编写 Echo 服务器

### 2.3.1 ChannelHandler 和业务逻辑

**如果不捕获异常，会发生什么呢**

每个 Channel 都拥有一个与之相关联的 ChannelPipeline，其持有一个 ChannelHandler 的实例链。在默认情况下，ChannelHandler 会把对它的方法的调用转发给链中的下一个 ChannelHandler。因此，如果 exceptionCaught() 方法没有被该链中的某处实现，那么所接收到的异常将会被传递到 ChannelPipeline 的尾端并被记录。为此，你的应用程序应该提供至少有一个实现了 exceptionCaught() 方法的 ChannelHandler。 

