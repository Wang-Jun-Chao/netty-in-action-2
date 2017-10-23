package nia.chapter07;

import io.netty.channel.Channel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Author: 王俊超
 * Date: 2017-10-15 22:53
 * Blog: http://blog.csdn.net/derrantcm
 * Github: https://github.com/wang-jun-chao
 * All Rights Reserved !!!
 */
public class ScheduleExamples {
    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();

    /**
     * Listing 7.2 Scheduling a task with a ScheduledExecutorService
     */
    public static void schedule() {
        // 创建一个其线程池具有10 个线程的ScheduledExecutorService
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);

        ScheduledFuture<?> future = executor.schedule(new Runnable() { // 创建一个 Runnable，以供调度稍后执行
            @Override
            public void run() {
                // 该任务要打印的消息
                System.out.println("Now it is 60 seconds later");
            }
        }, 60, TimeUnit.SECONDS); // 调度任务在从现在开始的60 秒之后执行
        //...
        // 一旦调度任务执行完成，就关闭ScheduledExecutorService 以释放资源
        executor.shutdown();
    }

    /**
     * Listing 7.3 Scheduling a task with EventLoop
     */
    public static void scheduleViaEventLoop() {
        Channel ch = CHANNEL_FROM_SOMEWHERE; // get reference from somewhere
        ScheduledFuture<?> future = ch.eventLoop().schedule(
                new Runnable() { // 创建一个 Runnable，以供调度稍后执行
                    @Override
                    public void run() {
                        // 要执行的代码
                        System.out.println("60 seconds later");
                    }
                }, 60, TimeUnit.SECONDS); // 调度任务在从现在开始的60 秒之后执行
    }

    /**
     * Listing 7.4 Scheduling a recurring task with EventLoop
     */
    public static void scheduleFixedViaEventLoop() {
        Channel ch = CHANNEL_FROM_SOMEWHERE; // get reference from somewhere
        // 创建一个 Runnable，以供调度稍后执行
        ScheduledFuture<?> future = ch.eventLoop().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                // 这将一直运行，直到ScheduledFuture 被取消
                System.out.println("Run every 60 seconds");
            }
        }, 60, 60, TimeUnit.SECONDS); // 调度在60 秒之后，并且以后每间隔60 秒运行
    }

    /**
     * Listing 7.5 Canceling a task using ScheduledFuture
     */
    public static void cancelingTaskUsingScheduledFuture() {
        Channel ch = CHANNEL_FROM_SOMEWHERE; // get reference from somewhere
        // 调度任务，并获得所返回的ScheduledFuture
        ScheduledFuture<?> future = ch.eventLoop().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("Run every 60 seconds");
            }
        }, 60, 60, TimeUnit.SECONDS);
        // Some other code that runs...
        boolean mayInterruptIfRunning = false;
        // 取消该任务，防止它再次运行
        future.cancel(mayInterruptIfRunning);
    }
}
