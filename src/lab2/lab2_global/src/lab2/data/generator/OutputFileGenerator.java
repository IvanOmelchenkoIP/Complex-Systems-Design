// Лабораторна робота 2
// OutputFileGenerator.java
// ВАРІАНТ 13
// В = D*MO-min(D)*C;                                      
// S = SORT(C*MO+D+C)

package lab2.data.generator;

import java.io.IOException;

import lab2.data.DataType;
import lab2.fs.FileSystem;

public class OutputFileGenerator {

	final private FileSystem fs;
	
	public OutputFileGenerator() {
		fs = new FileSystem();
	}
	
	public void write(String file, String name, DataType data) throws IOException {
		fs.write(file, name + "\n" + data.toString());
	}
}
