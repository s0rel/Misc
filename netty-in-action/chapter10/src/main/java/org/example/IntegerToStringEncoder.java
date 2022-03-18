package org.example;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * 代码清单 10-6 IntegerToStringEncoder 类
 */
public class IntegerToStringEncoder extends MessageToMessageEncoder<Integer> {
    @Override
    public void encode(ChannelHandlerContext ctx, Integer msg,
                       List<Object> out) throws Exception {
        out.add(String.valueOf(msg));
    }
}

