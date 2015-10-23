package br.gov.inmetro.dmtic.control;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import br.gov.inmetro.dmtic.database.MysqlConnection;

public class CampaignTableModel {
	
	public static TableModel updateTable(String banco, Vector<Object> operatorsSelected) 
	{
		
		DefaultTableModel model = new DefaultTableModel();
		MysqlConnection conection = new MysqlConnection(banco, "config/dbProperties.xml");
		String query = new String();
		
		query = 
			"SELECT " +
					"DATE(`msgtime`) AS 'campaignDate', " +
					"MIN(TIME(`msgtime`)) AS 'startTime', " +
					"MAX(TIME(`msgtime`)) AS 'endTime', " +
					"COUNT(DISTINCT(`sessionid`)) AS 'numSessions', " +
					"COUNT(msgtime) AS 'numSamples' " +
			"FROM " +
				"tabelao " +
			"WHERE ";
				
		query += "(";
		for (int i = 0; i < operatorsSelected.size(); i++) {
			query += "`MNC` = " + operatorsSelected.get(i);
			if (i != operatorsSelected.size() - 1)
				query = query.concat(" OR ");
		}
		query += ")";

		query += 
			"GROUP BY " +
				"campaignDate " +
			"ORDER BY " +
				"numSamples DESC";
		
		
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		try
		{
			conection.connect();
			
			Statement stm = conection.getConnection().createStatement();
			ResultSet result = stm.executeQuery(query);
			
			data = new Vector<Vector<Object>>();
			Vector<Object> vector;
			
			while (result.next()) 
			{
				vector = new Vector<Object>();
				vector.add(result.getString("campaignDate"));
				vector.add(result.getString("startTime"));
				vector.add(result.getString("endTime"));
				vector.add(result.getString("numSessions"));
				vector.add(result.getString("numSamples"));
				data.add(vector);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		
		Vector<Object>columnNames = new Vector<Object>();
		columnNames.add("Date");
		columnNames.add("Start Time");
		columnNames.add("End Time");
		columnNames.add("Sessions");
		columnNames.add("Samples");
		
		model.setDataVector(data, columnNames);
		
		
		return model;
	}

}
