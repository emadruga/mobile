package br.gov.inmetro.dmtic.copyandpaste;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.table.TableModel;

public class Core {

	public static String realPath;

	public static String swapUpPut(String upPut)
	{
		if (upPut.indexOf("Put") != -1)
		{
			return upPut.replace("Put", "UP");
		}
		if (upPut.indexOf("UP") != -1)
		{
			return upPut.replace("UP", "Put");
		}
		return upPut;
	}

	public static String swapDownGet(String downGet)
	{
		if (downGet.indexOf("Get") != -1)
		{
			return downGet.replace("Get", "DOWN");
		}
		if (downGet.indexOf("DOWN") != -1)
		{
			return downGet.replace("DOWN", "Get");
		}
		return downGet;
	}

	
	public static String getLocalidade(String banco)
	{
		if (banco.indexOf("am_manaus") != -1)
		{
			return "Manaus - AM";
		}
		if (banco.indexOf("Manaus - AM") != -1)
		{
			return "am_manaus";
		}
		if (banco.indexOf("ba_salvador") != -1)
		{
			return "Salvador - BA";
		}
		if (banco.indexOf("Salvador - BA") != -1)
		{
			return "ba_salvador";
		}
		if (banco.indexOf("ce_fortaleza") != -1)
		{
			return "Fortaleza - CE";
		}
		if (banco.indexOf("Fortaleza - CE") != -1)
		{
			return "ce_fortaleza";
		}
		if (banco.indexOf("df_brasilia") != -1)
		{
			return "Brasilia - DF";
		}
		if (banco.indexOf("Brasilia - DF") != -1)
		{
			return "df_brasilia";
		}
		if (banco.indexOf("mg_bh") != -1)
		{
			return "Belo Horizonte - MG";
		}
		if (banco.indexOf("Belo Horizonte - MG") != -1)
		{
			return "mg_bh";
		}
		if (banco.indexOf("mt_cuiaba") != -1)
		{
			return "Cuiaba - MT";
		}
		if (banco.indexOf("Cuiaba - MT") != -1)
		{
			return "mt_cuiaba";
		}
		if (banco.indexOf("pe_pernambuco") != -1)
		{
			return "Recife - PE";
		}
		if (banco.indexOf("Recife - PE") != -1)
		{
			return "pe_pernambuco";
		}
		if (banco.indexOf("pr_curitiba") != -1)
		{
			return "Curitiba - PR";
		}
		if (banco.indexOf("Curitiba - PR") != -1)
		{
			return "pr_curitiba";
		}
		if (banco.indexOf("rj_rj") != -1)
		{
			return "Rio de Janeiro - RJ";
		}
		if (banco.indexOf("Rio de Janeiro - RJ") != -1)
		{
			return "rj_rj";
		}
		if (banco.indexOf("rn_natal") != -1)
		{
			return "Natal - RN";
		}
		if (banco.indexOf("Natal - RN") != -1)
		{
			return "rn_natal";
		}
		if (banco.indexOf("rs_poa") != -1)
		{
			return "Porto Alegre - RS";
		}
		if (banco.indexOf("Porto Alegre - RS") != -1)
		{
			return "rs_poa";
		}
		if (banco.indexOf("sp_sp") != -1)
		{
			return "Sao Paulo - SP";
		}
		if (banco.indexOf("Sao Paulo - SP") != -1)
		{
			return "sp_sp";
		}
		return null;

	}

	public static String getTecnologia(String banco)
	{
		if (banco.indexOf("3g") != -1)
		{
			return "3G";
		}
		if (banco.indexOf("3G") != -1)
		{
			return "3g";
		}
		if (banco.indexOf("4g") != -1)
		{
			return "4G";
		}
		if (banco.indexOf("4G") != -1)
		{
			return "4g";
		}
		return null;
	}
	
	public static String getBanco(int city, int technology)
	{
	
		return getLocalidade(city) + "_" + getTecnologia(technology);
	}

	public static String getBanco(String city, String technology)
	{
	
		return getLocalidade(city) + "_" + getTecnologia(technology);
	}

