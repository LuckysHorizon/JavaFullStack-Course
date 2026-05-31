package com.upskill.javafse.threading;

public class ThreadLifecycle {

    public static void main(String[] args) throws InterruptedException {
        demonstrateStates();
        System.out.println();
        demonstrateInterrupt();
        System.out.println();
        demonstrateDaemonThread();
    }

    private static void demonstrateStates() throws InterruptedException {
        System.out.println("=== Thread State Transitions ===");

        var worker = new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Worker");

        System.out.println("After creation:   " + worker.getState());   // NEW

        worker.start();
        System.out.println("After start():    " + worker.getState());   // RUNNABLE

        Thread.sleep(100);
        System.out.println("During sleep():   " + worker.getState());   // TIMED_WAITING

        worker.join();
        System.out.println("After join():     " + worker.getState());   // TERMINATED
    }

    private static void demonstrateInterrupt() throws InterruptedException {
        System.out.println("=== Interrupt Handling ===");

        var sleeper = new Thread(() -> {
            try {
                System.out.println("Sleeper going to sleep for 5 seconds...");
                Thread.sleep(5000);
                System.out.println("Sleeper woke up naturally.");
            } catch (InterruptedException e) {
                System.out.println("Sleeper was interrupted! Cleaning up...");
                Thread.currentThread().interrupt();
            }
        }, "Sleeper");

        sleeper.start();
        Thread.sleep(500);

        System.out.println("Main thread interrupting sleeper...");
        sleeper.interrupt();

        sleeper.join();
        System.out.println("Sleeper thread finished. Interrupted flag: " + sleeper.isInterrupted());
    }

    private static void demonstrateDaemonThread() throws InterruptedException {
        System.out.println("=== Daemon Thread ===");

        var daemon = new Thread(() -> {
            var i = 0;
            while (true) {
                System.out.println("Daemon tick " + (++i));
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "DaemonThread");

        daemon.setDaemon(true);
        System.out.println("Is daemon: " + daemon.isDaemon());
        daemon.start();

        // Let the daemon run for a short time
        Thread.sleep(700);
        System.out.println("Main thread ending  daemon will be stopped by JVM automatically.");
    }
}
