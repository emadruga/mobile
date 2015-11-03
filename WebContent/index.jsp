<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mobile Web App</title>

<script src="javascript/jquery-1.11.1.js"></script>
<script src="javascript/jquery-ui.js"></script>
<script src="javascript/basic.js"></script>
<link rel="stylesheet" href="css/basic.css" type="text/css"	media="screen" />
<link rel="stylesheet" href="css/jquery-ui.css" type="text/css" />

</head>
<body>

	<div class="loading" style="display:none">
		<div class="loadingMessage">
			Carregando...
		</div>
	</div>


	<div class="common form">
		<form id="campanhas">
			<table>
				<tr>
					<td valign="top" width=250px>
						<div id="accordion">
						<h3>Selecione a(s) localidade(s)</h3>
						<div>
							<ol id="cityset">
								<li class="ui-widget-content" value="01">Manaus - AM</li><!-- am_manaus -->
								<li class="ui-widget-content" value="02">Salvador - BA</li><!-- ba_salvador -->
								<li class="ui-widget-content" value="03">Fortaleza - CE</li><!-- ce_fortaleza -->
								<li class="ui-widget-content" value="04">Brasilia - DF</li><!-- df_brasilia -->
								<li class="ui-widget-content" value="05">Belo Horizonte - MG</li><!-- mg_bh -->
								<li class="ui-widget-content" value="06">Cuiaba - MT</li><!-- mt_cuiaba -->
								<li class="ui-widget-content" value="07">Recife - PE</li><!-- pe_recife -->
								<li class="ui-widget-content" value="08">Curitiba - PR</li><!-- pr_curitiba -->
								<li class="ui-widget-content" value="09">Rio de Janeiro - RJ</li><!-- rj_rj -->
								<li class="ui-widget-content" value="10">Natal - RN</li><!-- rn_natal -->
								<li class="ui-widget-content" value="11">Porto Alegre - RS</li><!-- rs_poa -->
								<li class="ui-widget-content" value="12">Sao Paulo - SP</li><!-- sp_sp -->
							</ol>
						</div>
						<h3>Selecione a(s) tecnologia(s)</h3>
						<div>
							<ol id="tecnologiaset">
								<li class="ui-widget-content" value="3">3G</li><!-- 3g -->
								<li class="ui-widget-content" value="4">4G</li><!-- 4g -->
							</ol>
						</div>
						<h3>Selecione a(s) direcao(oes)</h3>
						<div>
							<ol id="operationset">
								<li class="ui-widget-content" value="1">Download</li><!-- Get -->
								<li class="ui-widget-content" value="2">Upload</li><!-- Put -->
							</ol>
						</div>
						<h3>Selecione a(s) operadora(s)</h3>
						<div>
							<ol id="mobileproviders">
								<li class="ui-widget-content" value="5">Claro</li><!-- 5 -->
								<li class="ui-widget-content" value="2">Tim</li><!-- 2 -->
								<li class="ui-widget-content" value="31">Oi</li><!-- 31 -->
								<li class="ui-widget-content" value="11">Vivo</li><!-- 11 -->
							</ol>
						</div>
						<h3>Selecione o relatorio</h3>
						<div>
							<p>
								<div id="typeset">
									<input type="radio" name="tipo" id="tipo3" value="pdfxcdf" checked="checked"><label for="tipo3">PDF x CDF</label><br />
									<input type="radio" name="tipo" id="tipo5" value="ftp"><label for="tipo5">FTP Detalhado</label><br />
									<input type="radio" name="tipo" id="tipo2" value="summary"><label for="tipo2">Taxa Sucesso</label><br />
									<input type="radio" name="tipo" id="tipo6" value="percentiles"><label for="tipo6">Velocidade Media</label><br />
									<input type="radio" name="tipo" id="tipo7" value="measurementReport"><label for="tipo7">PHY/LTE</label><br />						
								</div>
						</div>
						<h3>Selecione a visao</h3>
						<div>
							<p>
								<div id="viewset">
									<input type="radio" name="visao" id="visao1" value="instantanea"><label for="visao1">Instantanea</label><br />
									<input type="radio" name="visao" id="visao2" value="nao_instantanea" checked="checked"><label for="visao2">Nao Instantanea</label><br />
								</div>
						</div>
						<h3>Selecione anonimidade</h3>
						<div>
							<p>
								<div id="anonymousset">
									<input type="radio" name="anonimo" id="anonimo1" value="anonimo"><label for="anonimo1">Anonimo</label><br />
									<input type="radio" name="anonimo" id="anonimo2" value="nao_anonimo" checked="checked"><label for="anonimo2">Nao Anonimo</label><br />
								</div>
						</div>
						<h3>Selecione a saida</h3>
							<div>
								<p>
									<select name="outputType" id="outputType">
										<option id="html">HTML</option>
										<option id="pdf" selected>PDF</option>
										<option id="csv">CSV</option>
										<option id="rtf">RTF</option>
										<option id="xls">XLS</option>
										<option id="xml">XML</option>
									</select>
								</p>
							</div>
						</div>
					</td>
					<td valign="top" align=center width=750px>
						<div id="abas">
							<ul>
							<li><a href="#abas-1">Campanhas</a></li>
							<li><a href="#abas-2">Isonomia</a></li>
							</ul>
							<div id="abas-1">
								<div class="tabela">
									<div id="campaignList" class="ui-widget-content ui-corner-all">
										<h3 class="ui-widget-header ui-corner-all">Lista de Campanhas</h3>
											<table id="campaigns" border="1">
											</table>
									</div>
								</div>
								<div class="total">
									<br />
									<div id="selectedTotalEffect" class="ui-widget-content ui-corner-all">
										<h3 class="ui-widget-header ui-corner-all">TOTAL</h3>
										<p>
											Selected Samples: <label for="sumSamples"></label>
										</p>
										<p>
											Selected Sessions: <label for="sumSessions"></label>
										</p>
									</div>
								</div>
								<div class="tput3gup">
									<p>Throughput 3G - Up (kbps):
										<input type="text" name="tput3gup" id="tput3gup" maxlength="30" value="20">
									</p>
								</div>
								<div class="tput3gdown">
									<p>Throughput 3G - Down (kbps):
										<input type="text" name="tput3gdown" id="tput3gdown" maxlength="30" value="100">
									</p>
								</div>
								<div class="tput4gup">
									<p>Throughput 4G - Up (kbps):
										<input type="text" name="tput4gup" id="tput4gup" maxlength="30" value="100">
									</p>
								</div>
								<div class="tput4gdown">
									<p>Throughput 4G - Down (kbps):
										<input type="text" name="tput4gdown" id="tput4gdown" maxlength="30" value="500">
									</p>
								</div>
								
							</div>
							<div id="abas-2">
								<div class="tabelaIsonomia">
									<div id="campaignListIsonomia" class="ui-widget-content ui-corner-all">
										<h3 class="ui-widget-header ui-corner-all">Lista de Campanhas (Isonomia)</h3>
											<table id="campaignsIsonomia" border="1">
											</table>
									</div>
								</div>
								<div class="totalIsonomia">
									<br />
									<div id="selectedTotalEffectIsonomia" class="ui-widget-content ui-corner-all">
										<h3 class="ui-widget-header ui-corner-all">TOTAL</h3>
										<p>
											Selected Samples: <label for="sumSamplesIsonomia"></label>
										</p>
										<p>
											Selected Sessions: <label for="sumSessionsIsonomia"></label>
										</p>
									</div>
								</div>
								<div class="tput3gupIsonomia">
									<p>Throughput 3G - Up (kbps):
										<input type="text" name="tput3gupIsonomia" id="tput3gupIsonomia" maxlength="30" value="20">
									</p>
								</div>
								<div class="tput3gdownIsonomia">
									<p>Throughput 3G - Down (kbps):
										<input type="text" name="tput3gdownIsonomia" id="tput3gdownIsonomia" maxlength="30" value="100">
									</p>
								</div>
								<div class="tput4gupIsonomia">
									<p>Throughput 4G - Up (kbps):
										<input type="text" name="tput4gupIsonomia" id="tput4gupIsonomia" maxlength="30" value="100">
									</p>
								</div>
								<div class="tput4gdownIsonomia">
									<p>Throughput 4G - Down (kbps):
										<input type="text" name="tput4gdownIsonomia" id="tput4gdownIsonomia" maxlength="30" value="500">
									</p>
								</div>
							</div>
						</div>
					</td>
				</tr>
			</table>
			<button id="btnRunReport">Run Report</button>
		</form>
	</div>
</body>
</html>