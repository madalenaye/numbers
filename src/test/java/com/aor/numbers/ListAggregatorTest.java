package com.aor.numbers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

public class ListAggregatorTest {
    List<Integer> list;
    List<Integer> list2;
    List<Integer> list3;
    List<Integer> list4;

    @BeforeEach
    public void helper(){
        list = Arrays.asList(1,2,4,2,5);
        list2 = Arrays.asList(-1,-4,-5);
        list3 = Arrays.asList(1,2,4,2);
        list4 = Arrays.asList(1,2,4,2,5,5,1,6,Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    @Test
    public void sum() {

        ListAggregator aggregator = new ListAggregator();
        int sum = aggregator.sum(list);

        Assertions.assertEquals(14, sum);
    }

    @Test
    public void max() {

        ListAggregator aggregator = new ListAggregator();
        int max = aggregator.max(list4);

        Assertions.assertEquals(Integer.MAX_VALUE, max);
    }
    @Test
    public void max_bug_7263() {

        ListAggregator aggregator = new ListAggregator();
        int max = aggregator.max(list2);

        Assertions.assertEquals(-1, max);
    }

    @Test
    public void min() {

        ListAggregator aggregator = new ListAggregator();
        int min = aggregator.min(list4);

        Assertions.assertEquals(Integer.MIN_VALUE, min);
    }

    @Test
    public void distinct() {

        ListAggregator aggregator = new ListAggregator();

        GenericListDeduplicator deduplicator = Mockito.mock(GenericListDeduplicator.class);
        Mockito.when(deduplicator.deduplicate(Mockito.anyList())).thenReturn(Arrays.asList(1, 2, 2,4,5));

        int distinct = aggregator.distinct(list, deduplicator);

        Assertions.assertEquals(5, distinct);
    }

    @Test
    public void distinct_bug_8726() {

        ListAggregator aggregator = new ListAggregator();

        GenericListDeduplicator deduplicator = Mockito.mock(GenericListDeduplicator.class);
        Mockito.when(deduplicator.deduplicate(Mockito.anyList())).thenReturn(Arrays.asList(1, 2, 4));

        int distinct = aggregator.distinct(list3,deduplicator);

        Assertions.assertEquals(3, distinct);
    }

}
