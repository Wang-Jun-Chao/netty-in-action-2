package nia.chapter10;

import io.netty.channel.CombinedChannelDuplexHandler;

/**
 * Author: 王俊超
 * Date: 2017-10-16 08:12
 * Blog: http://blog.csdn.net/derrantcm
 * Github: https://github.com/wang-jun-chao
 * All Rights Reserved !!!
 */
// 通过该解码器和编码器实现参数化CombinedByteCharCodec
public class CombinedByteCharCodec extends CombinedChannelDuplexHandler<ByteToCharDecoder, CharToByteEncoder> {
    public CombinedByteCharCodec() {
        // 将委托实例传递给父类
        super(new ByteToCharDecoder(), new CharToByteEncoder());
    }
}