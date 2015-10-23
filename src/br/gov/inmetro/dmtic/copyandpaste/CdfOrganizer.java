package br.gov.inmetro.dmtic.copyandpaste;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

public class CdfOrganizer {

	public String operatorName = null;
	public String localidade = null;
	public String tecnologia = null;
	public String direcao = null;
	public Vector<Double> relativeFrequencies = null;
	public Vector<Double> cumulativeRelativeFrequencies = null;
	public Vector<String> intervalNames = null;
	public String startDate = null;
	public String endDate = null;
	public Double percentil95 = null;
	public Double percentil99 = null;
	public Double mediaAritmetica = null;
	public Double mediaPonderada = null;
	public Double mediana = null;
	public Integer n = null;

	/*
	 * constructor
	 */
	
	public CdfOrganizer(Sample sample) {

		// set some variables
		this.operatorName = sample.getOperatorName();
		this.localidade = sample.getLocalidade();
		this.tecnologia = sample.getTecnologia();
		this.percentil95 = sample.getPercentil95();
		this.percentil99 = sample.getPercentil99();
		this.mediaAritmetica = sample.getMediaAritmetica();
		this.mediaPonderada = sample.getMediaPonderada();
		this.mediana = sample.getMediana();
		this.direcao = sample.getDirecao();
		this.startDate = new SimpleDateFormat("dd/MM/yyyy").format(sample.getMinMsgTime());
		this.endDate = new SimpleDateFormat("dd/MM/yyyy").format(sample.getMaxMsgTime());

	
		/**
		 * Parameters:
		 * 		startDate
		 * 		endDate
		 * 		lowerLimit
		 * 		classInterval
		 * 		upperLimit
		 * 		numberOfClasses
		 */

		n = new Integer(0);
		
		for (Integer n_i: sample.getHistogram().values())
		{
		    n += n_i;
		}
		
		/**
		 * sortedSet = par <degrau, frequencia> ordenado por degrau
		 */
		
		SortedSet<Entry<BigDecimal, Double>> sortedSet = new TreeSet<Entry<BigDecimal, Double>>(
	            new Comparator<Entry<BigDecimal, Double>>() 
	            {
	                @Override
	                public int compare(Entry<BigDecimal, Double> e1, Entry<BigDecimal, Double> e2) 
	                {
	                	return e1.getKey().compareTo(e2.getKey());
	                }
	            });

	    for (Entry<BigDecimal, Integer> pair: sample.getHistogram().entrySet()) 
	    {
			Double freq = 100*(pair.getValue().doubleValue()/n);
			sortedSet.add(new AbstractMap.SimpleEntry<BigDecimal, Double>(pair.getKey(), freq));
	    }
	    
		relativeFrequencies = new Vector<Double>();
		cumulativeRelativeFrequencies = new Vector<Double>();
		intervalNames = new Vector<String>();

	    Double mod = 100.0;
	    Double downInterval = new Double(0.0);
	    Double wannabeUpInterval = new Double(0.0);
	    Double degrau = sample.getDegrau();

	    for (Entry<BigDecimal, Double> pair: sortedSet)
	    {
	    	while (downInterval + degrau < pair.getKey().doubleValue())
	    	{
		    	this.relativeFrequencies.add(0.0);
		    	this.cumulativeRelativeFrequencies.add(mod);
		    	wannabeUpInterval = (downInterval + degrau);
				this.intervalNames.add(String.format("%.2f", downInterval/1000.0) + " -- " + String.format("%.2f", wannabeUpInterval/1000.0));
				downInterval += degrau;
	    	}
	    	
	    	this.relativeFrequencies.add(pair.getValue());
	    	mod -= pair.getValue();
	    	this.cumulativeRelativeFrequencies.add(mod);
	    	
			this.intervalNames.add(String.format("%.2f", downInterval/1000.0) + " -- " + String.format("%.2f", pair.getKey().doubleValue()/1000.0));
			
			downInterval = pair.getKey().doubleValue();
	    }
		
	}

}
