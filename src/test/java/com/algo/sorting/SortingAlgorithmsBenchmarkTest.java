package com.algo.sorting;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

public class SortingAlgorithmsBenchmarkTest {
    private static final Random random = new Random();
    private static final int NUM_RUNS = 10; // Количество запусков для усреднения
    private static final int ARRAY_SIZE = 100000; // Размер массива
    private static final Integer[] array = new Integer[ARRAY_SIZE];

    @BeforeAll
    public static void init(){
        generateArray(array);
    }
    @Test
    public void randomBenchmarkNthElement() {
        int index = ARRAY_SIZE/2;
        for (int i = 0; i < 5; i++) {
            SortingAlgorithms.nthElement(array, index);
        }

        long totalDuration = 0;
        for (int i = 0; i < NUM_RUNS; i++) {
            Integer[] testArray = array.clone();
            long startTime = System.nanoTime();
            SortingAlgorithms.nthElement(testArray, index);
            long endTime = System.nanoTime();
            totalDuration += (endTime - startTime);
        }
        long averageDuration = totalDuration / NUM_RUNS;
        System.out.println("Benchmark 1 (Random distribution)\n \tSIZE=100000; N=50000;" + averageDuration + " nanoseconds");
        System.out.println("-----------------------------------------");
    }

    @Test
    public void sortedBenchmarkNthElement() {
        int index = ARRAY_SIZE/2;
        for (int i = 0; i < 5; i++) {
            SortingAlgorithms.nthElement(array, index);
        }

        long totalDuration = 0;
        Arrays.sort(array);
        for (int i = 0; i < NUM_RUNS; i++) {
            Integer[] testArray = array.clone(); // Клонируем массив для каждого запуска
            long startTime = System.nanoTime();
            SortingAlgorithms.nthElement(testArray, index);
            long endTime = System.nanoTime();
            totalDuration += (endTime - startTime);
        }
        long averageDuration = totalDuration / NUM_RUNS;
        System.out.println("Benchmark 2 (sorted array)\n \tSIZE=100000; N=50000;" + averageDuration + " nanoseconds");
        System.out.println("-----------------------------------------");
    }
    @Test
    public void stressTests() {
        for (int test=0; test<1000; test++) {
            int n = random.nextInt(1, 10000);
            Integer[] arr = new Integer[n];
            generateArray(arr);
            Integer[] sorted_arr = arr.clone();
            Arrays.sort(sorted_arr);

            int index = random.nextInt(n);

            SortingAlgorithms.nthElement(arr, index);
            Assertions.assertEquals(sorted_arr[index], arr[index]);
        }
    }

    private static void generateArray(Integer[] arr) {
        for (int i=0; i<arr.length; i++) {
            arr[i] = random.nextInt(-100, 100);
        }
    }


}
