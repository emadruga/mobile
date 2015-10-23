package br.gov.inmetro.dmtic.database;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Vector;

public class SqlServerConnection extends MysqlConnection {

	public void connect() {
			// Conecta a SQL Server 2012 R2
			try 
			{
				Class.forName("net.sourceforge.jtds.jdbc.Driver");
				setConnection (DriverManager.getConnection("jdbc:jtds:sqlserver://" + 
													getServer() + "/" +  getDatabase(), getUsername(), getPassword() ) );
				System.out.println("Conectado ao banco " + getDatabase() + " do servidor SQL Server " + getServer());
			} 
			catch (ClassNotFoundException e) 
			{
				e.printStackTrace();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}

		
		isConnected(true);
	}

	public String campaignListQuery(Vector<String> operatorsSelected, Vector<String> operationsSelected, Boolean isonomia)
	{
		String query = new String();
		
		query = 
				"SELECT " +
						"convert(DATE,[msgTime]) AS 'campaignDate', " +
						"MIN(convert(TIME,[msgTime])) AS 'StartTime', " +
						"MAX(convert(TIME,[msgTime])) AS 'EndTime', " +
						"COUNT([SessionID]) AS 'numSamples', " +
						"COUNT(DISTINCT [SessionID]) AS 'numSessions' " +
				"FROM " +
					"[" + getDatabase() + "].[dbo].[" + getTable() + "] " +
				"WHERE ";

		    // Forcar isonomia de 3 operadoras (calculo dinamico com numero de operadoras no dataset)
			if (isonomia && getDatabase().indexOf("pe_pernambuco_4g") != -1)
			{	
				operatorsSelected.remove("CLARO");
			}
		
		    // Forcar isonomia de 3 operadoras (calculo dinamico com numero de operadoras no dataset)			
			if (isonomia && getDatabase().indexOf("rs_poa_3g") != -1)
			{	
				operatorsSelected.remove("TIM");
			}

			query += operatorsListFilter(operatorsSelected);

			if (operatorsSelected != null && operationsSelected != null &&
					operatorsSelected.size() != 0 && operationsSelected.size() != 0)
			{
				query += "AND ";
			}
			
			query += operationsListFilter(operationsSelected);

			if (isonomia)
			{
				query += "AND ";
				query += isonomyListFilter(operatorsSelected, operationsSelected);
			}

			query += 
				"GROUP BY " +
					"convert(DATE,[msgTime]) " +
				"ORDER BY " +
					"convert(DATE,[msgTime]) ";
			
			System.out.println(query);
		return query;
	}
	
	public SqlServerConnection (String banco, String vizu, String pathToXml) {
		/*
		DbPropertiesSelector properties = new DbPropertiesSelector(pathToXml);
		this.server = properties.getServer();
		this.database = properties.getDatabase();
		this.username = properties.getUser();
		this.password = properties.getPassword();
		this.table = properties.getTable();
		*/
		super(banco,pathToXml);
		//setServer ("10.0.10.102");
		setServer ("192.168.1.107");
		//server = "127.0.0.1";
		setDatabase (banco);
		setUsername ("sa");
		setPassword ("copa2014");
		setTable (vizu);
	}
	
	public String campaignListFilter(Vector<String> campaigns)
	{
		String query = new String();

		for (int i = 0; i < campaigns.size(); i++) {
			query = query.concat(" convert(DATE,[msgTime]) = '" + campaigns.get(i) + "'");
			if (i != campaigns.size() - 1)
				query = query.concat(" OR");
		}
		query = "(" + query + ")";

		return query;
	}
	
	public String operatorsListFilter(Vector<String> operators)
	{
		String query = new String();
		query += "(";
		for (int i = 0; i < operators.size(); i++) {
			query = query.concat(operatorsListFilter(operators.get(i)));
			if (i != operators.size() - 1)
				query = query.concat(" OR ");
		}
		query += ") ";
		return query;
	}
	
	public String operatorsListFilter(String operator)
	{
		return "([OperatorName] = '" + operator + "')";
	}
	
	public String operationsListFilter(Vector<String> operations)
	{
		String query = new String();
		query += "(";
		for (int i = 0; i < operations.size(); i++) {
			query = query.concat(operationsListFilter(operations.get(i)));
			if (i != operations.size() - 1)
				query = query.concat(" OR ");
		}
		query += ") ";
		return query;
	}
	
	public String operationsListFilter(String operation)
	{
		return "([Operation] = '" + operation + "')";
	}
	
	public String isonomyListFilter(Vector<String> operators, Vector<String> operations)
	{
		String query = new String();
		query += "msgDate in "
				+ "(SELECT DISTINCT([msgDate]) "
				+ "FROM [" + getDatabase() + "].[dbo].[" + getTable() + "] "
				+ "WHERE ";
		
		query += operatorsListFilter(operators);
		
		query += " AND ";
		
		query += operationsListFilter(operations);
		
		query += " GROUP BY [msgDate] "
				+ "HAVING COUNT(DISTINCT([operatorName])) = "+operators.size()+") ";

		return query;
	}
	
}
