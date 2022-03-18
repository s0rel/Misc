package org.example;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 代码清单 10-9 CharToByteEncoder 类
 */
public class CharToByteEncoder extends MessageToByteEncoder<Character> {
    @Override
    public void encode(ChannelHandlerContext ctx, Character msg,
                       ByteBuf out) throws Exception {
        out.writeChar(msg);
    }
}

