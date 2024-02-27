// Програма з локалізацією змінних
// VectorManager.java
// ВАРІАНТ 13
// В = D*MO-min(D)*C;                                      
// S = SORT(C*MO+D+C)

package lab1.data.vector;

import java.io.IOException;

import lab1.data.DataFinder;
import lab1.data.generator.DoubleArrayGenerator;
import lab1.fs.MemFileSystem;

public class VectorManager {

	final private MemFileSystem fs;
	final private DataFinder df;
	final private DoubleArrayGenerator dg;

	public VectorManager() {
		this.fs = new MemFileSystem();
		this.df = new DataFinder();
		this.dg = new DoubleArrayGenerator();
	}

	public Vector createNew(String name, int size, int minVal, int maxVal, int minPrecision, int maxPrecision) /*throws IOException*/ {
		return new Vector(size, dg.generateDoubleArray(size, minVal, maxVal, minPrecision, maxPrecision));
	}

	private Vector readFromFile(String file, String name, int size) throws IOException {
		String contents = fs.read(file).trim();
		if (!contents.contains(name + "\n")) {
			return null;
		}
		return Vector.fromString(df.findVector(contents, name));
	}

	public Vector getVector(String file, String name, int size) throws /*InterruptedException,*/ IOException {
		return readFromFile(file, name, size);
	}
}
