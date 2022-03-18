package org.example;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;

/**
 * 代码清单 6-4 丢弃并释放出站消息
 */
@Sharable
public class DiscardOutboundHandler extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
        // 释放资源
        ReferenceCountUtil.release(msg);
        // 通知 ChannelPromise 数据已经被处理了
        promise.setSuccess();
    }
}

