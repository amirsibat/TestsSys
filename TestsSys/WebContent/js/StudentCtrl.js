(function (angular) {
    app.controller('StudentCtrl', ["$scope", "$rootScope", "PageNames", function ($scope, $rootScope, PageNames) {

    	$scope.studentExams = [];
    	$scope.studentCourses = [];
    	$scope.ExamHolder = [];	
    	$scope.studentSubmittedExams = [];
    	$scope.CourseHolder = [];
    	
        var exam = {
            "date": "2016-07-11",
            "duration": 20,
            "profession": {"name": "Math", "id": "01"},
            "code": "4DTG",
            "descriptionTeacher": "Hello all my note",
            "author": {
                "firstName": "David",
                "lastName": "Antoon",
                "courses": ["01", "02", "03", "15", "16"],
                "description": "Math and Computer Science Teacher ",
                "id": "0006",
                "type": "TEACHER",
                "username": "davida"
            },
            "questionsList": [{
                "question": {
                    "profession": {"name": "Math", "id": "01"},
                    "courses": ["01", "02"],
                    "author": {
                        "firstName": "Dave",
                        "lastName": "Reed",
                        "courses": ["01", "03", "13", "14"],
                        "description": "Math and Computer Science Teacher",
                        "id": "0002",
                        "type": "TEACHER",
                        "username": "daver"
                    },
                    "options": ["A function is not a relation", "Every function is a relation", "Every relation is a function", "A relation is not a function"],
                    "id": "01001",
                    "text": "Which of the following statements is ALWAYS true?",
                    "correctAnswer": 3
                }, "grade": 25
            }, {
                "question": {
                    "profession": {"name": "Math", "id": "01"},
                    "courses": ["01", "02"],
                    "author": {
                        "firstName": "Dave",
                        "lastName": "Reed",
                        "courses": ["01", "03", "13", "14"],
                        "description": "Math and Computer Science Teacher",
                        "id": "0002",
                        "type": "TEACHER",
                        "username": "daver"
                    },
                    "options": ["2x + 4 > 1", "|x| > −5", "|x| < −10", "−x + 3 ≥ 5"],
                    "id": "01002",
                    "text": "Which of these inequalities has NO solutions?",
                    "correctAnswer": 3
                }, "grade": 25
            }, {
                "question": {
                    "profession": {"name": "Math", "id": "01"},
                    "courses": ["01", "02"],
                    "author": {
                        "firstName": "Laura",
                        "lastName": "Blankenship",
                        "courses": ["01", "02", "03", "04", "05"],
                        "description": "Math and Physics Teacher",
                        "id": "0004",
                        "type": "TEACHER",
                        "username": "laurab"
                    },
                    "options": ["−4", "4", "−3", "3"],
                    "id": "01004",
                    "text": "The lines y = (a + 1)x + 3 and y = −3x + 2 are parallel if a = ?",
                    "correctAnswer": 3
                }, "grade": 25
            }, {
                "question": {
                    "profession": {"name": "Math", "id": "01"},
                    "courses": ["01", "02"],
                    "author": {
                        "firstName": "David",
                        "lastName": "Antoon",
                        "courses": ["01", "02", "03", "15", "16"],
                        "description": "Math and Computer Science Teacher ",
                        "id": "0006",
                        "type": "TEACHER",
                        "username": "davida"
                    },
                    "options": ["5", "0", "−2", "−6"],
                    "id": "01007",
                    "text": "Which of these values of x satisfies the inequality −2x + 4 ≤ −6?",
                    "correctAnswer": 1
                }, "grade": 25
            }],
            "description": "Hello ,, student notes",
            "course": {"profession": "01", "name": "Algebra", "id": "01"},
            "id": "010100",
        };


        $scope.generateDocxFile = function (exam) {
            DOCXjs.fromExam(exam);
        };      
        
        $scope.loadStudentExams = function(){       	
        	 Http.get("/record/GetPublishedExamsByCourse", null, function (result, error) {
                 $scope.$apply(function () {
                     if (error != null) {
                         $scope.studentExams = [];
                         return;
                     }
                     $scope.studentExams = result.success;
                    /* console.log($scope.studentExams);*/
                 });
             });
        	
        };
        
        $scope.loadStudentCourses = function(){
        	 Http.get("/student/GetStudentCourses", null, function (result, error) {
                 $scope.$apply(function () {
                     if (error != null) {
                         $scope.studentCourses = [];
                         return;
                     }
                     $scope.studentCourses = result.success;
                     console.log($scope.studentCourses);

                 });
             });
        	
        };
        
        $scope.openCourseDetails = function (course) {
        	var objectToSend = {
                    courseId: course.id,
                    name:  course.name,
                    profession: course.profession
                };
        	
            Http.get("/record/GetCourseDetails", null, objectToSend, function (result, error) {
                $scope.$apply(function () {
                    if (error != null) {
                        $scope.studentSubmittedExams = [];
                        return;
                    }
                    $scope.studentSubmittedExams = result.success;
                });
            });
        };
        
        $scope.openExamModal = function (exam) {
            $scope.ExamHolder.exam = exam;
            
            
            console.log($scope.ExamHolder);
            $('#startExamModal').modal("show");
        };

        $scope.getExamName = function () {
            if ($scope.ExamHolder.exam == null)
                return "";
            return $scope.ExamHolder.exam.profession.name +
                ", " + $scope.ExamHolder.exam.course.name + ", " + $scope.ExamHolder.exam.id;
        };
        
       $scope.loadStudentExams();
       $scope.loadStudentCourses();
       
       $scope.openCourseDetails = function(updateH){
    	   $rootScope.currentPage = STUDENT_COURSE_DETAILS;
           $rootScope.currentPageName = PageNames[$rootScope.currentPage];
           $scope.loadCourseDetails();
           if (updateH == null || updateH == true)
               updateHash($rootScope.currentPageName);
       };
       
       
       var currentHash = decodeURI(window.location.hash.substring(2));
       switch (currentHash) {
           case PageNames[STUDENT_COURSE_DETAILS]:
        	   $scope.openCourseDetails(false);
               break;
           case PageNames[STUDENT_EXAM]:
               $scope.openExam(false);
               break;
           default:
               $rootScope.currentPage = STUDENT_HOME;
               $rootScope.currentPageName = PageNames[$rootScope.currentPage];
               break;
       }

       window.addEventListener('popstate', function (event) {
           var currentHash = decodeURI(window.location.hash.substring(2));
           $scope.$apply(function () {
               switch (currentHash) {
                   case PageNames[STUDENT_COURSE_DETAILS]:
                       $scope.openCourseDetails(false);
                       break;
                   case PageNames[STUDENT_EXAM]:
                       $scope.openExam(false);
                       break;
                   default:
                       $rootScope.currentPage = STUDENT_HOME;
                       $rootScope.currentPageName = PageNames[$rootScope.currentPage];
                       break;
               }
           });
           event.preventDefault();
       });

    }]);
})();