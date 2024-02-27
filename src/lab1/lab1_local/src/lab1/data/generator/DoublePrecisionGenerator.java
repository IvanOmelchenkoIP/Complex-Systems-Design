// Програма з локалізацією змінних
// DoublePrecisionGenerator.java
// ВАРІАНТ 13
// В = D*MO-min(D)*C;                                      
// S = SORT(C*MO+D+C)

package lab1.data.generator;

import java.util.concurrent.ThreadLocalRandom;

public class DoublePrecisionGenerator {

	public double generate(int min, int max, int precision) {
		double num = ThreadLocalRandom.current().nextDouble(min, max);
		long order = Long.parseLong(("1" + "0".repeat(precision)).trim());
		return ((double)Math.round(num * order) / (double)order);
	}
}
