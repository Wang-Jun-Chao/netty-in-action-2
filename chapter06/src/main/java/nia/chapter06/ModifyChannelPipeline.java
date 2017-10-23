package nia.chapter06;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelPipeline;

import static io.netty.channel.DummyChannelPipeline.DUMMY_INSTANCE;

/**
 * Author: 王俊超
 * Date: 2017-10-15 22:54
 * Blog: http://blog.csdn.net/derrantcm
 * Github: https://github.com/wang-jun-chao
 * All Rights Reserved !!!
 */
public class ModifyChannelPipeline {
    private static final ChannelPipeline CHANNEL_PIPELINE_FROM_SOMEWHERE = DUMMY_INSTANCE;

    /**
     * Listing 6.5 Modify the ChannelPipeline
     */
    public static void modifyPipeline() {
        ChannelPipeline pipeline = CHANNEL_PIPELINE_FROM_SOMEWHERE; // get reference to pipeline;
        FirstHandler firstHandler = new FirstHandler();
        // 将该实例作为"handler1" 添加到ChannelPipeline 中
        pipeline.addLast("handler1", firstHandler);
        // 将一个SecondHandler的实例作为"handler2"添加到ChannelPipeline的第一个槽中。
        // 这意味着它将被放置在已有的"handler1"之前
        pipeline.addFirst("handler2", new SecondHandler());
        // 将一个ThirdHandler 的实例作为"handler3"添加到ChannelPipeline 的最后一个槽中
        pipeline.addLast("handler3", new ThirdHandler());
        //...
        // 通过名称移除"handler3"
        pipeline.remove("handler3");
        // 通过引 用移除FirstHandler（它是唯一的，所以不需要它的名称）
        pipeline.remove(firstHandler);
        // 将SecondHandler("handler2")替换为FourthHandler:"handler4"
        pipeline.replace("handler2", "handler4", new FourthHandler());

    }

    private static final class FirstHandler extends ChannelHandlerAdapter {
    }

    private static final class SecondHandler extends ChannelHandlerAdapter {

    }

    private static final class ThirdHandler extends ChannelHandlerAdapter {
    }

    private static final class FourthHandler extends ChannelHandlerAdapter {
    }
}
