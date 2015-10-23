package br.gov.inmetro.dmtic.copyandpaste;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class CdfTableModel extends DefaultTableModel implements IFaceTableModel {

	public Vector<Object> columnames = new Vector<Object>();
	
	// A ordem é relevante para metodo ´add´ abaixo !!
	private String[] columnNames_Base = 
		{ 
			"operatorName", 
			"localidade", 
			"tecnologia", 
			"direcao",
			"rFrequencies", 
			"crFrequencies", 
			"intervalNames", 
			"startDate", 
			"endDate", 
			"numSamples",
			"percentil95",
			"percentil99",
			"mediaAritmetica",
			"mediaPonderada",
			"mediana"
		};

	public Vector<Vector<Object>> data = new Vector<Vector<Object>>();

	public CdfTableModel()
	{
		super();

		organizeColumnNames();
	}

	public void add(CdfOrganizer cdfData)
	{
		Vector<Object> vector;
		for (int i = 0; i < cdfData.relativeFrequencies.size(); i++)
		{
			vector = new Vector<Object>();
			vector.add(cdfData.operatorName);
			vector.add(cdfData.localidade);
			vector.add(cdfData.tecnologia);
			vector.add(Core.swapDownGet(Core.swapUpPut(cdfData.direcao)));
			vector.add(cdfData.relativeFrequencies.get(i));
			vector.add(cdfData.cumulativeRelativeFrequencies.get(i));
			vector.add(cdfData.intervalNames.get(i));
			vector.add(cdfData.startDate);
			vector.add(cdfData.endDate);
			vector.add(cdfData.n);
			vector.add(cdfData.percentil95);
			vector.add(cdfData.percentil99);
			vector.add(cdfData.mediaAritmetica);
			vector.add(cdfData.mediaPonderada);
			vector.add(cdfData.mediana);

			this.data.add(vector);
		}
		
		setDataVector(data, columnames);
	}


	public void organizeColumnNames() 
	{
		for (int i = 0; i < columnNames_Base.length; i++)
		{
			columnames.add(columnNames_Base[i]);
		}
	}

	public void addAnonymous(CdfOrganizer cdfData) {
		Vector<Object> vector;
		for (int i = 0; i < cdfData.relativeFrequencies.size(); i++)
		{
			vector = new Vector<Object>();
			vector.add(Core.anonymiseOperatorName(cdfData.operatorName));
			vector.add(cdfData.localidade);
			vector.add(cdfData.tecnologia);
			vector.add(Core.swapDownGet(Core.swapUpPut(cdfData.direcao)));
			vector.add(cdfData.relativeFrequencies.get(i));
			vector.add(cdfData.cumulativeRelativeFrequencies.get(i));
			vector.add(cdfData.intervalNames.get(i));
			vector.add(cdfData.startDate);
			vector.add(cdfData.endDate);
			vector.add(cdfData.n);
			vector.add(cdfData.percentil95);
			vector.add(cdfData.percentil99);
			vector.add(cdfData.mediaAritmetica);
			vector.add(cdfData.mediaPonderada);
			vector.add(cdfData.mediana);

			this.data.add(vector);
		}
		
		setDataVector(data, columnames);
	}

}
