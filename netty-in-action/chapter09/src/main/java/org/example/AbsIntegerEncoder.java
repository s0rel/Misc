package org.example;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * 代码清单 9-3 AbsIntegerEncoder
 */
public class AbsIntegerEncoder extends MessageToMessageEncoder<ByteBuf> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext,
                          ByteBuf in, List<Object> out) throws Exception {
        while (in.readableBytes() >= 4) {
            int value = Math.abs(in.readInt());
            out.add(value);
        }
    }
}