	public static String getLocalidade(int city)
	{
		switch(city)
		{
			case 1:
				return "am_manaus";
			case 2:
				return "ba_salvador";
			case 3:
				return "ce_fortaleza";
			case 4:
				return "df_brasilia";
			case 5:
				return "mg_bh";
			case 6:
				return "mt_cuiaba";
			case 7:
			return "pe_pernambuco";
			case 8:
				return "pr_curitiba";
			case 9:
				return "rj_rj";
			case 10:
				return "rn_natal";
			case 11:
				return "rs_poa";
			case 12:
				return "sp_sp";
			default:
				return null;
		}
	}
	
	public static String getTecnologia(int technology)
	{
		switch(technology)
		{
			case 3:
				return "3g";
			case 4:
				return "4g";
			default:
				return null;
		}
	}
	
	public static String getOperation(int operation)
	{
		switch(operation)
		{
			case 1:
				return "Get";
			case 2:
				return "Put";
			default:
				return null;
		}
	}
	
	public static String getOperator(int operator)
	{
		switch(operator)
		{
			case 5:
				return "CLARO";
			case 2:
				return "TIM";
			case 31:
				return "OI";
			case 11:
				return "VIVO";
			default:
				return null;
		}
	}
	
	public static String anonymiseOperatorName(String operatorName)
	{
		switch(operatorName)
		{
			case "CLARO":
				return "Operadora A"; 
			case "OI":
				return "Operadora B"; 
			case "TIM":
				return "Operadora C"; 
			case "VIVO":
				return "Operadora D"; 
			default:
				return "INVALIDO";
		}
	}
	
	public static String anonymiseAPN(String apn)
	{
		switch(apn.toLowerCase())
		{
			case "claro.com.br":
				return "Operadora A"; 
			case "java.claro.com.br":
				return "Operadora A"; 
			case "bandalarga.claro.com.br":
				return "Operadora A"; 
			case "gprs.oi.com.br":
				return "Operadora B"; 
			case "timbrasil.br":
				return "Operadora C"; 
			case "zap.vivo.com.br":
				return "Operadora D"; 
			default:
				return "INVALIDO";
		}
	}
	

	private static HashMap<String, Vector<String>> agruparConsultas(Vector<String> campaignsList)
	{
		/**
		 * Agrupar por banco
		 */
		
		HashMap<String, Vector<String>> hashMap = new HashMap<String, Vector<String>>();

		for (String campaign: campaignsList)
		{
			String[] split = campaign.split(",");
			String banco = Core.getBanco(split[0], split[1]);

			if(!hashMap.containsKey(banco)){
	            Vector<String> vetor = new Vector<String>();
	            vetor.add(split[2]);
	            hashMap.put(banco, vetor);
	        }
	        else
	        {
	            hashMap.get(banco).add(split[2]);
	        }
		}
		
		return hashMap;
	}
	
