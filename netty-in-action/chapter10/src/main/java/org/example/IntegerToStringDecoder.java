package org.example;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * 代码清单 10-3 IntegerToStringDecoder 类
 */
public class IntegerToStringDecoder extends MessageToMessageDecoder<Integer> {
    @Override
    public void decode(ChannelHandlerContext ctx, Integer msg,
                       List<Object> out) throws Exception {
        out.add(String.valueOf(msg));
    }
}

