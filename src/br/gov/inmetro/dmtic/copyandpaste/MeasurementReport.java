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
	private Integer numFailed = null;
	private Integer numSuccess = null;
	private String operations = null;
	private String operatorName = null;
	private String status = null;
	private String tecnologia = null;

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

	public int getNumFailed() {
		return numFailed;
	}

	public int getNumSuccess() {
		return numSuccess;
	}

	public String getOperations() {
		return operations;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public String getStatus() {
		return status;
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

	public void setNumFailed(int numFailed) {
		this.numFailed = numFailed;
	}

	public void setNumSuccess(int numSuccess) {
		this.numSuccess = numSuccess;
	}

	public void setOperations(String operations) {
		this.operations = operations;
	}

	public void setOperatorName(String op) {
		this.operatorName = op;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setTecnologia(String tecnologia) {
		this.tecnologia = tecnologia;
	}

}
