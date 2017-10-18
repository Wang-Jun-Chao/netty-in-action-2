package nia.chapter04;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Author: 王俊超
 * Date: 2017-10-13 08:14
 * Blog: http://blog.csdn.net/derrantcm
 * Github: https://github.com/wang-jun-chao
 * All Rights Reserved !!!
 */
public class ChannelOperationExamples {
    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();

    /**
     * Listing 4.5 Writing to a Channel
     */
    public static void writingToChannel() {
        Channel channel = CHANNEL_FROM_SOMEWHERE; // Get the channel reference from somewhere
        // 创建持有要写数据的ByteBuf
        ByteBuf buf = Unpooled.copiedBuffer("your data", CharsetUtil.UTF_8);
        // 写数据并刷新
        ChannelFuture cf = channel.writeAndFlush(buf);
        // 添加ChannelFutureListener 以便在写操作完成后接收通知
        cf.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                // 写操作完成，并且没有错误发生
                if (future.isSuccess()) {
                    System.out.println("Write successful");
                } else {
                    // 记录错误
                    System.err.println("Write error");
                    future.cause().printStackTrace();
                }
            }
        });
    }

    /**
     * Listing 4.6 Using a Channel from many threads
     */
    public static void writingToChannelFromManyThreads() {
        final Channel channel = CHANNEL_FROM_SOMEWHERE; // Get the channel reference from somewhere
        final ByteBuf buf = Unpooled.copiedBuffer("your data", CharsetUtil.UTF_8);
        Runnable writer = new Runnable() {
            @Override
            public void run() {
                channel.write(buf.duplicate());
            }
        };
        Executor executor = Executors.newCachedThreadPool();

        // write in one thread
        executor.execute(writer);

        // write in another thread
        executor.execute(writer);
        //...
    }
}
