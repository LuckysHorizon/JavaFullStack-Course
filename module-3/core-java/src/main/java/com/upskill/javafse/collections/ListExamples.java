package com.upskill.javafse.collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Demonstrates core operations on ArrayList and LinkedList,
 * including iteration patterns and conversions between list types.
 */
public class ListExamples {

    public static void main(String[] args) {
        arrayListBasics();
        linkedListOperations();
        listConversions();
    }

    static void arrayListBasics() {
        System.out.println("=== ArrayList Basics ===");

        var fruits = new ArrayList<String>();
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Cherry");
        fruits.add("Date");
        fruits.add("Elderberry");

        System.out.printf("List: %s%n", fruits);
        System.out.printf("Element at index 2: %s%n", fruits.get(2));
        System.out.printf("Contains 'Banana': %b%n", fruits.contains("Banana"));
        System.out.printf("Size: %d%n", fruits.size());

        fruits.remove("Date");
        fruits.remove(0); // removes "Apple"
        System.out.printf("After removals: %s%n", fruits);

        // for-each iteration
        System.out.print("For-each: ");
        for (var fruit : fruits) {
            System.out.printf("[%s] ", fruit);
        }
        System.out.println();

        // iterator with safe removal
        var numbers = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8));
        Iterator<Integer> it = numbers.iterator();
        while (it.hasNext()) {
            if (it.next() % 2 == 0) {
                it.remove(); // safe removal during iteration
            }
        }
        System.out.printf("Odd numbers only: %s%n%n", numbers);
    }

    static void linkedListOperations() {
        System.out.println("=== LinkedList Operations ===");

        var tasks = new LinkedList<String>();
        tasks.add("Write code");
        tasks.add("Test code");

        tasks.addFirst("Plan sprint");
        tasks.addLast("Deploy to prod");

        System.out.printf("Tasks: %s%n", tasks);
        System.out.printf("First task (peek): %s%n", tasks.peekFirst());
        System.out.printf("Last task (peek): %s%n", tasks.peekLast());

        // use as a queue (FIFO)
        var queue = new LinkedList<String>();
        queue.offer("Request A");
        queue.offer("Request B");
        queue.offer("Request C");

        System.out.printf("Processing: %s%n", queue.poll());
        System.out.printf("Processing: %s%n", queue.poll());
        System.out.printf("Queue remaining: %s%n%n", queue);
    }

    static void listConversions() {
        System.out.println("=== List Conversions ===");

        var arrayList = new ArrayList<>(List.of("Red", "Green", "Blue"));
        var linkedList = new LinkedList<>(arrayList);
        System.out.printf("ArrayList -> LinkedList: %s%n", linkedList);

        var backToArrayList = new ArrayList<>(linkedList);
        System.out.printf("LinkedList -> ArrayList: %s%n", backToArrayList);

        // unmodifiable list from factory method
        var immutable = List.of("X", "Y", "Z");
        System.out.printf("Immutable list: %s%n", immutable);

        // copy into a mutable list
        var mutable = new ArrayList<>(immutable);
        mutable.add("W");
        System.out.printf("Mutable copy after add: %s%n", mutable);

        /*
         * Performance note:
         * - ArrayList: O(1) random access (get/set), O(n) insert/remove in middle.
         *   Best for read-heavy workloads with index-based access.
         * - LinkedList: O(1) insert/remove at head/tail, O(n) random access.
         *   Best for queue/deque patterns with frequent add/remove at ends.
         * In practice, ArrayList is almost always faster due to CPU cache locality.
         */
    }
}
