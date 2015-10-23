/**
 *  JQuery JavaScript Library - http://jquery.com/download
 */

function getSelectableGeneric(tag) {
	return $( tag ).find( ".ui-selected" ).map(function() {return this.value;}).get().join("+");
}

function getSelectableCampaigns(tag) {
	return $( tag ).find( ".ui-selected" ).map(function() {return this.children[0].firstChild.data + "," + this.children[1].firstChild.data + "," + this.children[2].firstChild.data;}).get().join("$");
}

function getSessions(tag) {
	return $( tag ).find( ".ui-selected" ).map(function() {return this.children[5].firstChild.data;}).get().join("+");
}

function getSamples(tag) {
	return $( tag ).find( ".ui-selected" ).map(function() {return this.children[6].firstChild.data;}).get().join("+");
}

function showTputProperly() {
	if (getSelectableGeneric("#tecnologiaset").indexOf("3") >= 0 && ( $( "#typeset" ).find(".ui-state-active")[0].textContent == 'Velocidade Media' || $( "#typeset" ).find(".ui-state-active")[0].textContent == 'PDF x CDF'))
	{
		if (getSelectableGeneric("#operationset").indexOf("1") >= 0)
		{
			$('.tput3gdown').show();
			$('.tput3gdownIsonomia').show();
		}
		else
		{
			$('.tput3gdown').hide();
			$('.tput3gdownIsonomia').hide();
		}
		if (getSelectableGeneric("#operationset").indexOf("2") >= 0)
		{
			$('.tput3gup').show();
			$('.tput3gupIsonomia').show();
		}
		else
		{
			$('.tput3gup').hide();
			$('.tput3gupIsonomia').hide();
		}	
	}
	else
	{
		$('.tput3gdown').hide();
		$('.tput3gup').hide();
		$('.tput3gdownIsonomia').hide();
		$('.tput3gupIsonomia').hide();
	}
	
	if (getSelectableGeneric("#tecnologiaset").indexOf("4") >= 0 && ( $( "#typeset" ).find(".ui-state-active")[0].textContent == 'Velocidade Media' || $( "#typeset" ).find(".ui-state-active")[0].textContent == 'PDF x CDF'))
	{
		if (getSelectableGeneric("#operationset").indexOf("1") >= 0)
		{
			$('.tput4gdown').show();
			$('.tput4gdownIsonomia').show();
		}
		else
		{
			$('.tput4gdown').hide();
			$('.tput4gdownIsonomia').hide();
		}
		if (getSelectableGeneric("#operationset").indexOf("2") >= 0)
		{
			$('.tput4gup').show();
			$('.tput4gupIsonomia').show();
		}
		else
		{
			$('.tput4gup').hide();
			$('.tput4gupIsonomia').hide();
		}	
	}
	else
	{
		$('.tput4gdown').hide();
		$('.tput4gup').hide();
		$('.tput4gdownIsonomia').hide();
		$('.tput4gupIsonomia').hide();
	}
}

function somaCampanhas(event, ui) {
	if ( getSelectableCampaigns("#campaigns") != '' )
	{
 		$("label[for='sumSessions']").text(eval(getSessions("#campaigns")));
 		$("label[for='sumSamples']").text(eval(getSamples("#campaigns")));
		if (!$('.total').is(":visible"))
		{
			$( '.total' ).show();
		}

		if (!$('#selectedTotalEffect' ).is(":visible"))
		{
			$( '#selectedTotalEffect' ).toggle( "highlight", {}, 500 );
		}

	}
	else
	{
		if ($('#selectedTotalEffect' ).is(":visible"))
		{
			$( '#selectedTotalEffect' ).toggle( "highlight", {}, 500 );
		}
	}
}

function somaCampanhasIsonomia(event, ui) {
	if ( getSelectableCampaigns("#campaignsIsonomia") != '' )
	{
 		$("label[for='sumSessionsIsonomia']").text(eval(getSessions("#campaignsIsonomia")));
 		$("label[for='sumSamplesIsonomia']").text(eval(getSamples("#campaignsIsonomia")));
		if (!$('.totalIsonomia').is(":visible"))
		{
			$( '.totalIsonomia' ).show();
		}

		if (!$('#selectedTotalEffectIsonomia' ).is(":visible"))
		{
			$( '#selectedTotalEffectIsonomia' ).toggle( "highlight", {}, 500 );
		}

	}
	else
	{
		if ($('#selectedTotalEffectIsonomia' ).is(":visible"))
		{
			$( '#selectedTotalEffectIsonomia' ).toggle( "highlight", {}, 500 );
		}
	}
}

