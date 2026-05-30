package com.upskill.javafse.basics;

public class PalindromeChecker {

    // Check if a string is a palindrome, ignoring case and spaces
    public static boolean isStringPalindrome(String text) {
        String cleaned = text.replaceAll("\\s+", "").toLowerCase();
        int left = 0;
        int right = cleaned.length() - 1;

        while (left < right) {
            if (cleaned.charAt(left) != cleaned.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    // Check if a number is a palindrome by reversing its digits
    public static boolean isNumericPalindrome(int number) {
        if (number < 0) {
            return false; // negative numbers are not palindromes
        }

        int original = number;
        int reversed = 0;

        while (number > 0) {
            int digit = number % 10;
            reversed = reversed * 10 + digit;
            number /= 10;
        }

        return original == reversed;
    }

    public static void main(String[] args) {
        System.out.println("===== Palindrome Checker =====\n");

        // String palindrome test cases
        String[] testStrings = {
            "racecar",
            "A man a plan a canal Panama",
            "hello",
            "Was it a car or a cat I saw",
            "Madam",
            "OpenAI"
        };

        System.out.println("-- String Palindrome Tests --");
        for (String s : testStrings) {
            boolean result = isStringPalindrome(s);
            System.out.println("\"" + s + "\" -> " + (result ? "Palindrome" : "Not a palindrome"));
        }

        // Numeric palindrome test cases
        int[] testNumbers = {121, 12321, 123, 0, 1001, 45654, 98};

        System.out.println("\n-- Numeric Palindrome Tests --");
        for (int n : testNumbers) {
            boolean result = isNumericPalindrome(n);
            System.out.println(n + " -> " + (result ? "Palindrome" : "Not a palindrome"));
        }
    }
}
