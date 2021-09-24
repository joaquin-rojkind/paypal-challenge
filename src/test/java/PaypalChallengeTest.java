import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class PaypalChallengeTest {

	@InjectMocks
	private PaypalChallenge paypalChallenge;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	public static Stream<Arguments> fourNumberSumDataProvider() {
		return Stream.of(
			// arguments: input array, target sum, expected matching quadruplets
			// test case 1
			Arguments.of(
				new int[] { 7, 6, 4, -1, 1, 2 },
				16,
				Arrays.asList(
					new int[] { 7, 6, 4, -1 },
					new int[] { 7, 6, 1, 2 }
				)
			),
			// test case 2
			Arguments.of(
				new int[] { 1, 2, 3, 4, 5, 6, 7 },
				10,
				Arrays.asList(
					new int[] { 1, 2, 3, 4 }
				)
			),
			// test case 3
			Arguments.of(
				new int[] { 5, -5, -2, 2, 3, -3 },
				0,
				Arrays.asList(
					new int[] { 5, -5, -2, 2 },
					new int[] { 5, -5, 3, -3 },
					new int[] { -2, 2, 3, -3 }
				)
			),
			// test case 4
			Arguments.of(
				new int[] { -2, -1, 1, 2, 3, 4, 5, 6, 7, 8, 9 },
				4,
				Arrays.asList(
					new int[] { -2, -1, 1, 6 },
					new int[] { -2, 1, 2, 3 },
					new int[] { -2, -1, 2, 5 },
					new int[] { -2, -1, 3, 4 }
				)
			),
			// test case 5
			Arguments.of(
				new int[] { -1, 22, 18, 4, 7, 11, 2, -5, -3 },
				30,
				Arrays.asList(
					new int[] { -1, 22, 7, 2 },
					new int[] { 22, 4, 7, -3 },
					new int[] { -1, 18, 11, 2 },
					new int[] { 18, 4, 11, -3 },
					new int[] { 22, 11, 2, -5 }
				)
			),
			// test case 6
			Arguments.of(
				new int[] { -10, -3, -5, 2, 15, -7, 28, -6, 12, 8, 11, 5 },
				20,
				Arrays.asList(
					new int[] { -5, 2, 15, 8 },
					new int[] { -3, 2, -7, 28 },
					new int[] { -10, -3, 28, 5 },
					new int[] { -10, 28, -6, 8 },
					new int[] { -7, 28, -6, 5 },
					new int[] { -5, 2, 12, 11 },
					new int[] { -5, 12, 8, 5 }
				)
			),
			// test case 7
			Arguments.of(
				new int[] { 1, 2, 3, 4, 5 },
				100,
				Collections.emptyList()
			),
			// test case 8
			Arguments.of(
				new int[] { 1, 2, 3, 4, 5, -5, 6, -6 },
				5,
				Arrays.asList(
					new int[] { 2, 3, 5, -5 },
					new int[] { 1, 4, 5, -5 },
					new int[] { 2, 4, 5, -6 },
					new int[] { 1, 3, -5, 6 },
					new int[] { 2, 3, 6, -6 },
					new int[] { 1, 4, 6, -6 }
				)
			)
		);
	}

	@ParameterizedTest
	@MethodSource("fourNumberSumDataProvider")
	public void testFourNumberSum_success(int[] input, int targetSum, List<int[]> expectedMatches) {
		List<Integer[]> results = paypalChallenge.fourNumberSum(input, targetSum);
		List<int[]> resultsAsInt = convertToListOfIntArrays(results);

		assertThat(expectedMatches.size()).isEqualTo(results.size());
		expectedMatches.forEach(match -> {
			assertThat(
				// for every result check for any match within the expected matches
				resultsAsInt.stream().anyMatch(result -> Arrays.equals(match, result))
			).isTrue();
		});
	}

	/**
	 * Ideally this method should not be needed. It would be easier to declare the expected matches in the
	 * data provider as Integer arrays. However, this poses a problem, when there's one single expected match
	 * the method Arrays.asList(...) will create a list of the array's elements instead of a list with
	 * the array itself. I'd rather not sacrifice legibility and simpleness in the data provider by having to
	 * declare the lists on separate lines each, in the traditional way.
	 */
	private List<int[]> convertToListOfIntArrays(List<Integer[]> listOfIntegerArrays) {
		return listOfIntegerArrays.stream()
			.map(integerArray -> Stream.of(integerArray).mapToInt(i -> i).toArray())
			.collect(Collectors.toList());
	}
}
