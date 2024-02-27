// Програма з глобальними змінними
// GlobalData.java
// ВАРІАНТ 13
// В = D*MO-min(D)*C;                                      
// S = SORT(C*MO+D+C)

package lab1.data;

import lab1.data.generator.OutputFileGenerator;
import lab1.data.matrix.Matrix;
import lab1.data.vector.Vector;

public class GlobalData {

	public static Vector C = null;
	public static Vector D = null;
	public static Matrix MO = null;
	public static OutputFileGenerator output = new OutputFileGenerator();
}
