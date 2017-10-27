package nia.chapter11;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * Author: 王俊超
 * Date: 2017-10-16 08:11
 * Blog: http://blog.csdn.net/derrantcm
 * Github: https://github.com/wang-jun-chao
 * All Rights Reserved !!!
 */
public class IdleStateHandlerInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(
                // IdleStateHandler 将在被触发时发送一个IdleStateEvent 事件
                new IdleStateHandler(0, 0, 60, TimeUnit.SECONDS));
        // 将一个HeartbeatHandler添加到ChannelPipeline中
        pipeline.addLast(new HeartbeatHandler());
    }

    // 实现userEventTriggered()方法以发送心跳消息
    public static final class HeartbeatHandler
            extends ChannelInboundHandlerAdapter {
        // 发送到远程节点的心跳消息
        private static final ByteBuf HEARTBEAT_SEQUENCE =
                Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("HEARTBEAT", CharsetUtil.ISO_8859_1));

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx,
                Object evt) throws Exception {
            // 发送心跳消息，并在发送失败时关闭该连接
            if (evt instanceof IdleStateEvent) {
                ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate())
                        .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            } else {
                // 不是IdleStateEvent事件，所以将它传递给下一个ChannelInboundHandler
                super.userEventTriggered(ctx, evt);
            }
        }
    }
}

