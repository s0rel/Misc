package org.example;

import io.netty.channel.CombinedChannelDuplexHandler;

/**
 * 代码清单 10-10 CombinedChannelDuplexHandler<I,O>
 */

public class CombinedByteCharCodec extends
        CombinedChannelDuplexHandler<ByteToCharDecoder, CharToByteEncoder> {
    public CombinedByteCharCodec() {
        super(new ByteToCharDecoder(), new CharToByteEncoder());
    }
}
