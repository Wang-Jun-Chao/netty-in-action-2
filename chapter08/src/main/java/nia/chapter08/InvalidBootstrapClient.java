package nia.chapter08;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.oio.OioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Author: 王俊超
 * Date: 2017-10-15 22:53
 * Blog: http://blog.csdn.net/derrantcm
 * Github: https://github.com/wang-jun-chao
 * All Rights Reserved !!!
 */
public class InvalidBootstrapClient {

    public static void main(String args[]) {
        InvalidBootstrapClient client = new InvalidBootstrapClient();
        client.bootstrap();
    }

    /**
     * Listing 8.3 Incompatible Channel and EventLoopGroup
     */
    public void bootstrap() {
        EventLoopGroup group = new NioEventLoopGroup();
        // 创建一个新的Bootstrap类的实例，以创建新的客户端Channel
        Bootstrap bootstrap = new Bootstrap();
        // 指定一个适用于NIO 的EventLoopGroup 实现
        bootstrap.group(group)
                // 指定一个适用于OIO 的Channel实现类
                .channel(OioSocketChannel.class)
                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf buf) throws Exception {
                        System.out.println("Received data");
                    }
                });
        // 尝试连接到远程节点
        ChannelFuture future = bootstrap.connect(
                new InetSocketAddress("www.manning.com", 80));
        future.syncUninterruptibly();
    }
}
