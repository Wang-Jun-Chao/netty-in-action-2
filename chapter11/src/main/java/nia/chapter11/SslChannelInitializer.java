package nia.chapter11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/**
 * Author: 王俊超
 * Date: 2017-10-16 08:11
 * Blog: http://blog.csdn.net/derrantcm
 * Github: https://github.com/wang-jun-chao
 * All Rights Reserved !!!
 */
public class SslChannelInitializer extends ChannelInitializer<Channel> {
    private final SslContext context;
    private final boolean startTls;

    // 传入要使用的SslContext
    // 如果设置为true，第一个写入的消息将不会被加密（客户端应该设置为true）
    public SslChannelInitializer(SslContext context, boolean startTls) {
        this.context = context;
        this.startTls = startTls;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        // 对于每个SslHandler 实例，都使用Channel 的ByteBufAllocator
        // 从SslContext获取一个新的SSLEngine
        SSLEngine engine = context.newEngine(ch.alloc());
        // 将SslHandler 作为第一个ChannelHandler 添加到ChannelPipeline 中
        ch.pipeline().addFirst("ssl", new SslHandler(engine, startTls));
    }
}
