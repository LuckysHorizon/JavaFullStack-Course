package com.upskill.javafse.collections;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Comprehensive Stream API demonstration using a Product catalog.
 * Covers filtering, mapping, reducing, collecting, sorting,
 * flatMap, Optional handling, and statistical summaries.
 */
public class StreamOperations {

    static class Product {
        String name;
        String category;
        double price;

        Product(String name, String category, double price) {
            this.name = name;
            this.category = category;
            this.price = price;
        }

        @Override
        public String toString() {
            return String.format("%s [%s] $%.2f", name, category, price);
        }
    }

    static List<Product> catalog() {
        return List.of(
                new Product("Laptop", "Electronics", 999.99),
                new Product("Mouse", "Electronics", 29.99),
                new Product("Keyboard", "Electronics", 79.99),
                new Product("Desk Chair", "Furniture", 249.99),
                new Product("Standing Desk", "Furniture", 549.99),
                new Product("Monitor", "Electronics", 399.99),
                new Product("Bookshelf", "Furniture", 89.99),
                new Product("Notebook", "Stationery", 4.99),
                new Product("Pen Set", "Stationery", 12.99),
                new Product("Webcam", "Electronics", 64.99)
        );
    }

    public static void main(String[] args) {
        filterExample();
        mapExample();
        reduceExample();
        collectGroupingBy();
        sortedExample();
        flatMapExample();
        optionalHandling();
        summaryStatistics();
    }

    static void filterExample() {
        System.out.println("=== Filter: Products over $50 ===");

        var expensive = catalog().stream()
                .filter(p -> p.price > 50.0)
                .toList();

        expensive.forEach(p -> System.out.printf("  %s%n", p));
        System.out.println();
    }

    static void mapExample() {
        System.out.println("=== Map: Extract product names ===");

        var names = catalog().stream()
                .map(p -> p.name)
                .toList();

        System.out.printf("  Names: %s%n%n", names);
    }

    static void reduceExample() {
        System.out.println("=== Reduce: Total price of all products ===");

        var total = catalog().stream()
                .map(p -> p.price)
                .reduce(0.0, Double::sum);

        System.out.printf("  Total catalog value: $%.2f%n", total);

        // alternative using mapToDouble
        var totalAlt = catalog().stream()
                .mapToDouble(p -> p.price)
                .sum();

        System.out.printf("  Total (mapToDouble): $%.2f%n%n", totalAlt);
    }

    static void collectGroupingBy() {
        System.out.println("=== Collect: Group by category ===");

        var byCategory = catalog().stream()
                .collect(Collectors.groupingBy(p -> p.category));

        byCategory.forEach((category, products) -> {
            System.out.printf("  %s:%n", category);
            products.forEach(p -> System.out.printf("    - %s ($%.2f)%n", p.name, p.price));
        });

        // count per category
        var countByCategory = catalog().stream()
                .collect(Collectors.groupingBy(p -> p.category, Collectors.counting()));

        System.out.printf("  Counts: %s%n%n", countByCategory);
    }

    static void sortedExample() {
        System.out.println("=== Sorted: By price ascending ===");

        catalog().stream()
                .sorted(Comparator.comparingDouble(p -> p.price))
                .forEach(p -> System.out.printf("  $%7.2f  %s%n", p.price, p.name));

        System.out.println("\n  Top 3 most expensive:");
        catalog().stream()
                .sorted(Comparator.comparingDouble((Product p) -> p.price).reversed())
                .limit(3)
                .forEach(p -> System.out.printf("    %s%n", p));
        System.out.println();
    }

    static void flatMapExample() {
        System.out.println("=== FlatMap: Flatten nested lists ===");

        // each "order" is a list of product names
        var orders = List.of(
                List.of("Laptop", "Mouse", "Keyboard"),
                List.of("Desk Chair", "Monitor"),
                List.of("Notebook", "Pen Set", "Webcam")
        );

        var allItems = orders.stream()
                .flatMap(List::stream)
                .toList();

        System.out.printf("  All items across orders: %s%n", allItems);

        // flatMap with splitting strings
        var sentences = List.of("Hello World", "Java Streams Rock", "FlatMap Is Useful");
        var words = sentences.stream()
                .flatMap(s -> Stream.of(s.split(" ")))
                .map(String::toLowerCase)
                .distinct()
                .sorted()
                .toList();

        System.out.printf("  Unique words: %s%n%n", words);
    }

    static void optionalHandling() {
        System.out.println("=== Optional Handling ===");

        // find first electronics product under $50
        Optional<Product> cheapElectronic = catalog().stream()
                .filter(p -> "Electronics".equals(p.category))
                .filter(p -> p.price < 50.0)
                .findFirst();

        cheapElectronic.ifPresentOrElse(
                p -> System.out.printf("  Cheapest electronic under $50: %s%n", p),
                () -> System.out.println("  No electronics under $50 found")
        );

        // orElse fallback
        var mostExpensiveStationery = catalog().stream()
                .filter(p -> "Stationery".equals(p.category))
                .max(Comparator.comparingDouble(p -> p.price))
                .map(p -> p.name)
                .orElse("N/A");

        System.out.printf("  Most expensive stationery: %s%n", mostExpensiveStationery);

        // check if any product is over $1000
        var hasLuxury = catalog().stream()
                .anyMatch(p -> p.price > 1000);
        System.out.printf("  Any product over $1000? %b%n%n", hasLuxury);
    }

    static void summaryStatistics() {
        System.out.println("=== Summary Statistics ===");

        DoubleSummaryStatistics stats = catalog().stream()
                .collect(Collectors.summarizingDouble(p -> p.price));

        System.out.printf("  Count:   %d%n", stats.getCount());
        System.out.printf("  Sum:     $%.2f%n", stats.getSum());
        System.out.printf("  Min:     $%.2f%n", stats.getMin());
        System.out.printf("  Max:     $%.2f%n", stats.getMax());
        System.out.printf("  Average: $%.2f%n", stats.getAverage());

        // per-category average price
        System.out.println("\n  Average price by category:");
        var avgByCategory = catalog().stream()
                .collect(Collectors.groupingBy(
                        p -> p.category,
                        Collectors.averagingDouble(p -> p.price)
                ));

        avgByCategory.forEach((cat, avg) ->
                System.out.printf("    %-15s $%.2f%n", cat, avg)
        );
    }
}
