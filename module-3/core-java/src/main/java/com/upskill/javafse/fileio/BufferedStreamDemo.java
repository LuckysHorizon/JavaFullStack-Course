package com.upskill.javafse.fileio;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class BufferedStreamDemo {

    private static final int LINE_COUNT = 100_000;

    public static void main(String[] args) throws IOException {
        System.out.println("=== Buffered vs Unbuffered Stream Performance ===\n");
        System.out.println("Writing " + LINE_COUNT + " lines with each approach...\n");

        var unbufferedFile = Path.of("unbuffered-output.txt");
        var bufferedFile = Path.of("buffered-output.txt");

        // Measure unbuffered (FileWriter only) performance
        var unbufferedTime = writeWithFileWriter(unbufferedFile);

        // Measure buffered (BufferedWriter wrapping FileWriter) performance
        var bufferedTime = writeWithBufferedWriter(bufferedFile);

        // Print comparison
        System.out.println("--- Performance Results ---");
        System.out.printf("  FileWriter (unbuffered):  %,d ns  (%.2f ms)%n", unbufferedTime, unbufferedTime / 1_000_000.0);
        System.out.printf("  BufferedWriter (buffered): %,d ns  (%.2f ms)%n", bufferedTime, bufferedTime / 1_000_000.0);

        if (unbufferedTime > bufferedTime) {
            var speedup = (double) unbufferedTime / bufferedTime;
            System.out.printf("%n  BufferedWriter was ~%.1fx faster!%n", speedup);
        } else {
            System.out.println("\n  Results were close  try running again with more data.");
        }

        // Verify file sizes match
        var unbufferedSize = Files.size(unbufferedFile);
        var bufferedSize = Files.size(bufferedFile);
        System.out.printf("%n  File sizes: unbuffered=%,d bytes, buffered=%,d bytes%n", unbufferedSize, bufferedSize);

        // cleanup
        Files.deleteIfExists(unbufferedFile);
        Files.deleteIfExists(bufferedFile);
        System.out.println("  Temp files cleaned up.");
    }

    // Write using FileWriter directly  each write() call may trigger a system I/O call
    private static long writeWithFileWriter(Path path) throws IOException {
        System.out.println("Writing with FileWriter (unbuffered)...");
        var startTime = System.nanoTime();

        try (var writer = new FileWriter(path.toFile())) {
            for (var i = 0; i < LINE_COUNT; i++) {
                writer.write("Line " + i + ": This is sample data for performance testing.\n");
            }
        }

        var elapsed = System.nanoTime() - startTime;
        System.out.println("  Done.\n");
        return elapsed;
    }

    // Write using BufferedWriter  batches writes in memory before flushing to disk
    private static long writeWithBufferedWriter(Path path) throws IOException {
        System.out.println("Writing with BufferedWriter (buffered)...");
        var startTime = System.nanoTime();

        try (var writer = new BufferedWriter(new FileWriter(path.toFile()))) {
            for (var i = 0; i < LINE_COUNT; i++) {
                writer.write("Line " + i + ": This is sample data for performance testing.\n");
            }
        }

        var elapsed = System.nanoTime() - startTime;
        System.out.println("  Done.\n");
        return elapsed;
    }
}
