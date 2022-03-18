package org.example;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * 代码清单 2-1 EchoServerHandler
 */
@Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    // 当被通知 Channel 是活跃的时候，发送一条消息
    @Override
    public void channelActive(ChannelHandlerContext context) {
        context.writeAndFlush(Unpooled.copiedBuffer("Netty rocks", CharsetUtil.UTF_8));
    }

    // 当从服务器接收到一条消息时被调用
    @Override
    public void channelRead0(ChannelHandlerContext context, ByteBuf in) throws Exception {
        System.out.println("Client received: " + in.toString(CharsetUtil.UTF_8));
    }

    // 在处理过程中引发异常抛出时被调用
    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
        cause.printStackTrace();
        context.close();
    }
}
