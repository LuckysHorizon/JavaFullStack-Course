package com.upskill.javafse.modern;

public class SwitchExpressions {

    // Local sealed hierarchy for the sealed-type switch demo
    sealed interface Vehicle permits Car, Truck, Motorcycle {}
    record Car(String model, int doors) implements Vehicle {}
    record Truck(String model, double payloadTons) implements Vehicle {}
    record Motorcycle(String model, int cc) implements Vehicle {}

    enum Day {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }

    // Old-style switch (statement)  verbose, error-prone
    static String dayTypeOldStyle(Day day) {
        String type;
        switch (day) {
            case MONDAY:
            case TUESDAY:
            case WEDNESDAY:
            case THURSDAY:
            case FRIDAY:
                type = "Weekday";
                break;
            case SATURDAY:
            case SUNDAY:
                type = "Weekend";
                break;
            default:
                type = "Unknown";
        }
        return type;
    }

    // New switch expression  concise, no fall-through bugs
    static String dayTypeNewStyle(Day day) {
        return switch (day) {
            case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY -> "Weekday";
            case SATURDAY, SUNDAY -> "Weekend";
        };
    }

    // Switch expression returning an int  number of letters in the day name
    static int dayLetterCount(Day day) {
        return switch (day) {
            case MONDAY    -> 6;
            case TUESDAY   -> 7;
            case WEDNESDAY -> 9;
            case THURSDAY  -> 8;
            case FRIDAY    -> 6;
            case SATURDAY  -> 8;
            case SUNDAY    -> 6;
        };
    }

    // Pattern matching with instanceof in switch (Java 21)
    static String describeObject(Object obj) {
        return switch (obj) {
            case Integer i  -> "Integer: %d".formatted(i);
            case Long l     -> "Long: %d".formatted(l);
            case Double d   -> "Double: %.2f".formatted(d);
            case String s   -> "String of length %d: \"%s\"".formatted(s.length(), s);
            case int[] arr  -> "int[] with %d elements".formatted(arr.length);
            case null       -> "null value";
            default         -> "Other: %s".formatted(obj.getClass().getSimpleName());
        };
    }

    // Guarded patterns with `when` clause (Java 21)
    static String classifyNumber(Object obj) {
        return switch (obj) {
            case Integer i when i < 0   -> "Negative integer: %d".formatted(i);
            case Integer i when i == 0  -> "Zero";
            case Integer i when i > 0   -> "Positive integer: %d".formatted(i);
            case Double d when d.isNaN() -> "Not a Number";
            case Double d when d > 0    -> "Positive double: %.2f".formatted(d);
            case Double d               -> "Non-positive double: %.2f".formatted(d);
            case String s when s.isBlank() -> "Blank string";
            case String s               -> "Non-blank string: \"%s\"".formatted(s);
            case null                   -> "null";
            default                     -> "Unrecognized type";
        };
    }

    // Exhaustive switch on sealed types  compiler ensures all subtypes are handled
    static String describeVehicle(Vehicle vehicle) {
        return switch (vehicle) {
            case Car c       -> "%s (car, %d doors)".formatted(c.model(), c.doors());
            case Truck t     -> "%s (truck, %.1ft payload)".formatted(t.model(), t.payloadTons());
            case Motorcycle m -> "%s (motorcycle, %dcc)".formatted(m.model(), m.cc());
        };
    }

    static double fuelEfficiency(Vehicle vehicle) {
        return switch (vehicle) {
            case Car c when c.doors() <= 2    -> 12.5;  // sporty 2-door
            case Car c                         -> 15.0;  // sedan
            case Truck t when t.payloadTons() > 5 -> 6.0;  // heavy truck
            case Truck t                       -> 9.0;   // light truck
            case Motorcycle m when m.cc() > 600 -> 18.0;  // big bike
            case Motorcycle m                  -> 25.0;   // small bike
        };
    }

    public static void main(String[] args) {
        System.out.println("=== Switch Expressions (Java 14-21) ===\n");

        // --- Old vs new switch ---
        System.out.println("Old vs New switch style:");
        for (var day : Day.values()) {
            System.out.printf("  %-9s -> old: %-7s | new: %-7s | letters: %d%n",
                    day, dayTypeOldStyle(day), dayTypeNewStyle(day), dayLetterCount(day));
        }

        // --- Pattern matching switch ---
        System.out.println("\nPattern matching with instanceof in switch:");
        Object[] samples = {42, 3_000_000_000L, 3.14, "Hello", new int[]{1, 2, 3}, null, true};
        for (var sample : samples) {
            System.out.printf("  %-20s -> %s%n", String.valueOf(sample), describeObject(sample));
        }

        // --- Guarded patterns ---
        System.out.println("\nGuarded patterns (when clause):");
        Object[] numbers = {-7, 0, 42, Double.NaN, 2.718, -1.5, "", "Java", null};
        for (var n : numbers) {
            System.out.printf("  %-20s -> %s%n", String.valueOf(n), classifyNumber(n));
        }

        // --- Sealed type switch ---
        System.out.println("\nExhaustive switch on sealed types:");
        Vehicle[] fleet = {
                new Car("Toyota Camry", 4),
                new Car("Mazda MX-5", 2),
                new Truck("Ford F-150", 1.5),
                new Truck("Volvo FH16", 8.0),
                new Motorcycle("Honda CBR", 600),
                new Motorcycle("Ducati Panigale", 1100)
        };
        for (var v : fleet) {
            System.out.printf("  %-40s  fuel efficiency: %.1f km/l%n",
                    describeVehicle(v), fuelEfficiency(v));
        }
    }
}
