package com.upskill.javafse.oop;

import java.util.ArrayList;
import java.util.List;

public class Member {

    private final String memberId;
    private final String name;
    private final List<Book> borrowedBooks;

    public Member(String memberId, String name) {
        this.memberId = memberId;
        this.name = name;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public List<Book> getBorrowedBooks() {
        return List.copyOf(borrowedBooks);
    }

    public void borrowBook(Book book) {
        if (!book.isAvailable()) {
            System.out.printf("%s cannot borrow '%s'  it's not available.%n",
                    name, book.getTitle());
            return;
        }
        book.borrowItem(name);
        borrowedBooks.add(book);
    }

    public void returnBook(Book book) {
        if (!borrowedBooks.contains(book)) {
            System.out.printf("%s has not borrowed '%s'.%n", name, book.getTitle());
            return;
        }
        book.returnItem();
        borrowedBooks.remove(book);
    }

    public int getBookCount() {
        return borrowedBooks.size();
    }

    @Override
    public String toString() {
        return String.format("Member[id=%s, name=%s, borrowedBooks=%d]",
                memberId, name, borrowedBooks.size());
    }
}
