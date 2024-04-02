// Лабораторна робота 3
// software transactional memory
// SelectThread1.java

package lab3.thread.select;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;

import lab3.connection.SQLConnectionManager;

public class SelectThread1 implements Runnable {

	private String url;
	private int loops;
	private SQLConnectionManager sqlConnectionManager;
	private Lock lock;
	private CountDownLatch countDownLatch;

	public SelectThread1(String url, SQLConnectionManager sqlConnectionManager, int loops, Lock lock,
			CountDownLatch countDownLatch) {
		this.url = url;
		this.loops = loops;
		this.sqlConnectionManager = sqlConnectionManager;
		this.lock = lock;
		this.countDownLatch = countDownLatch;
	}

	@Override
	public void run() {
		Connection connection;
		Statement selectStatement;
		try {
			connection = sqlConnectionManager.createConnection(url);
			selectStatement = sqlConnectionManager.createStatement(connection);
		} catch (SQLException e) {
			System.out.println("Помилка при встановленні з\'єднання з базою даних у потоці SELECT 1...");
			System.out.println(e);
			return;
		} finally {
			countDownLatch.countDown();
		}
		String sql = "SELECT * from Orders WHERE id=0";
		try {
			for (int i = 0; i < loops; i++) {
				lock.lock();
				selectStatement.executeQuery(sql);
				lock.unlock();
			}
		} catch (SQLException e) {
			System.out.println("Помилка у роботі потоку SELECT 1...");
			System.out.println(e);
			return;
		} finally {
			countDownLatch.countDown();
			closeConnections(connection, selectStatement);
		}
	}

	private void closeConnections(Connection connection, Statement statement) {
		try {
			sqlConnectionManager.closeConection(connection);
			sqlConnectionManager.closeStatement(statement);
		} catch (SQLException e) {
			System.out.println("Помилка при закритті з\'єднання з базою даних у потоці SELECT 1...");
			System.out.println(e);
		}
	}
}
