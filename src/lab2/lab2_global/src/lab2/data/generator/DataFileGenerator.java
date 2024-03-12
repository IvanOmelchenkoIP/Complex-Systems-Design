// Лабораторна робота 2
// DataFileGenerator.java
// ВАРІАНТ 13
// В = D*MO-min(D)*C;                                      
// S = SORT(C*MO+D+C)

package lab2.data.generator;

import java.io.IOException;

import lab2.data.DataType;
import lab2.data.matrix.MatrixManager;
import lab2.data.vector.VectorManager;
import lab2.fs.MemFileSystem;

public class DataFileGenerator {

	final private VectorManager vm;
	final private MatrixManager mm;
	final private MemFileSystem fs;

	public DataFileGenerator() {
		vm = new VectorManager();
		mm = new MatrixManager();
		fs = new MemFileSystem();
	}

	public void tryGenerate(String file, String[] names, int size, int minVal, int maxVal, int minPrecision,
			int maxPrecision) throws Exception, IOException {
		if (fs.exists(file)) {
			throw new Exception("File with data has already been created!");
		}
		for (String name : names) {
			switch (name.length()) {
			case 1 -> generateVector(file, name, size, minVal, maxVal, minPrecision, maxPrecision);
			case 2 -> generateMatrix(file, name, size, minVal, maxVal, minPrecision, maxPrecision);
			}
		}
	}

	private void generateVector(String file, String name, int size, int minVal, int maxVal, int minPrecision,
			int maxPrecision) throws IOException {
		try {
			DataType data = vm.createNew(name, size, minVal, maxVal, minPrecision, maxPrecision);
			writeToFile(file, name, data);
		} catch (IOException e) {
			throw new IOException("There was an error generating vector " + name + ":\n" + e.getMessage());
		}
	}

	private void generateMatrix(String file, String name, int size, int minVal, int maxVal, int minPrecision,
			int maxPrecision) throws IOException {
		try {
			DataType data = mm.createNew(name, size, minVal, maxVal, minPrecision, maxPrecision);
			writeToFile(file, name, data);
		} catch (IOException e) {
			throw new IOException("There was an error generating matrix " + name + ":\n" + e.getMessage());
		}
	}

	private void writeToFile(String file, String name, DataType data) throws IOException {
		fs.write(file, name + "\n" + data.toString());
	}
}
