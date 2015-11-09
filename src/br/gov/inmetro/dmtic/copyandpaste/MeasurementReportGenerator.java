package br.gov.inmetro.dmtic.copyandpaste;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Vector;

import br.gov.inmetro.dmtic.database.SqlServerConnection;

public class MeasurementReportGenerator {

	private static String queryNameUsed = "query4";
	private static String dbParametersFile = "config/dbProperties.xml";

	/**
	 * Populates <code>FTP Session</code>'s data
	 * 
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	private static MeasurementReport generateMeasurementReport(ResultSet resultSet) throws SQLException {

		MeasurementReport measRep= new MeasurementReport();

		measRep.setOperatorName(resultSet.getString("OperatorName")); 
		measRep.setLocalidade(Core.getLocalidade(resultSet.getStatement().getConnection().getMetaData().getURL()));  
		measRep.setTecnologia(Core.getTecnologia(resultSet.getStatement().getConnection().getMetaData().getURL()));  
		measRep.setApn(resultSet.getString("APN"));
		measRep.setRSRP(String.format("%6.2f", resultSet.getFloat("RSRP")));
		measRep.setLatitude(String.format("%f", resultSet.getFloat("Latitude")));
		measRep.setLongitude(String.format("%f", resultSet.getFloat("Longitude")));
		measRep.setMsgInstant(resultSet.getString("msgTime"));
		
		return measRep;
	}
	
	public static Vector<MeasurementReport> findMeasurementReports(String banco, String vizu, String operator, String operation, Vector<String> campaingsList, HashMap<String, Integer> tputMap) {

		// Variables Declaration
		Vector<MeasurementReport> measReps = new Vector<MeasurementReport>();
		SqlServerConnection connection = new SqlServerConnection(banco, vizu, Core.realPath + dbParametersFile);

		String query = QueryConstructor.queryPrepare(queryNameUsed, operator, operation, campaingsList, connection);
		System.out.println("Measurement Report Generator -- Query: " + query);
		connection.connect();

		try {
			Statement stm = connection.getConnection().createStatement();
			
			ResultSet result = stm.executeQuery(query);

			while (result.next())
			{
				measReps.add(generateMeasurementReport(result));
			}
			

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally 
		{
			connection.disconnect();
		}
		System.out.println("Measurement Report Generator -- Total of Reports: " + measReps.size());
		return measReps;
	}

}