	private static <E> SortedSet<E> agruparDados(final Collection<E> collection)
	{
		if (collection != null) 
		{
			for (Object candidate: collection)
			{
				if (candidate instanceof FTPSummarySession)
				{
		    		SortedSet<E> sortedSet = new TreeSet<E>(
		    	            new Comparator<E>() 
		    	            {
		    	                @Override
		    	                public int compare(E e1, E e2) 
		    	                {
		    	                	FTPSummarySession ftp1 = (FTPSummarySession)e1;
		    	                	FTPSummarySession ftp2 = (FTPSummarySession)e2;

		    	                	String key1 = ftp1.getOperatorName()+ftp1.getTecnologia()+ftp1.getLocalidade();
		    	                	String key2 = ftp2.getOperatorName()+ftp2.getTecnologia()+ftp2.getLocalidade();
		    	                	int compare = (key1).compareTo(key2);

		    	                	return (compare == 0)? -1: compare;
		    	                }
		    	            });

		    		sortedSet.addAll(collection);

		    		return sortedSet;
		        }
				if (candidate instanceof MeasurementReport)
				{
		    		SortedSet<E> sortedSet = new TreeSet<E>(
		    	            new Comparator<E>() 
		    	            {
		    	                @Override
		    	                public int compare(E e1, E e2) 
		    	                {
		    	                	MeasurementReport rep1 = (MeasurementReport)e1;
		    	                	MeasurementReport rep2 = (MeasurementReport)e2;

		    	                	String key1 = rep1.getOperatorName()+rep1.getTecnologia()+rep1.getLocalidade()+rep1.getMsgInstant();
		    	                	String key2 = rep2.getOperatorName()+rep2.getTecnologia()+rep2.getLocalidade()+rep2.getMsgInstant();
		    	                	int compare = (key1).compareTo(key2);

		    	                	return (compare == 0)? -1: compare;
		    	                }
		    	            });

		    		sortedSet.addAll(collection);

		    		return sortedSet;
		        }
				if (candidate instanceof FTPPercentilesSession)
				{
		    		SortedSet<E> sortedSet = new TreeSet<E>(
		    	            new Comparator<E>() 
		    	            {
		    	                @Override
		    	                public int compare(E e1, E e2) 
		    	                {
		    	                	FTPPercentilesSession ftp1 = (FTPPercentilesSession)e1;
		    	                	FTPPercentilesSession ftp2 = (FTPPercentilesSession)e2;

		    	                	String key1 = ftp1.getOperatorName()+ftp1.getTecnologia()+ftp1.getOperations()+ftp1.getLocalidade();
		    	                	String key2 = ftp2.getOperatorName()+ftp2.getTecnologia()+ftp2.getOperations()+ftp2.getLocalidade();
		    	                	int compare = (key1).compareTo(key2);

		    	                	return (compare == 0)? -1: compare;
		    	                }
		    	            });

		    		sortedSet.addAll(collection);

		    		return sortedSet;
		        }
				if (candidate instanceof FTPSession)
				{
		    		SortedSet<E> sortedSet = new TreeSet<E>(
		    	            new Comparator<E>() 
		    	            {
		    	                @Override
		    	                public int compare(E e1, E e2) 
		    	                {
		    	                	FTPSession ftp1 = (FTPSession)e1;
		    	                	FTPSession ftp2 = (FTPSession)e2;

		    	                	String date1 = null;
		    	                	String date2 = null;
		    	                	
									try
									{
										SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		    	                		Date d1 = sdf.parse(ftp1.getMsgDate());
		    	                		Date d2 = sdf.parse(ftp2.getMsgDate());
			    	                	sdf.applyPattern("yyyy.MM.dd");
			    	                	date1 = sdf.format(d1);
			    	                	date2 = sdf.format(d2);
									} 
									catch (ParseException e) 
									{
										e.printStackTrace();
									}
									
		    	                	String key1 = ftp1.getOperatorName()+ftp1.getTecnologia()+ftp1.getOperations()+ftp1.getLocalidade()+date1+ftp1.getMsgInstant();
		    	                	String key2 = ftp2.getOperatorName()+ftp2.getTecnologia()+ftp2.getOperations()+ftp2.getLocalidade()+date2+ftp2.getMsgInstant();
		    	                	int compare = (key1).compareTo(key2);

		    	                	return (compare == 0)? -1: compare;
		    	                }
		    	            });

		    		sortedSet.addAll(collection);

		    		return sortedSet;
		        }
				if (candidate instanceof Sample)
				{
		    		SortedSet<E> sortedSet = new TreeSet<E>(
		    	            new Comparator<E>() 
		    	            {
		    	                @Override
		    	                public int compare(E e1, E e2) 
		    	                {
		    	                	Sample sample1 = (Sample)e1;
		    	                	Sample sample2 = (Sample)e2;
		    	                	String key1 = sample1.getOperatorName()+sample1.getTecnologia()+sample1.getDirecao()+sample1.getLocalidade();
		    	                	String key2 = sample2.getOperatorName()+sample2.getTecnologia()+sample2.getDirecao()+sample2.getLocalidade();
		    	                	int compare = (key1).compareTo(key2);
		    	                    return (compare == 0)? -1: compare;
		    	                }
		    	            });

		    		sortedSet.addAll(collection);

		    		return sortedSet;
				}
				if (candidate instanceof Map.Entry)
				{
		    		SortedSet<E> sortedSet = new TreeSet<E>(
		    	            new Comparator<E>() 
		    	            {
		    	                @Override
		    	                public int compare(E e1, E e2) 
		    	                {
		    	                	Object key1 = ((Map.Entry)e1).getKey();
		    	                	Object key2 = ((Map.Entry)e2).getKey();
		    	                	int compare = key1.toString().compareTo(key2.toString());
		    	                    return (compare == 0)? -1: compare;
		    	                }
		    	            });

		    		sortedSet.addAll(collection);

		    		return sortedSet;
				}
			}
		}
		return null;
	}
	

