package com.upskill.javafse.oop;

/**
 * Interface for items that can be borrowed from a library.
 * Implementing classes must track availability and the current borrower.
 */
public interface Borrowable {

    /**
     * Borrows this item for the specified member.
     *
     * @param memberName the name of the member borrowing the item
     */
    void borrowItem(String memberName);

    /**
     * Returns this item to the library, making it available again.
     */
    void returnItem();

    /**
     * Checks whether this item is currently available for borrowing.
     *
     * @return true if the item is available, false otherwise
     */
    boolean isAvailable();
}
