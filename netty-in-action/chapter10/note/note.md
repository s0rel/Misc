# 第 10 章 编解码器框架

## 10.2 解码器

Netty 提供的解码器类覆盖了两个不同的用例：
* 将字节解码为消息 - ByteToMessageDecoder 和 ReplayingDecoder；
* 将一种消息类型解码为另一种 - MessageToMessageDecoder。

解码器实现了 ChannelInboundHandler，负责将入站数据从一种格式转换为另一种格式。

每当需要为 ChannelPipeline 中的下一个 ChannelInboundHandler 转换入站数据时会用到解码器。此外，得益于 ChannelPipeline 的设计，可以将多个解码器链接在一起，以实现任意复杂的转换逻辑。

### 10.2.1 抽象类 ByteToMessageDecoder

**编解码器中的引用计数**

一旦消息被编码或解码，它就会被 ReferenceCountUtil.release(message) 调用自动释放。如果需要保留引用计数以便稍后使用，可以调用 ReferenceCountUtil.retain(message) 方法。这将会增加该引用计数，从而防止该消息被释放。

### 10.2.2 抽象类 ReplayingDecoder

ReplayingDecoder 类扩展了 ByteToMessageDecoder 类，使得不必调用 readableBytes() 方法。它通过一个自定义的 ByteBuf 实现，ReplayingDecoderByteBuf，包装传入的 ByteBuf 实现了这一点，其将在内部执行对 readableBytes() 方法的调用。

使用 ReplayingDecoder 时需要注意：
* 并不是所有的 ByteBuf 操作都被支持，如果调用了一个不被支持的方法，将会抛出一个 UnsupportedOperationexception；
* ReplayingDecoder 稍慢于 ByteToMessageDecoder，如果使用 ByteToMessageDecoder 不会引入太多的复杂性，那么请使用它，否则，请使用 ReplayingDecoder。

### 10.2.4 TooLongFrameException 类

由于 Netty 是一个异步框架，所以需要在字节可以解码前在内存中缓存它们。因此，不能让解码器缓冲大量的数据以至于耗尽可用的内存。为了解除这个常见的顾虑，Netty 提供了 TooLongFrameException 类，其将由解码器在帧超出指定的大小限制时抛出。为了避免这种情况，可以设置一个最大字节数的阈值。如果超出该阈值，则会抛出一个 TooLongFrameException，随后会被 ChannelHandler.exceptionCaught() 方法捕获。然后，如何处理该异常则完全取决于用户。某些协议（如 HTTP）可能允许返回一个特殊的响应，而在其他情况下，唯一的选择可能就是关闭相应的连接。

## 10.3 编码器

编码器实现了 ChannelOutboundHandler，负责将出站数据从一种格式转换为另一种格式。




