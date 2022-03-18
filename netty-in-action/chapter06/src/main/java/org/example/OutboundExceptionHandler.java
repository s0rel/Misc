package org.example;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * 代码清单 6-14 添加 ChannelFutureListener 到 ChannelPromise
 */
public class OutboundExceptionHandler extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
        promise.addListener((ChannelFutureListener) (future) -> {
            if (!future.isSuccess()) {
                future.cause().printStackTrace();
                future.channel().close();
            }
        });
    }
}
