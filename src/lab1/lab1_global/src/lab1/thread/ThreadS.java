// Програма з глобальними змінними
// ThreadS.java
// ВАРІАНТ 13
// В = D*MO-min(D)*C;                                      
// S = SORT(C*MO+D+C)

package lab1.thread;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

import lab1.data.GlobalData;
import lab1.data.vector.Vector;

public class ThreadS implements Runnable {

	private String file;	
	private CountDownLatch countDownLatch;
	private Semaphore matrixSemaphore;
	private Semaphore vectorSemaphore;
	private Semaphore outputSemaphore;
	
	public ThreadS(String file, CountDownLatch countDownLatch, Semaphore matrixSemaphore, Semaphore vectorSemaphore, Semaphore outputSemaphore) {
		this.file = file;
		this.countDownLatch = countDownLatch;
		this.matrixSemaphore = matrixSemaphore;
		this.vectorSemaphore = vectorSemaphore;
		this.outputSemaphore = outputSemaphore;
	}
	
	@Override
	public void run() {
		Vector MO_C, D_C;
		try {
			matrixSemaphore.acquire();
			MO_C = GlobalData.C.getMatrixMultiplyProduct(GlobalData.MO);
		} catch (InterruptedException e) {
			countDownLatch.countDown();
			System.out.println("Неможливо продовжити обчислення вектора B \n" + e.getMessage());
			return;
		} finally {
			matrixSemaphore.release();
		}
		try {
			vectorSemaphore.acquire();
			D_C = GlobalData.C.getVectorSum(GlobalData.D);
		} catch (InterruptedException e) {
			countDownLatch.countDown();
			System.out.println("Неможливо продовжити обчислення вектора B \n" + e.getMessage());
			return;
		} finally {
			vectorSemaphore.release();
		}
		Vector S = MO_C.getVectorSum(D_C);
		S.sort();
		try {
			outputSemaphore.acquire();
			System.out.println("S\n" + S.toString());
			GlobalData.output.write(file, "S",S);
		} catch (InterruptedException e) {
			System.out.println("Помилка при записі вектора S \n" + e.getMessage());
			return;
		} catch (IOException e) {
			System.out.println("Помилка при записі вектора S \n" + e.getMessage());
			return;
		} finally {
			outputSemaphore.release();
			countDownLatch.countDown();
		}
	}
}
