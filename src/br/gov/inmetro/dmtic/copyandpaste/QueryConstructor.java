package br.gov.inmetro.dmtic.copyandpaste;

import java.util.Vector;

import br.gov.inmetro.dmtic.database.MysqlConnection;

public class QueryConstructor {

	public static String queryPrepare(String queryNameUsed, String operator, String operation, Vector<String> campaignsList, MysqlConnection conn) {
		String preparedQuery;
		String query = QueryReader.retrieveQueryByName(queryNameUsed);

		if (operation == null)
		{
			preparedQuery = query.replace("WHERECLAUSULE",
					conn.operatorsListFilter(operator) + " AND " +
					conn.campaignListFilter(campaignsList));
		}
		
		else if (campaignsList != null)
		{
			preparedQuery = query.replace("WHERECLAUSULE",
					conn.operatorsListFilter(operator) + " AND " +
					conn.operationsListFilter(operation) + " AND " +
					conn.campaignListFilter(campaignsList));
		}
		else
		{
			preparedQuery = query.replace("WHERECLAUSULE",
					conn.operatorsListFilter(operator) + " AND " +
					conn.operationsListFilter(operation));
		}

		preparedQuery = preparedQuery.replace("TABLENAME", MysqlConnection.getTable());

		return preparedQuery;

	}

	public static String queryPrepare(String queryNameUsed, String operator, Vector<String> operations, Vector<String> campaignsList, MysqlConnection conn) {
		String preparedQuery;
		String query = QueryReader.retrieveQueryByName(queryNameUsed);

		if (campaignsList != null)
		{
			preparedQuery = query.replace("WHERECLAUSULE",
					conn.operatorsListFilter(operator) + " AND " +
					conn.operationsListFilter(operations) + " AND " +
					conn.campaignListFilter(campaignsList));
		}
		else
		{
			preparedQuery = query.replace("WHERECLAUSULE",
					conn.operatorsListFilter(operator) + " AND " +
					conn.operationsListFilter(operations));
		}

		preparedQuery = preparedQuery.replace("TABLENAME", MysqlConnection.getTable());

		return preparedQuery;

	}

}
