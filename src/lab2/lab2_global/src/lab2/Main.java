// Лабораторна робота 2
// Main.java
// ВАРІАНТ 13
// В = D*MO-min(D)*C;                                      
// S = SORT(C*MO+D+C)

package lab2;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import lab2.data.GlobalData;
import lab2.data.generator.DataFileGenerator;
import lab2.data.matrix.MatrixManager;
import lab2.data.vector.VectorManager;
import lab2.fs.FileSystem;
import lab2.thread.AnonymousRunnableLauncher;

public class Main {

	public static void main(String[] args) {
		final int MIN_VAL = 0;
		final int MAX_VAL = 100;
		final int MIN_PRECISION = 3;
		final int MAX_PRECISION = 15;
		
		final int N = 200;
		
		final int N_100 = 100;
		final int N_200 = 200;
		final int N_400 = 400;
		final int N_600 = 600;
		final int N_800 = 800;
		final int N_1000 = 1000;
		
		final String INPUT_PATH = "../input/input.txt";
		final String OUTPUT_PATH_TSX_EN = "../output/output_tsx_en.txt";
		final String OUTPUT_PATH_TSX_DIS = "../output/output_tsx_dis.txt";
		
		final String INPUT_PATH_100 = "../input/input_100.txt";
		final String INPUT_PATH_200 = "../input/input_200.txt";
		final String INPUT_PATH_400 = "../input/input_400.txt";
		final String INPUT_PATH_600 = "../input/input_600.txt";
		final String INPUT_PATH_800 = "../input/input_800.txt";
		final String INPUT_PATH_1000 = "../input/input_1000.txt";
		
		final String OUTPUT_PATH_TSX_EN_100 = "../output/output_tsx_en_100.txt";
		final String OUTPUT_PATH_TSX_DIS_100 = "../output/output_tsx_dis_100.txt";
		final String OUTPUT_PATH_TSX_EN_200 = "../output/output_tsx_en_200.txt";
		final String OUTPUT_PATH_TSX_DIS_200 = "../output/output_tsx_dis_200.txt";
		final String OUTPUT_PATH_TSX_EN_400 = "../output/output_tsx_en_400.txt";
		final String OUTPUT_PATH_TSX_DIS_400 = "../output/output_tsx_dis_400.txt";
		final String OUTPUT_PATH_TSX_EN_600 = "../output/output_tsx_en_600.txt";
		final String OUTPUT_PATH_TSX_DIS_600 = "../output/output_tsx_dis_600.txt";
		final String OUTPUT_PATH_TSX_EN_800 = "../output/output_tsx_en_800.txt";
		final String OUTPUT_PATH_TSX_DIS_800 = "../output/output_tsx_dis_800.txt";
		final String OUTPUT_PATH_TSX_EN_1000 = "../output/output_tsx_en_1000.txt";
		final String OUTPUT_PATH_TSX_DIS_1000 = "../output/output_tsx_dis_1000.txt";
		
		
		final int THREAD_AMOUNT = 2;
		
		final String[] dataNames = { "D", "MO", "C" };
		
		VectorManager vm = new VectorManager();
		MatrixManager mm = new MatrixManager();
		
		Lock matrixLock = new ReentrantLock();
		Lock vectorLock = new ReentrantLock();
		Lock outputLock = new ReentrantLock();
		
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
		
		AnonymousRunnableLauncher launcher = new AnonymousRunnableLauncher(OUTPUT_PATH_TSX_EN, countDownLatch, matrixLock, vectorLock, outputLock);
		launcher.launchAnonymousRunnableB();
		launcher.launchAnonymousRunnableS();
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			System.out.println("Роботу одного з потоків перервано некоректно - " + e);
		}
		
		long ms = System.currentTimeMillis() - start;
		String msMessage = "Час виконання: " + ms + " мс";
		try {
			new FileSystem().write(OUTPUT_PATH_TSX_EN, msMessage + "\n\n");
		} catch (IOException ex) {
			System.out.println("Помилка при записі часу виконання - " + ex);
		}
		System.out.println(msMessage);
	}

}
