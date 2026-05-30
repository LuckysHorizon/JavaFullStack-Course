package com.upskill.javafse.modern;

public class RecordExamples {

    // Basic record with compact constructor validation
    record Person(String name, int age) {
        Person {
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Name must not be null or blank");
            }
            if (age < 0) {
                throw new IllegalArgumentException("Age must be non-negative, got: " + age);
            }
        }
    }

    // Record with a custom method
    record Point(double x, double y) {
        double distanceTo(Point other) {
            var dx = this.x - other.x;
            var dy = this.y - other.y;
            return Math.sqrt(dx * dx + dy * dy);
        }

        @Override
        public String toString() {
            return "(%.2f, %.2f)".formatted(x, y);
        }
    }

    // Record representing RGB color  shows records with multiple components
    record Color(int red, int green, int blue) {
        Color {
            if (red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255) {
                throw new IllegalArgumentException("Color values must be 0-255");
            }
        }

        String toHex() {
            return "#%02X%02X%02X".formatted(red, green, blue);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Record Examples (Java 16+) ===\n");

        // --- Person record ---
        var alice = new Person("Alice", 30);
        var bob = new Person("Bob", 25);
        var aliceCopy = new Person("Alice", 30);

        System.out.println("Person records:");
        System.out.printf("  alice       = %s%n", alice);
        System.out.printf("  bob         = %s%n", bob);
        System.out.printf("  aliceCopy   = %s%n", aliceCopy);
        System.out.printf("  alice.name() = %s, alice.age() = %d%n", alice.name(), alice.age());

        // Records auto-generate equals() and hashCode()
        System.out.printf("  alice.equals(aliceCopy) = %b%n", alice.equals(aliceCopy));
        System.out.printf("  alice == aliceCopy       = %b%n", alice == aliceCopy);
        System.out.printf("  alice.equals(bob)        = %b%n", alice.equals(bob));

        // Compact constructor validation
        System.out.println("\nCompact constructor validation:");
        try {
            var invalid = new Person("", 20);
        } catch (IllegalArgumentException e) {
            System.out.printf("  Blank name rejected: %s%n", e.getMessage());
        }
        try {
            var invalid = new Person("Charlie", -5);
        } catch (IllegalArgumentException e) {
            System.out.printf("  Negative age rejected: %s%n", e.getMessage());
        }

        // --- Point record ---
        System.out.println("\nPoint records:");
        var origin = new Point(0, 0);
        var p1 = new Point(3, 4);
        var p2 = new Point(6, 8);

        System.out.printf("  origin = %s%n", origin);
        System.out.printf("  p1     = %s%n", p1);
        System.out.printf("  p2     = %s%n", p2);
        System.out.printf("  Distance from origin to p1: %.2f%n", origin.distanceTo(p1));
        System.out.printf("  Distance from p1 to p2:     %.2f%n", p1.distanceTo(p2));

        // --- Color record ---
        System.out.println("\nColor records:");
        var red = new Color(255, 0, 0);
        var teal = new Color(0, 128, 128);
        System.out.printf("  red  = %s -> hex: %s%n", red, red.toHex());
        System.out.printf("  teal = %s -> hex: %s%n", teal, teal.toHex());

        try {
            var bad = new Color(300, 0, 0);
        } catch (IllegalArgumentException e) {
            System.out.printf("  Invalid color rejected: %s%n", e.getMessage());
        }
    }
}
