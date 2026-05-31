package com.upskill.javafse.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Demonstrates sorting with Comparable, Comparator, and lambda expressions.
 * Uses an inner Student class to show various sorting strategies.
 */
public class SortingDemo {

    static class Student implements Comparable<Student> {
        String name;
        int age;
        double gpa;

        Student(String name, int age, double gpa) {
            this.name = name;
            this.age = age;
            this.gpa = gpa;
        }

        // natural ordering: by GPA descending (highest first)
        @Override
        public int compareTo(Student other) {
            return Double.compare(other.gpa, this.gpa);
        }

        @Override
        public String toString() {
            return String.format("%s(age=%d, gpa=%.1f)", name, age, gpa);
        }
    }

    // explicit Comparator class  sort by name alphabetically
    static class ByNameComparator implements Comparator<Student> {
        @Override
        public int compare(Student a, Student b) {
            return a.name.compareTo(b.name);
        }
    }

    // explicit Comparator class  sort by age
    static class ByAgeComparator implements Comparator<Student> {
        @Override
        public int compare(Student a, Student b) {
            return Integer.compare(a.age, b.age);
        }
    }

    public static void main(String[] args) {
        comparableDemo();
        comparatorClassDemo();
        lambdaComparatorDemo();
        reverseOrderDemo();
    }

    static List<Student> buildRoster() {
        var students = new ArrayList<Student>();
        students.add(new Student("Alice", 21, 3.8));
        students.add(new Student("Bob", 19, 3.5));
        students.add(new Student("Charlie", 22, 3.9));
        students.add(new Student("Diana", 20, 3.7));
        students.add(new Student("Eve", 21, 3.5));
        return students;
    }

    static void comparableDemo() {
        System.out.println("=== Comparable (natural order: GPA descending) ===");

        var students = buildRoster();
        Collections.sort(students); // uses compareTo
        students.forEach(s -> System.out.printf("  %s%n", s));
        System.out.println();
    }

    static void comparatorClassDemo() {
        System.out.println("=== Named Comparator Classes ===");

        var students = buildRoster();

        // sort by name using explicit comparator
        students.sort(new ByNameComparator());
        System.out.println("By name:");
        students.forEach(s -> System.out.printf("  %s%n", s));

        // sort by age using explicit comparator
        students.sort(new ByAgeComparator());
        System.out.println("By age:");
        students.forEach(s -> System.out.printf("  %s%n", s));
        System.out.println();
    }

    static void lambdaComparatorDemo() {
        System.out.println("=== Lambda-based Comparators ===");

        var students = buildRoster();

        // sort by GPA ascending using lambda
        students.sort((a, b) -> Double.compare(a.gpa, b.gpa));
        System.out.println("By GPA (ascending, lambda):");
        students.forEach(s -> System.out.printf("  %s%n", s));

        // sort by name using method reference
        students.sort(Comparator.comparing(s -> s.name));
        System.out.println("By name (method ref style):");
        students.forEach(s -> System.out.printf("  %s%n", s));

        // chained: by GPA descending, then by name ascending
        students.sort(
                Comparator.comparingDouble((Student s) -> s.gpa)
                        .reversed()
                        .thenComparing(s -> s.name)
        );
        System.out.println("By GPA desc, then name asc:");
        students.forEach(s -> System.out.printf("  %s%n", s));
        System.out.println();
    }

    static void reverseOrderDemo() {
        System.out.println("=== Reverse Ordering ===");

        // reverse natural order of strings
        var languages = new ArrayList<>(List.of("Java", "Python", "C++", "Rust", "Go"));
        Collections.sort(languages);
        System.out.printf("Sorted:  %s%n", languages);

        languages.sort(Comparator.reverseOrder());
        System.out.printf("Reverse: %s%n", languages);

        // reverse natural order of students (GPA ascending, since natural is descending)
        var students = buildRoster();
        students.sort(Comparator.reverseOrder());
        System.out.println("Students in reverse natural order (GPA ascending):");
        students.forEach(s -> System.out.printf("  %s%n", s));
    }
}
