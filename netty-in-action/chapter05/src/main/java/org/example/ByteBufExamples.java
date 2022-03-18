package org.example;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class ByteBufExamples {
    private static final Random random = new Random();
    private static final ByteBuf BYTE_BUF_FROM_SOMEWHERE = Unpooled.buffer(1024);
    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();

    /**
     * 代码清单 5-1 支撑数组
     */
    public static void heapBuffer() {
        ByteBuf heapBuf = BYTE_BUF_FROM_SOMEWHERE;
        // 检查 ByteBuf 是否有支撑数组
        if (heapBuf.hasArray()) {
            // 如果有，则获取对该数组的引用
            byte[] array = heapBuf.array();
            // 计算第一个字节的偏移量
            int offset = heapBuf.arrayOffset() + heapBuf.readerIndex();
            // 获得可读字节数
            int length = heapBuf.readableBytes();
            handleArray(array, offset, length);
        }
    }

    /**
     * 代码清单 5-2 访问直接缓冲区的数据
     */
    public static void directBuffer() {
        ByteBuf directBuf = BYTE_BUF_FROM_SOMEWHERE;
        // 检查 ByteBuf 是否有支撑数组，如果没有，则这是一个直接缓冲区
        if (!directBuf.hasArray()) {
            int length = directBuf.readableBytes();
            byte[] array = new byte[length];
            // 将字节复制到该数组
            directBuf.getBytes(directBuf.readerIndex(), array);
            handleArray(array, 0, length);
        }
    }

    /**
     * 代码清单 5-3 使用 ByteBuffer 的复合缓冲区模式
     */
    public static void byteBufferComposite(ByteBuffer header, ByteBuffer body) {
        ByteBuffer[] message = new ByteBuffer[]{header, body};
        ByteBuffer message2 = ByteBuffer.allocate(header.remaining() + body.remaining());
        message2.put(header);
        message2.put(body);
        message2.flip();
    }


    /**
     * 代码清单 5-4 使用 CompositeByteBuf 的复合缓冲区模式
     */
    public static void byteBufComposite() {
        CompositeByteBuf messageBuf = Unpooled.compositeBuffer();
        // headerBuf 和 bodyBuf 既可以使用堆缓冲区，也可以使用直接缓冲区
        ByteBuf headerBuf = BYTE_BUF_FROM_SOMEWHERE;
        ByteBuf bodyBuf = BYTE_BUF_FROM_SOMEWHERE;
        // 将 ByteBuf 实例添加到 CompositeByteBuf 中
        messageBuf.addComponents(headerBuf, bodyBuf);
        messageBuf.removeComponent(0);
        for (ByteBuf buf : messageBuf) {
            System.out.println(buf.toString());
        }
    }

    /**
     * 代码清单 5-5 访问 CompositeByteBuf 中的数据
     */
    public static void byteBufCompositeArray() {
        CompositeByteBuf compBuf = Unpooled.compositeBuffer();
        int length = compBuf.readableBytes();
        byte[] array = new byte[length];
        compBuf.getBytes(compBuf.readerIndex(), array);
        handleArray(array, 0, array.length);
    }

    /**
     * 代码清单 5-6 访问数据
     */
    public static void byteBufRelativeAccess() {
        ByteBuf buffer = BYTE_BUF_FROM_SOMEWHERE;
        for (int i = 0; i < buffer.capacity(); i++) {
            byte b = buffer.getByte(i);
            System.out.println((char) b);
        }
    }

    /**
     * 代码清单 5-7 读取所有数据
     */
    public static void readAllData() {
        ByteBuf buffer = BYTE_BUF_FROM_SOMEWHERE;
        while (buffer.isReadable()) {
            System.out.println(buffer.readByte());
        }
    }

    /**
     * 代码清单 5-8 写数据
     */
    public static void write() {
        ByteBuf buffer = BYTE_BUF_FROM_SOMEWHERE;
        while (buffer.writableBytes() >= 4) {
            buffer.writeInt(random.nextInt());
        }
    }

    /**
     * 代码清单 5-10 对 ByteBuf 进行切片
     */
    public static void byteBufSlice() {
        Charset utf8 = StandardCharsets.UTF_8;
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        ByteBuf sliced = buf.slice(0, 15);
        System.out.println(sliced.toString(utf8));
        buf.setByte(0, (byte) 'J');
        assert buf.getByte(0) == sliced.getByte(0);
    }

    /**
     * 代码清单 5-11 复制一个 ByteBuf
     */
    public static void byteBufCopy() {
        Charset utf8 = StandardCharsets.UTF_8;
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        ByteBuf copy = buf.copy(0, 15);
        System.out.println(copy.toString(utf8));
        buf.setByte(0, (byte) 'J');
        assert buf.getByte(0) != copy.getByte(0);
    }

    /**
     * 代码清单 5-12 get() 和 set() 方法的用法
     */
    public static void byteBufSetGet() {
        Charset utf8 = StandardCharsets.UTF_8;
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        System.out.println((char) buf.getByte(0));
        int readerIndex = buf.readerIndex();
        int writerIndex = buf.writerIndex();
        buf.setByte(0, (byte) 'B');
        System.out.println((char) buf.getByte(0));
        assert readerIndex == buf.readerIndex();
        assert writerIndex == buf.writerIndex();
    }

    /**
     * 代码清单 5-13 ByteBuf 上的 read() 和 write() 方法
     */
    public static void byteBufWriteRead() {
        Charset utf8 = StandardCharsets.UTF_8;
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        System.out.println((char) buf.readByte());
        int readerIndex = buf.readerIndex();
        int writerIndex = buf.writerIndex();
        buf.writeByte((byte) '?');
        assert readerIndex == buf.readerIndex();
        assert writerIndex != buf.writerIndex();
    }

    private static void handleArray(byte[] array, int offset, int len) {

    }

    /**
     * 代码清单 5-14 获取一个到 ByteBufAllocator 的引用
     */
    public static void obtainingByteBufAllocatorReference() {
        Channel channel = CHANNEL_FROM_SOMEWHERE;
        ByteBufAllocator allocator = channel.alloc();
    }

    /**
     * 代码清单 5-15 引用计数
     */
    public static void referenceCounting() {
        Channel channel = CHANNEL_FROM_SOMEWHERE;
        ByteBufAllocator allocator = channel.alloc();
        ByteBuf buffer = allocator.directBuffer();
        assert buffer.refCnt() == 1;
    }

    /**
     * 代码清单 5-16 释放引用计数的对象
     */
    public static void releaseReferenceCountedObject() {
        ByteBuf buffer = BYTE_BUF_FROM_SOMEWHERE;
        boolean released = buffer.release();
    }
}
