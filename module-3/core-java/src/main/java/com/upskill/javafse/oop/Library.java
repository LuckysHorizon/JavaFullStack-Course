package com.upskill.javafse.oop;

import java.util.ArrayList;
import java.util.List;

public class Library {

    private final String name;
    private final List<Book> books;
    private final List<Member> members;

    public Library(String name) {
        this.name = name;
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addBook(Book book) {
        books.add(book);
        System.out.printf("Added '%s' to %s library.%n", book.getTitle(), name);
    }

    public void addMember(Member member) {
        members.add(member);
        System.out.printf("Registered member %s at %s library.%n", member.getName(), name);
    }

    public List<Book> searchByTitle(String keyword) {
        return books.stream()
                .filter(b -> b.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }

    public List<Book> searchByAuthor(String authorName) {
        return books.stream()
                .filter(b -> b.getAuthor().toLowerCase().contains(authorName.toLowerCase()))
                .toList();
    }

    public List<Book> getAvailableBooks() {
        return books.stream()
                .filter(Book::isAvailable)
                .toList();
    }

    public List<Book> getAllBooks() {
        return List.copyOf(books);
    }

    public List<Member> getMembers() {
        return List.copyOf(members);
    }
}
