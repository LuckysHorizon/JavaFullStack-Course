package com.upskill.javafse.threading;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumer {

    private static final String POISON_PILL = "DONE";

    public static void main(String[] args) throws InterruptedException {
        var queue = new ArrayBlockingQueue<String>(5);

        var producer = new Thread(() -> produce(queue), "Producer");
        var consumer = new Thread(() -> consume(queue), "Consumer");

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        System.out.println("Both producer and consumer have finished.");
    }

    private static void produce(BlockingQueue<String> queue) {
        var items = new String[]{"Apple", "Banana", "Cherry", "Date", "Elderberry"};

        try {
            for (var item : items) {
                System.out.println(Thread.currentThread().getName() + " producing: " + item);
                queue.put(item);
                Thread.sleep(200);
            }
            queue.put(POISON_PILL);
            System.out.println("Producer sent poison pill  shutting down.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Producer was interrupted.");
        }
    }

    private static void consume(BlockingQueue<String> queue) {
        try {
            while (true) {
                var item = queue.take();
                if (POISON_PILL.equals(item)) {
                    System.out.println("Consumer received poison pill  shutting down.");
                    break;
                }
                System.out.println(Thread.currentThread().getName() + " consumed: " + item);
                Thread.sleep(300);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Consumer was interrupted.");
        }
    }
}
