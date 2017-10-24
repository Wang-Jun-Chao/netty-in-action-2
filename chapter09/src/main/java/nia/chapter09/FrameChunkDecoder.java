package nia.chapter09;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

/**
 * Author: 王俊超
 * Date: 2017-10-16 08:09
 * Blog: http://blog.csdn.net/derrantcm
 * Github: https://github.com/wang-jun-chao
 * All Rights Reserved !!!
 */
// 扩展ByteToMessageDecoder 以将入站字节解码为消息
public class FrameChunkDecoder extends ByteToMessageDecoder {
    private final int maxFrameSize;

    // 指定将要产生的帧的最大允许大小
    public FrameChunkDecoder(int maxFrameSize) {
        this.maxFrameSize = maxFrameSize;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out)
            throws Exception {
        int readableBytes = in.readableBytes();
        // 如果该帧太大，则丢弃它并抛 出一个TooLongFrameException
        if (readableBytes > maxFrameSize) {
            // discard the bytes
            in.clear();
            throw new TooLongFrameException();
        }
        // 否则，从ByteBuf 中读取一个新的帧
        ByteBuf buf = in.readBytes(readableBytes);
        // 将该帧添加到解码消息的List 中
        out.add(buf);
    }
}
