package io.netty.channel;

/**
 * Author: 王俊超
 * Date: 2017-10-15 22:52
 * Blog: http://blog.csdn.net/derrantcm
 * Github: https://github.com/wang-jun-chao
 * All Rights Reserved !!!
 */
public class DummyChannelPipeline extends DefaultChannelPipeline {
    public static final ChannelPipeline DUMMY_INSTANCE = new DummyChannelPipeline(null);
    public DummyChannelPipeline(Channel channel) {
        super(channel);
    }
}
