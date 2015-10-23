package br.gov.inmetro.dmtic.copyandpaste;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;

public class Sample {

	private Double degrau = null;
	private String direcao = null;
	private String localidade = null;
	private Double mediaAritmetica = null;
	private Double mediana = null;
	private Double mediaPonderada = null;
	private Timestamp minMsgTime = null;
	private Timestamp maxMsgTime = null;
	private String operatorName = null;
	private Double percentil95 = null;
	private Double percentil99 = null;
	private String tecnologia = null;
	private HashMap<BigDecimal, Integer> histogram = null;

	public Double getDegrau() {
		return degrau;
	}

	public String getDirecao() {
		return direcao;
	}

	public HashMap<BigDecimal, Integer> getHistogram() {
		return histogram;
	}

	public String getLocalidade() {
		return localidade;
	}

	public Double getMediaAritmetica() {
		return mediaAritmetica;
	}

	public Double getMediana() {
		return mediana;
	}

	public Double getMediaPonderada() {
		return mediaPonderada;
	}

	public Timestamp getMinMsgTime() {
		return minMsgTime;
	}
	
	public Timestamp getMaxMsgTime() {
		return maxMsgTime;
	}
	
	public String getOperatorName() {
		return operatorName;
	}
	
	public Double getPercentil95() {
		return percentil95;
	}
	
	public Double getPercentil99() {
		return percentil99;
	}
	
	public String getTecnologia() {
		return tecnologia;
	}

	public void setDegrau(Double degrau) {
		this.degrau = degrau;
	}

	public void setDirecao(String direcao) {
		this.direcao = direcao;
	}

	public void setHistogram(HashMap<BigDecimal, Integer> histogram) {
		this.histogram = histogram;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}
	
	public void setMediaAritmetica(Double mediaAritmetica) {
		this.mediaAritmetica = mediaAritmetica;
	}

	public void setMediana(Double mediana) {
		this.mediana = mediana;
	}

	public void setMediaPonderada(Double mediaPonderada) {
		this.mediaPonderada = mediaPonderada;
	}

	public void setMinMsgTime(Timestamp minMsgTime) {
		this.minMsgTime = minMsgTime;
	}

	public void setMaxMsgTime(Timestamp maxMsgTime) {
		this.maxMsgTime = maxMsgTime;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public void setPercentil95(Double percentil95) {
		this.percentil95 = percentil95;
	}

	public void setPercentil99(Double percentil99) {
		this.percentil99 = percentil99;
	}

	public void setTecnologia(String tecnologia) {
		this.tecnologia = tecnologia;
	}
}
