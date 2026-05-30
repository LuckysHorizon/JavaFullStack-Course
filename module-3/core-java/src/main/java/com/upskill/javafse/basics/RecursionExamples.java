package com.upskill.javafse.basics;

public class RecursionExamples {

    // Factorial: n! = n * (n-1)!
    public static long factorial(int n) {
        if (n < 0) throw new IllegalArgumentException("Negative numbers not allowed");
        if (n == 0 || n == 1) return 1;
        return n * factorial(n - 1);
    }

    // Fibonacci: fib(n) = fib(n-1) + fib(n-2)
    public static long fibonacci(int n) {
        if (n < 0) throw new IllegalArgumentException("Negative index not allowed");
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    // Tower of Hanoi  prints each move
    public static void towerOfHanoi(int n, char source, char auxiliary, char target) {
        if (n == 1) {
            System.out.println("Move disk 1 from " + source + " to " + target);
            return;
        }
        towerOfHanoi(n - 1, source, target, auxiliary);
        System.out.println("Move disk " + n + " from " + source + " to " + target);
        towerOfHanoi(n - 1, auxiliary, source, target);
    }

    // Power: base^exp using recursion
    public static double power(double base, int exp) {
        if (exp == 0) return 1;
        if (exp < 0) return 1.0 / power(base, -exp); // handle negative exponents

        // Use fast exponentiation: base^exp = (base^(exp/2))^2
        if (exp % 2 == 0) {
            double half = power(base, exp / 2);
            return half * half;
        } else {
            return base * power(base, exp - 1);
        }
    }

    public static void main(String[] args) {
        System.out.println("===== Recursion Examples =====\n");

        // Factorial demos
        System.out.println("-- Factorial --");
        for (int i = 0; i <= 10; i++) {
            System.out.println(i + "! = " + factorial(i));
        }

        // Fibonacci demos
        System.out.println("\n-- Fibonacci (first 15 terms) --");
        for (int i = 0; i < 15; i++) {
            System.out.print(fibonacci(i) + " ");
        }
        System.out.println();

        // Tower of Hanoi
        System.out.println("\n-- Tower of Hanoi (3 disks) --");
        towerOfHanoi(3, 'A', 'B', 'C');

        // Power demos
        System.out.println("\n-- Power --");
        System.out.println("2^10 = " + power(2, 10));
        System.out.println("3^5 = " + power(3, 5));
        System.out.println("5^0 = " + power(5, 0));
        System.out.println("2^(-3) = " + power(2, -3));
        System.out.println("7^3 = " + power(7, 3));
    }
}
