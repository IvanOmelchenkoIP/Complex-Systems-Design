// Програма з глобальними змінними
// Main.java
// ВАРІАНТ 13
// В = D*MO-min(D)*C;                                      
// S = SORT(C*MO+D+C)

package lab1;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

import lab1.data.GlobalData;
import lab1.data.generator.DataFileGenerator;
import lab1.data.matrix.MatrixManager;
import lab1.data.vector.VectorManager;
import lab1.thread.ThreadB;
import lab1.thread.ThreadS;
import lab1.fs.FileSystem;

public class Main {

	public static void main(String[] args) {
		final int MIN_VAL = 0;
		final int MAX_VAL = 100;
		final int MIN_PRECISION = 3;
		final int MAX_PRECISION = 15;
		
		final int N = 200;
		final String INPUT_PATH = "../input/input.txt";
		final String OUTPUT_PATH = "../output/output_global.txt";
		
		final int THREAD_AMOUNT = 2;
		
		final String[] dataNames = { "D", "MO", "C" };
		
		VectorManager vm = new VectorManager();
		MatrixManager mm = new MatrixManager();
		
		Semaphore matrixSemaphore = new Semaphore(1);
		Semaphore vectorSemaphore = new Semaphore(1);
		Semaphore outputSemaphore = new Semaphore(1);
		
		DataFileGenerator dfg = new DataFileGenerator();
		try {
			dfg.tryGenerate(INPUT_PATH,  dataNames, N, MIN_VAL, MAX_VAL, MIN_PRECISION, MAX_PRECISION);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		CountDownLatch countDownLatch = new CountDownLatch(THREAD_AMOUNT);
		
		try {
			GlobalData.MO = mm.getMatrix(INPUT_PATH, "MO", N);
			GlobalData.D = vm.getVector(INPUT_PATH, "D", N);
			GlobalData.C = vm.getVector(INPUT_PATH, "C", N);
		} catch (IOException e) {
			System.out.println("Помидка при читанні даних \n" + e.getMessage());
			return;
		}
		
		long start = System.currentTimeMillis();
		
		Thread threadS = new Thread(new ThreadS(OUTPUT_PATH, countDownLatch, matrixSemaphore, vectorSemaphore, outputSemaphore));
		Thread threadB = new Thread(new ThreadB(OUTPUT_PATH, countDownLatch, matrixSemaphore, vectorSemaphore, outputSemaphore));
		threadS.start();
		threadB.start();
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			System.out.println("Роботу одного з потоків перервано некоректно - " + e);
		}
		
		long ms = System.currentTimeMillis() - start;
		String msMessage = "Час виконання: " + ms + " мс";
		try {
			new FileSystem().write(OUTPUT_PATH, msMessage + "\n\n");
		} catch (IOException ex) {
			System.out.println("Помилка при записі часу виконання - " + ex);
		}
		System.out.println(msMessage);
	}

}
