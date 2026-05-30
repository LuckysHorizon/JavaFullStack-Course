package com.upskill.javafse.basics;

import java.util.Arrays;

public class ArrayOperations {

    // Bubble Sort  repeatedly swap adjacent elements if out of order
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) break; // optimization: stop if already sorted
        }
    }

    // Selection Sort  find minimum in unsorted portion and place it at the front
    public static void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            // Swap the found minimum with element at index i
            int temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
    }

    // Binary Search  assumes array is sorted, returns index or -1
    public static int binarySearch(int[] arr, int target) {
        int low = 0;
        int high = arr.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2; // avoids overflow
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    // Left rotate array by k positions
    public static void rotateArray(int[] arr, int k) {
        int n = arr.length;
        if (n == 0) return;

        k = k % n; // handle k > n
        if (k < 0) k += n;

        // Reversal algorithm: reverse first k, reverse rest, reverse all
        reverse(arr, 0, k - 1);
        reverse(arr, k, n - 1);
        reverse(arr, 0, n - 1);
    }

    private static void reverse(int[] arr, int start, int end) {
        while (start < end) {
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }

    // Merge two sorted arrays into a single sorted array
    public static int[] mergeSortedArrays(int[] arr1, int[] arr2) {
        int[] merged = new int[arr1.length + arr2.length];
        int i = 0, j = 0, k = 0;

        while (i < arr1.length && j < arr2.length) {
            if (arr1[i] <= arr2[j]) {
                merged[k++] = arr1[i++];
            } else {
                merged[k++] = arr2[j++];
            }
        }

        // Copy remaining elements
        while (i < arr1.length) {
            merged[k++] = arr1[i++];
        }
        while (j < arr2.length) {
            merged[k++] = arr2[j++];
        }

        return merged;
    }

    // Find the second largest element in the array
    public static int findSecondLargest(int[] arr) {
        if (arr.length < 2) {
            throw new IllegalArgumentException("Array must have at least 2 elements");
        }

        int largest = Integer.MIN_VALUE;
        int secondLargest = Integer.MIN_VALUE;

        for (int num : arr) {
            if (num > largest) {
                secondLargest = largest;
                largest = num;
            } else if (num > secondLargest && num != largest) {
                secondLargest = num;
            }
        }

        if (secondLargest == Integer.MIN_VALUE) {
            throw new IllegalArgumentException("No second largest element (all elements may be equal)");
        }

        return secondLargest;
    }

    public static void main(String[] args) {
        System.out.println("===== Array Operations =====\n");

        // Bubble Sort
        int[] bubbleArr = {64, 34, 25, 12, 22, 11, 90};
        System.out.println("-- Bubble Sort --");
        System.out.println("Before: " + Arrays.toString(bubbleArr));
        bubbleSort(bubbleArr);
        System.out.println("After:  " + Arrays.toString(bubbleArr));

        // Selection Sort
        int[] selectionArr = {29, 10, 14, 37, 13};
        System.out.println("\n-- Selection Sort --");
        System.out.println("Before: " + Arrays.toString(selectionArr));
        selectionSort(selectionArr);
        System.out.println("After:  " + Arrays.toString(selectionArr));

        // Binary Search
        int[] sortedArr = {2, 5, 8, 12, 16, 23, 38, 56, 72, 91};
        System.out.println("\n-- Binary Search --");
        System.out.println("Array: " + Arrays.toString(sortedArr));
        int target1 = 23;
        int target2 = 50;
        System.out.println("Search " + target1 + ": index " + binarySearch(sortedArr, target1));
        System.out.println("Search " + target2 + ": index " + binarySearch(sortedArr, target2));

        // Rotate Array
        int[] rotateArr = {1, 2, 3, 4, 5, 6, 7};
        System.out.println("\n-- Rotate Array (left by 3) --");
        System.out.println("Before: " + Arrays.toString(rotateArr));
        rotateArray(rotateArr, 3);
        System.out.println("After:  " + Arrays.toString(rotateArr));

        // Merge Sorted Arrays
        int[] a = {1, 3, 5, 7};
        int[] b = {2, 4, 6, 8, 10};
        System.out.println("\n-- Merge Sorted Arrays --");
        System.out.println("Array 1: " + Arrays.toString(a));
        System.out.println("Array 2: " + Arrays.toString(b));
        int[] merged = mergeSortedArrays(a, b);
        System.out.println("Merged:  " + Arrays.toString(merged));

        // Find Second Largest
        int[] nums = {12, 35, 1, 10, 34, 1};
        System.out.println("\n-- Find Second Largest --");
        System.out.println("Array: " + Arrays.toString(nums));
        System.out.println("Second largest: " + findSecondLargest(nums));
    }
}
