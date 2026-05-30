package com.upskill.javafse.basics;

import java.util.HashMap;
import java.util.Map;

public class StringManipulation {

    // Reverse a string without using StringBuilder.reverse()
    public static String reverseString(String str) {
        char[] chars = str.toCharArray();
        int left = 0;
        int right = chars.length - 1;

        while (left < right) {
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }

        return new String(chars);
    }

    // Check if two strings are anagrams (ignoring case and spaces)
    public static boolean isAnagram(String s1, String s2) {
        String cleaned1 = s1.replaceAll("\\s+", "").toLowerCase();
        String cleaned2 = s2.replaceAll("\\s+", "").toLowerCase();

        if (cleaned1.length() != cleaned2.length()) return false;

        // Count character frequencies using an array for lowercase letters
        int[] charCount = new int[26];
        for (int i = 0; i < cleaned1.length(); i++) {
            charCount[cleaned1.charAt(i) - 'a']++;
            charCount[cleaned2.charAt(i) - 'a']--;
        }

        for (int count : charCount) {
            if (count != 0) return false;
        }
        return true;
    }

    // Count frequency of each character in a string
    public static Map<Character, Integer> countCharFrequency(String str) {
        Map<Character, Integer> frequencyMap = new HashMap<>();

        for (char c : str.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }

        return frequencyMap;
    }

    // Capitalize the first letter of each word
    public static String capitalizeWords(String str) {
        if (str == null || str.isEmpty()) return str;

        char[] chars = str.toCharArray();
        boolean capitalizeNext = true;

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == ' ') {
                capitalizeNext = true;
            } else if (capitalizeNext) {
                chars[i] = Character.toUpperCase(chars[i]);
                capitalizeNext = false;
            }
        }

        return new String(chars);
    }

    // Remove consecutive duplicate characters
    public static String removeConsecutiveDuplicates(String str) {
        if (str == null || str.length() <= 1) return str;

        StringBuilder result = new StringBuilder();
        result.append(str.charAt(0));

        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) != str.charAt(i - 1)) {
                result.append(str.charAt(i));
            }
        }

        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println("===== String Manipulation =====\n");

        // Reverse String
        System.out.println("-- Reverse String --");
        String original = "Hello, World!";
        System.out.println("Original: \"" + original + "\"");
        System.out.println("Reversed: \"" + reverseString(original) + "\"");

        String palindromeTest = "racecar";
        System.out.println("Original: \"" + palindromeTest + "\"");
        System.out.println("Reversed: \"" + reverseString(palindromeTest) + "\"");

        // Anagram Check
        System.out.println("\n-- Anagram Check --");
        String[][] anagramPairs = {
            {"listen", "silent"},
            {"hello", "world"},
            {"Astronomer", "Moon starer"},
            {"abc", "cba"},
            {"java", "avaj"}
        };

        for (String[] pair : anagramPairs) {
            boolean result = isAnagram(pair[0], pair[1]);
            System.out.println("\"" + pair[0] + "\" & \"" + pair[1] + "\" -> "
                + (result ? "Anagrams" : "Not anagrams"));
        }

        // Character Frequency
        System.out.println("\n-- Character Frequency --");
        String freqStr = "programming";
        Map<Character, Integer> freq = countCharFrequency(freqStr);
        System.out.println("String: \"" + freqStr + "\"");
        System.out.println("Frequencies: " + freq);

        // Capitalize Words
        System.out.println("\n-- Capitalize Words --");
        String[] sentences = {
            "hello world from java",
            "the quick brown fox",
            "capitalize every first letter"
        };

        for (String sentence : sentences) {
            System.out.println("\"" + sentence + "\" -> \"" + capitalizeWords(sentence) + "\"");
        }

        // Remove Consecutive Duplicates
        System.out.println("\n-- Remove Consecutive Duplicates --");
        String[] dupStrings = {"aabbccdd", "abcabc", "aaabbbccc", "hello", "mississippi"};

        for (String s : dupStrings) {
            System.out.println("\"" + s + "\" -> \"" + removeConsecutiveDuplicates(s) + "\"");
        }
    }
}
