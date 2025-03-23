package com.algo.nth_element;

import java.util.Arrays;

public class NthElementUtil {
    public static <T extends Comparable<T>> void nthElement(T[] array, int n) {
        if (array == null || n < 0 || n >= array.length) {
            throw new IllegalArgumentException("Index n is out of range");
        }

        int depthLimit = 2 * (int) Math.log(array.length);
        introSelect(array, 0, array.length - 1, n, depthLimit);
    }

    private static <T extends Comparable<T>> void introSelect(T[] array,
                                                              int left,
                                                              int right,
                                                              int n,
                                                              int depthLimit) {
        if (left == right) {
            return;
        }

        if (depthLimit == 0) {
            Arrays.sort(array, left, right + 1);
            return;
        }

        int pivotIndex = partition(array, left, right);

        if (n < pivotIndex) {
            introSelect(array, left, pivotIndex - 1, n, depthLimit - 1); // Ищем в левой части
        } else if (n > pivotIndex) {
            introSelect(array, pivotIndex + 1, right, n, depthLimit - 1); // Ищем в правой части
        }
    }

    // Hoare partitioning
    private static <T extends Comparable<T>> int partition(T[] array, int left, int right) {
        T pivot = array[left];
        int i = left - 1;
        int j = right + 1;

        while (true) {
            // Find leftmost element greater
            // than or equal to pivot
            do {
                i++;
            } while (array[i].compareTo(pivot) < 0);

            // Find rightmost element smaller
            // than or equal to pivot
            do {
                j--;
            } while (array[j].compareTo(pivot) > 0);

            // If two pointers met.
            if (i >= j) {
                return j;
            }
            swap(array, i, j);
        }
    }

    private static <T> void swap(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
