package nia.chapter06;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;

/**
 * Author: 王俊超
 * Date: 2017-10-15 22:54
 * Blog: http://blog.csdn.net/derrantcm
 * Github: https://github.com/wang-jun-chao
 * All Rights Reserved !!!
 */
// 扩展了ChannelInboundHandlerAdapter
@Sharable
public class DiscardOutboundHandler
        extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
        // 通过调用ReferenceCountUtil.release() 方法释放资源
        ReferenceCountUtil.release(msg);
        // 通知ChannelPromise数据已经被处理了
        promise.setSuccess();
    }
}

