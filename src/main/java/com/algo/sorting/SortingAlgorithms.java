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
        if (array.length < 20) {
            bubbleSort(array);
            return;
        }

        if (depthLimit == 0) {
            mergeSort(array);
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

    public static <T extends Comparable<T>> void mergeSort(T[] arr) {
        @SuppressWarnings("unchecked")
        T[] aux = (T[]) Array.newInstance(arr.getClass().getComponentType(), arr.length);
        mergeSort(arr, aux, 0, arr.length);
    }

    private static <T extends Comparable<T>> void mergeSort(T[] arr, T[] aux, int low, int high) {
        if (high - low < 2) {
            return;
        }
        int mid = (low + high) / 2;
        mergeSort(arr, aux, low, mid);
        mergeSort(arr, aux, mid, high);
        merge(arr, aux, low, mid, high);
    }

    private static <T extends Comparable<T>> void merge(T[] arr, T[] aux, int low, int mid, int high) {
        System.arraycopy(arr, low, aux, low, high - low);

        int i = low;
        int j = mid;
        int k = low;

        // Пошаговое слияние из aux обратно в arr
        while (i < mid && j < high) {
            if (aux[i].compareTo(aux[j]) <= 0) {
                arr[k++] = aux[i++];
            } else {
                arr[k++] = aux[j++];
            }
        }
        while (i < mid) {
            arr[k++] = aux[i++];
        }
    }

    private static <T extends Comparable<T>> void bubbleSort(T[] arr) {
        boolean swapped;
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j].compareTo(arr[j + 1]) > 0) {
                    T temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true; // Установим флаг, что была перестановка
                }
            }
            if (!swapped) {
                break;
            }
        }
    }
}
