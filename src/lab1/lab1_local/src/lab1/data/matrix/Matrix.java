// Програма з локалізацією змінних
// Matrix.java
// ВАРІАНТ 13
// В = D*MO-min(D)*C;                                      
// S = SORT(C*MO+D+C)

package lab1.data.matrix;

import lab1.data.DataType;

public class Matrix extends DataType {
	
	private int size;
	private double[][] matrix;

	public Matrix(int size, double[][] valueMA) {
		this.size = size;
		this.matrix = valueMA;
	}
	
	public static Matrix fromString(String str) {
		String[] lines = str.trim().split("\n");
		int size = lines.length;
		final double[][] matrix = new double[size][size];
		for (int i = 0; i < size; i++) {
			String[] elements = lines[i].split(" ");
			for (int j = 0; j < size; j++) {
				matrix[i][j] = Double.parseDouble(elements[j]);
			}
		}
		return new Matrix(size, matrix);
	}
	
	@Override
	public String toString() {
		String str = "";
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				str += matrix[i][j] + " ";
			}
			str += "\n";
		}
		return str;
	}
	
	public double[][] getValue() {
		return matrix;
	}
}
