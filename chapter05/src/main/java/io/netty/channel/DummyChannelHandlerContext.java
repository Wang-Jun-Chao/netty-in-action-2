package io.netty.channel;

import io.netty.util.concurrent.EventExecutor;

/**
 * Author: 王俊超
 * Date: 2017-10-15 22:42
 * Blog: http://blog.csdn.net/derrantcm
 * Github: https://github.com/wang-jun-chao
 * All Rights Reserved !!!
 */
public class DummyChannelHandlerContext extends AbstractChannelHandlerContext {
    public static ChannelHandlerContext DUMMY_INSTANCE = new DummyChannelHandlerContext(
            null, null, null, true, true);

    public DummyChannelHandlerContext(DefaultChannelPipeline pipeline, EventExecutor executor,
            String name, boolean inbound, boolean outbound) {
        super(pipeline, executor, name, inbound, outbound);
    }

    @Override
    public ChannelHandler handler() {
        return null;
    }
}
