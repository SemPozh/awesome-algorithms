package com.algo.nth_element;

import java.util.Arrays;

public class NthElementUtil {
    public static <T extends Comparable<T>> void nthElement(T[] array, int n) {
        if (array == null || n < 0 || n >= array.length) {
            throw new IllegalArgumentException("Index n is out of range");
        }

        int depthLimit = 2 * (int) Math.log(array.length);
        introSelect(array, 0, array.length - 1, depthLimit);
    }

    private static <T extends Comparable<T>> void introSelect(T[] array,
                                                              int left,
                                                              int right,
                                                              int depthLimit) {
        if (left == right) {
            return;
        }

        if (depthLimit == 0) {
            Arrays.sort(array, left, right + 1);
            return;
        }

        int pivotIndex = partition(array, left, right);
        if (array.length < pivotIndex) {
            introSelect(array, left, pivotIndex - 1, depthLimit - 1); // Ищем в левой части
        } else if (array.length > pivotIndex) {
            introSelect(array, pivotIndex + 1, right, depthLimit - 1); // Ищем в правой части
        }
    }

    private static <T extends Comparable<T>> int partition(T[] array, int left, int right) {
        T pivot = array[right];
        int i = left;

        for (int j = left; j < right; j++) {
            if (array[j].compareTo(pivot) < 0) {
                swap(array, i, j);
                i++;
            }
        }
        swap(array, i, right);
        return i;
    }

    private static <T> void swap(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
