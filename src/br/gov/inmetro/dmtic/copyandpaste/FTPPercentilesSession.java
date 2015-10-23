package br.gov.inmetro.dmtic.copyandpaste;

import java.math.BigDecimal;

/**
 * 
 * @author User
 */
public class FTPPercentilesSession {

	private Double commitment = null;
	private String localidade = null;
	private Double media = null;
	private Integer numFailed = null;
	private Integer numSuccess = null;
	private String operations = null;
	private String operatorName = null;
	private String tecnologia = null;
	private String velocidadeConforme = null;

	public Double getCommitment() {
		return commitment;
	}

	public String getLocalidade() {
		return localidade;
	}

	public Double getMedia() {
		return media;
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

	public String getTecnologia() {
		return tecnologia;
	}

	public String getVelocidadeConforme() {
		return velocidadeConforme;
	}

	public void setCommitment(Double commitment) {
		this.commitment = commitment;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public void setMedia(Double media) {
		this.media = media;
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

	public void setTecnologia(String tecnologia) {
		this.tecnologia = tecnologia;
	}

	public void setVelocidadeConforme(String velocidadeConforme) {
		this.velocidadeConforme = velocidadeConforme;
	}

}
