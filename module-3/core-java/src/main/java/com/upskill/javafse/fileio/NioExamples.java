package com.upskill.javafse.fileio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class NioExamples {

    public static void main(String[] args) throws IOException {
        System.out.println("=== Java NIO File Operations ===\n");

        pathOperations();
        System.out.println();
        readWriteWithFiles();
        System.out.println();
        fileExistsAndCreateDirectories();
        System.out.println();
        directoryTraversal();
        System.out.println();
        copyAndMove();
    }

    // Path creation, resolution, and relativization
    private static void pathOperations() {
        System.out.println("--- Path Operations ---");

        var basePath = Path.of("project", "src", "main");
        var filePath = Path.of("project", "src", "main", "App.java");

        System.out.println("  Base path: " + basePath);
        System.out.println("  File path: " + filePath);
        System.out.println("  File name: " + filePath.getFileName());
        System.out.println("  Parent:    " + filePath.getParent());
        System.out.println("  Root:      " + filePath.getRoot());
        System.out.println("  Name count: " + filePath.getNameCount());

        // resolve appends a child path to a base
        var resolved = basePath.resolve("java/com/App.java");
        System.out.println("  Resolved:  " + resolved);

        // relativize computes relative path between two paths
        var from = Path.of("project", "src");
        var to = Path.of("project", "src", "main", "App.java");
        var relative = from.relativize(to);
        System.out.println("  Relative from " + from + " to " + to + ": " + relative);

        // normalize removes redundant elements
        var messy = Path.of("project", "src", "..", "src", "main", ".", "App.java");
        System.out.println("  Normalized: " + messy.normalize());
    }

    // Reading and writing files using the Files utility class
    private static void readWriteWithFiles() throws IOException {
        System.out.println("--- Files.readAllLines / Files.write ---");

        var path = Path.of("nio-demo.txt");
        var lines = List.of("Alpha", "Bravo", "Charlie", "Delta", "Echo");

        // Write lines to a file
        Files.write(path, lines);
        System.out.println("  Wrote " + lines.size() + " lines to " + path);

        // Read all lines back
        var readLines = Files.readAllLines(path);
        System.out.println("  Read back " + readLines.size() + " lines:");
        for (var line : readLines) {
            System.out.println("    " + line);
        }

        Files.deleteIfExists(path);
    }

    // Checking existence and creating directory hierarchies
    private static void fileExistsAndCreateDirectories() throws IOException {
        System.out.println("--- Files.exists / Files.createDirectories ---");

        var dirPath = Path.of("nio-temp", "sub1", "sub2");

        System.out.println("  Directory exists before? " + Files.exists(dirPath));

        // createDirectories creates the full hierarchy, like mkdir -p
        Files.createDirectories(dirPath);
        System.out.println("  Created: " + dirPath);
        System.out.println("  Directory exists after?  " + Files.exists(dirPath));

        // create a file inside the nested directory
        var filePath = dirPath.resolve("test.txt");
        Files.writeString(filePath, "Hello from nested directory!");
        System.out.println("  File exists? " + Files.exists(filePath));
        System.out.println("  Content: " + Files.readString(filePath));

        // cleanup nested structure
        Files.deleteIfExists(filePath);
        Files.deleteIfExists(dirPath);
        Files.deleteIfExists(dirPath.getParent());
        Files.deleteIfExists(dirPath.getParent().getParent());
        System.out.println("  Cleaned up temp directories.");
    }

    // Walking a directory tree using Files.walk
    private static void directoryTraversal() throws IOException {
        System.out.println("--- Files.walk (Directory Traversal) ---");

        // set up a small directory tree
        var root = Path.of("nio-walk-demo");
        Files.createDirectories(root.resolve("docs"));
        Files.createDirectories(root.resolve("src/main"));
        Files.writeString(root.resolve("README.md"), "# Project");
        Files.writeString(root.resolve("docs/guide.txt"), "User guide");
        Files.writeString(root.resolve("src/main/App.java"), "class App {}");

        // walk the tree and print each entry
        System.out.println("  Walking directory: " + root);
        try (var stream = Files.walk(root)) {
            stream.forEach(path -> {
                var type = Files.isDirectory(path) ? "[DIR] " : "[FILE]";
                System.out.println("    " + type + " " + path);
            });
        }

        // cleanup
        Files.deleteIfExists(root.resolve("src/main/App.java"));
        Files.deleteIfExists(root.resolve("src/main"));
        Files.deleteIfExists(root.resolve("src"));
        Files.deleteIfExists(root.resolve("docs/guide.txt"));
        Files.deleteIfExists(root.resolve("docs"));
        Files.deleteIfExists(root.resolve("README.md"));
        Files.deleteIfExists(root);
        System.out.println("  Cleaned up walk-demo directory.");
    }

    // Copying and moving files using NIO
    private static void copyAndMove() throws IOException {
        System.out.println("--- Files.copy / Files.move ---");

        var original = Path.of("nio-original.txt");
        var copied = Path.of("nio-copied.txt");
        var moved = Path.of("nio-moved.txt");

        Files.writeString(original, "Original file content for NIO demo.");
        System.out.println("  Created original: " + original);

        // copy the file (REPLACE_EXISTING overwrites if target exists)
        Files.copy(original, copied, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("  Copied to: " + copied);
        System.out.println("  Copy content: " + Files.readString(copied));

        // move/rename the copy
        Files.move(copied, moved, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("  Moved " + copied + " -> " + moved);
        System.out.println("  Moved file exists? " + Files.exists(moved));
        System.out.println("  Old copy exists?   " + Files.exists(copied));

        // cleanup
        Files.deleteIfExists(original);
        Files.deleteIfExists(moved);
        System.out.println("  Cleaned up.");
    }
}
