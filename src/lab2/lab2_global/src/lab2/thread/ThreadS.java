// Програма з глобальними змінними
// ThreadS.java
// ВАРІАНТ 13
// В = D*MO-min(D)*C;                                      
// S = SORT(C*MO+D+C)

package lab2.thread;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

import lab2.data.GlobalData;
import lab2.data.vector.Vector;

public class ThreadS implements Runnable {

	private String file;	
	private CountDownLatch countDownLatch;
	private Lock matrixLock;
	private Lock vectorLock;
	private Lock outputLock;
	
	public ThreadS(String file, CountDownLatch countDownLatch, Lock matrixLock, Lock vectorLock, Lock outputLock) {
		this.file = file;
		this.countDownLatch = countDownLatch;
		this.matrixLock = matrixLock;
		this.vectorLock = vectorLock;
		this.outputLock = outputLock;
	}
	
	@Override
	public void run() {
		Vector MO_C, D_C;

		matrixLock.lock();
		MO_C = GlobalData.C.getMatrixMultiplyProduct(GlobalData.MO);
		matrixLock.unlock();
		
		vectorLock.lock();
		D_C = GlobalData.C.getVectorSum(GlobalData.D);
		vectorLock.unlock();
		
		Vector S = MO_C.getVectorSum(D_C);
		S.sort();
		outputLock.lock();
		try {
			System.out.println("S\n" + S.toString());
			GlobalData.output.write(file, "S",S);
		} catch (IOException e) {
			System.out.println("Помилка при записі вектора S \n" + e.getMessage());
			return;
		} finally {
			outputLock.unlock();
			countDownLatch.countDown();
		}
	}
}
