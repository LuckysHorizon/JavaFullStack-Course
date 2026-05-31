package com.upskill.javafse.fileio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SerializationDemo {

    static class Student implements Serializable {
        private static final long serialVersionUID = 1L;

        private final String name;
        private final int age;
        private final double gpa;

        Student(String name, int age, double gpa) {
            this.name = name;
            this.age = age;
            this.gpa = gpa;
        }

        @Override
        public String toString() {
            return String.format("Student{name='%s', age=%d, gpa=%.2f}", name, age, gpa);
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("=== Serialization Demo ===\n");

        var singleFile = Path.of("student.ser");
        var listFile = Path.of("students.ser");

        serializeSingleObject(singleFile);
        deserializeSingleObject(singleFile);
        System.out.println();
        serializeList(listFile);
        deserializeList(listFile);

        // cleanup
        Files.deleteIfExists(singleFile);
        Files.deleteIfExists(listFile);
        System.out.println("\nSerialized files cleaned up.");
    }

    // Serialize a single Student object to a file
    private static void serializeSingleObject(Path path) throws IOException {
        System.out.println("--- Serialize Single Object ---");
        var student = new Student("Alice", 21, 3.85);

        try (var oos = new ObjectOutputStream(new FileOutputStream(path.toFile()))) {
            oos.writeObject(student);
        }
        System.out.println("  Serialized: " + student);
    }

    // Deserialize a single Student object from a file
    private static void deserializeSingleObject(Path path) throws IOException, ClassNotFoundException {
        System.out.println("--- Deserialize Single Object ---");

        try (var ois = new ObjectInputStream(new FileInputStream(path.toFile()))) {
            var student = (Student) ois.readObject();
            System.out.println("  Deserialized: " + student);
        }
    }

    // Serialize a List of Student objects
    @SuppressWarnings("unchecked")
    private static void serializeList(Path path) throws IOException {
        System.out.println("--- Serialize List of Objects ---");
        var students = new ArrayList<Student>();
        students.add(new Student("Bob", 22, 3.50));
        students.add(new Student("Carol", 20, 3.92));
        students.add(new Student("Dave", 23, 3.10));

        try (var oos = new ObjectOutputStream(new FileOutputStream(path.toFile()))) {
            oos.writeObject(students);
        }
        System.out.println("  Serialized " + students.size() + " students to " + path);
    }

    // Deserialize a List of Student objects
    @SuppressWarnings("unchecked")
    private static void deserializeList(Path path) throws IOException, ClassNotFoundException {
        System.out.println("--- Deserialize List of Objects ---");

        try (var ois = new ObjectInputStream(new FileInputStream(path.toFile()))) {
            var students = (List<Student>) ois.readObject();
            System.out.println("  Deserialized " + students.size() + " students:");
            for (var student : students) {
                System.out.println("    " + student);
            }
        }
    }
}
