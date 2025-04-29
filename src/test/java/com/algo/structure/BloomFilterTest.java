package com.algo.structure;

import com.algo.structure.hash.factory.IntHashFunctionFactory;
import com.algo.structure.hash.factory.ObjectHashFunctionFactory;
import com.algo.structure.hash.factory.StringHashFunctionFactory;
import org.junit.jupiter.api.Test;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BloomFilterTest {

    @Test
    void testErrorProbability() {
        int bitsetSize = 1000;
        int k = 5;    // Количество хэш-функций
        int n = 100;  // Количество элементов
        double eps = 0.01;

        BloomFilter bloomFilter = new BloomFilter(
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
        double actualProbability = (double) falsePositives / totalChecks;
        double expectedProbability = Math.pow(1 - Math.exp(-k * n / (double) bitsetSize), k);
        System.out.println(actualProbability);
        System.out.println(expectedProbability);
        assertTrue(Math.abs(actualProbability - expectedProbability) < eps);
    }

    @Test
    void testFilterCapacityAssessment() {
        double p = 0.01; // Желаемая вероятность ложных срабатываний
        int n = 1000;  // Количество элементов
        int k = (int) Math.round(-Math.log(p) / Math.log(2)); // Оптимальное k при заданном p
        int bitsetSize = (int) Math.ceil(-n * Math.log(p) / (Math.pow(Math.log(2), 2))); // Теоретический размер
        double eps = 0.1;

        BloomFilter bloomFilter = new BloomFilter(
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
        double actualProbability = (double) falsePositives / totalChecks;
        System.out.println(actualProbability);
        assertTrue(Math.abs(actualProbability - p) < eps);
    }
}
