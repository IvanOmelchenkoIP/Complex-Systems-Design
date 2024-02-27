// Програма з глобальними змінними
// Vector.java
// ВАРІАНТ 13
// В = D*MO-min(D)*C;                                      
// S = SORT(C*MO+D+C)

package lab1.data.vector;

import java.util.Arrays;

import lab1.data.DataType;
import lab1.data.calc.KahanSum;
import lab1.data.matrix.Matrix;

public class Vector extends DataType {

	final private int size;
	final private double[] vector;
	
	public Vector(int size, double[] vector) {
		this.size = size;
		this.vector = vector;
	}
	
	public static Vector fromString(String str) {
		String[] elements = str.trim().split(" ");
		int size = elements.length;
		final double[] vector = new double[size];
		for (int i = 0; i < size; i++) {
			vector[i] = Double.parseDouble(elements[i]);
		}
		return new Vector(size, vector);
	}
	
	@Override
	public String toString() {
		String str = "";
		for (double a : vector) {
			str += a + " ";
		}
		str += "\n";
		return str;
	}
	
	public double[] getValue() {
		return vector;
	}
	
	public Vector getMatrixMultiplyProduct(Matrix MA) {
		double[][] matrix = MA.getValue();
		double[] result = new double[size];
		for (int i = 0; i < size; i++) {
			double[] products = new double[size];
			for (int j = 0; j < size; j++) {
				products[j] = matrix[i][j] * vector[j];
			}
			result[i] = KahanSum.calculate(products);
		}
		return new Vector(size, result);
	}
	
	public Vector getScalarMultiplyProduct(double a) {
		double[] result = new double[size];
		for (int i = 0; i < size; i++) {
			result[i] = a * result[i];
		}
		return new Vector(size, result);
	}
	
	public Vector getVectorDifference(Vector A) {
		double[] other = A.getValue();
		double[] result = new double[size];
		for (int i = 0; i < size; i++) {
			double[] args = { vector[i], -1 * other[i] };
			result[i] = KahanSum.calculate(args);
		}
		return new Vector(size, result);
	}
	
	public Vector getVectorSum(Vector A) {
		double[] other = A.getValue();
		double[] result = new double[size];
		for (int i = 0; i < size; i++) {
			double[] args = { vector[i], other[i] };
			result[i] = KahanSum.calculate(args);
		}
		return new Vector(size, result);
	}
	
	public double min() {
		double minimal = vector[0];
		for (int i = 1; i < size; i++) {
			if (minimal > vector[i]) {
				minimal = vector[i];
			}
		}
		return minimal;
	}
	
	public void sort() {
		Arrays.sort(vector);
	}
} 
