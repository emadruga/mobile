package br.gov.inmetro.dmtic.copyandpaste;


/**
 * 
 * @author User
 */
public class FTPSummarySession {

	private String localidade = null;
	private Integer numFailed = null;
	private Integer numSuccess = null;
	private String operatorName = null;
	private String tecnologia = null;

	public String getLocalidade() {
		return localidade;
	}

	public int getNumFailed() {
		return numFailed;
	}

	public int getNumSuccess() {
		return numSuccess;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public String getTecnologia() {
		return tecnologia;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public void setNumFailed(int numFailed) {
		this.numFailed = numFailed;
	}

	public void setNumSuccess(int numSuccess) {
		this.numSuccess = numSuccess;
	}

	public void setOperatorName(String op) {
		this.operatorName = op;
	}

	public void setTecnologia(String tecnologia) {
		this.tecnologia = tecnologia;
	}

}
