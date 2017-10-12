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

        // 不会阻塞
        ChannelFuture future = channel.connect(new InetSocketAddress("192.168.0.1", 25));
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    ByteBuf buffer = Unpooled.copiedBuffer("Hello", Charset.forName("UTF-8"));
                    ChannelFuture wf = future.channel().write(buffer);
                    // ...
                } else {
                    Throwable cause = future.cause();
                    cause.printStackTrace();
                }
            }
        });
    }
}
