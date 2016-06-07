$(document).ready(function() {

		
	    $(window).load(function () {
	        setTimeout(function(){

			$.ajax({
		        url: "/TestsSys/user/SessionAuth",
		        type: 'GET',
		        contentType: "application/json; charset=utf-8",
		        success: function(data) {
		        	if(data.result == "success"){
						console.log("success");
		    		}
		        }
		    	})
				
		    	.done(function (data) {
		    		if(data.result == "success"){
						console.log("success");
		    		}
		    		// if Authintication failed send user to index.html
			        	else{ window.location.pathname = "/TestsSys/index.html"; } 
		    	 })
		    	 .fail( function (data) {
		    		 console.log(data);
		    	 });
			$('#preloader').fadeOut('slow', function () { // fadeOut preloader in 2 sec
            });
        },2000);

    	});



    	 // will logout the user. 
	    $("#logout_btn").click(function() {
	    	$.ajax({
		        url: "/TestsSys/user/LogoutUser",
		        type: 'GET'
		    	}).done(function (data) {
		    		if(data.result == "success"){
						window.location.pathname = "/TestsSys/index.html"; // send user to index.html page
		    		}
		    	 }).fail( function (data) {
		    		 console.log(data.result);
		    	 });
	    	
	    	});
	});