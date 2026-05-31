package com.upskill.javafse.modern;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class VirtualThreadDemo {

    public static void main(String[] args) throws Exception {
        System.out.println("=== Virtual Threads (Java 21) ===\n");

        basicVirtualThread();
        executorServiceDemo();
        performanceComparison();
    }

    // Basic virtual thread creation with Thread.ofVirtual()
    static void basicVirtualThread() throws InterruptedException {
        System.out.println("--- Basic Virtual Thread ---\n");

        var vThread = Thread.ofVirtual()
                .name("my-virtual-thread")
                .start(() -> {
                    var t = Thread.currentThread();
                    System.out.printf("  Thread name:  %s%n", t.getName());
                    System.out.printf("  Is virtual:   %b%n", t.isVirtual());
                    System.out.printf("  Thread ID:    %d%n", t.threadId());

                    try {
                        Thread.sleep(100);  // simulated I/O
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                    System.out.printf("  Task complete on: %s%n", t.getName());
                });

        // Also create a platform thread for comparison
        var pThread = Thread.ofPlatform()
                .name("my-platform-thread")
                .start(() -> {
                    var t = Thread.currentThread();
                    System.out.printf("  Thread name:  %s%n", t.getName());
                    System.out.printf("  Is virtual:   %b%n", t.isVirtual());
                });

        vThread.join();
        pThread.join();
        System.out.println();
    }

    // Using Executors.newVirtualThreadPerTaskExecutor() for concurrent tasks
    static void executorServiceDemo() throws InterruptedException {
        System.out.println("--- Virtual Thread Executor Service ---\n");

        var taskCount = 20;
        var completedCount = new AtomicInteger(0);

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 1; i <= taskCount; i++) {
                var taskId = i;
                executor.submit(() -> {
                    var thread = Thread.currentThread();
                    try {
                        // Simulate varying I/O latency
                        Thread.sleep(50 + (long) (Math.random() * 100));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    completedCount.incrementAndGet();
                    if (taskId <= 5) {
                        System.out.printf("  Task %2d completed on %-30s (virtual: %b)%n",
                                taskId, thread.getName(), thread.isVirtual());
                    }
                });
            }
        } // executor.close() waits for all tasks to finish

        System.out.printf("  ... (showing first 5 of %d tasks)%n", taskCount);
        System.out.printf("  Total tasks completed: %d%n%n", completedCount.get());
    }

    // Performance comparison: virtual threads vs platform threads
    static void performanceComparison() throws InterruptedException {
        System.out.println("--- Performance Comparison ---\n");

        var threadCount = 10_000;
        var sleepMillis = 50;  // simulate brief I/O per task

        System.out.printf("  Creating %,d threads, each sleeping %d ms...%n%n", threadCount, sleepMillis);

        // --- Virtual Threads ---
        var virtualStart = Instant.now();
        var virtualThreads = new Thread[threadCount];

        for (int i = 0; i < threadCount; i++) {
            virtualThreads[i] = Thread.ofVirtual().start(() -> {
                try {
                    Thread.sleep(sleepMillis);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        for (var t : virtualThreads) {
            t.join();
        }
        var virtualDuration = Duration.between(virtualStart, Instant.now());

        System.out.printf("  Virtual threads:  %,d threads completed in %,d ms%n",
                threadCount, virtualDuration.toMillis());

        // --- Platform Threads (smaller count to avoid OS limits) ---
        var platformCount = Math.min(threadCount, 2_000);
        var platformStart = Instant.now();
        var platformThreads = new Thread[platformCount];

        for (int i = 0; i < platformCount; i++) {
            platformThreads[i] = Thread.ofPlatform().start(() -> {
                try {
                    Thread.sleep(sleepMillis);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        for (var t : platformThreads) {
            t.join();
        }
        var platformDuration = Duration.between(platformStart, Instant.now());

        System.out.printf("  Platform threads: %,d threads completed in %,d ms%n%n",
                platformCount, platformDuration.toMillis());

        // Summary
        System.out.println("  Summary:");
        System.out.printf("    Virtual  (%,6d threads): %,d ms%n", threadCount, virtualDuration.toMillis());
        System.out.printf("    Platform (%,6d threads): %,d ms%n", platformCount, platformDuration.toMillis());

        if (platformCount < threadCount) {
            System.out.printf("%n  Note: Platform thread count was capped at %,d to avoid OS resource limits.%n",
                    platformCount);
            System.out.println("  Virtual threads can easily scale to 10,000+ concurrent tasks!");
        }

        // Print thread type info
        System.out.println("\n  Thread type info:");
        var vt = Thread.ofVirtual().unstarted(() -> {});
        var pt = Thread.ofPlatform().unstarted(() -> {});
        System.out.printf("    Virtual thread  -> isVirtual(): %b, class: %s%n",
                vt.isVirtual(), vt.getClass().getSimpleName());
        System.out.printf("    Platform thread -> isVirtual(): %b, class: %s%n",
                pt.isVirtual(), pt.getClass().getSimpleName());
    }
}
