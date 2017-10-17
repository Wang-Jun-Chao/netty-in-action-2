package nia.chapter01;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Author: 王俊超
 * Date: 2017-10-12 23:20
 * Blog: http://blog.csdn.net/derrantcm
 * Github: https://github.com/wang-jun-chao
 * All Rights Reserved !!!
 */
public class ConnectExample {
    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioServerSocketChannel();

    public static void connect() {
        Channel channel = CHANNEL_FROM_SOMEWHERE; // 指向某个位置

        // 不会阻塞，异步地连接到远程节点
        ChannelFuture future = channel.connect(new InetSocketAddress("192.168.0.1", 25));
        // 注册一个ChannelFutureListener，以便在操作完成时获得通知
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                // 检查操作的状态
                if (future.isSuccess()) {
                    // 如果操作是成功的，则创建一个ByteBuf 以持有数据
                    ByteBuf buffer = Unpooled.copiedBuffer("Hello", Charset.forName("UTF-8"));
                    // 将数据异步地发送到远程节点。返回一个ChannelFuture
                    ChannelFuture wf = future.channel().write(buffer);
                    // ...
                } else {
                    // 如果发生错误，则访问描述原因的Throwable
                    Throwable cause = future.cause();
                    cause.printStackTrace();
                }
            }
        });
    }
}
