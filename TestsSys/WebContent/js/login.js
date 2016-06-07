$(document).ready(function() {
			
		    $(window).load(function () {
		        setTimeout(function(){
		        	//Authenticate Session before showing the page and after 2000ms show page or redirect.
	        		$.ajax({
	        	        url: "/TestsSys/user/SessionAuth",
	        	        type: 'GET',
	        	        contentType: "application/json; charset=utf-8"
	        	    	})
	        			
	        	    	.done(function (data) {
	        	    		if(data.result == "success"){
	        					//console.log("success");
	        					// send user to the TestsSys if session was Authenticated
	        					window.location.pathname = "/TestsSys/index.html";
	        	    		}
	        	    	 })
	        	    	 .fail( function (data) {
	        	    		 console.log(data);
	        	    	 });
		        	//fadeOut preloader
		            $('#preloader').fadeOut('slow', function () {  
		            });
		        },2000);

		    }); 
			
			
			//Name can't be blank function called on user input
			$('#username').on('input', function() {
				var input=$(this);
				var is_username=input.val();
				// if username is empty add invalid class
				if(!is_username){
					input.addClass("invalid");
				}
				else{
					input.removeClass("invalid");
				}
			});

			//function called on user input 
			$('#password').on('input', function() {
				var input=$(this);
				var is_pass=input.val();
				//if password is empty add invalid class
				if(!is_pass){
					input.addClass("invalid");
				}
				else{
					input.removeClass("invalid");
				}
			});
			
			
			// after form submission validate inputs
			$("#loginButton").click(function(event){
				
				var form_data=$("#loginForm").serializeArray(); // Get All HTML objects inside the form
				var has_error=false;
				
				for (var input in form_data){ // loop over the objects
					var element=$("#"+form_data[input]['name']); // concatinate # to the form input name to get the object
					var invalid=element.hasClass("invalid");
					if (invalid || !element.val() ){
						element.addClass("invalid"); has_error=true;
						}					
				}
				// if has_error true prevent from from submiting and show alert error message
				if (has_error){
					$('.alertUserInvalid').show(1000);
					event.preventDefault(); // prevent form from sumbmitting
				}
				else{
					//Make AJAX call with server
					
					$('.alertUserInvalid').hide();
					var user = new Object();
				    user.Username = $('#username').val();
				    user.Password = $('#password').val();
				    
				    $.ajax({
				        url: "/TestsSys/user/LoginUser",
				        type: 'POST',
				        dataType: 'json',
				        data: JSON.stringify(user),
				        contentType: "application/json; charset=utf-8"
				    	})
						
				    	.done(function (data) {
				    		if(data.result == "successTEACHER"){
				    			// send user to dashboard after hiding the error message
				    			$('.alertUserInvalid').hide();
								//console.log("success");
								window.location.pathname = "/TestsSys/teacher_html_files/teacher.html";
				    		}
				    		if(data.result == "successMANAGER"){
				    			// send user to dashboard after hiding the error message
				    			$('.alertUserInvalid').hide();
								//console.log("success");
								window.location.pathname = "/TestsSys/manager_html_files/manager.html";
				    		}
				    		if(data.result == "successSTUDENT"){
				    			// send user to dashboard after hiding the error message
				    			$('.alertUserInvalid').hide();
								//console.log("success");
								window.location.pathname = "/TestsSys/student_html_files/student.html";
				    		}
				    		//if result not success show error message
					        	else{ $('.alertUserInvalid').show(1000); console.log("Invalid user or password"); }
				    	 })
				    	 .fail( function (data) {
				    		 console.log(data);
				    	 });
				    	
				}
			});
			
			
			
		});
		