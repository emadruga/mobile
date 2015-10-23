/* Header: contains the package and necessary imports. */
package com.prd.web.actions;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.table.TableModel;

import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.engine.classic.core.ReportProcessingException;
import org.pentaho.reporting.engine.classic.core.TableDataFactory;
import org.pentaho.reporting.engine.classic.core.modules.output.pageable.pdf.PdfReportUtil;
import org.pentaho.reporting.engine.classic.core.modules.output.table.base.StreamReportProcessor;
import org.pentaho.reporting.engine.classic.core.modules.output.table.csv.CSVReportUtil;
import org.pentaho.reporting.engine.classic.core.modules.output.table.html.AllItemsHtmlPrinter;
import org.pentaho.reporting.engine.classic.core.modules.output.table.html.FileSystemURLRewriter;
import org.pentaho.reporting.engine.classic.core.modules.output.table.html.HtmlPrinter;
import org.pentaho.reporting.engine.classic.core.modules.output.table.html.StreamHtmlOutputProcessor;
import org.pentaho.reporting.engine.classic.core.modules.output.table.rtf.RTFReportUtil;
import org.pentaho.reporting.engine.classic.core.modules.output.table.xls.ExcelReportUtil;
import org.pentaho.reporting.engine.classic.core.modules.output.table.xml.XmlTableReportUtil;
import org.pentaho.reporting.libraries.repository.ContentIOException;
import org.pentaho.reporting.libraries.repository.ContentLocation;
import org.pentaho.reporting.libraries.repository.DefaultNameGenerator;
import org.pentaho.reporting.libraries.repository.file.FileRepository;
import org.pentaho.reporting.libraries.resourceloader.Resource;
import org.pentaho.reporting.libraries.resourceloader.ResourceException;
import org.pentaho.reporting.libraries.resourceloader.ResourceManager;

import br.gov.inmetro.dmtic.copyandpaste.Core;

