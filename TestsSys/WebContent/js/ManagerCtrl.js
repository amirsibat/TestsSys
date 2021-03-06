var managerScope;
(function (angular) {
    app.controller('ManagerCtrl', ["$scope", "$rootScope", "PageNames", function ($scope, $rootScope, PageNames) {

        managerScope = $scope;
        $scope.allExams = [];
        $scope.allQuestions = [];
        $scope.allGrades = [];
        $scope.selectedExam = {};
        $scope.selectedQuestion = {};
        $scope.selectedGrade = {};
        $scope.pendingRequests = [];
        $scope.allStats = [];
        $scope.courseStats = [];
        $scope.studentStats = [];
        $scope.teacherStats = [];
        $scope.probStats = [];
        $('#examDetails').modal({show: false});
        $('#questionDetails').modal({show: false});
        $('#gradeDetails').modal({show: false});
        $('#courseExamsStats').modal({show: false});
        $('#studentExamsStats').modal({show: false});
        $('#teacherExamsStats').modal({show: false});
        $('#ProbabilityDetails').modal({show: false});


        $scope.loadAllExams = function () {
            Http.get("/exam/getAllExams", null, function (result, error) {
                $scope.$apply(function () {

                    if (error != null) {
                        console.log(error);
                        $scope.allExams = [];
                        return;
                    }

                    $scope.allExams = result.success;
                });
            })
        };
        $scope.loadAllQuestions = function () {
            Http.get("/question/getAllQuestions", null, function (result, error) {
                $scope.$apply(function () {

                    if (error != null) {
                        console.log(error);
                        $scope.allQuestions = [];
                        return;
                    }

                    $scope.allQuestions = result.success;
                });
            })
        };
        $scope.loadAllGrades = function () {
            Http.get("/record/GetAllRecords", null, function (result, error) {
                $scope.$apply(function () {

                    if (error != null) {
                        console.log(error);
                        $scope.allGrades = [];
                        return;
                    }

                    $scope.allGrades = result.success;
                });
            })
        };

        $scope.loadPendingRequests = function () {
            Http.get("/request/GetPendingRequest", null, function (result, error) {
                $scope.$apply(function () {

                    if (error != null) {
                        console.log(error);
                        $scope.pendingRequests = [];
                        return;
                    }

                    $scope.pendingRequests = result.success;
                });
            })

        };

        $scope.openModal = function (exam) {
            if (exam.type == 0) {
                DOCXjs.fromExam(exam)
            } else {
                $('#examDetails').modal('show');
                setTimeout(function () {
                    $scope.$apply(function () {
                        $scope.selectedExam = exam;
                    });
                }, 100);
            }
        };

        $scope.openModalQuestion = function (question) {
            $('#questionDetails').modal('show');
            setTimeout(function () {
                $scope.$apply(function () {
                    $scope.selectedQuestion = question;
                });
            }, 100);
        };
        $scope.openModalGrade = function (grade) {
            $('#gradeDetails').modal('show');
            setTimeout(function () {
                $scope.$apply(function () {
                    $scope.selectedGrade = grade;
                });
            }, 100);
        };

        $scope.getCoursesNames = function (courses) {
            var coursesString = "";
            for (var i = 0; i < courses.length; i++) {
                coursesString += courses[i].name + (i < courses.length - 1 ? ", " : "");
            }
            return coursesString;
        };


        $scope.answerRequest = function (request, accept) {
            Http.post("/request/AnswerRequest", null, {"id": request.id, "accept": accept}, function (result, error) {
                $scope.$apply(function () {
                    if (error != null) {
                        console.log(error);
                        alert("Error");
                        return;
                    }
                    $scope.loadPendingRequests();
                });
            })
        };
        
        $scope.loadManagerStats = function(){
        	Http.get("/record/GetAdminStatistics", null, function (result, error) {
                $scope.$apply(function () {
                    if (error != null) {
                    	$scope.allStats = [];  
                        return;
                    }
                    $scope.allStats = result.success;
                });
            })
        	
        };
        
        $scope.openCourseStatsModal = function(){
        	$('#courseExamsStats').modal('show');
            setTimeout(function () {
                $scope.$apply(function () {
                    $scope.courseStats = $scope.allStats.courseExamAverage;
                });
            }, 100);
        };
        
        
        $scope.openProbabilityModal = function(probs){
        	$('#ProbabilityDetails').modal('show');
        	$scope.probStats = [];
            setTimeout(function () {
                $scope.$apply(function () {
                    $scope.probStats = probs[0];;
                });
            }, 100);
        };
       
        
        $scope.openStudentStatsModal = function(){
        	$('#studentExamsStats').modal('show');
            setTimeout(function () {
                $scope.$apply(function () {
                    $scope.studentStats = $scope.allStats.studentExamAverage;
                });
            }, 100);
        };
        
        $scope.openTeacherStatsModal = function(){
        	$('#teacherExamsStats').modal('show');
            setTimeout(function () {
                $scope.$apply(function () {
                    $scope.teacherStats = $scope.allStats.teacherExamAverage;
                });
            }, 100);
        };
        
        $scope.openExams = function (updatH) {
            $rootScope.currentPage = MANAGER_EXAMS;
            $rootScope.currentPageName = PageNames[$rootScope.currentPage];
            $scope.loadAllExams();
            if (updatH == null || updatH == true)
                updateHash($rootScope.currentPageName);
        };
        $scope.openQuestions = function (updatH) {
            $rootScope.currentPage = MANAGER_QUESTION;
            $rootScope.currentPageName = PageNames[$rootScope.currentPage];
            $scope.loadAllQuestions();
            if (updatH == null || updatH == true)
                updateHash($rootScope.currentPageName);
        };
        $scope.openGrades = function (updatH) {
            $rootScope.currentPage = MANAGER_GRADES;
            $rootScope.currentPageName = PageNames[$rootScope.currentPage];
            $scope.loadAllGrades();
            if (updatH == null || updatH == true)
                updateHash($rootScope.currentPageName);
        };
        $scope.openStatistics = function (updatH) {
            $rootScope.currentPage = MANAGER_STATISTICS;
            $rootScope.currentPageName = PageNames[$rootScope.currentPage];
            $scope.loadManagerStats();
            if (updatH == null || updatH == true)
                updateHash($rootScope.currentPageName);
        };
        $scope.openRequests = function (updatH) {
            $rootScope.currentPage = MANAGER_PENDING_REQUESTS;
            $rootScope.currentPageName = PageNames[$rootScope.currentPage];
            $scope.loadPendingRequests();
            if (updatH == null || updatH == true)
                updateHash($rootScope.currentPageName);
        };


        $scope.loadPendingRequests();

        var currentHash = decodeURI(window.location.hash.substring(2));
        switch (currentHash) {
            case PageNames[MANAGER_EXAMS]:
                $scope.openExams(false);
                break;
            case PageNames[MANAGER_QUESTION]:
                $scope.openQuestions(false);
                break;
            case PageNames[MANAGER_GRADES]:
                $scope.openGrades(false);
                break;
            case PageNames[MANAGER_STATISTICS]:
                $scope.openStatistics(false);
                break;
            case PageNames[MANAGER_PENDING_REQUESTS]:
                $scope.openRequests(false);
                break;
            default:
                $rootScope.currentPage = MANAGER_HOME;
                $rootScope.currentPageName = PageNames[$rootScope.currentPage];
                break;
        }

        window.addEventListener('popstate', function (event) {
            var currentHash = decodeURI(window.location.hash.substring(2));
            $scope.$apply(function () {
                switch (currentHash) {
                    case PageNames[MANAGER_EXAMS]:
                        $scope.openExams(false);
                        break;
                    case PageNames[MANAGER_QUESTION]:
                        $scope.openQuestions(false);
                        break;
                    case PageNames[MANAGER_GRADES]:
                        $scope.openGrades(false);
                        break;
                    case PageNames[MANAGER_STATISTICS]:
                        $scope.openStatistics(false);
                        break;
                    case PageNames[MANAGER_PENDING_REQUESTS]:
                        $scope.openRequests(false);
                        break;
                    default:
                        $rootScope.currentPage = MANAGER_HOME;
                        $rootScope.currentPageName = PageNames[$rootScope.currentPage];
                        break;
                }
            });
            event.preventDefault();
        });
    }]);
})();
