// Програма з локалізацією змінних
// Double2DArrayGenerator.java
// ВАРІАНТ 13
// В = D*MO-min(D)*C;                                      
// S = SORT(C*MO+D+C)

package lab1.data.generator;

public class Double2DArrayGenerator extends DoubleArrayGenerator {

	public double[][] generate2DDoubleArray(int size, int min, int max, int minPrecision, int maxPrecision) {
		final double[][] array = new double[size][size];
		for (int i = 0; i < size; i++) {
			array[i] = generateDoubleArray(size, min, max, minPrecision, maxPrecision);
		}
		return array;
	}
}
