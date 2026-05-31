package com.upskill.javafse.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Demonstrates Map operations using HashMap and TreeMap.
 * Covers basic CRUD, sorted keys, modern convenience methods,
 * and a practical word-frequency counter.
 */
public class MapExamples {

    public static void main(String[] args) {
        hashMapBasics();
        treeMapDemo();
        modernMapMethods();
        wordFrequencyCounter();
    }

    static void hashMapBasics() {
        System.out.println("=== HashMap Basics ===");

        var capitals = new HashMap<String, String>();
        capitals.put("India", "New Delhi");
        capitals.put("Japan", "Tokyo");
        capitals.put("France", "Paris");
        capitals.put("Brazil", "Braslia");

        System.out.printf("Capital of Japan: %s%n", capitals.get("Japan"));
        System.out.printf("Contains 'France': %b%n", capitals.containsKey("France"));
        System.out.printf("Contains value 'London': %b%n", capitals.containsValue("London"));

        // overwrite existing value
        capitals.put("Japan", "Tky");
        System.out.printf("Updated Japan: %s%n", capitals.get("Japan"));

        // entrySet iteration
        System.out.println("All entries:");
        for (var entry : capitals.entrySet()) {
            System.out.printf("  %s => %s%n", entry.getKey(), entry.getValue());
        }
        System.out.println();
    }

    static void treeMapDemo() {
        System.out.println("=== TreeMap (sorted keys) ===");

        var scores = new TreeMap<String, Integer>();
        scores.put("Charlie", 88);
        scores.put("Alice", 95);
        scores.put("Bob", 72);
        scores.put("Diana", 91);

        // keys come out in alphabetical order
        System.out.println("Scores (sorted by name):");
        scores.forEach((name, score) ->
                System.out.printf("  %-10s : %d%n", name, score)
        );

        System.out.printf("First entry: %s%n", scores.firstEntry());
        System.out.printf("Last entry: %s%n%n", scores.lastEntry());
    }

    static void modernMapMethods() {
        System.out.println("=== Modern Map Methods ===");

        var settings = new HashMap<String, String>();
        settings.put("theme", "dark");

        // getOrDefault  no NPE risk
        var fontSize = settings.getOrDefault("fontSize", "14px");
        System.out.printf("Font size (defaulted): %s%n", fontSize);

        // computeIfAbsent  only compute if key is missing
        var registry = new HashMap<String, List<String>>();
        registry.computeIfAbsent("admins", k -> new ArrayList<>()).add("alice");
        registry.computeIfAbsent("admins", k -> new ArrayList<>()).add("bob");
        registry.computeIfAbsent("users", k -> new ArrayList<>()).add("charlie");
        System.out.printf("Registry: %s%n", registry);

        // merge  combine values
        var inventory = new HashMap<String, Integer>();
        inventory.put("Apples", 10);
        inventory.merge("Apples", 5, Integer::sum);
        inventory.merge("Oranges", 3, Integer::sum);
        System.out.printf("Inventory: %s%n%n", inventory);
    }

    static void wordFrequencyCounter() {
        System.out.println("=== Word Frequency Counter ===");

        var text = "the quick brown fox jumps over the lazy dog the fox";
        var words = text.split("\\s+");

        var frequency = new HashMap<String, Integer>();
        for (var word : words) {
            frequency.merge(word, 1, Integer::sum);
        }

        // display sorted by frequency (descending)
        System.out.println("Word frequencies:");
        frequency.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(e -> System.out.printf("  %-10s : %d%n", e.getKey(), e.getValue()));
    }
}
