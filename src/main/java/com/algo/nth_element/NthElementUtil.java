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
        if (depthLimit == 0) {
            Arrays.sort(array, left, right + 1);
            return;
        }

        int pivotIndex = partition(array, left, right);

        if (pivotIndex > n) {
            introSelect(array, left, pivotIndex - 1, n, depthLimit - 1); // Ищем в левой части
        } else if (pivotIndex < n) {
            introSelect(array, pivotIndex + 1, right, n, depthLimit - 1); // Ищем в правой части
        }
    }

    private static <T extends Comparable<T>> int partition(T[] arr, int low, int high) {
        T pivot = arr[high];
        int pivotloc = low;
        for (int i = low; i <= high; i++) {
            // inserting elements of less value
            // to the left of the pivot location
            if (arr[i].compareTo(pivot) < 0) {
                swap(arr, i, pivotloc);
                pivotloc++;
            }
        }

        // swapping pivot to the final pivot location
        swap(arr, high, pivotloc);

        return pivotloc;
    }

    private static <T> void swap(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
