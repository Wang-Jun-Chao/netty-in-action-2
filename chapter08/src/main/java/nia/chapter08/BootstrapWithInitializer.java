package nia.chapter08;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;

import java.net.InetSocketAddress;

/**
 * Author: 王俊超
 * Date: 2017-10-15 22:53
 * Blog: http://blog.csdn.net/derrantcm
 * Github: https://github.com/wang-jun-chao
 * All Rights Reserved !!!
 */
public class BootstrapWithInitializer {

    /**
     * Listing 8.6 Bootstrapping and using ChannelInitializer
     * */
    public void bootstrap() throws InterruptedException {
        // 创建ServerBootstrap 以创建和绑定新的Channel
        ServerBootstrap bootstrap = new ServerBootstrap();
        // 设置EventLoopGroup，其将提供用以处理Channel事件的EventLoop
        bootstrap.group(
                new NioEventLoopGroup(),
                new NioEventLoopGroup()) // 指定Channel的实现
            .channel(NioServerSocketChannel.class)//
            .childHandler(new ChannelInitializerImpl()); // 注册一个ChannelInitializerImpl 的实例来设置ChannelPipeline
        ChannelFuture future = bootstrap.bind(new InetSocketAddress(8080)); //绑定到地址
        future.sync();
    }

    // 用以设置ChannelPipeline 的自定义ChannelInitializerImpl 实现
    // 在大部分的场景下，如果你不需要使用只存在于SocketChannel 上的方法，
    // 使用ChannelInitializer<Channel>就可以了，否则你可以使用
    // ChannelInitializer<SocketChannel>，其中SocketChannel扩展了Channel。
    final class ChannelInitializerImpl extends ChannelInitializer<Channel> {
        @Override
        protected void initChannel(Channel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
            pipeline.addLast(new HttpClientCodec());
            pipeline.addLast(new HttpObjectAggregator(Integer.MAX_VALUE));

        }
    }
}
