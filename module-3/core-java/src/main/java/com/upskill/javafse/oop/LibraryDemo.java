package com.upskill.javafse.oop;

public class LibraryDemo {

    public static void main(String[] args) {
        System.out.println("=== Library Management System Demo ===\n");

        var library = new Library("City Central");

        // Add books
        System.out.println("--- Adding Books ---");
        var book1 = new Book("978-0-13-468599-1", "Effective Java", "Joshua Bloch", "Programming");
        var book2 = new Book("978-0-596-00712-6", "Head First Design Patterns", "Eric Freeman", "Programming");
        var book3 = new Book("978-0-06-112008-4", "To Kill a Mockingbird", "Harper Lee", "Fiction");
        var book4 = new Book("978-0-7432-7356-5", "1984", "George Orwell", "Fiction");
        var book5 = new Book("978-0-13-235088-4", "Clean Code", "Robert C. Martin", "Programming");

        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
        library.addBook(book4);
        library.addBook(book5);

        // Register members
        System.out.println("\n--- Registering Members ---");
        var alice = new Member("M001", "Alice");
        var bob = new Member("M002", "Bob");
        library.addMember(alice);
        library.addMember(bob);

        // Borrowing flow
        System.out.println("\n--- Borrowing Books ---");
        alice.borrowBook(book1);
        alice.borrowBook(book3);
        bob.borrowBook(book2);

        // Try to borrow an already-borrowed book
        bob.borrowBook(book1);

        System.out.printf("%nAlice has %d book(s), Bob has %d book(s)%n",
                alice.getBookCount(), bob.getBookCount());

        // Search operations
        System.out.println("\n--- Search by Title: 'java' ---");
        for (Book b : library.searchByTitle("java")) {
            System.out.println("  " + b);
        }

        System.out.println("\n--- Search by Author: 'orwell' ---");
        for (Book b : library.searchByAuthor("orwell")) {
            System.out.println("  " + b);
        }

        // Available books
        System.out.println("\n--- Available Books ---");
        for (Book b : library.getAvailableBooks()) {
            System.out.println("  " + b);
        }

        // Return flow
        System.out.println("\n--- Returning Books ---");
        alice.returnBook(book1);
        alice.returnBook(book3);

        System.out.printf("%nAlice now has %d book(s)%n", alice.getBookCount());

        System.out.println("\n--- Available Books After Returns ---");
        for (Book b : library.getAvailableBooks()) {
            System.out.println("  " + b);
        }
    }
}
