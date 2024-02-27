// Програма з локалізацією змінних
// ThreadB.java
// ВАРІАНТ 13
// В = D*MO-min(D)*C;                                      
// S = SORT(C*MO+D+C)

package lab1.thread;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import lab1.data.Result;
import lab1.data.matrix.Matrix;
import lab1.data.matrix.MatrixManager;
import lab1.data.vector.Vector;
import lab1.data.vector.VectorManager;

public class ThreadB implements Runnable {

	private CountDownLatch countDownLatch;
	private String file;
	private int N;
	private VectorManager vm;
	private MatrixManager mm;
	private Result result;
	
	public ThreadB(int N, String file, CountDownLatch countDownLatch, VectorManager vm, MatrixManager mm, Result result) {
		this.N = N;
		this.file = file;
		this.countDownLatch = countDownLatch;
		this.vm = vm;
		this.mm = mm;
		this.result = result;
	}
	
	@Override
	public void run() {
		Matrix MO;
		Vector D, C;
		try {
			MO = mm.getMatrix(file, "MO", N);
			D = vm.getVector(file, "D", N);
			C = vm.getVector(file, "C", N);
		} catch (IOException e) {
			countDownLatch.countDown();
			System.out.println("Неможливо продовжити обчислення вектора B \n" + e.getMessage());
			return;
		}
		Vector B = D.getMatrixMultiplyProduct(MO).getVectorDifference(C.getScalarMultiplyProduct(D.min()));
		result.setResult(B);
		countDownLatch.countDown();
	}
}
