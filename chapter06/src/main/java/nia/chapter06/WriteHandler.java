package nia.chapter06;


import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Author: 王俊超
 * Date: 2017-10-15 22:54
 * Blog: http://blog.csdn.net/derrantcm
 * Github: https://github.com/wang-jun-chao
 * All Rights Reserved !!!
 */
public class WriteHandler extends ChannelHandlerAdapter {
    private ChannelHandlerContext ctx;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        // 存储到 ChannelHandlerContext的引用以供稍后使用
        this.ctx = ctx;
    }

    public void send(String msg) {
        // 使用之前存储的到ChannelHandlerContext的引用来发送消息
        ctx.writeAndFlush(msg);
    }
}
