package com.prd.web.actions;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import br.gov.inmetro.dmtic.copyandpaste.Core;
import br.gov.inmetro.dmtic.database.MysqlConnection;
import br.gov.inmetro.dmtic.database.SqlServerConnection;

import com.google.gson.Gson;
import com.lowagie.text.pdf.hyphenation.TernaryTree.Iterator;

/**
 * gson Jar files - https://code.google.com/p/google-gson/
 */

/**
 * Servlet implementation class UpdateMobileWebApp
 */
@WebServlet("/updateMobileWebApp")
public class UpdateMobileWebApp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateMobileWebApp() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map <String, Object> map = new HashMap<String, Object>();
		boolean isValid = false;
		DefaultTableModel table = null;
		DefaultTableModel tableIsonomia = null;

		String operadoras = request.getParameter("operadoras");

		String cidades = request.getParameter("cidades");

		String tecnologias = request.getParameter("tecnologias");
		
		String operacoes = request.getParameter("operacoes");
		
		String visao = request.getParameter("visao").equals("instantanea")? "VIZU_APN_INSTANT": "VIZU_APN";

		if (operadoras != null && !operadoras.equals("") &&
				tecnologias != null && !tecnologias.equals("") &&
				cidades != null && !cidades.equals("") &&
				operacoes != null && !operacoes.equals(""))
		{
			Vector<String> operators = new Vector<String>();
			String[] cities = cidades.split(" ");
			String[] technologies = tecnologias.split(" ");
			Vector<String> operations = new Vector<String>();
			isValid = true;
			table = new DefaultTableModel();
			table.addColumn("City");
			table.addColumn("Technology");
			table.addColumn("Date");
			table.addColumn("Start Time");
			table.addColumn("End Time");
			table.addColumn("Sessions");
			table.addColumn("Samples");
			
			tableIsonomia = new DefaultTableModel();
			tableIsonomia.addColumn("City");
			tableIsonomia.addColumn("Technology");
			tableIsonomia.addColumn("Date");
			tableIsonomia.addColumn("Start Time");
			tableIsonomia.addColumn("End Time");
			tableIsonomia.addColumn("Sessions");
			tableIsonomia.addColumn("Samples");
			
			for (String operadora: operadoras.split(" "))
			{
				operators.add(Core.getOperator(Integer.parseInt(operadora)));
			}

			for (String operacao: operacoes.split(" "))
			{
				operations.add(Core.getOperation(Integer.parseInt(operacao)));
			}


			for (int i = 0; i < cities.length; i++)
			{
				for (int j = 0; j < technologies.length; j++)
				{
					updateTable(table, Core.getBanco(Integer.parseInt(cities[i]), Integer.parseInt(technologies[j])), visao, operators, operations, false);
					updateTable(tableIsonomia, Core.getBanco(Integer.parseInt(cities[i]), Integer.parseInt(technologies[j])), visao, operators, operations, true);
				}
			}
			
			
	
			map.put("isValid", isValid);
			map.put("table", table);
			map.put("tableIsonomia", tableIsonomia);
		}
		else
		{
			map.put("isValid", isValid);
		}

		write(response, map);
	}

	private void write(HttpServletResponse response, Map<String, Object> map) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(new Gson().toJson(map));
	}
	
		
	private TableModel updateTable(DefaultTableModel model, String banco, String vizu, Vector<String> operatorsSelected, Vector<String> operationsSelected, Boolean isonomia) 
	{
	
		SqlServerConnection connection = new SqlServerConnection(banco, vizu, "config/dbProperties.xml");
		String query = new String();
		
		query = connection.campaignListQuery(operatorsSelected, operationsSelected, isonomia);
		
		System.out.println("Consulta: " + query);
				
		try
		{
			connection.connect();
			
			Statement stm = connection.getConnection().createStatement();
			ResultSet result = stm.executeQuery(query);
			
			Vector<Object> vector;

			while (result.next()) 
			{
				vector = new Vector<Object>();
				vector.add(Core.getLocalidade(banco));
				vector.add(Core.getTecnologia(banco));
				vector.add(result.getString("campaignDate"));
				vector.add(result.getString("StartTime"));
				vector.add(result.getString("EndTime"));
				vector.add(result.getString("numSessions"));
				vector.add(result.getString("numSamples"));
				model.addRow(vector);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
	
		return model;
	}
	
}
