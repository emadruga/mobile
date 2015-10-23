package br.gov.inmetro.dmtic.copyandpaste;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class FTPSummaryTableModel extends DefaultTableModel implements IFaceTableModel {

	public Vector<Object> columnames = new Vector<Object>();

	// A ordem é relevante para metodo ´add´ abaixo !!
	private String[] columnNames_Base = { "OperatorName", "Localidade",
			"Tecnologia", "numSuccess", "numFailed" };

	public Vector<Vector<Object>> data = new Vector<Vector<Object>>();

	public FTPSummaryTableModel() {
		super();
		organizeColumnNames();
	}

	public void add(FTPSummarySession ftpSummarySession) {
		Vector<Object> vector;
		vector = new Vector<Object>();

		vector.add(ftpSummarySession.getOperatorName());
		vector.add(ftpSummarySession.getLocalidade());
		vector.add(ftpSummarySession.getTecnologia());
		vector.add(ftpSummarySession.getNumSuccess());
		vector.add(ftpSummarySession.getNumFailed());

		this.data.add(vector);

		setDataVector(data, columnames);
	}

	public void organizeColumnNames() {
		for (int i = 0; i < columnNames_Base.length; i++) {
			columnames.add(columnNames_Base[i]);
		}
	}

	public void addAnonymous(FTPSummarySession ftpSummarySession) {
		Vector<Object> vector;
		vector = new Vector<Object>();

		vector.add(Core.anonymiseOperatorName(ftpSummarySession.getOperatorName()));
		vector.add(ftpSummarySession.getLocalidade());
		vector.add(ftpSummarySession.getTecnologia());
		vector.add(ftpSummarySession.getNumSuccess());
		vector.add(ftpSummarySession.getNumFailed());

		this.data.add(vector);

		setDataVector(data, columnames);
	}
}
