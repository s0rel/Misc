package org.example;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 代码清单 1-2 被回调触发的 ConnectHandler
 */
public class ConnectHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext context) {
        System.out.println("Client " + context.channel().remoteAddress() + " connected");
    }
}
