package com.upskill.javafse.modern;

/**
 * Demonstrates sealed classes and interfaces introduced in Java 17.
 * A sealed type restricts which classes can implement or extend it,
 * enabling exhaustive pattern matching in switch expressions.
 */
public class SealedClassDemo {

    /**
     * A sealed interface representing geometric shapes.
     * Only Circle, Rectangle, and Triangle are permitted implementations.
     */
    sealed interface Shape permits Circle, Rectangle, Triangle {}

    record Circle(double radius) implements Shape {
        Circle {
            if (radius <= 0) throw new IllegalArgumentException("Radius must be positive");
        }
    }

    record Rectangle(double width, double height) implements Shape {
        Rectangle {
            if (width <= 0 || height <= 0)
                throw new IllegalArgumentException("Width and height must be positive");
        }

        double diagonal() {
            return Math.sqrt(width * width + height * height);
        }

        boolean isSquare() {
            return Double.compare(width, height) == 0;
        }
    }

    static final class Triangle implements Shape {
        private final double base;
        private final double height;

        Triangle(double base, double height) {
            if (base <= 0 || height <= 0)
                throw new IllegalArgumentException("Base and height must be positive");
            this.base = base;
            this.height = height;
        }

        double base() { return base; }
        double height() { return height; }

        @Override
        public String toString() {
            return "Triangle[base=%.2f, height=%.2f]".formatted(base, height);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Triangle t)) return false;
            return Double.compare(t.base, base) == 0
                    && Double.compare(t.height, height) == 0;
        }

        @Override
        public int hashCode() {
            return java.util.Objects.hash(base, height);
        }
    }

    // Exhaustive pattern matching  compiler verifies all subtypes are covered
    static double calculateArea(Shape shape) {
        return switch (shape) {
            case Circle c    -> Math.PI * c.radius() * c.radius();
            case Rectangle r -> r.width() * r.height();
            case Triangle t  -> 0.5 * t.base() * t.height();
        };
    }

    static double calculatePerimeter(Shape shape) {
        return switch (shape) {
            case Circle c    -> 2 * Math.PI * c.radius();
            case Rectangle r -> 2 * (r.width() + r.height());
            case Triangle t  -> {
                // Approximate: assume isosceles triangle for perimeter
                var side = Math.sqrt((t.base() / 2) * (t.base() / 2) + t.height() * t.height());
                yield t.base() + 2 * side;
            }
        };
    }

    static String describe(Shape shape) {
        return switch (shape) {
            case Circle c when c.radius() > 100  ->
                    "A large circle with radius %.2f".formatted(c.radius());
            case Circle c ->
                    "A circle with radius %.2f".formatted(c.radius());
            case Rectangle r when r.isSquare() ->
                    "A square with side %.2f".formatted(r.width());
            case Rectangle r ->
                    "A rectangle of %.2f x %.2f (diagonal: %.2f)".formatted(
                            r.width(), r.height(), r.diagonal());
            case Triangle t ->
                    "A triangle with base %.2f and height %.2f".formatted(t.base(), t.height());
        };
    }

    static String shapeCategory(Shape shape) {
        return switch (shape) {
            case Circle c    -> "Curved";
            case Rectangle r -> "Quadrilateral";
            case Triangle t  -> "Polygon (3 sides)";
        };
    }

    public static void main(String[] args) {
        System.out.println("=== Sealed Classes & Interfaces (Java 17+) ===\n");

        Shape[] shapes = {
                new Circle(5),
                new Circle(150),
                new Rectangle(10, 5),
                new Rectangle(7, 7),
                new Triangle(8, 6)
        };

        System.out.printf("%-45s %-18s %10s %12s%n",
                "Description", "Category", "Area", "Perimeter");
        System.out.println("-".repeat(90));

        for (var shape : shapes) {
            var desc = describe(shape);
            var category = shapeCategory(shape);
            var area = calculateArea(shape);
            var perimeter = calculatePerimeter(shape);

            System.out.printf("%-45s %-18s %10.2f %12.2f%n",
                    desc, category, area, perimeter);
        }

        // Show that sealed types prevent unauthorized implementations
        System.out.println("\nSealed type properties:");
        System.out.printf("  Shape is sealed: %b%n", Shape.class.isSealed());
        System.out.println("  Permitted subtypes:");
        for (var permitted : Shape.class.getPermittedSubclasses()) {
            System.out.printf("    - %s%n", permitted.getSimpleName());
        }

        // Demonstrate record equality for Circle and Rectangle
        System.out.println("\nEquality checks:");
        var c1 = new Circle(5);
        var c2 = new Circle(5);
        var t1 = new Triangle(8, 6);
        var t2 = new Triangle(8, 6);
        System.out.printf("  Circle(5).equals(Circle(5)):     %b%n", c1.equals(c2));
        System.out.printf("  Triangle(8,6).equals(Triangle(8,6)): %b%n", t1.equals(t2));
    }
}
