import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;


@Component
public class PaypalChallenge {

	public List<Integer[]> fourNumberSum(int[] array, int targetSum) {

		List<Integer[]> results = new ArrayList<>();
		int numCount = array.length;

		// store each distinct pair of positions in the input array, e.g. 0-1, 0-2, 0,3, 1,2, 1,3, 2,3
		List<Pair> pairs = new ArrayList<>();

		for (int i = 0; i < numCount; i++) {
			for (int j = i + 1; j < numCount; j++) {
				pairs.add(Pair.of(i, j));
			}
		}
		// hash each quadruplet that represents a valid output in order to avoid duplicates
		Map<Integer, Integer> hashedQuadruplets = new HashMap<>();

		for (int i = 0; i < pairs.size()-1; i++) {
			for (int k = i + 1; k < pairs.size(); k++) {

				// get the two positions from both pairs being compared
				Integer a1 = (Integer) pairs.get(i).getLeft();
				Integer a2 = (Integer) pairs.get(i).getRight();
				Integer b1 = (Integer) pairs.get(k).getLeft();
				Integer b2 = (Integer) pairs.get(k).getRight();

				// instantiate an array with the potential result
				Integer[] result = new Integer[] { array[a1], array[a2], array[b1], array[b2] };
				// sort the potential results to prevent equal values in different order altering the hashing
				Arrays.sort(result);
				Integer hashedResult = Objects.hash(result[0], result[1], result[2], result[3]);

				// conditions:
				// all positions in the pairs being compared are distinct
				// the sum of the values given by the positions equal the target sum
				// no hash exists for the given results array
				if (a1 != b1 &&
					a1 != b2 &&
					a2 != b1 &&
					a2 != b2 &&
					targetSum == array[a1] + array[a2] + array[b1] + array[b2] &&
					hashedQuadruplets.get(hashedResult) == null
				) {
					results.add(result);
					// if a new result is added, add corresponding hash to the map
					hashedQuadruplets.put(hashedResult, 0);
				}
			}
		}
		return results;
	}
}
