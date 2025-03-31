package com.algo.sorting;

import java.lang.reflect.Array;

@SuppressWarnings("unchecked")
public class SortingAlgorithms {
    public static <T extends Comparable<T>> void nthElement(T[] array, int n) {
        if (n < 0 || n >= array.length) {
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
            mergeSort(array, array.length);
            return;
        }

        int pivotIndex = hoarePartition(array, left, right);

        if (pivotIndex > n) {
            introSelect(array, left, pivotIndex - 1, n, depthLimit - 1);
        } else if (pivotIndex < n) {
            introSelect(array, pivotIndex + 1, right, n, depthLimit - 1);
        }
    }

    private static <T extends Comparable<T>> int hoarePartition(T[] arr, int low, int high) {
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

    public static <T extends Comparable<T>> void merge(T[] arr, T[] l, T[] r, int left, int right) {
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < left && j < right) {
            if (l[i].compareTo(r[j]) <= 0) {
                arr[k++] = l[i++];
            } else {
                arr[k++] = r[j++];
            }
        }
        while (i < left) {
            arr[k++] = l[i++];
        }
        while (j < right) {
            arr[k++] = r[j++];
        }
    }

    private static <T> T[] createArray(Class<T> clazz, int size) {
        return (T[]) Array.newInstance(clazz, size);
    }

    public static <T extends Comparable<T>> void mergeSort(T[] arr, int n) {
        if (n < 2) {
            return;
        }
        int mid = n / 2;
        T[] l = createArray((Class<T>) arr.getClass().getComponentType(), mid);
        T[] r = createArray((Class<T>) arr.getClass().getComponentType(), n - mid);

        System.arraycopy(arr, 0, l, 0, mid);
        if (n - mid >= 0) System.arraycopy(arr, mid, r, 0, n - mid);
        mergeSort(l, mid);
        mergeSort(r, n - mid);

        merge(arr, l, r, mid, n - mid);
    }
}
