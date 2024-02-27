// Програма з локалізацією змінних
// Main.java
// ВАРІАНТ 13
// В = D*MO-min(D)*C;                                      
// S = SORT(C*MO+D+C)

package lab1;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import lab1.data.DataType;
import lab1.data.Result;
import lab1.data.generator.DataFileGenerator;
import lab1.data.generator.OutputFileGenerator;
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
		final String OUTPUT_PATH = "../output/output_local.txt";
		
		final int THREAD_AMOUNT = 2;
		
		final String[] dataNames = { "D", "MO", "C" };
		
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
		Result resultS = new Result();
		Result resultB = new Result();
		OutputFileGenerator output = new OutputFileGenerator();
		
		long start = System.currentTimeMillis();
		
		Thread threadS = new Thread(new ThreadS(N, INPUT_PATH, countDownLatch, new VectorManager(), new MatrixManager(), resultS));
		Thread threadB = new Thread(new ThreadB(N, INPUT_PATH, countDownLatch, new VectorManager(), new MatrixManager(), resultB));
		threadS.start();
		threadB.start();
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			System.out.println("Роботу одного з потоків перервано некоректно - " + e);
		}
		
		DataType strResultS = resultS.getResult();
		DataType strResultB = resultB.getResult();
		System.out.println("S\n" + strResultS.toString());
		System.out.println("B\n" + strResultB.toString());
		try {
			output.write(OUTPUT_PATH, "S", strResultS);
			output.write(OUTPUT_PATH, "B", strResultB);
		} catch (IOException e) {
			System.out.println("Помилка при записі результату у файл - " + e);
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
