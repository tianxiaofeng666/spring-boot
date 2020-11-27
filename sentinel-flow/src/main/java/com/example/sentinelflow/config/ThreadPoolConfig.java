package com.example.sentinelflow.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
@Slf4j
public class ThreadPoolConfig {
    private int corePoolSize = 10;

    @Bean(destroyMethod = "shutdown", name = "testThreadPool")
    public ThreadPoolExecutor testThreadPool() {
        return new ThreadPoolExecutor(corePoolSize, corePoolSize, 60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(200), new ThreadFactory() {
            private static final String THREAD_NAME_PATTERN = "%s-%d";
            private final AtomicInteger counter = new AtomicInteger();

            @Override
            public Thread newThread(Runnable r) {
                final String threadName = String.format(THREAD_NAME_PATTERN, "srvcloudwdRelease",
                        counter.incrementAndGet());
                return new Thread(r, threadName);
            }
        }, new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                try {
                    executor.getQueue().put(r);
                } catch (InterruptedException e) {
                    log.error("Work discarded, thread was interrupted " + "while waiting for space to schedule: "+r);
                }
            }
        });
    }
}
