package org.example;

import io.netty.channel.Channel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduleExamples {
    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();

    /**
     * 代码清单 7-2 使用 ScheduledExecutorService 调度任务
     */
    public static void schedule() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);

        ScheduledFuture<?> future = executor.schedule(
                () -> System.out.println("Now it is 60 seconds later"), 60, TimeUnit.SECONDS);
        executor.shutdown();
    }

    /**
     * 代码清单 7-3 使用 EventLoop 调度任务
     */
    public static void scheduleViaEventLoop() {
        Channel ch = CHANNEL_FROM_SOMEWHERE;
        ScheduledFuture<?> future = ch.eventLoop().schedule(
                () -> System.out.println("60 seconds later"), 60, TimeUnit.SECONDS);
    }

    /**
     * 代码清单 7-3 使用 EventLoop 调度周期性任务
     */
    public static void scheduleFixedViaEventLoop() {
        Channel ch = CHANNEL_FROM_SOMEWHERE;
        ScheduledFuture<?> future = ch.eventLoop().scheduleAtFixedRate(
                () -> System.out.println("Run every 60 seconds"), 60, 60, TimeUnit.SECONDS);
    }

    /**
     * 代码清单 7-5 使用 ScheduledFuture 取消任务
     */
    public static void cancelingTaskUsingScheduledFuture() {
        Channel ch = CHANNEL_FROM_SOMEWHERE;
        ScheduledFuture<?> future = ch.eventLoop().scheduleAtFixedRate(
                () -> System.out.println("Run every 60 seconds"), 60, 60, TimeUnit.SECONDS);
        boolean mayInterruptIfRunning = false;
        future.cancel(mayInterruptIfRunning);
    }
}
