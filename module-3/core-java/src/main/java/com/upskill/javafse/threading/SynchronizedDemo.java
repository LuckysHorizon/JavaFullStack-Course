package com.upskill.javafse.threading;

public class SynchronizedDemo {

    static class SharedCounter {
        private int count = 0;

        void increment() {
            count++;
        }

        void decrement() {
            count--;
        }

        synchronized void syncIncrement() {
            count++;
        }

        synchronized void syncDecrement() {
            count--;
        }

        int getCount() {
            return count;
        }

        void reset() {
            count = 0;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        var counter = new SharedCounter();

        System.out.println("=== Without Synchronization (potential race condition) ===");
        runWithoutSync(counter);

        counter.reset();

        System.out.println("\n=== With Synchronization (correct result) ===");
        runWithSync(counter);
    }

    private static void runWithoutSync(SharedCounter counter) throws InterruptedException {
        var threads = new Thread[10];

        for (var i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (var j = 0; j < 10_000; j++) {
                    counter.increment();
                }
            });
        }

        for (var t : threads) t.start();
        for (var t : threads) t.join();

        System.out.println("Expected: " + (10 * 10_000));
        System.out.println("Actual:   " + counter.getCount());
        System.out.println("Match: " + (counter.getCount() == 10 * 10_000));
    }

    private static void runWithSync(SharedCounter counter) throws InterruptedException {
        var threads = new Thread[10];

        for (var i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (var j = 0; j < 10_000; j++) {
                    counter.syncIncrement();
                }
            });
        }

        for (var t : threads) t.start();
        for (var t : threads) t.join();

        System.out.println("Expected: " + (10 * 10_000));
        System.out.println("Actual:   " + counter.getCount());
        System.out.println("Match: " + (counter.getCount() == 10 * 10_000));
    }
}