	public static TableModel launchVizu(String vizu, Boolean anonimo, Vector<String> operatorsSelected, Vector<String> operationsSelected, Vector<String> campaignsList, HashMap<String, Integer> tputMap) {

		FTPTableModel ftpTableModel = new FTPTableModel();
		Vector<FTPSession> allFTPSessions = new Vector<FTPSession>();

		/**
		 * Gerar dados
		 */
		
		for (Entry<String,Vector<String>> pair: agruparConsultas(campaignsList).entrySet())
		{
			String banco = pair.getKey();
			Vector<String> campanhas = pair.getValue();
			for (String operator: operatorsSelected)
			{
				//if (tputMap.get("isonomia") == 1 && banco.equals("pe_pernambuco_4g") && operator.equals("CLARO")) continue;
				//if (tputMap.get("isonomia") == 1 && banco.equals("rs_poa_3g") && operator.equals("TIM")) continue;

				for (String operation: operationsSelected)
				{
					allFTPSessions.addAll(FTPSessionGenerator.findFTPSessions(banco, vizu, operator, operation, campanhas, tputMap));	
				}
			}
		}

		/**
		 * Agrupar dados por operadora, tecnologia, cidade, operacao
		 */

		if (anonimo)
		{
			for (FTPSession ftpsession: agruparDados(allFTPSessions))
			{
				ftpTableModel.addAnonymous(ftpsession);
			}
		}
		else
		{
			for (FTPSession ftpsession: agruparDados(allFTPSessions))
			{
				ftpTableModel.add(ftpsession);
			}
		}
		
		return ftpTableModel;
	}

	public static TableModel launchMeasurementReports(String vizu, Boolean anonimo, Vector<String> operatorsSelected, Vector<String> operationsSelected, Vector<String> campaignsList, HashMap<String, Integer> tputMap) {

		MeasurementReportTableModel measRepTableModel = new MeasurementReportTableModel();
		Vector<MeasurementReport> allReports = new Vector<MeasurementReport>();

		/**
		 * Gerar dados
		 */
		
		for (Entry<String,Vector<String>> pair: agruparConsultas(campaignsList).entrySet())
		{
			String banco = pair.getKey();
			Vector<String> campanhas = pair.getValue();
			for (String operator: operatorsSelected)
			{
				//if (tputMap.get("isonomia") == 1 && banco.equals("pe_pernambuco_4g") && operator.equals("CLARO")) continue;
				//if (tputMap.get("isonomia") == 1 && banco.equals("rs_poa_3g") && operator.equals("TIM")) continue;

				for (String operation: operationsSelected)
				{
					allReports.addAll(MeasurementReportGenerator.findMeasurementReports(banco, vizu, operator, operation, campanhas, tputMap));	
				}
			}
		}

		/**
		 * Agrupar dados por operadora, tecnologia, cidade, operacao
		 */

		if (anonimo)
		{
			for (MeasurementReport mrep: agruparDados(allReports))
			{
				measRepTableModel.addAnonymous(mrep);
			}
		}
		else
		{
			for (MeasurementReport mrep: agruparDados(allReports))
			{
				measRepTableModel.add(mrep);
			}
		}
		
		return measRepTableModel;
	}
	
	
	public static TableModel percentiles(String vizu, Boolean anonimo, Vector<String> operatorsSelected, Vector<String> operationsSelected, Vector<String> campaignsList, HashMap<String, Integer> tputMap) {

		FTPPercentilesTableModel ftpPercentilesTableModel = new FTPPercentilesTableModel();
		Vector<FTPPercentilesSession> allFTPPercentilesSessions = new Vector<FTPPercentilesSession>();

		/**
		 * Gerar dados
		 */
		
		for (Entry<String,Vector<String>> pair: agruparConsultas(campaignsList).entrySet())
		{
			String banco = pair.getKey();
			Vector<String> campanhas = pair.getValue();
			for (String operator: operatorsSelected)
			{
				if (tputMap.get("isonomia") == 1 && banco.equals("pe_pernambuco_4g") && operator.equals("CLARO")) continue;
				if (tputMap.get("isonomia") == 1 && banco.equals("rs_poa_3g") && operator.equals("TIM")) continue;

				for (String operation: operationsSelected)
				{
					allFTPPercentilesSessions.addAll(FTPPercentilesSessionGenerator.findFTPPercentilesSessions(banco, vizu, operator, operation, campanhas, tputMap));	
				}
			}
		}

		/**
		 * Agrupar dados por operadora, tecnologia, cidade, operacao
		 */

		if (anonimo)
		{
			for (FTPPercentilesSession ftpPercentilesSession: agruparDados(allFTPPercentilesSessions))
			{
				ftpPercentilesTableModel.addAnonymous(ftpPercentilesSession);
			}
		}
		else
		{
			for (FTPPercentilesSession ftpPercentilesSession: agruparDados(allFTPPercentilesSessions))
			{
				ftpPercentilesTableModel.add(ftpPercentilesSession);
			}
		}
		
		return ftpPercentilesTableModel;
	}

