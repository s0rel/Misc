package org.example;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * 代码清单 1-3 异步地建立连接
 * 代码清单 1-4 回调实战
 */
public class ConnectExample {
    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();

    public static void connect() {
        Channel channel = CHANNEL_FROM_SOMEWHERE;
        // 异步连接到远程节点
        ChannelFuture future = channel.connect(new InetSocketAddress("192.168.0.1", 25));
        // 注册一个 ChannelFutureListener 以便在操作完成时获得通知
        future.addListener((ChannelFutureListener) channelFuture -> {
            if (channelFuture.isSuccess()) {
                // 如果操作是成功的，则创建一个 ByteBuf 以持有数据
                ByteBuf buffer = Unpooled.copiedBuffer("Hello", Charset.defaultCharset());
                ChannelFuture writeFuture = channelFuture.channel().writeAndFlush(buffer);
                // ...
            } else {
                // 如果发生错误，打印异常堆栈
                Throwable cause = future.cause();
                cause.printStackTrace();
            }
        });
    }
}
