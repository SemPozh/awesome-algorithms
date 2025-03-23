package com.algo.nth_element;

public class Main {
    public static void main(String[] args) {
        Integer[] arr = {2, 4, 1, 5, 3, 2, 9, 8, 11};
        NthElementUtil.nthElement(arr, 3);
        for (Integer el : arr) {
            System.out.println(el);
        }
    }
}
