package com.algo.structure;

import com.algo.structure.hash.factory.IntHashFunctionFactory;
import com.algo.structure.hash.factory.ObjectHashFunctionFactory;
import com.algo.structure.hash.factory.StringHashFunctionFactory;
import org.junit.jupiter.api.Test;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BloomFilterTest {

    @Test
    void testErrorProbability() {
        int bitsetSize = 1000;
        int hashFunctionsCount =  5;
        int elementsCount = 100;  // Количество элементов
        double eps = 0.01;

        double actualProbability = getActualProbability(bitsetSize, hashFunctionsCount, elementsCount);
        double expectedProbability = Math.pow(1 - Math.exp(-hashFunctionsCount * elementsCount / (double) bitsetSize), hashFunctionsCount);
        assertTrue(Math.abs(actualProbability - expectedProbability) < eps);
    }

    @Test
    void testFilterCapacityAssessment() {
        double p = 0.01; // Желаемая вероятность ложных срабатываний
        int n = 1000;  // Количество элементов
        int k = (int) Math.round(-Math.log(p) / Math.log(2)); // Оптимальное k при заданном p
        int bitsetSize = (int) Math.ceil(-n * Math.log(p) / (Math.pow(Math.log(2), 2)));
        double eps = 0.1;

        double actualProbability = getActualProbability(bitsetSize, k, n);
        assertTrue(Math.abs(actualProbability - p) < eps);
    }

    @Test
    public void testEmptyFilter() {
        var filter = new BloomFilter(
                1000,
                new IntHashFunctionFactory(),
                new StringHashFunctionFactory(),
                new ObjectHashFunctionFactory(),
                5,
                new Random(42)
        );

        assertEquals(0.0, filter.estimatedCardinality(), 0.001);
    }

    @Test
    void testEstimateElementCount() {
        int bitsetSize = 10000; // Размер битсета
        int k = 7;      // Количество хэш-функций
        int n = 500;    // Реальное количество элементов
        double tolerance = 0.20; // Допустимое отклонение 20%

        var filter = new BloomFilter(
                bitsetSize,
                new IntHashFunctionFactory(),
                new StringHashFunctionFactory(),
                new ObjectHashFunctionFactory(),
                k,
                new Random(42) // Фиксированный сид
        );

        // Добавляем n элементов
        for (int i = 0; i < n; i++) {
            filter.putInt(i); // Добавляем уникальные целые числа
        }

        // Получаем оценку
        double estimated = filter.estimatedCardinality();

        // Проверяем, что оценка близка к реальному n
        assertTrue(Math.abs(estimated - n) <= n * tolerance);
    }

    private static double getActualProbability(int bitsetSize, int k, int n) {
        var bloomFilter = new BloomFilter(
                bitsetSize,
                new IntHashFunctionFactory(),
                new StringHashFunctionFactory(),
                new ObjectHashFunctionFactory(),
                k,
                new Random(42)
        );

        for (int i = 0; i < n; i++) {
            bloomFilter.putInt(i);
        }

        // Проверка ложных срабатываний
        int falsePositives = 0;
        int totalChecks = 10000;
        for (int i = n; i < n + totalChecks; i++) {
            if (bloomFilter.mightContainInt(i)) {
                falsePositives++;
            }
        }
        return (double) falsePositives / totalChecks;
    }
}
