function fnFormatDetails ( oTable, nTr )
{
    var aData = oTable.fnGetData( nTr );
    var id = aData[1].replace('<span>', '').replace('</span>', '');
    var name = aData[2].replace('<span>', '').replace('</span>', '');
    var sOut = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
    sOut += '<tr><td>Tên:</td><td><a href="/admin/company/redirect?action=view&id=' + id + '">'+aData[2]+'<\a></td></tr>';
    sOut += '<tr><td>Địa chỉ:</td><td>'+aData[3]+'</td></tr>';
    sOut += '<tr><td></td><td>'+
    		'<a href="/admin/company/redirect?action=viewEdit&id='+id+'" class="btn btn-info" style="margin-right: 10px">Cập nhập</a>' +
    		'<a class="btn btn-danger" data-toggle="modal" href="#myModal6" onclick="showDialog(\''+name+'\','+id+');">Xóa</a>'
    	 +'</td></tr>';
    sOut += '</table>';
    return sOut;
}

$(document).ready(function() {

    $('#dynamic-table').dataTable( {
        "aaSorting": [[ 4, "desc" ]]
    } );

    /*
     * Insert a 'details' column to the table
     */
    var nCloneTh = document.createElement( 'th' );
    nCloneTh.style="width: 5%";
    var nCloneTd = document.createElement( 'td' );
    nCloneTd.innerHTML = '<img src="/static/admin/img/details_open.png">';
    nCloneTd.className = "center";

    $('#hidden-table-info thead tr').each( function () {
        this.insertBefore( nCloneTh, this.childNodes[0] );
    } );

    $('#hidden-table-info tbody tr').each( function () {
        this.insertBefore(  nCloneTd.cloneNode( true ), this.childNodes[0] );
    } );

    /*
     * Initialse DataTables, with no sorting on the 'details' column
     */
    var oTable = $('#hidden-table-info').dataTable( {
        "aoColumnDefs": [
            { "bSortable": false, "aTargets": [ 0 ] }
        ],
        "aaSorting": [[1, 'asc']]
    });

    /* Add event listener for opening and closing details
     * Note that the indicator for showing which row is open is not controlled by DataTables,
     * rather it is done here
     */
    $(document).on('click','#hidden-table-info tbody td img',function () {
        var nTr = $(this).parents('tr')[0];
        if ( oTable.fnIsOpen(nTr) )
        {
            /* This row is already open - close it */
            this.src = "/static/admin/img/details_open.png";
            oTable.fnClose( nTr );
        }
        else
        {
            /* Open this row */
            this.src = "/static/admin/img/details_close.png";
            oTable.fnOpen( nTr, fnFormatDetails(oTable, nTr), 'details' );
        }
    } );
} );