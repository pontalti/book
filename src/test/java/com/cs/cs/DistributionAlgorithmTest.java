package com.cs.cs;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class DistributionAlgorithmTest {

	@ParameterizedTest
	@DisplayName("Test to distribute the order quantity by the number of executed executions.")
	@CsvSource({ "85, 2", "80, 2", "85, 3", "80, 3" })
	public void test_1(Integer quantity, Integer numberOfExecutions) {
		Integer initial = 1;
		AtomicInteger counter = new AtomicInteger(quantity);
        counter.incrementAndGet();

		List<Integer> quantList =  IntStream.range(initial, counter.intValue())
												.map(i -> Integer.valueOf(counter.intValue() - i + initial - 1))
												.boxed()
												.collect(Collectors.toList());
		
		Map<Integer, List<Integer>> groups = quantList
												.stream()
												.collect(Collectors.groupingBy(s -> (s - 1) / (quantList.size()/numberOfExecutions) ));
		List<List<Integer>> subSets = new ArrayList<List<Integer>>(groups.values());
		assertNotNull(subSets);	
		instanceOf(List.class);
	}

}
