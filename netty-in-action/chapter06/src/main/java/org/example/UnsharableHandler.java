package org.example;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 代码清单 6-11 @Sharable 的错误用法
 */
@Sharable
public class UnsharableHandler extends ChannelInboundHandlerAdapter {
    private int count;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        count++;
        System.out.println("inboundBufferUpdated(...) called the " + count + " time");
        ctx.fireChannelRead(msg);
    }
}

