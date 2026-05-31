package com.upskill.javafse.threading;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceDemo {

    public static void main(String[] args) throws InterruptedException {
        submitCallableTasks();
        System.out.println();
        invokeAllExample();
    }

    private static void submitCallableTasks() throws InterruptedException {
        System.out.println("=== Submit Callable Tasks (FixedThreadPool of 3) ===");
        var executor = Executors.newFixedThreadPool(3);

        var future1 = executor.submit(factorialTask(5));
        var future2 = executor.submit(factorialTask(7));
        var future3 = executor.submit(factorialTask(10));
        var future4 = executor.submit(factorialTask(12));

        for (var future : List.of(future1, future2, future3, future4)) {
            try {
                System.out.println("Result: " + future.get());
            } catch (ExecutionException e) {
                System.out.println("Task failed: " + e.getCause().getMessage());
            }
        }

        executor.shutdown();
        var terminated = executor.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("Executor terminated cleanly: " + terminated);
    }

    private static void invokeAllExample() throws InterruptedException {
        System.out.println("=== invokeAll Example ===");
        var executor = Executors.newFixedThreadPool(3);

        var tasks = List.of(
                factorialTask(3),
                factorialTask(6),
                factorialTask(8),
                factorialTask(15)
        );

        List<Future<String>> results = executor.invokeAll(tasks);

        for (var result : results) {
            try {
                System.out.println(result.get());
            } catch (ExecutionException e) {
                System.out.println("Task failed: " + e.getCause().getMessage());
            }
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("All invokeAll tasks completed.");
    }

    private static Callable<String> factorialTask(int n) {
        return () -> {
            var threadName = Thread.currentThread().getName();
            var result = factorial(n);
            return threadName + " => " + n + "! = " + result;
        };
    }

    private static long factorial(int n) {
        var result = 1L;
        for (var i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}
