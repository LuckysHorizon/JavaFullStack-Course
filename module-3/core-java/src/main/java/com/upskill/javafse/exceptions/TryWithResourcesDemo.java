package com.upskill.javafse.exceptions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TryWithResourcesDemo {

    // Custom AutoCloseable resource to illustrate automatic cleanup
    static class SimpleResource implements AutoCloseable {
        private final String name;

        SimpleResource(String name) {
            this.name = name;
            System.out.println("  [" + name + "] Resource opened.");
        }

        public void doWork() {
            System.out.println("  [" + name + "] Doing work...");
        }

        @Override
        public void close() {
            System.out.println("  [" + name + "] Resource closed automatically.");
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("=== Try-With-Resources Demo ===\n");

        customResourceDemo();
        System.out.println();
        fileReadWithTryWithResources();
        System.out.println();
        multipleResourcesDemo();
        System.out.println();
        compareTraditionalVsTryWithResources();
    }

    // Using a custom AutoCloseable resource
    private static void customResourceDemo() {
        System.out.println("--- Custom AutoCloseable Resource ---");
        try (SimpleResource resource = new SimpleResource("DatabaseConnection")) {
            resource.doWork();
            // resource.close() is called automatically, even if an exception occurs
        }
    }

    // Reading a file using try-with-resources
    private static void fileReadWithTryWithResources() throws IOException {
        System.out.println("--- File Read with Try-With-Resources ---");

        Path tempFile = Files.createTempFile("twr-demo", ".txt");
        Files.writeString(tempFile, "Line 1\nLine 2\nLine 3\n");

        try (BufferedReader reader = new BufferedReader(new FileReader(tempFile.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("  Read: " + line);
            }
        }
        // Reader is automatically closed here

        Files.deleteIfExists(tempFile);
        System.out.println("  Temp file cleaned up.");
    }

    // Multiple resources in a single try-with-resources statement
    private static void multipleResourcesDemo() throws IOException {
        System.out.println("--- Multiple Resources in Single Try ---");

        Path sourceFile = Files.createTempFile("twr-source", ".txt");
        Path destFile = Files.createTempFile("twr-dest", ".txt");

        Files.writeString(sourceFile, "Hello from source file!\nSecond line of source.\n");

        // Both reader and writer are managed together  closed in reverse order
        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFile.toFile()));
             BufferedWriter writer = new BufferedWriter(new FileWriter(destFile.toFile()))) {

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line.toUpperCase());
                writer.newLine();
            }
            System.out.println("  Copied and uppercased content from source to dest.");
        }

        // Verify the copy
        System.out.println("  Dest contents: " + Files.readString(destFile).trim());

        Files.deleteIfExists(sourceFile);
        Files.deleteIfExists(destFile);
    }

    // Side-by-side comparison of traditional try-finally vs try-with-resources
    private static void compareTraditionalVsTryWithResources() {
        System.out.println("--- Traditional try-finally vs try-with-resources ---");

        // Traditional approach: verbose and error-prone
        System.out.println("\n  Traditional approach:");
        SimpleResource resource = null;
        try {
            resource = new SimpleResource("TraditionalRes");
            resource.doWork();
        } finally {
            if (resource != null) {
                resource.close();
            }
        }

        // Modern approach: concise and safe
        System.out.println("\n  Try-with-resources approach:");
        try (SimpleResource modern = new SimpleResource("ModernRes")) {
            modern.doWork();
        }

        System.out.println("\n  Notice: both produce the same result, but try-with-resources");
        System.out.println("  is shorter, safer, and handles suppressed exceptions properly.");
    }
}
