package br.gov.inmetro.dmtic.copyandpaste;

import java.math.BigDecimal;

/**
 * 
 * @author User
 */
public class MeasurementReport {

	private String apn = null;
	private String latitude = null;
	private String localidade = null;
	private String longitude = null;
	private String msgDate = null;
	private String msgInstant = null;
	private String operatorName = null;
	private String tecnologia = null;
	private Double rsrp = 0.0;

	public String getApn() {
		return apn;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLocalidade() {
		return localidade;
	}

	public String getLongitude() {
		return longitude;
	}

	public String getMsgDate() {
		return msgDate;
	}

	public String getMsgInstant() {
		return msgInstant;
	}


	public String getOperatorName() {
		return operatorName;
	}

	public String getTecnologia() {
		return tecnologia;
	}

	public void setApn(String newApn) {
		this.apn = newApn;
	}

	public void setLatitude(String lat) {
		this.latitude = lat;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public void setLongitude(String lgt) {
		this.longitude = lgt;
	}

	public void setMsgDate(String msgDate) {
		this.msgDate = msgDate;
	}

	public void setMsgInstant(String msgInstant) {
		this.msgInstant = msgInstant;
	}

	public void setOperatorName(String op) {
		this.operatorName = op;
	}

	public void setTecnologia(String tecnologia) {
		this.tecnologia = tecnologia;
	}

	public Double getRSRP() {
		return rsrp;
	}

	public void setRSRP(Double rsrp) {
		this.rsrp = rsrp;
	}

}
