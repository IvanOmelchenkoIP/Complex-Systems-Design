// Лабораторна робота 2
// MatrixManager.java
// ВАРІАНТ 13
// В = D*MO-min(D)*C;                                      
// S = SORT(C*MO+D+C)

package lab2.data.matrix;

import java.io.IOException;

import lab2.data.DataFinder;
import lab2.data.generator.Double2DArrayGenerator;
import lab2.fs.MemFileSystem;

public class MatrixManager {

	final private MemFileSystem fs;
	final private DataFinder df;
	final private Double2DArrayGenerator dg;

	public MatrixManager() {
		this.fs = new MemFileSystem();
		this.df = new DataFinder();
		this.dg = new Double2DArrayGenerator();
	}

	public Matrix createNew(String name, int size, int minVal, int maxVal, int minPrecision, int maxPrecision) {
		return new Matrix(size, dg.generate2DDoubleArray(size, minVal, maxVal, minPrecision, maxPrecision));
	}

	private Matrix getFromFile(String file, String name, int size) throws IOException {
		String contents = fs.read(file).trim();
		if (!contents.contains(name + "\n")) {
			return null;
		}
		return Matrix.fromString(df.findMatrix(contents, name, size));
	}

	public Matrix getMatrix(String file, String name, int size) throws IOException {
		return getFromFile(file, name, size);
	}
}
