// Лабораторна робота 2
// MemFileSystem.java
// ВАРІАНТ 13
// В = D*MO-min(D)*C;                                      
// S = SORT(C*MO+D+C)

package lab2.fs;

import java.io.IOException;
import java.util.HashMap;

public class MemFileSystem extends FileSystem {

private final static HashMap<String, String> files = new HashMap<String, String>();
	
	@Override
	public String read(String filepath) throws IOException {
		String contents = files.get(filepath);
		if (contents == null) {
			contents = super.read(filepath);
			files.put(filepath, contents);
		}
		return contents;
	}
}