function stop(event, ui) {
	if (getSelectableGeneric( "#mobileproviders" ) != '' && getSelectableGeneric( "#cityset" ) != '' &&
			getSelectableGeneric( "#operationset" ) != '' && getSelectableGeneric( "#tecnologiaset" ) != '')
	{
		
		$.ajax ({
			url:
				'updateMobileWebApp',
				
			type:
				'POST',
				
			datatype:
				'json',
				
			data:
				$("#campanhas").serialize() +
				"&operadoras=" + getSelectableGeneric( "#mobileproviders" ) +
				"&cidades=" + getSelectableGeneric( "#cityset" ) +
				"&operacoes=" + getSelectableGeneric( "#operationset" ) +
				"&tecnologias=" + getSelectableGeneric( "#tecnologiaset" ),
				
			success:
				function(data) 
				{
					if (data.isValid) 
					{
						var table='<table id="campaigns">';
						/* loop over each object in the array to create rows */
						table+='<thead><tr>';
						$.each(data.table.columnIdentifiers, function(index, header) {
							/* add to html string started above*/
							table+='<th>'+header+'</th>';
						});
						table+='</tr></thead>';
						/* loop over each object in the array to create rows */
						table+='</tbody>';
						$.each(data.table.dataVector, function(index, row) {
							table+='<tr id="row'+index+'" class="ui-widget-content ui-selectee ui-selected">';
							$.each(row, function(index, cell) {
								/* add to html string started above*/
								table+='<td>'+cell+'</td>';
							});
							table+='</tr>';
						});
						table+='</tbody></table>';
						/* insert the html string*/
						$('#campaigns').html(table);

						var tableIsonomia='<table id="campaignsIsonomia">';
						/* loop over each object in the array to create rows */
						tableIsonomia+='<thead><tr>';
						$.each(data.tableIsonomia.columnIdentifiers, function(index, header) {
							/* add to html string started above*/
							tableIsonomia+='<th>'+header+'</th>';
						});
						tableIsonomia+='</tr></thead>';
						/* loop over each object in the array to create rows */
						tableIsonomia+='</tbody>';
						$.each(data.tableIsonomia.dataVector, function(index, row) {
							tableIsonomia+='<tr id="rowIsonomia'+index+'" class="ui-widget-content ui-selectee ui-selected">';
							$.each(row, function(index, cell) {
								/* add to html string started above*/
								tableIsonomia+='<td>'+cell+'</td>';
							});
							tableIsonomia+='</tr>';
						});
						tableIsonomia+='</tbody></table>';
						/* insert the html string*/
						$('#campaignsIsonomia').html(tableIsonomia);
						
						if (!$('.tabela').is(":visible"))
						{
							$( '.tabela' ).show();
						}

						if (!$('#campaignList' ).is(":visible"))
						{
							$( '#campaignList' ).toggle( "highlight", {}, 500 );
						}

						if (!$('.tabelaIsonomia').is(":visible"))
						{
							$( '.tabelaIsonomia' ).show();
						}

						if (!$('#campaignListIsonomia' ).is(":visible"))
						{
							$( '#campaignListIsonomia' ).toggle( "highlight", {}, 500 );
						}

						somaCampanhas(null, null);
						somaCampanhasIsonomia(null, null);

					}
					else
					{

						if ($('#campaignList' ).is(":visible"))
						{
							$( '#campaignList' ).toggle( "highlight", {}, 500 );
						}

						if ($('#campaignListIsonomia' ).is(":visible"))
						{
							$( '#campaignListIsonomia' ).toggle( "highlight", {}, 500 );
						}

						//alert('Please, choose a campaign.');
					}
				}
		});
	}
}


$(document).ready(function()
{

	$('.loading').hide();

	if ($( '.total' ).is(":visible"))
	{
 		$( '.total' ).hide();
	}

	if ($( '.tabela' ).is(":visible"))
	{
 		$( '.tabela' ).hide();
	}

	if ($( '.totalIsonomia' ).is(":visible"))
	{
 		$( '.totalIsonomia' ).hide();
	}

	if ($( '.tabelaIsonomia' ).is(":visible"))
	{
 		$( '.tabelaIsonomia' ).hide();
	}

	$(document).ajaxStart(function () {
		$('.loading').fadeIn(300);
		$('body').css('cursor', 'wait');
	});
	
	$(document).ajaxStop(function () {
		$('.loading').fadeOut(300);
		$('body').css('cursor', 'default');
	});

	$( "#abas" ).tabs();
	
	$( "#tecnologiaset" ).selectable({
		stop: 
			function (event, ui)
			{
				showTputProperly();
				stop(event, ui);
			}
	});
	
	$( "#cityset" ).selectable({
		stop: 
			function (event, ui)
			{
				stop(event, ui);
			}
	});
	
	$( "#operationset" ).selectable({
		stop: 
			function (event, ui)
			{
				showTputProperly();
				stop(event, ui);
			}
	
	});
	
	$( "#typeset" ).buttonset().click(function (event) {
		showTputProperly();
    });
	
	$( "#typeset" ).click();

	$( "#viewset" ).buttonset().click(function (event, ui) {
		if (event.timeStamp == 0)
		{
			stop(event, ui);
		}
    });
	
	$( "#anonymousset" ).buttonset();

	$( "#outputType" ).selectmenu();
	$( "#accordion" ).accordion();
	$( "#btnRunReport" ).button().click(
			function( event ) {
				$.ajax ({
					url:
						'runReport',
						
					type:
						'GET',
						
					datatype:
						'json',
						
					data:
						$("#campanhas").serialize() +
						"&aba=" + $("#abas").tabs('option', 'active') +
						"&operadoras=" + getSelectableGeneric( "#mobileproviders" ) +
						"&operacoes=" + getSelectableGeneric( "#operationset" ) +
						"&campaigns=" + 
						($("#abas").tabs('option', 'active') == 0 ? getSelectableCampaigns("#campaigns") : getSelectableCampaigns("#campaignsIsonomia")),
					success: function(data) {
						window.open(data, '_blank');
					}
				});
				event.preventDefault();
			});
	

	$( "#campaigns" ).selectable({
		 filter:
			 'tbody tr',
		 stop:
				function (event, ui)
				{
					somaCampanhas(event, ui);
				}
	});
	
	$( "#campaignsIsonomia" ).selectable({
		 filter:
			 'tbody tr',
		 stop:
				function (event, ui)
				{
					somaCampanhasIsonomia(event, ui);
				}
	});
	
	$( "#mobileproviders" ).selectable ({
		stop: 
			function (event, ui)
			{
				stop(event, ui);
			}
	});

	
	
});