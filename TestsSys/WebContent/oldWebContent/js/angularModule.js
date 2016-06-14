var generateData = function(){
  var arr = [];
  var letterWords = ["alpha","bravo","charlie","daniel","earl","fish","grace","henry","ian","jack","karen","mike","delta","alex","larry","bob","zelda"]
  for (var i=1;i<60;i++){
    var id = letterWords[Math.floor(Math.random()*letterWords.length)];
    arr.push({"id":id+i,"name":"name "+i,"description":"Description of item #"+i,"field3":id,"field4":"Some stuff about rec: "+i,"field5":"field"+i});
  }
  return arr;
}


var app = angular.module('TestsApp',[])
		.controller('TestsController', ['$scope','$filter','$sce','$http','$interval', function($scope,$filter,$sce,$http,$interval) {
		
			var sortingOrder = 'name'; //default sort
	
			// init
			  $scope.sortingOrder = sortingOrder;
			  $scope.pageSizes = [5,10,25,50];
			  $scope.reverse = false;
			  $scope.filteredItems = [];
			  $scope.groupedItems = [];
			  $scope.itemsPerPage = 10;
			  $scope.pagedItems = [];
			  $scope.currentPage = 0;
			  $scope.items = generateData();

			  var searchMatch = function (haystack, needle) {
			    if (!needle) {
			      return true;
			    }
			    return haystack.toLowerCase().indexOf(needle.toLowerCase()) !== -1;
			  };
			  
			  // init the filtered items
			  $scope.search = function () {
			    $scope.filteredItems = $filter('filter')($scope.items, function (item) {
			      for(var attr in item) {
			        if (searchMatch(item[attr], $scope.query))
			          return true;
			      }
			      return false;
			    });
			    // take care of the sorting order
			    if ($scope.sortingOrder !== '') {
			      $scope.filteredItems = $filter('orderBy')($scope.filteredItems, $scope.sortingOrder, $scope.reverse);
			    }
			    $scope.currentPage = 0;
			    // now group by pages
			    $scope.groupToPages();
			  };
			  
			  // show items per page
			  $scope.perPage = function () {
			    $scope.groupToPages();
			  };
			  
			  // calculate page in place
			  $scope.groupToPages = function () {
			    $scope.pagedItems = [];
			    
			    for (var i = 0; i < $scope.filteredItems.length; i++) {
			      if (i % $scope.itemsPerPage === 0) {
			        $scope.pagedItems[Math.floor(i / $scope.itemsPerPage)] = [ $scope.filteredItems[i] ];
			      } else {
			        $scope.pagedItems[Math.floor(i / $scope.itemsPerPage)].push($scope.filteredItems[i]);
			      }
			    }
			  };
			  
			   $scope.deleteItem = function (idx) {
			        var itemToDelete = $scope.pagedItems[$scope.currentPage][idx];
			        var idxInItems = $scope.items.indexOf(itemToDelete);
			        $scope.items.splice(idxInItems,1);
			        $scope.search();
			        
			        return false;
			    };
			  
			  $scope.range = function (start, end) {
			    var ret = [];
			    if (!end) {
			      end = start;
			      start = 0;
			    }
			    for (var i = start; i < end; i++) {
			      ret.push(i);
			    }
			    return ret;
			  };
			  
			  $scope.prevPage = function () {
			    if ($scope.currentPage > 0) {
			      $scope.currentPage--;
			    }
			  };
			  
			  $scope.nextPage = function () {
			    if ($scope.currentPage < $scope.pagedItems.length - 1) {
			      $scope.currentPage++;
			    }
			  };
			  
			  $scope.setPage = function () {
			    $scope.currentPage = this.n;
			  };
			  
			  // functions have been describe process the data for display
			  $scope.search();
			 
			  
			  // change sorting order
			  $scope.sort_by = function(newSortingOrder) {
			    if ($scope.sortingOrder == newSortingOrder)
			      $scope.reverse = !$scope.reverse;
			    
			    $scope.sortingOrder = newSortingOrder;
			  };

			
			
			
	$scope.hideLoading = function() {
		
		// will fade out LoadingDiv and show Messages in 3 seconds.
		setTimeout(function(){$('#LoadingViewDiv').fadeOut(1000);
		$('#QestionDiv').delay(1000).fadeIn(500);
	 
	 },2000);
	
	};

	
	$scope.show_question_form = function() {
 		$('#PageHeader').html($('#show_question_form').html()); // change page header
		
		$('#LoadingViewDiv').height($('#QuestionDiv').height()).show(); // set LoadingViewDiv height to overly the same area of Questions
		
		$('#QuestionDiv').show(); // show QUESTIONS
		

		
		$scope.hideLoading(); // call hideLoading fun.
		
	};
	
	
 //DISPLAY QUESTIONS TABLE IN ORDER TO CREATE A NEW EXAM
	$scope.show_questions_table = function() {
 		$('#PageHeader').html($('#show_questions_table').html()); // change page header
		
		$('#LoadingViewDiv').height($('#listQuestionsDiv').height()).show(); // set LoadingViewDiv height to overly the same area of Questions
		
		$('#listQuestionsDiv').show(); // show QUESTIONS

		
		$scope.hideLoading(); // call hideLoading fun.
		
	};
	
	
	
	// create new Question func
	$scope.btn_create_quest = function() {
		
		if(($('#txtQuest').val().trim().length > 0 ) && ($('#firstAnswer').val().trim().length > 0) 
        		&& ($('#secondAnswer').val().trim().length > 0) 
        		&& ($('#thirdAsnwer').val().trim().length > 0) 
        		&& ($('#fourthAnswer').val().trim().length > 0) ){
        	
        	//create js object
    		var Quest = new Object();
    	    Quest.QuestionText = $scope.txtQuest;
    	    Quest.CorrectAnswer = $scope.correctAnswer;
    	    /* Quest.TeacherID = ""; */
    	    Quest.QuestionOptions1 = $scope.firstAnswer;
    	    Quest.QuestionOptions2 = $scope.secondAnswer;
    	    Quest.QuestionOptions3 = $scope.thirdAnswer;
    	    Quest.QuestionOptions4 = $scope.fourthAnswer;
    	    Quest.CoursesList = $scope.relatedCourses ; 
    	    
    	    
    	    //Make and http call to the server
        	var req = $http({
                method: "post",
                url: "/TestsSys/question/CreateQuestion",
                dataType: 'json',
                contentType: "application/json; charset=utf-8",
    	        data: JSON.stringify(Quest)
            });
            var data = JSON.stringify(Quest);
    	    req.success(function(response) {
    	    	$('.alertPostQuestSuc').show(1000).delay(3000).hide(1000); // show for 3 seconds and hide
    	    	//clear form
    	    	$scope.QuestionText = "";
    	    	$scope.CorrectAnswer = "";
    	    	$scope.QuestionOptions1 = "";
    	    	$scope.QuestionOptions2 = "";
    	    	$scope.QuestionOptions3 = "";
    	    	$scope.QuestionOptions4 = "";
    		});	
        }
        else{
        	$('.alertPostQuestEmpty').show(1000).delay(3000).hide(1000); // show for 3 seconds and hide
        }
    };

    
    
	
 // add username to link
	$scope.getUsernameAsLink = function(username)
	{
	return "profile.html?name="+username;
	};


	// create new Exam func
	$scope.btn_create_exam = function() {
		
		if(($('#duration').val().trim().length > 0 ) && ($('#examCode').val().trim().length > 0) 
        		&& ($('#studentGuidelines').val().trim().length > 0) 
        		&& ($('#teacherGuidelines').val().trim().length > 0)){
        	
        	//create js object
    		var Exam = new Object();
    		Exam.QuestionText = $scope.txtQuest;
    		Exam.CorrectAnswer = $scope.correctAnswer;
    	    /* Exam.TeacherID = ""; */
    		Exam.QuestionOptions1 = $scope.firstAnswer;
    		Exam.QuestionOptions2 = $scope.secondAnswer;
    		Exam.QuestionOptions3 = $scope.thirdAnswer;
    		Exam.QuestionOptions4 = $scope.fourthAnswer;
    		Exam.CoursesList = $scope.relatedCourses ; 
    	    
    	    
    	    //Make and http call to the server
        	var req = $http({
                method: "post",
                url: "/TestsSys/question/CreateQuestion",
                dataType: 'json',
                contentType: "application/json; charset=utf-8",
    	        data: JSON.stringify(Quest)
            });
            var data = JSON.stringify(Quest);
    	    req.success(function(response) {
    	    	$('.alertPostQuestSuc').show(1000).delay(3000).hide(1000); // show for 3 seconds and hide
    	    	//clear form
    	    	$scope.QuestionText = "";
    	    	$scope.CorrectAnswer = "";
    	    	$scope.QuestionOptions1 = "";
    	    	$scope.QuestionOptions2 = "";
    	    	$scope.QuestionOptions3 = "";
    	    	$scope.QuestionOptions4 = "";
    		});	
        }
        else{
        	$('.alertPostQuestEmpty').show(1000).delay(3000).hide(1000); // show for 3 seconds and hide
        }
    };

    
    $scope.btn_config_exam = function() {
    
    	
    }
    
    
	// show New Question modal fun
	$scope.NewQuestModalPop = function(){
		
		$('#NewQuestModal').modal('show'); // show reply modal 
		};
	

	
	}]);