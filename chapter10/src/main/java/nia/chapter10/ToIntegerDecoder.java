package nia.chapter10;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Author: 王俊超
 * Date: 2017-10-16 08:15
 * Blog: http://blog.csdn.net/derrantcm
 * Github: https://github.com/wang-jun-chao
 * All Rights Reserved !!!
 */
// 扩展ByteToMessageDecoder 类，以将字节解码为特定的格式
public class ToIntegerDecoder extends ByteToMessageDecoder {
    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 检查是否至少有4字节可读（一个int的字节长度）
        if (in.readableBytes() >= 4) {
            // 从入站ByteBuf 中读取一个int，并将其添加到解码消息的List 中
            out.add(in.readInt());
        }
    }
}
