package nia.chapter08;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;

import java.net.InetSocketAddress;

/**
 * Author: 王俊超
 * Date: 2017-10-15 22:53
 * Blog: http://blog.csdn.net/derrantcm
 * Github: https://github.com/wang-jun-chao
 * All Rights Reserved !!!
 */
public class GracefulShutdown {
    public static void main(String args[]) {
        GracefulShutdown client = new GracefulShutdown();
        client.bootstrap();
    }

    /**
     * Listing 8.9 Graceful shutdown
     */
    public void bootstrap() {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                //...
                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    protected void channelRead0(
                            ChannelHandlerContext channelHandlerContext,
                            ByteBuf byteBuf) throws Exception {
                        System.out.println("Received data");
                    }
                });
        bootstrap.connect(new InetSocketAddress("www.manning.com", 80)).syncUninterruptibly();
        //,,,
        Future<?> future = group.shutdownGracefully();
        // block until the group has shutdown
        future.syncUninterruptibly();
    }
}
