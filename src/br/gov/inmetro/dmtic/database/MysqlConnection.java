package br.gov.inmetro.dmtic.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Vector;

import br.gov.inmetro.dmtic.copyandpaste.Core;

public class MysqlConnection {

	private static Connection connection = null;
	private static String server = new String();
	private static String database = new String();
	private static String username = new String();
	private static String password = new String();
	private static String table = new String();
	private static boolean isConnected = false;

	
	public static void setServer(String server) {
		MysqlConnection.server = server;
	}

	public static void setDatabase(String database) {
		MysqlConnection.database = database;
	}

	public static void setUsername(String username) {
		MysqlConnection.username = username;
	}

	public static void setPassword(String password) {
		MysqlConnection.password = password;
	}

	public static void setTable(String table) {
		MysqlConnection.table = table;
	}

	public static void setConnected(boolean isConnected) {
		MysqlConnection.isConnected = isConnected;
	}

	public static String getServer() {
		return server;
	}

	public static String getDatabase() {
		return database;
	}

	public static String getUsername() {
		return username;
	}

	public static String getPassword() {
		return password;
	}

	public static boolean isConnected() {
		return isConnected;
	}

	public static void isConnected(boolean value) {
		isConnected = value;
	}
	
	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection conn) {
		connection = conn;
	}

	public boolean getStatus() {
		return isConnected;
	}

	public void connect() {

			try 
			{
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection("jdbc:mysql://" + this.server + "/" + this.database, this.username, this.password);
				System.out.println("Conectado ao banco " + database + " do servidor " + server);
			} 
			catch (ClassNotFoundException e) 
			{
				e.printStackTrace();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}

		
		isConnected = true;
	}

	public void disconnect() {
		try 
		{
			connection.close();
		} 
		catch (SQLException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public MysqlConnection(String banco, String pathToXml) {
		/*
		DbPropertiesSelector properties = new DbPropertiesSelector(pathToXml);
		this.server = properties.getServer();
		this.database = properties.getDatabase();
		this.username = properties.getUser();
		this.password = properties.getPassword();
		this.table = properties.getTable();
		*/
		server = "10.0.10.101";
		//server = "127.0.0.1";
		database = banco;
		username = "root";
		password = "copa2014";
		//password = "root";
		table = "vizu_apn";
	}

	public String campaignListQuery(Vector<String> operatorsSelected, Vector<String> operationsSelected)
	{
		String query = new String();
		
		query = 
				"SELECT " +
						"DATE(`msgDate`) AS 'campaignDate', " +
						"MIN(TIME(`msgDate`)) AS 'startTime', " +
						"MAX(TIME(`msgDate`)) AS 'endTime', " +
						"COUNT(DISTINCT(`sessionid`)) AS 'numSessions', " +
						"COUNT(msgDate) AS 'numSamples' " +
				"FROM " +
					getTable() + " " +
				"WHERE ";
					
		query += operatorsListFilter(operatorsSelected);

		if (operatorsSelected != null && operationsSelected != null &&
				operatorsSelected.size() != 0 && operationsSelected.size() != 0)
		{
			query += "AND ";
		}
		
		query += operationsListFilter(operationsSelected);

			query += 
				" GROUP BY " +
					"campaignDate " +
				"ORDER BY " +
					"campaignDate ";
		return query;
	}

	public String campaignListFilter(Vector<String> campaigns)
	{
		String query = new String();

		for (int i = 0; i < campaigns.size(); i++) {
			query = query.concat("DATE(`msgDate`) = '" + campaigns.get(i) + "'");
			if (i != campaigns.size() - 1)
				query = query.concat(" OR ");
		}
		query = "(" + query + ")";

		return query;
	}
	
	public String operatorsListFilter(Vector<String> operators)
	{
		String query = new String();
		for (int i = 0; i < operators.size(); i++) {
			query = query.concat(operatorsListFilter(operators.get(i)));
			if (i != operators.size() - 1)
				query = query.concat(" OR ");
		}
		query = "(" + query + ")";	
		return query;
	}
	
	public String operatorsListFilter(String operator)
	{
		return "(`OperatorName` = '" + operator + "')";
	}
	
	public String operationsListFilter(Vector<String> operations)
	{
		String query = new String();
		for (int i = 0; i < operations.size(); i++) {
			query = query.concat(operationsListFilter(operations.get(i)));
			if (i != operations.size() - 1)
				query = query.concat(" OR ");
		}
		query = "(" + query + ")";	
		return query;
	}
	
	public String operationsListFilter(String operation)
	{
		return "(`Operation` = '" + operation + "')";
	}
	
	public static String getTable() {
		return table;
	}

}
