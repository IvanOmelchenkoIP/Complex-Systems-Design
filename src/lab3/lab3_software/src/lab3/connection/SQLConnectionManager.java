// Лабораторна робота 3
// software transactional memory
// SQLConnectionManager.java

package lab3.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class SQLConnectionManager {

	public Connection createConnection(String url) throws SQLException {
		Connection connection;
		try {
			connection = DriverManager.getConnection(url);
		} catch (SQLException e) {
			throw e;
		}
		return connection;
	}
	
	public Connection createConnection(String url, Properties connectionProperties) throws SQLException {
		Connection connection;
		try {
			connection = DriverManager.getConnection(url, connectionProperties);
		} catch (SQLException e) {
			throw e;
		}
		return connection;
	}

	public void closeConection(Connection connection) throws SQLException {
		try {
			connection.close();
		} catch (SQLException e) {
			throw e;
		}
	}

	public Statement createStatement(Connection connection) throws SQLException {
		Statement statement;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			throw e;
		}
		return statement;
	}

	public void closeStatement(Statement statement) throws SQLException {
		try {
			statement.close();
		} catch (SQLException e) {
			throw e;
		}
	}
}
