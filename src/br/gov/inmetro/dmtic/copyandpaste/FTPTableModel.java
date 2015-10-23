package br.gov.inmetro.dmtic.copyandpaste;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class FTPTableModel extends DefaultTableModel implements IFaceTableModel {

	public Vector<Object> columnames = new Vector<Object>();

	// A ordem é relevante para metodo ´add´ abaixo !!
	private String[] columnNames_Base = { "OperatorName", "Localidade",
			"Tecnologia", "msgDate", "msgInstant", "APN", "status",
			"numSuccess", "numFailed", "Latitude", "Longitude", "Operations" };

	public Vector<Vector<Object>> data = new Vector<Vector<Object>>();

	public FTPTableModel() {
		super();
		organizeColumnNames();
	}

	public void add(FTPSession ftpSession) {
		Vector<Object> vector;
		vector = new Vector<Object>();

		vector.add(ftpSession.getOperatorName());
		vector.add(ftpSession.getLocalidade());
		vector.add(ftpSession.getTecnologia());
		vector.add(ftpSession.getMsgDate());
		vector.add(ftpSession.getMsgInstant());
		vector.add(ftpSession.getApn());
		vector.add(ftpSession.getStatus());
		vector.add(ftpSession.getNumSuccess());
		vector.add(ftpSession.getNumFailed());
		vector.add(ftpSession.getLatitude());
		vector.add(ftpSession.getLongitude());
		vector.add(Core.swapDownGet(Core.swapUpPut(ftpSession.getOperations())));

		this.data.add(vector);

		setDataVector(data, columnames);
	}

	public void organizeColumnNames() {
		for (int i = 0; i < columnNames_Base.length; i++) {
			columnames.add(columnNames_Base[i]);
		}
	}

	public void addAnonymous(FTPSession ftpSession) {
		Vector<Object> vector;
		vector = new Vector<Object>();

		vector.add(Core.anonymiseOperatorName(ftpSession.getOperatorName()));
		vector.add(ftpSession.getLocalidade());
		vector.add(ftpSession.getTecnologia());
		vector.add(ftpSession.getMsgDate());
		vector.add(ftpSession.getMsgInstant());
		vector.add(Core.anonymiseAPN(ftpSession.getApn()));
		vector.add(ftpSession.getStatus());
		vector.add(ftpSession.getNumSuccess());
		vector.add(ftpSession.getNumFailed());
		vector.add(ftpSession.getLatitude());
		vector.add(ftpSession.getLongitude());
		vector.add(Core.swapDownGet(Core.swapUpPut(ftpSession.getOperations())));

		this.data.add(vector);

		setDataVector(data, columnames);
	}
}
