var EditableTableInVoice = function () {

    return {

        //main function to initiate the module
        init: function () {
            function restoreRow(oTable, nRow) {
                var aData = oTable.fnGetData(nRow);
                var jqTds = $('>td', nRow);

                for (var i = 0, iLen = jqTds.length; i < iLen; i++) {
                    oTable.fnUpdate(aData[i], nRow, i, false);
                }

                oTable.fnDraw();
            }

            function editRow(oTable, nRow) {
                var aData = oTable.fnGetData(nRow);
                var jqTds = $('>td', nRow);
                var jqInputs = $('input', nRow);
                
                jqTds[0].innerHTML = '<input type="text" style="width:100%" id="invoiceNum" name="invoiceNum" class="form-control small required" value="' + aData[0] + '">';
                jqTds[1].innerHTML = '<input type="radio" style="width:100%" id="includeFlag" name="invoiceFlag" class="radio-inline required m-bot15" checked>';
                jqTds[2].innerHTML = '<input type="radio" style="width:100%" id="excludeFlag" name="invoiceFlag" class="radio-inline required m-bot15">';
                /**
                 * While editing existing row, radio button values has to be retrieved from existing input form and appending. 
                 * With aData attribute, we will get the entire radio tag, not just whether it is checked or not.
                 */
                if(jqInputs != null && jqInputs.length > 0){
                    if(jqInputs[0].checked == true){
                    	jqTds[1].innerHTML = '<input type="radio" style="width:100%" id="includeFlag" name="invoiceFlag" class="radio-inline required m-bot15" checked>';	
                    }
                    else{
                    	jqTds[1].innerHTML = '<input type="radio" style="width:100%" id="includeFlag" name="invoiceFlag" class="radio-inline required m-bot15">';
                    }
                    if(jqInputs[1].checked == true){
                    	jqTds[2].innerHTML = '<input type="radio" style="width:100%" id="excludeFlag" name="invoiceFlag" class="radio-inline required m-bot15" checked>';	
                    }
                    else{
                    	jqTds[2].innerHTML = '<input type="radio" style="width:100%" id="excludeFlag" name="invoiceFlag" class="radio-inline required m-bot15">';
                    }
                    
                }
                if($(aData[3]).attr('id') == null){
                    jqTds[3].innerHTML = '<a class="edit" href="">Save</a>';
                }
                else{
                    jqTds[3].innerHTML = '<a id='+ $(aData[3]).attr('id') +' class="edit" href="">Save</a>';  
                }
                if($(aData[4]).attr('id') == null){
                    if(aData[4].toString().indexOf('data-mode') > -1){
                           jqTds[4].innerHTML = aData[4];
                           /*'<a class="cancel" data-mode="new" href="">Cancel</a>'*/
                    }
                    else{
                           jqTds[4].innerHTML = '<a class="cancel" href="">Cancel</a>';      
                    }
                }
                else{
                    jqTds[4].innerHTML = '<a id='+ $(aData[4]).attr('id') +' class="cancel" href="">Cancel</a>';      
                }
            }

           
            
            function saveRow(oTable, nRow) {
                var jqInputs = $('input', nRow);
                if(jqInputs.length == 0 || jqInputs.length == 2){//Always jqInputs will be 2 due to radio buttons. Only if the length is 3, then something has to b saved.
                    /**There is no record to edit.
                     * Returning true, so that nEditable value to null.*/
                    return true;
                }
                if((jqInputs[0].value.trim()=="")){
                    $("#invoiceNum").valid();
                    return false;
                }
                
                var aData = oTable.fnGetData(nRow);
                
                oTable.fnUpdate(jqInputs[0].value, nRow, 0, true);
                /*oTable.fnUpdate(jqInputs[1].checked, nRow, 2, false);
                oTable.fnUpdate(jqInputs[2].checked, nRow, 3, false);*/
                if(jqInputs[1].checked){
                     oTable.fnUpdate('<input type="radio" style="width:100%" class="radio-inline required m-bot15" checked disabled/>', nRow,1, false);
                }
                else{
                     oTable.fnUpdate('<input type="radio" style="width:100%" class="radio-inline required m-bot15" disabled/>', nRow, 1, false);
                }
                if(jqInputs[2].checked){
                     oTable.fnUpdate('<input type="radio" style="width:100%" class="radio-inline required m-bot15" checked disabled/>', nRow, 2, false);
                }
                else{
                     oTable.fnUpdate('<input type="radio" style="width:100%" class="radio-inline required m-bot15" disabled/>', nRow, 2, false);
                }
                var jqAnchor = $('a', nRow);
                if($(jqAnchor[0]).attr('id') == null){
                    oTable.fnUpdate('<a class="edit" href="">Edit</a>', nRow, 3, false);
                }
                else{
                    oTable.fnUpdate('<a id='+ $(jqAnchor[0]).attr('id') +' class="edit" href="">Edit</a>', nRow, 3, false);    
                }
                if($(jqAnchor[1]).attr('id') == null){
                    oTable.fnUpdate('<a class="delete" href="">Delete</a>', nRow, 4, false);
                }
                else{
                    oTable.fnUpdate('<a id='+ $(jqAnchor[1]).attr('id') +' class="delete" href="">Delete</a>', nRow, 4, false);  
                }
                
                oTable.fnDraw();
                
                return true;
            }
            
            function cancelEditRow(oTable, nRow) {
                var jqInputs = $('input', nRow);
                /**Check if either amount or begin range is entered or not. If Any one of the value is entered,
                 * then throw error message.
                 * 
                 * If no values is added by user. Delete that row.*/
                if((jqInputs[0].value.trim()=="") && ($('#includeFlagId').is(':checked'))){
                    oTable.fnDeleteRow(nRow);
                }
                else if ((jqInputs[0].value.trim()=="")||(jqInputs[1].value.trim()=="")) {
                    $("#invoiceNum").valid();
                                  $('#includeFlagId').is(':checked');
                    alert('Invoice data should not be empty');
                    return false;
                }
                else{
                    oTable.fnUpdate(jqInputs[0].value, nRow, 1, false);
                    oTable.fnUpdate(jqInputs[1].value, nRow, 2, false);
                    /*oTable.fnUpdate(jqInputs[2].value, nRow, 2, false);*/
                    //oTable.fnUpdate(jqInputs[3].value, nRow, 3, false);
                    oTable.fnUpdate('<a class="edit" href="">Edit</a>', nRow, 4, false);
                    oTable.fnDraw();   
                }
                
            }

            var oTable = $('#editable-sample,#invoice-table').dataTable({
                "aLengthMenu": [
                    [5, 10, 20, -1],
                    [5, 10, 20, "All"] // change per page values here
                ],
                // set the initial value
                "iDisplayLength": 50,
            	"bDestroy": true,
            	"bSort": false,
            	"sPaginationType": "bootstrap",
            	"sDom": 't',
                "oLanguage": {
                    "sLengthMenu": "_MENU_ records per page",
                    "oPaginate": {
                        "sPrevious": "Prev",
                        "sNext": "Next"
                    }
                },
                "bAutoWidth": false , 
                "aoColumnDefs": [ {
                          'sClass' : 'center',
                          'bSortable': false,
                          'aTargets' : [0]                       
                    },
                    {
                          'bSortable': false,
                        'aTargets': [1]
                    },
                    {
                          'bSortable': false,
                        'aTargets': [2]
                    },
                    {
                        'bSortable': false,
                        'aTargets': [3]
                    },
                    {
                        'bSortable': false,
                        'aTargets': [4]
                    },
                ],
            });

            jQuery('#tier-schedule_wrapper .dataTables_filter input').addClass("form-control medium"); // modify table search input
            jQuery('#tier-schedule_wrapper .dataTables_length select').addClass("form-control xsmall"); // modify table per page dropdown

            var nEditing = null;

            $('#invoice_data_new').click(function(e) {
					e.preventDefault();
					var noRecord = true;

					$('#invoice-table tbody').find('tr').each(function(i) {
						var tds = $(this).find('td'), 
							tierId = tds.eq(0).text();
						if (tierId == 'No data available in table') {
							nEditing = null;
						}
					});

					var valid = true;
					if (nEditing != null) {
						valid = saveRow(oTable, nEditing);
						if (valid) {
							nEditing = null;
						}
					}

					if (valid) {
						$('#invoice-table tbody').find('tr').each(function(i) {
							var tds = $(this).find('td'), 
							tierId = tds.eq(0).text();

							if (tierId != 'No data available in table' && nEditing != null) {
								noRecord = false;
							}
						});

						if (noRecord || ($("#invoiceNum").valid() && $('#includeFlagId').is(':checked'))) {
							var aiNew = oTable
									.fnAddData(['','','',
											'<a class="edit" href="">Edit</a>',
											'<a class="cancel" data-mode="new" href="">Cancel</a>' ]);
							var nRow = oTable.fnGetNodes(aiNew[0]);
							editRow(oTable, nRow);

							nEditing = nRow;
						} else {
							$("#invoiceNum").valid();
							$('#includeFlagId').is(':checked');
							$("#errorMessageModal")
									.html(
											"Please rectify the highlighted errors !!!");
							$('#myModal5').modal('toggle');
						}
					}

				});

            $('#editable-sample a.delete,#invoice-table a.delete').live('click', function (e) {
                e.preventDefault();
                var id = $(this).attr('id');
                if (confirm("Are you sure you want to delete this row ?") == false) {
                    return;
                }

                var nRow = $(this).parents('tr')[0];
                oTable.fnDeleteRow(nRow);
            });

            $('#editable-sample a.cancel,#invoice-table a.cancel').live('click', function (e) {
                e.preventDefault();
                if ($(this).attr("data-mode") == "new") {
                    var nRow = $(this).parents('tr')[0];
                    oTable.fnDeleteRow(nRow);
                    if(nRow == nEditing){
                          nEditing = null;
                    }
                } else {
                    restoreRow(oTable, nEditing);
                    nEditing = null;
                }
            });

            $('#editable-sample a.edit,#invoice-table a.edit').live('click', function (e) {
                e.preventDefault();
                /* Get the row as a parent of the link that was clicked on */
                var nRow = $(this).parents('tr')[0];
                if (nEditing !== null && nEditing != nRow) {
                    /* Currently editing - but not this row - restore the old before continuing to edit mode */
                    var valid=saveRow(oTable, nEditing);
                    /*restoreRow(oTable, nEditing);*/
                    if(valid){
                        editRow(oTable, nRow);
                        nEditing = nRow;      
                    }
                } else if (nEditing == nRow && this.innerHTML == "Save") {
                    /* Editing this row and want to save it */
                    var valid=saveRow(oTable, nEditing);
                    if(valid){
                          nEditing = null;
                    }
                } else {
                    /* No edit in progress - let's start one */
                    editRow(oTable, nRow);
                    nEditing = nRow;
                }
                
            });
        }

    };

}();