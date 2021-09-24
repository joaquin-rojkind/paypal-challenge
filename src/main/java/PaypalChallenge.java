import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;


@Component
public class PaypalChallenge {

	public List<Integer[]> fourNumberSum(int[] array, int targetSum) {

		List<Integer[]> results = new ArrayList<>();
		int numCount = array.length;

		for (int i = 0; i < numCount - 3; i++) {
			for (int j = i + 1; j < numCount - 2; j++) {
				for (int k = j + 1; k < numCount - 1; k++) {
					for (int l = k + 1; l < numCount; l++) {
						int sum = array[i] + array[j] + array[k] + array[l];
						if (sum == targetSum) {
							results.add(new Integer[] { array[i], array[j], array[k], array[l] } );
						}
					}
				}
			}
		}
		return results;
	}
}
