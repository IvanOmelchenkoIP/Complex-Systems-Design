// Лабораторна робота 2
// DataFinder.java
// ВАРІАНТ 13
// В = D*MO-min(D)*C;                                      
// S = SORT(C*MO+D+C)

package lab2.data;

import java.util.Arrays;

public class DataFinder {

	public String findMatrix(String contents, String matrixName, int size) {
		String[] lines = contents.split("\n");
		int index = Arrays.asList(lines).indexOf(matrixName) + 1;
		String matrix = String.join("\n", Arrays.copyOfRange(lines, index, index + size));
		return matrix;
	}
	
	public String findVector(String contents, String vectorName) {
		return findMatrix(contents, vectorName, 1);
	}
}
