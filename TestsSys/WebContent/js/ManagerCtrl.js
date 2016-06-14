(function (angular) {
    app.controller('ManagerCtrl', ["$scope", "$rootScope", "PageNames", function ($scope, $rootScope, PageNames) {


        $scope.allExams = [];
        $scope.allQuestions = [];
        $scope.selectedExam = {};
        $('#examDetails').modal({show: false});


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


        $scope.openModal = function (exam) {
            $('#examDetails').modal('show');
            setTimeout(function () {
                $scope.$apply(function () {
                    $scope.selectedExam = exam;
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


        $scope.openExams = function () {
            $rootScope.currentPage = MANAGER_EXAMS;
            $rootScope.currentPageName = PageNames[$rootScope.currentPage];
            $scope.loadAllExams();
            updateHash($rootScope.currentPageName);
        };
        $scope.openQuestions = function () {
            $rootScope.currentPage = MANAGER_QUESTION;
            $rootScope.currentPageName = PageNames[$rootScope.currentPage];
            $scope.loadAllQuestions();
            updateHash($rootScope.currentPageName);
        };
        $scope.openGrades = function () {
            $rootScope.currentPage = MANAGER_GRADES;
            $rootScope.currentPageName = PageNames[$rootScope.currentPage];
            updateHash($rootScope.currentPageName);
        };
        $scope.openStatistics = function () {
            $rootScope.currentPage = MANAGER_STATISTICS;
            $rootScope.currentPageName = PageNames[$rootScope.currentPage];
            updateHash($rootScope.currentPageName);
        };
        $scope.openRequests = function () {
            $rootScope.currentPage = MANAGER_PENDING_REQUESTS;
            $rootScope.currentPageName = PageNames[$rootScope.currentPage];
            updateHash($rootScope.currentPageName);
        };


        var currentHash = decodeURI(window.location.hash.substring(2));
        switch (currentHash) {
            case PageNames[MANAGER_EXAMS]:
                $scope.openExams();
                break;
            case PageNames[MANAGER_QUESTION]:
                $scope.openQuestions();
                break;
            case PageNames[MANAGER_GRADES]:
                $scope.openGrades();
                break;
            case PageNames[MANAGER_STATISTICS]:
                $scope.openStatistics();
                break;
            case PageNames[MANAGER_PENDING_REQUESTS]:
                $scope.openRequests();
                break;
            default:
                $rootScope.currentPage = MANAGER_HOME;
                $rootScope.currentPageName = PageNames[$rootScope.currentPage];
                break;
        }
    }]);
})();
