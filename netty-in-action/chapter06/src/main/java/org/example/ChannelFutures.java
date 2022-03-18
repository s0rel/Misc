package org.example;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 代码清单 6-13 添加 ChannelFutureListener 到 ChannelFuture
 */
public class ChannelFutures {
    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();
    private static final ByteBuf SOME_MSG_FROM_SOMEWHERE = Unpooled.buffer(1024);

    public static void addingChannelFutureListener() {
        Channel channel = CHANNEL_FROM_SOMEWHERE;
        ByteBuf someMessage = SOME_MSG_FROM_SOMEWHERE;

        ChannelFuture future = channel.write(someMessage);
        future.addListener((ChannelFutureListener) f -> {
            if (!f.isSuccess()) {
                f.cause().printStackTrace();
                f.channel().close();
            }
        });
    }
}
