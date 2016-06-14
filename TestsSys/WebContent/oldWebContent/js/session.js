

// get Param from url fun
		function getUrlParameter(sParam)
		{
			var sPageURL = window.location.search.substring(1);
			var sURLVariables = sPageURL.split('&'); // in case there's more than 1 param split by &
			
			//loop over the split url array and return sParam
			for (var i = 0; i < sURLVariables.length; i++) {
				
				var sParameterName = sURLVariables[i].split('=');
				if (sParameterName[0] == sParam){
				        return sParameterName[1];
				        }
				    }
				} 
			
		var uID,gUser; // Global Variables
		uID = getUrlParameter("name"); // get name param value

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
						gUser = data.username;
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