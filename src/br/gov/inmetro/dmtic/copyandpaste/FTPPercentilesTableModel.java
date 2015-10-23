package br.gov.inmetro.dmtic.copyandpaste;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class FTPPercentilesTableModel extends DefaultTableModel implements IFaceTableModel {

	public Vector<Object> columnames = new Vector<Object>();

	// A ordem é relevante para metodo ´add´ abaixo !!
	private String[] columnNames_Base = { "OperatorName", "Localidade",
			"Tecnologia", "commitment", "numSuccess", "numFailed", "media",
			"Operations" };

	public Vector<Vector<Object>> data = new Vector<Vector<Object>>();


	public FTPPercentilesTableModel()
	{
		super();
		organizeColumnNames();
	}

	public void add(FTPPercentilesSession ftpPercentilesSession) 
	{
		Vector<Object> vector;
		vector = new Vector<Object>();

		vector.add(ftpPercentilesSession.getOperatorName());
		vector.add(ftpPercentilesSession.getLocalidade());
		vector.add(ftpPercentilesSession.getTecnologia());
		vector.add(ftpPercentilesSession.getCommitment());
		vector.add(ftpPercentilesSession.getNumSuccess());
		vector.add(ftpPercentilesSession.getNumFailed());
		vector.add(ftpPercentilesSession.getMedia());
		vector.add(Core.swapDownGet(Core.swapUpPut(ftpPercentilesSession.getOperations())));

		this.data.add(vector);

		setDataVector(data, columnames);
	}
	
	public void organizeColumnNames() 
	{
		for (int i = 0; i < columnNames_Base.length; i++) 
		{
			columnames.add(columnNames_Base[i]);
		}
	}

	public void addAnonymous(FTPPercentilesSession ftpPercentilesSession) {
		Vector<Object> vector;
		vector = new Vector<Object>();

		vector.add(Core.anonymiseOperatorName(ftpPercentilesSession.getOperatorName()));
		vector.add(ftpPercentilesSession.getLocalidade());
		vector.add(ftpPercentilesSession.getTecnologia());
		vector.add(ftpPercentilesSession.getCommitment());
		vector.add(ftpPercentilesSession.getNumSuccess());
		vector.add(ftpPercentilesSession.getNumFailed());
		vector.add(ftpPercentilesSession.getMedia());
		vector.add(Core.swapDownGet(Core.swapUpPut(ftpPercentilesSession.getOperations())));
		
		this.data.add(vector);

		setDataVector(data, columnames);
	}
}
