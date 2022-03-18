package org.example;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * 代码清单 10-2 ToIntegerDecoder2 类扩展了 ReplayingDecoder 类
 */
public class ToIntegerDecoder2 extends ReplayingDecoder<Void> {
    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in,
                       List<Object> out) throws Exception {
        // 传入的 ByteBuf 是 ReplayingDecoderByteBuf
        out.add(in.readInt());
    }
}

