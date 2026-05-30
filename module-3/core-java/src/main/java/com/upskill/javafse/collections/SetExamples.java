package com.upskill.javafse.collections;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.TreeSet;

/**
 * Demonstrates the three main Set implementations: HashSet, TreeSet, and LinkedHashSet.
 * Shows duplicate handling, ordering guarantees, and practical deduplication.
 */
public class SetExamples {

    public static void main(String[] args) {
        hashSetDemo();
        treeSetDemo();
        linkedHashSetDemo();
        deduplicateList();
    }

    static void hashSetDemo() {
        System.out.println("=== HashSet (no ordering guarantee) ===");

        var colors = new HashSet<String>();
        colors.add("Red");
        colors.add("Blue");
        colors.add("Green");

        // duplicates are silently ignored
        boolean added = colors.add("Red");
        System.out.printf("Tried adding 'Red' again  added? %b%n", added);
        System.out.printf("Set contents: %s%n", colors);
        System.out.printf("Contains 'Blue': %b%n", colors.contains("Blue"));
        System.out.printf("Size: %d%n%n", colors.size());
    }

    static void treeSetDemo() {
        System.out.println("=== TreeSet (sorted) ===");

        // natural ordering (alphabetical for Strings)
        var names = new TreeSet<String>();
        names.add("Charlie");
        names.add("Alice");
        names.add("Bob");
        names.add("Diana");
        System.out.printf("Natural order: %s%n", names);
        System.out.printf("First: %s, Last: %s%n", names.first(), names.last());

        // custom comparator  sort by string length, then alphabetically
        var byLength = new TreeSet<String>(
                Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder())
        );
        byLength.addAll(List.of("Banana", "Fig", "Apple", "Kiwi", "Date"));
        System.out.printf("Sorted by length: %s%n", byLength);

        // range operations
        System.out.printf("Names before 'Charlie': %s%n", names.headSet("Charlie"));
        System.out.printf("Names from 'Bob' to 'Diana': %s%n%n", names.subSet("Bob", true, "Diana", true));
    }

    static void linkedHashSetDemo() {
        System.out.println("=== LinkedHashSet (insertion order) ===");

        var visitOrder = new LinkedHashSet<String>();
        visitOrder.add("Homepage");
        visitOrder.add("Products");
        visitOrder.add("About");
        visitOrder.add("Contact");
        visitOrder.add("Products"); // duplicate, ignored but order unchanged

        System.out.printf("Pages visited (in order): %s%n", visitOrder);

        // iteration preserves insertion order
        System.out.print("Iterating: ");
        for (var page : visitOrder) {
            System.out.printf("%s -> ", page);
        }
        System.out.println("done\n");
    }

    static void deduplicateList() {
        System.out.println("=== Deduplication using Set ===");

        var withDuplicates = List.of("Java", "Python", "Java", "Go", "Python", "Rust", "Go");
        System.out.printf("Original list: %s%n", withDuplicates);

        // LinkedHashSet preserves first-occurrence order
        var unique = new LinkedHashSet<>(withDuplicates);
        System.out.printf("Deduplicated (preserving order): %s%n", unique);

        // back to a list if needed
        var deduped = new ArrayList<>(unique);
        System.out.printf("As list: %s%n", deduped);
    }
}
