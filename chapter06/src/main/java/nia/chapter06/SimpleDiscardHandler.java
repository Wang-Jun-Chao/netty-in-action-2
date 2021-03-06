package nia.chapter06;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Author: 王俊超
 * Date: 2017-10-15 22:54
 * Blog: http://blog.csdn.net/derrantcm
 * Github: https://github.com/wang-jun-chao
 * All Rights Reserved !!!
 */
// 扩展了SimpleChannelInboundHandler
@Sharable
public class SimpleDiscardHandler extends SimpleChannelInboundHandler<Object> {
    @Override
    public void channelRead0(ChannelHandlerContext ctx,
            Object msg) {
        // 不需要任何显式的资源释放
        // No need to do anything special
    }
}
