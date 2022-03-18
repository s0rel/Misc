# 第 5 章 ByteBuf

## 5.1 ByteBuf 的 API

Netty 的数据处理 API 通过两个组件暴露 - ByteBuf 抽象类和 ByteBufHolder 接口。ByteBuf API 具有如下优点：
* 可以被用户自定义的缓冲区类型扩展；
* 通过内置的复合缓冲区类型实现了透明的零拷贝；
* 容量可以按需增长（类似于 JDK 的 StringBuilder）；
* 在读和写这两种模式之间切换不需要调用 ByteBuffer 的 flip() 方法；
* 读和写使用了不同的索引；
* 支持方法的链式调用；
* 支持引用计数；
* 支持池化。

## 5.2 ByteBuf 类 - Netty 的数据容器

### 5.2.1 它是如何工作的

ByteBuf 维护了两个不同的索引：一个用于读取，一个用于写入。当从 ByteBuf 读取时，它的 readIndex 将会被递增已经被读取的字节数。同样地，当写入 ByteBuf 时，它的 writeIndex 也会被递增。如果打算读取字节直到 readIndex 达到和 writeIndex 同样的值时会抛出 IndexOutOfBoundsException。

名称以 read 或者 write 开头的 ByteBuf 方法，将会推进其对应的索引，而名称以 set 或 get 开头的操作则不会。后面的这些方法将在作为一个参数传入的一个相对索引上执行操作。

可以指定 ByteBuf 的最大容量，默认限制是 Integer.MAX_VALUE。试图移动写索引超过这个这个值将会触发一个异常。

### 5.2.2 ByteBuf 的使用模式

#### 1. 堆缓冲区

最常用的 ByteBuf 模式是将数据存储在 JVM 的堆空间中。这种模式被称为支撑数组，它能在没有使用池化的情况下提供快速的分配和释放，非常适合于有遗留数据需要处理的情况。

#### 2. 直接缓冲区

直接缓冲区是另外一种 ByteBuf 模式。我们期望用于对象创建的内存分配永远都来自于堆中，但这并不是必须的 - NIO 在 JDK 1.4 中引入的 ByteBuffer 类允许 JVM 实现通过本地调用来分配内存。这主要是为了避免在每次调用本地 I/O 操作之前（或者之后）将缓冲区的内容复制到一个中间缓冲区（或者把内容从中间缓冲区复制到缓冲区）。

直接缓冲区对于网络数据传输是理想的选择，如果你的数据包含在一个在堆上分配的缓冲区中，那么在通过套接字发送它之前，JVM 将会在内部把你的缓冲区复制到一个直接缓冲区中。直接缓冲区的主要缺点是，相对于基于堆的缓冲区，它们的分配和释放都比较昂贵。

#### 3. 复合缓冲区

复合缓冲区为多个 ByteBuf 提供了一个聚合视图。在这里可以根据需要添加或删除 ByteBuf 实例。这是一个 JDK 的 ByteBuffer 实现完全缺失的特性。Netty 通过一个 ByteBuf 子类 - CompositeByteBuf 实现这一模式，它提供了一个将多个缓冲区表示为单个合并缓冲区的虚拟表示。

CompositeByteBuf 中的 ByteBuf 实例可能同时包含直接内存分配和非直接内存分配。如果其中只有一个实例，那么对 CompositeByteBuf 上的 hasArray() 方法的调用将返回该组件上的 hasArray() 方法的值，否则它将返回 false。

## 5.3 字节级操作

### 5.3.1 随机访问索引

如同在普通的 Java 字节数组中一样，ByteBuf 的索引是从零开始的；第一个字节的索引是 0，最后一个字节的索引总是 capacity() - 1。需要注意的是，使用那些需要一个索引值参数的方法（的其中）之一来访问数据既不会改变 readIndex 也不会改变 writeIndex。如果有需要，也可以通过 readIndex(index) 或者 writeIndex(index) 来手动移动这两者。

### 5.3.2 顺序访问索引

图 5-3 展示了 ByteBuf 是如何被它的两个索引划分成 3 个区域的。

![图 5-3 ByteBuf 的内部分段](pic/pic_5-3.svg)
<div style="text-align: center;">图 5-3 ByteBuf 的内部分段</div>

### 5.3.3 可丢弃字节

图 5-3 中标记为可丢弃字节的分段包含了已经被读过的字节，这个分段的初始大小为 0，存储在 readIndex 中，会随着 read 操作的执行而增加（get* 操作不会移动 readIndex）。

