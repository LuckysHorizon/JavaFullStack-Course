package com.upskill.javafse.oop;

public class Book implements Borrowable {

    private final String isbn;
    private final String title;
    private final String author;
    private final String genre;
    private boolean available;
    private String borrowedBy;

    public Book(String isbn, String title, String author, String genre) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.available = true;
        this.borrowedBy = null;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getBorrowedBy() {
        return borrowedBy;
    }

    @Override
    public void borrowItem(String memberName) {
        if (!available) {
            System.out.printf("'%s' is already borrowed by %s.%n", title, borrowedBy);
            return;
        }
        available = false;
        borrowedBy = memberName;
        System.out.printf("'%s' has been borrowed by %s.%n", title, memberName);
    }

    @Override
    public void returnItem() {
        if (available) {
            System.out.printf("'%s' is not currently borrowed.%n", title);
            return;
        }
        System.out.printf("'%s' has been returned by %s.%n", title, borrowedBy);
        available = true;
        borrowedBy = null;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public String toString() {
        String status = available ? "Available" : "Borrowed by " + borrowedBy;
        return String.format("Book[isbn=%s, title='%s', author=%s, genre=%s, status=%s]",
                isbn, title, author, genre, status);
    }
}
