// Лабораторна робота 3
// hardware transactional memory
// Main.java

package lab3;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.CountDownLatch;

import lab3.connection.SQLConnectionManager;
import lab3.fs.FileSystem;
import lab3.thread.select.SelectThread1;
import lab3.thread.select.SelectThread2;
import lab3.thread.select.SelectThread3;
import lab3.thread.select.SelectThread4;

public class Main {

	public static void main(String[] args) {
		try {
			registerDriver(new org.postgresql.Driver());
		} catch (SQLException e) {
			System.out.println("Помилка при завантаженні драйвера PostgreSQL...");
			System.err.println(e);
			return;
		}
		
		final int THREADS = 4;
		//final int LOOPS = 100000;
		//final int LOOPS = 200000;
		//final int LOOPS = 500000;
		//final int LOOPS = 1000000;
		//final int LOOPS = 2000000;
		//final int LOOPS = 5000000;
		final int LOOPS = 10000000;
		//final String OUTPUT_PATH = "./output/output_en_1.txt";
		//final String OUTPUT_PATH = "./output/output_dis_1.txt";
		//final String OUTPUT_PATH = "./output/output_en_2.txt";
		final String OUTPUT_PATH = "./output/output_dis_2.txt";
		final String URL = "jdbc:postgresql://localhost:5432/testdb?user=testuser&password=testpass";

		try {
			prepareDatabase(URL);
		} catch (SQLException e) {
			System.out.println("Помилка при підготовці бази даних...");
			System.out.println(e);
			return;
		}
		
		long selectStart = System.currentTimeMillis();
		CountDownLatch selectCountDownLatch = new CountDownLatch(THREADS);
		new Thread(new SelectThread1(URL, new SQLConnectionManager(), LOOPS, selectCountDownLatch)).start();
		new Thread(new SelectThread2(URL, new SQLConnectionManager(), LOOPS, selectCountDownLatch)).start();
		new Thread(new SelectThread3(URL, new SQLConnectionManager(), LOOPS, selectCountDownLatch)).start();
		new Thread(new SelectThread4(URL, new SQLConnectionManager(), LOOPS, selectCountDownLatch)).start();

		try {
			selectCountDownLatch.await();
		} catch (InterruptedException e) {
			System.out.println("Перервано роботу програми (виконання SELECT) - ");
			System.out.println(e);
			return;
		}
		
		long selectMs = System.currentTimeMillis() - selectStart;
		String selectMsMessage = "Операція SELECT. Час виконання: " + selectMs + " мс";

		long updateStart = System.currentTimeMillis();
		CountDownLatch updateCountDownLatch = new CountDownLatch(THREADS);
		new Thread(new SelectThread1(URL, new SQLConnectionManager(), LOOPS, updateCountDownLatch)).start();
		new Thread(new SelectThread2(URL, new SQLConnectionManager(), LOOPS, updateCountDownLatch)).start();
		new Thread(new SelectThread3(URL, new SQLConnectionManager(), LOOPS, updateCountDownLatch)).start();
		new Thread(new SelectThread4(URL, new SQLConnectionManager(), LOOPS, updateCountDownLatch)).start();

		try {
			updateCountDownLatch.await();
		} catch (InterruptedException e) {
			System.out.println("Перервано роботу програми (виконання UPDATE) - ");
			System.out.println(e);
			return;
		} 
		
		long updateMs = System.currentTimeMillis() - updateStart;
		String updateMsMessage = "Операція UPDATE. Час виконання: " + updateMs + " мс";
		
		FileSystem fs = new FileSystem();
		String output = "Кількість ітерацій - " + LOOPS + "\n" + selectMsMessage + "\n" + updateMsMessage + "\n\n";
		System.out.println(output);
		try {
			fs.write(OUTPUT_PATH, output);
			return;
		} catch (IOException e) {
			System.out.println("Помилка при записі результату...");
			System.out.println(e);
		} 
	}
	
	private static void registerDriver(Driver driver) throws SQLException {
		try {
			DriverManager.registerDriver(driver);
		} catch (SQLException e) {
			throw e;
		}
	}
	
	private static void prepareDatabase(String url) throws SQLException {
		SQLConnectionManager sqlConnectionManager = new SQLConnectionManager();
		Connection prepareConnection = sqlConnectionManager.createConnection(url);
		Statement prepareStatement;
		try {
			prepareStatement = sqlConnectionManager.createStatement(prepareConnection);
		} catch (SQLException e) {
			sqlConnectionManager.closeConection(prepareConnection);
			throw e;
		}
		try {
			ResultSet orders = prepareStatement.executeQuery("SELECT * FROM Orders");
			if (!orders.next()) {
				prepareStatement.executeQuery("INSERT INTO Orders (id, name, priority) VALUES (0, \'TV\', 3)");
			} else {
				prepareStatement.executeUpdate("UPDATE Orders SET priority=3 WHERE id=0");
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			sqlConnectionManager.closeConection(prepareConnection);
			sqlConnectionManager.closeStatement(prepareStatement);
		}
	}
}
