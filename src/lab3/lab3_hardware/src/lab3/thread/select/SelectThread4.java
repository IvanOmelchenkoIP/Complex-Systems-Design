// Лабораторна робота 3
// hardware transactional memory
// SelectThread4.java

package lab3.thread.select;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.CountDownLatch;

import lab3.connection.SQLConnectionManager;

public class SelectThread4 implements Runnable {

	private String url;
	private int loops;
	private SQLConnectionManager sqlConnectionManager;
	private CountDownLatch countDownLatch;

	public SelectThread4(String url, SQLConnectionManager sqlConnectionManager, int loops,
			CountDownLatch countDownLatch) {
		this.url = url;
		this.loops = loops;
		this.sqlConnectionManager = sqlConnectionManager;
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
			System.out.println("Помилка при встановленні з\'єднання з базою даних у потоці SELECT 4...");
			System.out.println(e);
			return;
		} finally {
			countDownLatch.countDown();
		}
		String sql = "SELECT * from Orders WHERE id=0";
		try {
			for (int i = 0; i < loops; i++) {
				selectStatement.executeQuery(sql);
			}
		} catch (SQLException e) {
			System.out.println("Помилка у роботі потоку SELECT 4...");
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
			System.out.println("Помилка при закритті з\'єднання з базою даних у потоці SELECT 4...");
			System.out.println(e);
		}
	}
}
