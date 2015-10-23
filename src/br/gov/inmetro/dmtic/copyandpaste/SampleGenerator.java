package br.gov.inmetro.dmtic.copyandpaste;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Vector;

import br.gov.inmetro.dmtic.database.MysqlConnection;
import br.gov.inmetro.dmtic.database.SqlServerConnection;

public class SampleGenerator {

	private static String queryNameUsed = "query4";
	private static String dbParametersFile = "config/dbProperties.xml";

	/**
	 * Populates <code>Sample</code>'s data
	 * 
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	private static Sample generateSample(ResultSet resultSet, HashMap<BigDecimal, Integer> histogram, HashMap<String, Double> medias) throws SQLException {

		Sample oneSample = new Sample();
		oneSample.setOperatorName(resultSet.getString("OperatorName"));
		oneSample.setLocalidade(Core.getLocalidade(resultSet.getStatement().getConnection().getMetaData().getURL()));
		oneSample.setTecnologia(Core.getTecnologia(resultSet.getStatement().getConnection().getMetaData().getURL()));
		oneSample.setDirecao(resultSet.getString("operation"));
		oneSample.setMinMsgTime(resultSet.getTimestamp("minMsgTime"));
		oneSample.setMaxMsgTime(resultSet.getTimestamp("maxMsgTime"));
		oneSample.setHistogram(histogram);
		
		oneSample.setDegrau(medias.get("degrau"));
		
		oneSample.setPercentil95(medias.get("percentil95"));  
		oneSample.setPercentil99(medias.get("percentil99"));  
		oneSample.setMediana(medias.get("mediana"));  
		oneSample.setMediaAritmetica(medias.get("mediaAritmetica"));  
		oneSample.setMediaPonderada(medias.get("mediaPonderada"));  
		
		return oneSample;
	}

	public static Vector<Sample> findSamples(String banco, String vizu, String operator, String operation, Vector<String> campaingsList, HashMap<String, Integer> tputMap) {

		Vector<Sample> vectorOfSamples = new Vector<Sample>();
		
		MysqlConnection connection = new SqlServerConnection(banco, vizu, Core.realPath + dbParametersFile);

		String query = QueryConstructor.queryPrepare(queryNameUsed, operator, operation, campaingsList, connection);
		
		System.out.println("Sample Generator -- Query: " + query);
		
		connection.connect();

		try
		{
			Statement stm = connection.getConnection().createStatement();
			
			/**
			 * Ler TODO do Metodo FTPSessionGenerator.generateMedias !
			 */
			
			HashMap<BigDecimal, Integer> histogramMap = null;

			HashMap<String, Double> mediasMap = new HashMap<String, Double>();
			mediasMap.put("percentil95", null);
			mediasMap.put("percentil99", null);
			mediasMap.put("mediana", null);
			mediasMap.put("mediaAritmetica", null);
			mediasMap.put("mediaPonderada", null);
			mediasMap.put("degrau", FTPSessionGenerator.degrau(stm, query, tputMap).doubleValue());
			
			histogramMap = FTPSessionGenerator.histogram(stm, query, tputMap);
			
			mediasMap = FTPSessionGenerator.generateMedias(stm, query, histogramMap, mediasMap);
			
			ResultSet result = stm.executeQuery(
					"SELECT t1.OperatorName, t1.operation, min(t1.MsgTime) as minMsgTime, max(t1.MsgTime) as maxMsgTime " +
					"FROM (" + query + ") as t1 " +
					"GROUP BY t1.OperatorName, t1.operation");

			while (result.next())
			{
				vectorOfSamples.add(generateSample(result, histogramMap, mediasMap));
			}

		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		} 
		finally
		{
			connection.disconnect();
		}
		
		System.out.println("Sample Generator -- Total of Samples: " + vectorOfSamples.size());
		
		return vectorOfSamples;
	}

}
