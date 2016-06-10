

/* Formatting function for row details - modify as you need */
function format ( d ) {
    // `d` is the original data object for the row
    return '<table>'+
        '<tr>'+
           
        '<td>Grade:</td>'+
        '<td>'+'<input></input>'+'</td>'+
            
        '</tr>'+
        '<tr>'+
            '<td>Extension number:</td>'+
            '<td>'+d.extn+'</td>'+
        '</tr>'+
        '<tr>'+
            '<td>Extra info:</td>'+
            '<td>And any further details here (images etc)...</td>'+
        '</tr>'+
    '</table>';
}


//
// Updates "Select all" control in a data table
//
function updateDataTableSelectAllCtrl(table){
   var $table             = table.table().node();
   var $chkbox_all        = $('tbody input[type="checkbox"]', $table);
   var $chkbox_checked    = $('tbody input[type="checkbox"]:checked', $table);
   var chkbox_select_all  = $('thead input[name="select_all"]', $table).get(0);

   // If none of the checkboxes are checked
   if($chkbox_checked.length === 0){
      chkbox_select_all.checked = false;
      if('indeterminate' in chkbox_select_all){
         chkbox_select_all.indeterminate = false;
      }

   // If all of the checkboxes are checked
   } else if ($chkbox_checked.length === $chkbox_all.length){
      chkbox_select_all.checked = true;
      if('indeterminate' in chkbox_select_all){
         chkbox_select_all.indeterminate = false;
      }

   // If some of the checkboxes are checked
   } else {
      chkbox_select_all.checked = true;
      if('indeterminate' in chkbox_select_all){
         chkbox_select_all.indeterminate = true;
      }
   }
}

 
$(document).ready(function() {
	
	
	

	
	   // Array holding selected row IDs
	   var rows_selected = [];
	   var table = $('#myQuestions').DataTable({
	      'ajax': 'https://api.myjson.com/bins/1us28',
	      'columnDefs': [{
	         'targets': 0,
	         'searchable':false,
	         'orderable':false,
	         'width':'1%',
	         'className': 'dt-body-center',
	         'render': function (data, type, full, meta){
	
	             return '<input type="checkbox">';
	         }
	      }],
	      'order': [1, 'asc'],
	      'rowCallback': function(row, data, dataIndex){
	         // Get row ID
	         var rowId = data[0];
	         var grade = data[7];

	         // If row ID is in the list of selected row IDs
	         if($.inArray(rowId, rows_selected) !== -1){
	            $(row).find('input[type="checkbox"]').prop('checked', true);
	            $(row).addClass('selected');
	         }
	      }
	   });

	   // Handle click on checkbox
	   $('#myQuestions tbody').on('click', 'input[type="checkbox"]', function(e){
	      var $row = $(this).closest('tr');

	      // Get row data
	      var data = table.row($row).data();

	      // Get row ID
	      var rowId = data[0];

	      // Determine whether row ID is in the list of selected row IDs 
	      var index = $.inArray(rowId, rows_selected);

	      // If checkbox is checked and row ID is not in list of selected row IDs
	      if(this.checked && index === -1){
	         rows_selected.push(rowId);

	      // Otherwise, if checkbox is not checked and row ID is in list of selected row IDs
	      } else if (!this.checked && index !== -1){
	         rows_selected.splice(index, 1);
	      }

	      if(this.checked){
	         $row.addClass('selected');
	      } else {
	         $row.removeClass('selected');
	      }

	      // Update state of "Select all" control
	      updateDataTableSelectAllCtrl(table);

	      // Prevent click event from propagating to parent
	      e.stopPropagation();
	   });

	   // Handle click on table cells with checkboxes
	   $('#myQuestions').on('click', 'tbody td, thead th:first-child', function(e){
	      $(this).parent().find('input[type="checkbox"]').trigger('click');
	   });

	   // Handle click on "Select all" control
	   $('thead input[name="select_all"]', table.table().container()).on('click', function(e){
	      if(this.checked){
	         $('#myQuestions tbody input[type="checkbox"]:not(:checked)').trigger('click');
	      } else {
	         $('#myQuestions tbody input[type="checkbox"]:checked').trigger('click');
	      }

	      // Prevent click event from propagating to parent
	      e.stopPropagation();
	   });

	   // Handle table draw event
	   table.on('draw', function(){
	      // Update state of "Select all" control
	      updateDataTableSelectAllCtrl(table);
	   });
	    
	   $('#btn2').click( function () {
	    	
   			var selectedArray = [];
	    	for (var i = 0 ; i < table.rows('.selected').data().length; i++) {
		    	selectedArray[i] = table.rows('.selected').data()[i];
		    	console.log(selectedArray[i] );
		    }
	       
	    	
	   
	       
	    } );

	
	
	
	/*var selectedArray = new Object(); 
	
	var myQuestionsTable = $('#myQuestions').DataTable( {
        "ajax": "../js/Exams/data.txt",
        "columns": [
            {
                "className":      'details-control',
                "orderable":      false,
                "data":           null,
                "defaultContent": ''
            },
            { "data": "name" },
            { "data": "position" },
            { "data": "office" },
            { "data": "salary" }
        ],
        "order": [[1, 'asc']]
    } );
     
	
    // Add event listener for opening and closing details
    $('#myQuestions tbody').on('click', 'td.details-control', function () {
        var tr = $(this).closest('tr');
        var row = myQuestionsTable.row( tr );
 
        if ( row.child.isShown() ) {
            // This row is already open - close it
            row.child.hide();
            tr.removeClass('shown');
        }
        else {
            // Open this row
            row.child( format(row.data()) ).show();
            tr.addClass('shown');
        }
    } );
    
    $('#myQuestions tbody').on( 'click', 'tr', function () {
        $(this).toggleClass('selected');
    } );
    
    
    $('#btn2').click( function () {
    	
    		
    	for (var i = 0 ; i < myQuestionsTable.rows('.selected').data().length; i++) {
	    	selectedArray[i] = myQuestionsTable.rows('.selected').data()[i];
	    }
       console.log( myQuestionsTable.rows('.selected').data().length +' row(s) selected' );
      
   
       
    } );	
    */
  
   /* var table = $('#selectedQuestions').DataTable( {
        "ajax": "../js/Exams/data.txt",
        
        "columns": [
            {
                "className":      'details-control',
                "orderable":      false,
                "data":           null,
                "defaultContent": ''
            },
            { "data": "mText" },
            { "data": "mCorrectAnswer" },
            { "data": "mAuthor" },
            { "data": "mProfession" },
            { "data": "mOptions1" },
            { "data": "mOptions2" },
            { "data": "mOptions3" },
            { "data": "mOptions4" },
            { "data": "mCourses" }
            { "data": "name" },
            { "data": "position" },
            { "data": "office" },
            { "data": "salary" }
           
        ],
        "order": [[1, 'asc']]
    } );
     
    // Add event listener for opening and closing details
    $('#selectedQuestions tbody').on('click', 'td.details-control', function () {
        var tr = $(this).closest('tr');
        var row = table.row( tr );
 
        if ( row.child.isShown() ) {
            // This row is already open - close it
            row.child.hide();
            tr.removeClass('shown');
        }
        else {
            // Open this row
            row.child( format(row.data()) ).show();
            tr.addClass('shown');
        }
    } );
    
    $('#selectedQuestions tbody').on( 'click', 'tr', function () {
        $(this).toggleClass('selected');
        
    } );
    
    $('#save_exam').click( function () {
    	var rows;
        for (var i = 0; i < table.rows('.selected').data().length; i++) { 
        	table.rows('.selected').data()[i].Grade = "";
        	console.log( table.rows('.selected').data()[i]);
         
        }
        
    } );
    */
    
    
    
} );