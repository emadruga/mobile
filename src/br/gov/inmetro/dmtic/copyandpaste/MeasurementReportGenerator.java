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
		measRep.setLatitude(String.format("%f", resultSet.getFloat("Latitude")));
		measRep.setLongitude(String.format("%f", resultSet.getFloat("Longitude")));
		measRep.setMsgInstant(resultSet.getString("msgInstant"));
		
		return measRep;
	}
	
	public static Integer degrau(final Statement stm, final String query, final HashMap<String, Integer> tputMap) throws SQLException
	{
		Integer throughput = null;

		if (Core.getTecnologia(stm.getConnection().getMetaData().getURL()).equals("3G"))
		{
			if (query.indexOf("\'Get\'") >= 0)
			{
				throughput = tputMap.get("tput3gdown");
			}
			if (query.indexOf("\'Put\'") >= 0)
			{
				throughput = tputMap.get("tput3gup");
			}
		}
		else
		{
			if (query.indexOf("\'Get\'") >= 0)
			{
				throughput = tputMap.get("tput4gdown");
			}
			if (query.indexOf("\'Put\'") >= 0)
			{
				throughput = tputMap.get("tput4gup");
			}
		}
		
		return throughput;
	}
	
	public static HashMap<BigDecimal, Integer> histogram(final Statement stm, final String query, final HashMap<String, Integer> tputMap) throws SQLException
	{
		
		Integer throughput = degrau(stm, query, tputMap);

		ResultSet result = stm.executeQuery(
				"select "+throughput+"*floor((t1.throughput*8.0)/("+throughput+"*1000.0) + 1) as up_interval, count(*) as n_i " +
				"from (" + query  + ") as t1 " +
				"group by "+throughput+"*floor((t1.throughput*8.0)/("+throughput+"*1000.0) + 1)");
		
		HashMap<BigDecimal, Integer> histogramMap = new HashMap<BigDecimal, Integer>();
		
		while (result.next())
		{
			BigDecimal T_i = result.getBigDecimal(1);
			Integer n_i = result.getInt(2);
			histogramMap.put(T_i, n_i);
		}

		return histogramMap;
	}
	
	/**
	 * TODO: Mover metodo abaixo para uma classe mais apropriada porque este metodo tambem e' usado na classe SampleGenerator.java
	 */

	public static HashMap<String, Double> generateMedias(final Statement stm, final String query, final HashMap<BigDecimal, Integer> histogramMap, HashMap<String, Double> mediasMap) throws SQLException
	{
		
		ResultSet result = null;
		
		/**
		 * Calcula Percentil 95
		 */
		
		if (mediasMap.containsKey("percentil95"))
		{
			result = stm.executeQuery("SELECT 8.0*avg(cast(t1.throughput as bigint)) FROM (SELECT TOP 95 PERCENT WITH TIES t2.throughput FROM (" + query  + ") as t2 ORDER BY t2.throughput ASC) as t1");
			while (result.next())
				mediasMap.put("percentil95", result.getInt(1)/(1000.0*1000.0));
		}
		
		/**
		 * Calcula Percentil 99
		 */
		
		if (mediasMap.containsKey("percentil99"))
		{
			result = stm.executeQuery("SELECT 8.0*avg(cast(t1.throughput as bigint)) FROM (SELECT TOP 99 PERCENT WITH TIES t2.throughput FROM (" + query  + ") as t2 ORDER BY t2.throughput ASC) as t1");
			while (result.next())
				mediasMap.put("percentil99", result.getInt(1)/(1000.0*1000.0));
		}
		
		/**
		 * Calcula Mediana
		 */
		
		if (mediasMap.containsKey("mediana"))
		{
			result = stm.executeQuery(
				"(SELECT 8.0*max(cast(BottomHalf.throughput as bigint)) FROM (SELECT TOP 50 PERCENT WITH TIES t2.throughput FROM (" + query  + ") as t2 ORDER BY t2.throughput ASC) as BottomHalf)");
			
			while (result.next())
			{
				mediasMap.put("mediana", result.getInt(1)/(1000.0*1000.0));
			}
		}
		
		/**
		 * Calcula Media Aritmetica
		 */
		
		if (mediasMap.containsKey("mediaAritmetica"))
		{

			result = stm.executeQuery("SELECT 8.0*avg(cast(t1.throughput as bigint)) FROM (" + query  + ") as t1");
			while (result.next())
			{
				mediasMap.put("mediaAritmetica", result.getInt(1)/(1000.0*1000.0));
			}
		}
		
		/**
		 * Calcula Media Ponderada
		 */
		
		if (mediasMap.containsKey("mediaPonderada"))
		{
			
			/** 
			 * Variaveis para Media Ponderada
			 */
			
			Double numerador = new Double(0);
			Double denominador = new Double(0);
			Integer n = new Integer(0);
			
			for (Integer n_i: histogramMap.values())
			{
			    n += n_i;
			}
				
		    for (Entry<BigDecimal, Integer> pair: histogramMap.entrySet()) 
		    {
		        Double T_i = pair.getKey().doubleValue();
				Double n_i = pair.getValue().doubleValue();
				
				numerador += (n_i*n_i*T_i)/(n*n);
				denominador += (n_i*n_i)/(n*n);
		    }
		    
		    mediasMap.put("mediaPonderada", numerador/ (denominador * 1000.0));
		    
		}
		
		return mediasMap;
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
