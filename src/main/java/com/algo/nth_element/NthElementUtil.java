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

    private static void merge(int arr[], int l, int m, int r) {
        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;

        // Create temp arrays
        int L[] = new int[n1];
        int R[] = new int[n2];

        // Copy data to temp arrays
        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];

        // Merge the temp arrays
        // Initial indices of first and second subarrays
        int i = 0, j = 0;
        // Initial index of merged subarray array
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        // Copy remaining elements of L[] if any
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        // Copy remaining elements of R[] if any
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    private static void mergeSort(int arr[], int l, int r) {
        if (l < r) {
            // Find the middle point
            int m = l + (r - l) / 2;

            // Sort first and second halves
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);

            // Merge the sorted halves
            merge(arr, l, m, r);
        }
    }
}