通过调用 discardReadBytes() 方法，可以丢弃它们并回收空间。注意，在调用 discardReadBytes() 方法之后，对于可写分段的内容并没有任何保证，因为只是移动了可以读取的字节以及 writeIndex，而没有对所有可写入的字节进行擦除写。虽然可能会倾向于频繁调用 discardReadBytes() 方法以确保可写分段的最大化，但请注意，这将极有可能导致内存复制。因为可读字节必须被移动到缓冲区的开始位置。建议只在真正需要的时候才这样做，例如，当内存非常宝贵时。

### 5.3.4 可读字节

ByteBuf 的可读字节分段存储了实际数据。新分配的、包装的或者复制的缓冲区的默认 readIndex 值为 0。任何名称以 read 或者 skip 开头的操作都将检索或者跳过位于当前 readIndex 的数据，并将它增加已读字节数。

如果被调用的方法需要一个 ByteBuf 参数作为写入目标，并且没有指定目标索引参数，那么该目标缓冲区的 writeIndex 也将被增加。

### 5.3.5 可写字节

可写字节分段是指一个拥有未定义内容的、写入就绪的内存区域。新分配的缓冲区的 writeIndex 的默认值为 0.。任何名称以 write 开头的操作都将从当前的 writeIndex 处开始写数据，并将它增加已写入字节数。如果写操作的目标也是 ByteBuf，并且没有指定源索引的值，则源缓冲区的 readIndex 也同样会被增加相同的大小。

### 5.3.6 索引管理

JDK 的 InputStream 定义了 mark(int readlimit) 和 reset() 方法，这些方法分别用来将流中的当前位置标记为指定的值，以及将流重置到该位置。同样，可以调用 markReaderIndex()、markWriterIndex()、resetReaderIndex() 和 resetWriterIndex() 来标记和重置 ByteBuf 的 readIndex 和 writeIndex。

### 5.3.8 派生缓冲区

派生缓冲区为 ByteBuf 提供了以专门的方式来呈现其内容的视图。这类视图是通过以下方法被创建的：
* duplicate()；
* slice()；
* slice(int, int)；
* Unpooled.unmodifiableBuffer(...)；
* order(ByteOrder)；
* readSSlice(int)。

以上每个方法都将返回一个新的 ByteBuf 实例，它具有自己的读索引、写索引和标记索引，其内部存储和 JDK 的 ByteBuffer 一样是共享的。这使得派生缓冲区的创建成本很低廉，但也意味着修改了它的内容，也同时修改了其对应的源实例。如果需要使用一个现有的缓冲区的真实副本，请使用 copy() 或者 copy(int, int) 方法，不同于派生缓冲区，由这个调用所返回的 ByteBuf 拥有独立的数据副本。

## 5.5 ByteBuf 分配

### 5.5.1 按需分配：ByteBufAllocator 接口

为了降低分配和释放内存的开销，Netty 通过 ByteBufAllocator 接口实现了 ByteBuf 的池化，它可以用来分配我们所描述过的任意类型的 ByteBuf 实例。使用池化是特定于应用程序的决定，其并不会以任何方式改变 ByteBuf API 的语义。可以通过 Channel（每个都可以有一个不同的 ByteBufAllocator 实例）或者绑定到 ChannelHandler 的 ChannelHandlerContext 获取一个到 ByteBufAllocator 的引用。

Netty 提供了两种 ByteBufAllocator 的实现：PooledByteBufAllocator 和 UnpooledByteBufAllocator。前者池化了 ByteBuf 的实例以提高性能并最大限度地减少内存碎片。此实现使用了一种称为 jemalloc 的已被大量现代操作系统所采用的高效方法来分配内存。后者的实现不池化 ByteBuf 实例，并且在每次它被调用时都会返回一个新的实例。

### 5.5.2 Unpooled 缓冲区

可能某些情况下，未能获取到一个到 ByteBufAllocator 的引用。对于这种情况，Netty 提供了一个简单的称为 Unpooled 的工具类，它提供了静态的辅助方法来创建未池化的 ByteBuf 实例。

## 5.6 引用计数

引用计数是一种通过在某个对象所持有的资源不再被其他对象引用时释放该对象所持有的资源来优化内存使用和性能的技术。Netty 为 ByteBuf 和 ByteBufHolder 引入了引用计数技术。
