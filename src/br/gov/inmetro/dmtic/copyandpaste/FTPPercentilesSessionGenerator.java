package br.gov.inmetro.dmtic.copyandpaste;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Vector;

import br.gov.inmetro.dmtic.database.SqlServerConnection;

public class FTPPercentilesSessionGenerator {

	private static String queryNameUsed = "query4";
	private static String dbParametersFile = "config/dbProperties.xml";

	private static FTPPercentilesSession generatePercentiles(ResultSet resultSet, HashMap<String, Double> medias) throws SQLException {

		FTPPercentilesSession ftpPercentileSession = new FTPPercentilesSession();

		ftpPercentileSession.setOperatorName(resultSet.getString("OperatorName")); 
		ftpPercentileSession.setLocalidade(Core.getLocalidade(resultSet.getStatement().getConnection().getMetaData().getURL()));  
		ftpPercentileSession.setTecnologia(Core.getTecnologia(resultSet.getStatement().getConnection().getMetaData().getURL()));  
		ftpPercentileSession.setOperations(resultSet.getString("operation"));
		ftpPercentileSession.setNumSuccess(resultSet.getInt("numSuccess"));  
		ftpPercentileSession.setNumFailed(resultSet.getInt("numFailed"));  
		ftpPercentileSession.setCommitment(new Double(resultSet.getString("commitment"))/(1000.0*1000.0));
		ftpPercentileSession.setMedia(medias.get("mediaPonderada"));
		
		return ftpPercentileSession;
	}

	public static Vector<FTPPercentilesSession> findFTPPercentilesSessions(String banco, String vizu, String operator, String operation, Vector<String> campaingsList, HashMap<String, Integer> tputMap) {

		// Variables Declaration
		Vector<FTPPercentilesSession> ftpPercentilesSessions = new Vector<FTPPercentilesSession>();
		SqlServerConnection connection = new SqlServerConnection(banco, vizu, Core.realPath + dbParametersFile);

		String query = QueryConstructor.queryPrepare(queryNameUsed, operator, operation, campaingsList, connection);
		System.out.println("FTP Session Percentiles Generator -- Query: " + query);
		connection.connect();

		try {
			Statement stm = connection.getConnection().createStatement();
			
			/**
			 * Ler TODO do Metodo FTPSessionGenerator.generateMedias !
			 */
			
			HashMap<BigDecimal, Integer> histogramMap = FTPSessionGenerator.histogram(stm, query, tputMap);
			
			HashMap<String, Double> mediasMap = new HashMap<String, Double>();
			mediasMap.put("mediaPonderada", null);
			
			mediasMap = FTPSessionGenerator.generateMedias(stm, query, histogramMap, mediasMap);
			
			ResultSet result = stm.executeQuery(
					"SELECT t1.OperatorName, t1.operation, t1.commitment, sum(t1.numSuccess) as numSuccess, sum(t1.numFailed) as numFailed " +
					"FROM (" + query + ") as t1 " +
					"GROUP BY t1.OperatorName, t1.operation, t1.commitment");
			
			while (result.next())
			{
				ftpPercentilesSessions.add(generatePercentiles(result, mediasMap));
			}
			

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally 
		{
			connection.disconnect();
		}
		System.out.println("FTP Session Percentile Generator -- Total of FTP Sessions: " + ftpPercentilesSessions.size());
		return ftpPercentilesSessions;
	}

}
