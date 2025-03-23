package com.algo.nth_element;

public class Main {
    public static void main(String[] args) {
        Integer[] arr = {25, 3, 15, 10, 13, 22, 80, 1, 4, 2, 20, 1, 3};
        NthElementUtil.nthElement(arr, 10);
        for (Integer el : arr) {
            System.out.println(el);
        }
    }
}