/* The next line defines that this class is a Servlet, as well as the URL Mapping. */
@WebServlet("/runReport")
public class RunReport extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*
	 * Method doGet(): this determines that this class will only respond to
	 * requests of the type HTTP GET.
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * The following three lines take the parameters sent by the client and
		 * assign them to variables. In our case, we need "year" and "month" so
		 * that we can pass them to the PRD engine, since in our report we have
		 * defined the parameters "SelectYear" and "SelectMonth" respectively.
		 * The parameter "outputType" will help us to determine which type of
		 * output was required by the user.
		 */
		
		Map <String, Object> map = new HashMap<String, Object>();
		Boolean isValid = true;
		String operadoras = request.getParameter("operadoras");
		String campanhas = request.getParameter("campaigns");
		String operacoes = request.getParameter("operacoes");
		String tipo = request.getParameter("tipo");
		String visao = request.getParameter("visao");
		String anonimo = request.getParameter("anonimo");
		String tput3gdown = request.getParameter("tput3gdown");
		String tput3gup = request.getParameter("tput3gup");
		String tput4gdown = request.getParameter("tput4gdown");
		String tput4gup = request.getParameter("tput4gup");
		String tput3gdownIsonomia = request.getParameter("tput3gdownIsonomia");
		String tput3gupIsonomia = request.getParameter("tput3gupIsonomia");
		String tput4gdownIsonomia = request.getParameter("tput4gdownIsonomia");
		String tput4gupIsonomia = request.getParameter("tput4gupIsonomia");
		String cidade = request.getParameter("city");
		String tecnologia = request.getParameter("tecnologia");
		String outputType = request.getParameter("outputType");
		String aba = request.getParameter("aba");
		Vector<String> operadorasVetor = new Vector<String>();
		Vector<String> operacoesVetor = new Vector<String>();
		Vector<String> campanhasVetor = new Vector<String>();
		
		if (operadoras != null && !operadoras.equals(""))
		{
			String[] o = operadoras.split(" ");
			for (int i = 0; i < o.length; i++)
			{
				operadorasVetor.add(Core.getOperator(Integer.parseInt(o[i])));
			}
			map.put("operadorasVetor", operadorasVetor);
		}
		else
		{
			isValid = false;			
		}

		if (operacoes != null && !operacoes.equals(""))
		{
			String[] o = operacoes.split(" ");
			for (int i = 0; i < o.length; i++)
			{
				operacoesVetor.add(Core.getOperation(Integer.parseInt(o[i])));
			}
			map.put("operacoesVetor", operacoesVetor);
		}
		else
		{
			isValid = false;			
		}

		if (campanhas != null && !campanhas.equals(""))
		{
			String[] c = campanhas.split("\\$");
			for (int i = 0; i < c.length; i++)
			{
				campanhasVetor.add(c[i]);
			}
			map.put("campanhasVetor", campanhasVetor);
		}
		else
		{
			isValid = false;			
		}

		HashMap<String, Integer> tputMap = new HashMap<String, Integer>();
		
		if (aba != null && !aba.equals(""))
		{
			tputMap.put("isonomia", Integer.parseInt(aba));
		}
		
		if (tput3gdown != null && !tput3gdown.equals("") && aba != null && aba.equals("0"))
		{
			tputMap.put("tput3gdown", Integer.parseInt(tput3gdown));
		}

		if (tput3gup != null && !tput3gup.equals("") && aba != null && aba.equals("0"))
		{
			tputMap.put("tput3gup", Integer.parseInt(tput3gup));
		}

		if (tput4gdown != null && !tput4gdown.equals("") && aba != null && aba.equals("0"))
		{
			tputMap.put("tput4gdown", Integer.parseInt(tput4gdown));
		}

		if (tput4gup != null && !tput4gup.equals("") && aba != null && aba.equals("0"))
		{
			tputMap.put("tput4gup", Integer.parseInt(tput4gup));
		}

		if (tput3gdownIsonomia != null && !tput3gdownIsonomia.equals("") && aba != null && aba.equals("1"))
		{
			tputMap.put("tput3gdown", Integer.parseInt(tput3gdownIsonomia));
		}

		if (tput3gupIsonomia != null && !tput3gupIsonomia.equals("") && aba != null && aba.equals("1"))
		{
			tputMap.put("tput3gup", Integer.parseInt(tput3gupIsonomia));
		}

		if (tput4gdownIsonomia != null && !tput4gdownIsonomia.equals("") && aba != null && aba.equals("1"))
		{
			tputMap.put("tput4gdown", Integer.parseInt(tput4gdownIsonomia));
		}
		
		if (tput4gupIsonomia != null && !tput4gupIsonomia.equals("") && aba != null && aba.equals("1"))
		{
			tputMap.put("tput4gup", Integer.parseInt(tput4gupIsonomia));
		}
		
		map.put("tput", tputMap);
		
		map.put("banco", cidade + "_" + tecnologia);
		map.put("tipo", tipo);
		
		if (visao != null && !visao.equals(""))
		{
			if (visao.equals("instantanea"))
			{
				map.put("visao", "VIZU_APN_INSTANT");
			}
			else
			{
				map.put("visao", "VIZU_APN");
			}
		}
		
		if (anonimo != null && !anonimo.equals(""))
		{
			if (anonimo.equals("anonimo"))
			{
				map.put("anonimo", true);
			}
			else
			{
				map.put("anonimo", false);
			}
		}
		
		map.put("outputType", outputType);
		map.put("isValid", isValid);
		
		try 
		{
			map.put("Lenght", (int) 9);
			map.put("Delta1", (float) 0.4);
			map.put("Delta2", (float) 0.4);

			doReport(map, response);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}			


		/*
		 * The following if checks if an error has occurred, in which case
		 * creates a string with the error message and stores it in an attribute
		 * of the "request" object, and then performs a redirect to the index.
		 
		if (errorMsg.length() > 0) 
		{
			request.setAttribute("errorMsg", errorMsg);
			
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
			
			dispatcher.forward(request, response);
		}
		
		*/
	}

	/* Method doReport(): */
	private void doReport(Map <String, Object> map, HttpServletResponse response) throws ReportProcessingException, IOException, ResourceException, ContentIOException {

		Core.realPath = getServletContext().getRealPath("/");
		TableModel tableModel = null;
		URL urlToReport = null;
		TableDataFactory factory = null;

		if (map.get("tipo").equals("summary"))
		{
			tableModel = Core.summary(
					(String) map.get("visao"),
					(Boolean) map.get("anonimo"),
					(Vector<String>) map.get("operadorasVetor"), 
					(Vector<String>) map.get("operacoesVetor"), 
					(Vector<String>) map.get("campanhasVetor"),
					(HashMap<String, Integer>) map.get("tput"));
			
			urlToReport = new URL("file:" + getServletContext().getRealPath("WEB-INF/reports/FTPSuccessRatev5-resumido.prpt"));
			factory = new TableDataFactory();
			factory.addTable("default", (TableModel) tableModel);
		}
		
		if (map.get("tipo").equals("pdfxcdf"))
		{
			tableModel = Core.cdfXPdf(
					(String) map.get("visao"),
					(Boolean) map.get("anonimo"),
					(Vector<String>) map.get("operadorasVetor"),
					(Vector<String>) map.get("operacoesVetor"),
					(Vector<String>) map.get("campanhasVetor"),
					(HashMap<String, Integer>) map.get("tput"));
			
			urlToReport = new URL("file:" + getServletContext().getRealPath("WEB-INF/reports/pdfCdf-v5.prpt"));
			factory = new TableDataFactory();
			factory.addTable("default", (TableModel) tableModel);
		}
		
		if (map.get("tipo").equals("ftp"))
		{
			tableModel = Core.launchVizu(
					(String) map.get("visao"),
					(Boolean) map.get("anonimo"),
					(Vector<String>) map.get("operadorasVetor"), 
					(Vector<String>) map.get("operacoesVetor"), 
					(Vector<String>) map.get("campanhasVetor"),
					(HashMap<String, Integer>) map.get("tput"));

			urlToReport = new URL("file:" + getServletContext().getRealPath("WEB-INF/reports/FTPSuccessRatev5-1.prpt"));
			factory = new TableDataFactory();
			factory.addTable("default", (TableModel) tableModel);
		}
		
		if (map.get("tipo").equals("percentiles"))
		{
			tableModel = Core.percentiles(
					(String) map.get("visao"),
					(Boolean) map.get("anonimo"),
					(Vector<String>) map.get("operadorasVetor"), 
					(Vector<String>) map.get("operacoesVetor"), 
					(Vector<String>) map.get("campanhasVetor"),
					(HashMap<String, Integer>) map.get("tput"));
			urlToReport = new URL("file:" + getServletContext().getRealPath("WEB-INF/reports/FTPSuccessRatev5-percentiles.prpt"));
			factory = new TableDataFactory();
			factory.addTable("default", (TableModel) tableModel);
		}
		

		
		String type = (String) map.get("outputType");
		/* -> Global Setup */
		ResourceManager manager = new ResourceManager();
		manager.registerDefaults();

		Resource res = manager.createDirectly(urlToReport, MasterReport.class);
		MasterReport report = (MasterReport) res.getResource();

		
			
		if (factory != null)
		{
			report.setDataFactory(factory);
		}
		//
		response.setHeader("Content-disposition", "filename=out." + type);

		String fileName = "_out" + System.currentTimeMillis() + "." + type;
		
		File folderOut = new File(getServletContext().getRealPath("/out/" + System.currentTimeMillis()));
		
		if (!folderOut.exists()) 
		{
			folderOut.mkdirs();
			
			if (type.equalsIgnoreCase("pdf")) {
				//response.setContentType("application/pdf");
				File file = new File(folderOut.getPath() + "/" + fileName);
				PdfReportUtil.createPDF(report, file);
			}

			if (type.equalsIgnoreCase("csv")) {
				CSVReportUtil.createCSV(report, folderOut.getPath() + "/" + fileName);
			}

			if (type.equalsIgnoreCase("rtf")) {
				RTFReportUtil.createRTF(report, folderOut.getPath() + "/" + fileName);
			}

			if (type.equalsIgnoreCase("xls")) {
				ExcelReportUtil.createXLS(report, folderOut.getPath() + "/" + fileName);
			}

			if (type.equalsIgnoreCase("xml")) {
				XmlTableReportUtil.createStreamXML(report, folderOut.getPath() + "/" + fileName);
			}
		
			
			if (type.equalsIgnoreCase("html")) {
				//response.setContentType("text/html");
					
				final FileRepository targetRepository = new FileRepository(folderOut);
				final ContentLocation targetRoot = targetRepository.getRoot();
				final HtmlPrinter printer = new AllItemsHtmlPrinter(report.getResourceManager());
				final StreamHtmlOutputProcessor outputProcessor = new StreamHtmlOutputProcessor(report.getConfiguration());
				
				printer.setContentWriter(targetRoot, new DefaultNameGenerator(targetRoot, fileName));
				printer.setDataWriter(targetRoot, new DefaultNameGenerator(targetRoot, "content"));
				printer.setUrlRewriter(new FileSystemURLRewriter());
				
				outputProcessor.setPrinter(printer);
				
				final StreamReportProcessor reportProcessor = new StreamReportProcessor(report, outputProcessor);
				
				reportProcessor.processReport();
				reportProcessor.close();
				
			}

			String route = "out/" + folderOut.getName() + "/" + fileName;
			
			response.getWriter().write(route);
		}
	}
}