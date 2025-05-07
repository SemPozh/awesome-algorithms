package com.algo.structure.set;

import com.algo.structure.bloom.BloomFilter;
import com.algo.structure.bloom.BloomFilterConfig;
import com.algo.structure.bloom.GenericBloomFilter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ShardedHashSet<T> {
    private final List<Partition<T>> partitions;

    public ShardedHashSet(int partitionsCount,
                          int expectedElementsPerPartition,
                          double falsePositiveProbability) {
        this.partitions = new ArrayList<>(partitionsCount);


        for (int i = 0; i < partitionsCount; i++) {
            partitions.add(new Partition<>(
                    new GenericBloomFilter<T>(
                            new BloomFilter(
                                    new BloomFilterConfig.Builder()
                                            .withExpectedElementsCount(expectedElementsPerPartition)
                                            .withFalsePositiveProbability(falsePositiveProbability)
                                            .build()
                            )
                    ),
                    new HashSet<>())
            );
        }
    }

    public void add(T element) {
        int partitionIndex = calculateIndex(element);
        Partition<T> partition = partitions.get(partitionIndex);

        partition.filter.put(element);
        partition.set.add(element);
    }

    public boolean contains(T element) {
        int partitionIndex = calculateIndex(element);
        Partition<T> partition = partitions.get(partitionIndex);

        if (!partition.filter.mightContain(element)) {
            return false;
        }

        return partition.set.contains(element);

    }

    public int size() {
        return partitions.stream()
                .mapToInt(p -> p.set.size())
                .sum();
    }

    private int calculateIndex(T element) {
        return Math.abs(element.hashCode() % partitions.size());
    }

    private record Partition<T>(GenericBloomFilter<T> filter, HashSet<T> set) {}
}
