// Лабораторна робота 3
// hardware transactional memory
// FileSystem.java

package lab3.fs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileSystem {

	public void write(String filepath, String data) throws IOException {
		if (!exists(filepath)) {
			Files.write(Paths.get(filepath), data.getBytes(), StandardOpenOption.CREATE_NEW);
		} else {
			Files.write(Paths.get(filepath), data.getBytes(), StandardOpenOption.APPEND);
		}
	}

	public boolean exists(String filepath) {
		return new File(filepath).exists();
	}
}
