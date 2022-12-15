package genericLibraries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.jdbc.Driver;

/**
 * This class contains methods to perform actions on Database
 * @author QPS-Basavanagudi
 *
 */

public class DataBaseUtility {
	
	Connection connection;
	
	/**
	 * This method is used to establish database connection
	 * @param url
	 * @param username
	 * @param password
	 */
	
	public void openDatabaseConnection(String url, String username, String password) {
		Driver dbDriver = null;
		try {
			dbDriver = new Driver();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			DriverManager.registerDriver(dbDriver);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is used to fetch the column data from database
	 * @param query
	 * @param columnName
	 * @return
	 * @throws SQLException
	 */
	public List<String> fetchDataFromDatabase(String query, String columnName) throws SQLException {
		Statement statement = null;
		ResultSet result = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			result = statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		List<String> list = new ArrayList<>();
		while(result.next()) {
			list.add(result.getString(columnName));
		}
		return list;
	}
	
	/**
	 * This method is used to modify the database
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	public int modifyDataInDatabase(String query) throws SQLException {
		Statement statement = connection.createStatement();
		int result = statement.executeUpdate(query);
		return result;
	}
	
	/**
	 * This method is used to close database connection
	 */
	public void closeDatabase() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
