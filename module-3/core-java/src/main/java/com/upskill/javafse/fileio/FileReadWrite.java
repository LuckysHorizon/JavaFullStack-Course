package com.upskill.javafse.fileio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileReadWrite {

    public static void main(String[] args) throws IOException {
        System.out.println("=== File Read/Write Demo ===\n");

        var filePath = Path.of("fileio-demo.txt");

        writeLines(filePath);
        readLines(filePath);
        appendToFile(filePath);
        readEntireContent(filePath);

        // cleanup
        Files.deleteIfExists(filePath);
        System.out.println("\nDemo file cleaned up.");
    }

    // Write multiple lines to a file using BufferedWriter
    private static void writeLines(Path path) throws IOException {
        System.out.println("--- Writing Lines ---");
        var lines = List.of(
                "First line of the file",
                "Second line with some data",
                "Third line  the original content"
        );

        try (var writer = new BufferedWriter(new FileWriter(path.toFile()))) {
            for (var line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
        System.out.println("  Wrote " + lines.size() + " lines to " + path);
    }

    // Read lines one by one using BufferedReader
    private static void readLines(Path path) throws IOException {
        System.out.println("\n--- Reading Lines ---");
        try (var reader = new BufferedReader(new FileReader(path.toFile()))) {
            var line = reader.readLine();
            var lineNumber = 1;
            while (line != null) {
                System.out.println("  Line " + lineNumber + ": " + line);
                line = reader.readLine();
                lineNumber++;
            }
        }
    }

    // Append additional content to an existing file
    private static void appendToFile(Path path) throws IOException {
        System.out.println("\n--- Appending to File ---");
        // second argument true = append mode
        try (var writer = new BufferedWriter(new FileWriter(path.toFile(), true))) {
            writer.write("Fourth line  appended later");
            writer.newLine();
            writer.write("Fifth line  also appended");
            writer.newLine();
        }
        System.out.println("  Appended 2 more lines.");
    }

    // Read the entire file content at once using Files API
    private static void readEntireContent(Path path) throws IOException {
        System.out.println("\n--- Reading Entire File Content ---");
        var content = Files.readString(path);
        System.out.println("  Full content:");
        System.out.println("  ----------");
        // indent each line for readability
        for (var line : content.split("\n")) {
            System.out.println("  " + line);
        }
        System.out.println("  ----------");
        System.out.println("  Total characters: " + content.length());
    }
}
