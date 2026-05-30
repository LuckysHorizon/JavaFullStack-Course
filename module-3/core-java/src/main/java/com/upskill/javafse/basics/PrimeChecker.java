package com.upskill.javafse.basics;

import java.util.ArrayList;
import java.util.List;

public class PrimeChecker {

    // Efficient primality check  only test divisors up to sqrt(n)
    public static boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;

        // Check from 5, skipping even numbers and multiples of 3
        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }

    // Sieve of Eratosthenes  returns all primes up to the given limit
    public static List<Integer> sieveOfEratosthenes(int limit) {
        boolean[] isComposite = new boolean[limit + 1];
        List<Integer> primes = new ArrayList<>();

        for (int i = 2; i <= limit; i++) {
            if (!isComposite[i]) {
                primes.add(i);
                // Mark all multiples of i as composite
                for (long j = (long) i * i; j <= limit; j += i) {
                    isComposite[(int) j] = true;
                }
            }
        }

        return primes;
    }

    // Print all primes in the range [start, end]
    public static void printPrimesInRange(int start, int end) {
        System.out.println("Primes between " + start + " and " + end + ":");
        boolean found = false;

        for (int i = start; i <= end; i++) {
            if (isPrime(i)) {
                System.out.print(i + " ");
                found = true;
            }
        }

        if (!found) {
            System.out.print("None found");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("===== Prime Checker =====\n");

        // Test isPrime with individual numbers
        int[] testNumbers = {1, 2, 3, 4, 17, 25, 29, 100, 97, 541};
        System.out.println("-- isPrime Tests --");
        for (int n : testNumbers) {
            System.out.println(n + " -> " + (isPrime(n) ? "Prime" : "Not prime"));
        }

        // Sieve of Eratosthenes up to 50
        System.out.println("\n-- Sieve of Eratosthenes (up to 50) --");
        List<Integer> primesUpTo50 = sieveOfEratosthenes(50);
        System.out.println(primesUpTo50);

        // Primes in a range
        System.out.println("\n-- Primes in Range --");
        printPrimesInRange(10, 50);
        printPrimesInRange(90, 100);
    }
}
