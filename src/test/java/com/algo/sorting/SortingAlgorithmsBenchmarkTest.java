package com.algo.sorting;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.File;
import java.io.IOException;
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

    @Test
    public void visualizeBenchmarks() throws IOException {
        int[] xData = new int[18];
        long[] yData = new long[18];

        int n = 5;
        for (int i = 0; i < 18; i++) {
            Integer[] arr = new Integer[n];
            generateArray(arr);

            for (int j = 0; j < 5; j++) {
                SortingAlgorithms.nthElement(arr, n/2);
            }
            long totalDuration = 0;
            for (int k = 0; k < NUM_RUNS; k++) {
                Integer[] testArray = arr.clone(); // Клонируем массив для каждого запуска
                long startTime = System.nanoTime();
                SortingAlgorithms.nthElement(testArray, n/2);
                long endTime = System.nanoTime();
                totalDuration += (endTime - startTime);
            }
            long averageDuration = totalDuration / NUM_RUNS;
            xData[i] = n;
            yData[i] = averageDuration;
            n*=2;
        }

        XYSeriesCollection dataset = createDataset(xData, yData);
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Time dependence on N", // Заголовок
                "N", // Ось X
                "time, ns", // Ось Y
                dataset// Данные
        );

        ChartUtils.saveChartAsPNG(new File("chart.png"), chart, 800, 600);
        System.out.println("График сохранен в файл chart.png");
    }

    private static XYSeriesCollection createDataset(int[] xData, long[] yData) {
        XYSeries series = new XYSeries("Данные");

        // Добавляем данные из массивов
        for (int i = 0; i < xData.length; i++) {
            series.add(xData[i], yData[i]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        return dataset;
    }



}
