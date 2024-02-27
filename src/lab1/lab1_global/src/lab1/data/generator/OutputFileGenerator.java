// Програма з глобальними змінними
// OutputFileGenerator.java
// ВАРІАНТ 13
// В = D*MO-min(D)*C;                                      
// S = SORT(C*MO+D+C)

package lab1.data.generator;

import java.io.IOException;

import lab1.data.DataType;
import lab1.fs.FileSystem;

public class OutputFileGenerator {

	final private FileSystem fs;
	
	public OutputFileGenerator() {
		fs = new FileSystem();
	}
	
	public void write(String file, String name, DataType data) throws IOException {
		fs.write(file, name + "\n" + data.toString());
	}
}
