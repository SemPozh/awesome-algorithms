package com.algo.structure;

import com.algo.structure.bloom.BloomFilter;
import com.algo.structure.bloom.BloomFilterConfig;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BloomFilterTest {

    @Test
    void testFalsePositiveProbability_givesExpectedProbability() {
        int elementsCount = 1000;
        double falsePositiveProbability = 0.1;

        var bloomFiler = new BloomFilter(new BloomFilterConfig.Builder()
                .withExpectedElementsCount(elementsCount)
                .withFalsePositiveProbability(falsePositiveProbability)
                .build()
        );

        double eps = 0.2;
        double actualProbability = getActualProbability(bloomFiler, elementsCount);
        System.out.println(actualProbability);
        assertTrue(Math.abs(actualProbability - falsePositiveProbability) < eps);
    }


    @Test
    public void testEmptyFilter_returnsZeroCardinality() {
        var filter = new BloomFilter(new BloomFilterConfig.Builder().build());

        assertEquals(0.0, filter.estimatedCardinality(), 0.001);
    }



    @Test
    void testEstimateElementCount() {
        int elementsCount = 10000;
        double eps = 0.20;

        var filter = new BloomFilter(
                new BloomFilterConfig.Builder()
                        .withExpectedElementsCount(elementsCount)
                        .build()
        );

        for (int i = 0; i < elementsCount; i++) {
            filter.putInt(i);
        }

        double estimated = filter.estimatedCardinality();
        assertTrue(Math.abs(estimated - elementsCount) <= elementsCount * eps);
    }

    private static double getActualProbability(BloomFilter bloomFilter, int elementCount) {

        for (int i = 0; i < elementCount; i++) {
            bloomFilter.putInt(i);
        }

        // Проверка ложных срабатываний
        int falsePositives = 0;
        int totalChecks = 100000;
        for (int i = elementCount; i < elementCount + totalChecks; i++) {
            if (bloomFilter.mightContainInt(i)) {
                falsePositives++;
            }
        }
        return (double) falsePositives / totalChecks;
    }
}
