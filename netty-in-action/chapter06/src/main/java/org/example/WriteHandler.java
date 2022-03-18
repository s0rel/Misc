package org.example;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * 代码清单 6-9 缓存到 ChannelHandlerContext 的引用
 */
public class WriteHandler extends ChannelHandlerAdapter {
    private ChannelHandlerContext ctx;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public void send(String msg) {
        ctx.writeAndFlush(msg);
    }
}
