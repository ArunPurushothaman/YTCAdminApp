var EditableAttachmentTable = function () {

    return {

        //main function to initiate the module
        init: function () {
            var oTable = $('#upload-attachment-table').dataTable({
                "aLengthMenu": [
                    [5, 10, 20, -1],
                    [5, 10, 20, "All"] // change per page values here
                ],
                // set the initial value
                "iDisplayLength": 50,
                "sDom": "<'row'<'col-lg-6'l><'col-lg-6'f>r>t<'row'<'col-lg-6'i><'col-lg-6'p>>",
                "sPaginationType": "bootstrap",
                "oLanguage": {
                    "sLengthMenu": "_MENU_ records per page",
                    "oPaginate": {
                        "sPrevious": "Prev",
                        "sNext": "Next"
                    }
                },
                "bAutoWidth": false , 
                "aoColumnDefs": [{
                        'bSortable': true,
                        'sClass' : 'read_only',
                        'aTargets': [0]
                    },
                    {
                        'bSortable': false,
                        'sClass' : 'read_only',
                        'aTargets': [1]
                    },
                    {
                        'bSortable': false,
                        'aTargets': [2]
                    },
                ],
            });

            $('#upload-attachment-table a.delete').live('click', function (e) {
                e.preventDefault();
                var id = $(this).attr('id');
                var $outerthis = $(this);
                $.dialog.confirm({message: 'Are you sure about this?', callback: function(result) {
                	if(result == true){
                        var nRow = $outerthis.parents('tr')[0];
                        var aData = oTable.fnGetData(nRow);
                        oTable.fnDeleteRow(nRow);

                        if(!isNaN(id)){
                        	var index = deletedUploadedFileList.length;
                        	/*if(index > 0){
                        		index++;
                        	}*/
                        	deletedUploadedFileList[index] = parseInt(id);             	
                        }
                        else{
                        	var index = newFileDeletedList.length;
                        	newFileDeletedList[index] = aData[1];               	
                        }                   		
                	}
                	}
                });
            });
        }
    };

}();