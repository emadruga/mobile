package br.gov.inmetro.dmtic.copyandpaste;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Vector;

import br.gov.inmetro.dmtic.database.SqlServerConnection;

public class FTPSummarySessionGenerator {

	private static String queryNameUsed = "query4";
	private static String dbParametersFile = "config/dbProperties.xml";

	private static FTPSummarySession generateSummary(final ResultSet resultSet, final String operator) throws SQLException {

		FTPSummarySession ftpSummarySession = new FTPSummarySession();

		ftpSummarySession.setOperatorName(operator); 
		ftpSummarySession.setLocalidade(Core.getLocalidade(resultSet.getStatement().getConnection().getMetaData().getURL()));  
		ftpSummarySession.setTecnologia(Core.getTecnologia(resultSet.getStatement().getConnection().getMetaData().getURL()));  
		ftpSummarySession.setNumSuccess(resultSet.getInt("numSuccess"));  
		ftpSummarySession.setNumFailed(resultSet.getInt("numFailed"));  
		
		return ftpSummarySession;
	}

	public static Vector<FTPSummarySession> findFTPSummarySessions(String banco, String vizu, String operator, Vector<String> operations, Vector<String> campaingsList, HashMap<String, Integer> tputMap) {

		// Variables Declaration
		Vector<FTPSummarySession> ftpSummarySessions = new Vector<FTPSummarySession>();
		SqlServerConnection connection = new SqlServerConnection(banco, vizu, Core.realPath + dbParametersFile);

		String query = QueryConstructor.queryPrepare(queryNameUsed, operator, operations, campaingsList, connection);
		System.out.println("FTP Session Percentiles Generator -- Query: " + query);
		connection.connect();

		try {
			Statement stm = connection.getConnection().createStatement();
			
			ResultSet result = stm.executeQuery(query.replaceFirst("\\*", "sum(numSuccess) as numSuccess, sum(numFailed) as numFailed"));
			
			while (result.next())
			{
				ftpSummarySessions.add(generateSummary(result, operator));
			}
			

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally 
		{
			connection.disconnect();
		}
		System.out.println("FTP Session Summary Generator -- Total of FTP Sessions: " + ftpSummarySessions.size());
		return ftpSummarySessions;
	}

}
