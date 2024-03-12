// Лабораторна робота 2
// GlobalData.java
// ВАРІАНТ 13
// В = D*MO-min(D)*C;                                      
// S = SORT(C*MO+D+C)

package lab2.data;

import lab2.data.generator.OutputFileGenerator;
import lab2.data.matrix.Matrix;
import lab2.data.vector.Vector;

public class GlobalData {

	public static Vector C = null;
	public static Vector D = null;
	public static Matrix MO = null;
	public static OutputFileGenerator output = new OutputFileGenerator();
}
