// Лабораторна робота 2
// DoubleRangePrecisionGenerator.java
// ВАРІАНТ 13
// В = D*MO-min(D)*C;                                      
// S = SORT(C*MO+D+C)

package lab2.data.generator;

import java.util.concurrent.ThreadLocalRandom;

public class DoubleRangePrecisionGenerator extends DoublePrecisionGenerator {

	public double generate(int min, int max, int minPrecision, int maxPrecision) {
		return generate(min, max, ThreadLocalRandom.current().nextInt(minPrecision, maxPrecision));
	}
}
