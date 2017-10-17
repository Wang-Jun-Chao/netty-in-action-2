package nia.chapter02.echoserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Author: 王俊超
 * Date: 2017-10-13 07:42
 * Blog: http://blog.csdn.net/derrantcm
 * Github: https://github.com/wang-jun-chao
 * All Rights Reserved !!!
 */
public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {

        if (args == null || args.length == 0) {
            args = new String[]{"8888"};
        }

        if (args.length != 1) {
            System.err.println("Usage: " + EchoServer.class.getSimpleName() + " <port>");
            return;
        }
        // 设置端口值（如果端口参数的格式不正确，则抛出一个NumberFormatException）
        int port = Integer.parseInt(args[0]);
        // 调用服务器的start()方法
        new EchoServer(port).start();
    }

    private void start() throws Exception {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        // 1、创建Event-LoopGroup
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 2、创建Server-Bootstrap
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class) // 3、指定所使用的NIO传输Channel
                    .localAddress(new InetSocketAddress(port)) // 4、使用指定的端口设置套接字地址
                    .childHandler(new ChannelInitializer<SocketChannel>() { // 5、添加一个EchoServer-Handler 到子Channel的ChannelPipeline
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // EchoServerHandler 被标注为@Shareable，所以我们可以总是使用同样的实例
                            // 这里对于所有的客户端连接来说，都会使用同一个EchoServerHandler，
                            // 因为其被标注为@Sharable，这将在后面的章节中讲到
                            ch.pipeline().addLast(serverHandler);
                        }
                    });
            // 6、异步地绑定服务器；调用sync()方法阻塞等待直到绑定完成
            ChannelFuture f = b.bind().sync();
            System.out.println(EchoServer.class.getName()
                    + " started and listening for connections on "
                    + f.channel().localAddress());
            // 7、获取Channel 的CloseFuture，并且阻塞当前线程直到它完成
            f.channel().closeFuture().sync();
        } finally {
            // 8、关闭EventLoopGroup，释放所有的资源
            group.shutdownGracefully().sync();
        }
    }

}
