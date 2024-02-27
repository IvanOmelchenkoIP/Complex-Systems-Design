// Програма з глобальними змінними
// DoubleArrayGenerator.java
// ВАРІАНТ 13
// В = D*MO-min(D)*C;                                      
// S = SORT(C*MO+D+C)

package lab1.data.generator;

public class DoubleArrayGenerator {
	
	DoubleRangePrecisionGenerator doubleGenerator;
	
	public DoubleArrayGenerator() {
		this.doubleGenerator = new DoubleRangePrecisionGenerator();
	}
	
	public double[] generateDoubleArray(int size, int min, int max, int minPrecision, int maxPrecision) {
		final double[] array = new double[size];
		for (int i = 0; i < size; i++) {
			array[i] = doubleGenerator.generate(min, max, minPrecision, maxPrecision);
		}
		return array;
	}
}