	public static TableModel summary(String vizu, Boolean anonimo, Vector<String> operatorsSelected, Vector<String> operationsSelected, Vector<String> campaignsList, HashMap<String, Integer> tputMap) {

		FTPSummaryTableModel ftpSummaryTableModel = new FTPSummaryTableModel();
		Vector<FTPSummarySession> allFTPSummarySessions = new Vector<FTPSummarySession>();

		/**
		 * Gerar dados
		 */
		
		for (Entry<String,Vector<String>> pair: agruparConsultas(campaignsList).entrySet())
		{
			String banco = pair.getKey();
			Vector<String> campanhas = pair.getValue();
			for (String operator: operatorsSelected)
			{
					if (tputMap.get("isonomia") == 1 && banco.equals("pe_pernambuco_4g") && operator.equals("CLARO")) continue;
					if (tputMap.get("isonomia") == 1 && banco.equals("rs_poa_3g") && operator.equals("TIM")) continue;
				
					allFTPSummarySessions.addAll(FTPSummarySessionGenerator.findFTPSummarySessions(banco, vizu, operator, operationsSelected, campanhas, tputMap));	
			}
		}

		/**
		 * Agrupar dados por operadora, tecnologia, cidade, operacao
		 */

		if (anonimo)
		{
			for (FTPSummarySession ftpSummarySession: agruparDados(allFTPSummarySessions))
			{
				ftpSummaryTableModel.addAnonymous(ftpSummarySession);
			}
		}
		else
		{
			for (FTPSummarySession ftpSummarySession: agruparDados(allFTPSummarySessions))
			{
				ftpSummaryTableModel.add(ftpSummarySession);
			}
		}
		return ftpSummaryTableModel;
	}

	public static TableModel cdfXPdf(String vizu, Boolean anonimo, Vector<String> operatorsSelected, Vector<String> operationsSelected, Vector<String> campaignsList, HashMap<String, Integer> tputMap)
	{

		CdfTableModel tableModel = new CdfTableModel();
		
		Vector<Sample> allSamples = new Vector<Sample>();
				
		/**
		 * Gerar relatorio iterando por banco
		 */
		
		for (Entry<String, Vector<String>> pair: agruparConsultas(campaignsList).entrySet())
		{
			String banco = pair.getKey();
			Vector<String> campanhas = pair.getValue();

			// acquiring samples
			for (String operator: operatorsSelected)
			{
				if (tputMap.get("isonomia") == 1 && banco.equals("pe_pernambuco_4g") && operator.equals("CLARO")) continue;
				if (tputMap.get("isonomia") == 1 && banco.equals("rs_poa_3g") && operator.equals("TIM")) continue;
				
				for (String operation: operationsSelected)
				{
					allSamples.addAll(SampleGenerator.findSamples(banco, vizu, operator, operation, campanhas, tputMap));
				}
			}
		}

		if (anonimo)
		{
			for (Sample sample: agruparDados(allSamples))
			{
				CdfOrganizer cdfData = new CdfOrganizer(sample);
				tableModel.addAnonymous(cdfData);
			}
		}
		else
		{
			for (Sample sample: agruparDados(allSamples))
			{
				CdfOrganizer cdfData = new CdfOrganizer(sample);
				tableModel.add(cdfData);
			}
		}
		
		return tableModel;

	}

}
