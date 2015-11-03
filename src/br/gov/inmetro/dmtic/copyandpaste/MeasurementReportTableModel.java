package br.gov.inmetro.dmtic.copyandpaste;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class MeasurementReportTableModel extends DefaultTableModel implements IFaceTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Vector<Object> columnames = new Vector<Object>();

	// A ordem é relevante para metodo ´add´ abaixo !!
	private String[] columnNames_Base = { 
			"Localidade","Tecnologia", "APN","Latitude", 
			"Longitude", "msgInstant","RSRP","OperatorName" };

	public Vector<Vector<Object>> data = new Vector<Vector<Object>>();

	public MeasurementReportTableModel() {
		super();
		organizeColumnNames();
	}

	public void add(MeasurementReport measRep) {
		Vector<Object> vector;
		vector = new Vector<Object>();

		vector.add(measRep.getLocalidade());
		vector.add(measRep.getTecnologia());
		vector.add(measRep.getApn());
		vector.add(measRep.getLatitude());
		vector.add(measRep.getLongitude());
		vector.add(measRep.getMsgInstant());
		vector.add(measRep.getRSRP());
		vector.add(measRep.getOperatorName());
		
		this.data.add(vector);

		setDataVector(data, columnames);
	}

	public void organizeColumnNames() {
		for (int i = 0; i < columnNames_Base.length; i++) {
			columnames.add(columnNames_Base[i]);
		}
	}

	public void addAnonymous(MeasurementReport measRep) {
		Vector<Object> vector;
		vector = new Vector<Object>();

		vector.add(Core.anonymiseOperatorName(measRep.getOperatorName()));
		vector.add(measRep.getLocalidade());
		vector.add(measRep.getTecnologia());
		vector.add(Core.anonymiseAPN(measRep.getApn()));
		vector.add(measRep.getLatitude());
		vector.add(measRep.getLongitude());
		vector.add(measRep.getMsgInstant());
		vector.add(measRep.getRSRP());
		vector.add(measRep.getOperatorName());


		this.data.add(vector);

		setDataVector(data, columnames);
	}
}
