package com.upskill.javafse.exceptions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileExceptionDemo {

    public static void main(String[] args) {
        System.out.println("=== File Exception Handling Demo ===\n");

        readNonExistentFile();
        System.out.println();
        writeToInvalidLocation();
        System.out.println();
        multipleCatchDemo();
        System.out.println();
        finallyBlockDemo();
    }

    // Attempt to read a file that doesn't exist
    private static void readNonExistentFile() {
        System.out.println("--- FileNotFoundException Demo ---");
        try {
            FileReader reader = new FileReader("this_file_does_not_exist.txt");
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Caught FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Caught IOException: " + e.getMessage());
        }
    }

    // Attempt to write to an invalid/read-only location
    private static void writeToInvalidLocation() {
        System.out.println("--- IOException (Write) Demo ---");
        try {
            // Attempting to write to a path that likely doesn't exist or is restricted
            FileWriter writer = new FileWriter("/invalid_path/readonly/output.txt");
            writer.write("This should fail");
            writer.close();
        } catch (IOException e) {
            System.out.println("Caught IOException while writing: " + e.getMessage());
        }
    }

    // Multiple catch blocks handling different exception types
    private static void multipleCatchDemo() {
        System.out.println("--- Multiple Catch Blocks Demo ---");
        String[] filenames = {"nonexistent.txt", "/bad/path/file.txt"};

        for (String filename : filenames) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(filename));
                String line = reader.readLine();
                System.out.println("Read: " + line);
                reader.close();
            } catch (FileNotFoundException e) {
                System.out.println("  File not found: " + filename);
            } catch (IOException e) {
                System.out.println("  IO error reading: " + filename + " -> " + e.getMessage());
            } catch (Exception e) {
                System.out.println("  Unexpected error: " + e.getMessage());
            }
        }
    }

    // Demonstrating the finally block  runs whether or not an exception occurred
    private static void finallyBlockDemo() {
        System.out.println("--- Finally Block Demo ---");
        BufferedReader reader = null;

        // Case 1: exception occurs
        try {
            System.out.println("Attempting to open a missing file...");
            reader = new BufferedReader(new FileReader("missing.txt"));
            System.out.println("This line won't execute.");
        } catch (FileNotFoundException e) {
            System.out.println("  Caught: " + e.getMessage());
        } finally {
            System.out.println("  Finally block executed (exception case).");
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println("  Error closing reader: " + e.getMessage());
                }
            }
        }

        // Case 2: no exception
        try {
            System.out.println("\nPerforming a simple calculation...");
            int result = 42 / 2;
            System.out.println("  Result: " + result);
        } finally {
            System.out.println("  Finally block executed (no exception case).");
        }
    }
}
