(function (angular) {
    app.controller('MainCtrl', ["$scope", "$rootScope", "UserType", "PageNames", "$location", function ($scope, $rootScope, UserType, PageNames, $location) {


        Http.get("/user/SessionAuth", null, function (result, error) {
            if (error != null) {
                setTimeout(function () {
                    window.location = "login.html";
                }, 200);
            } else {

                $scope.$apply(function () {

                    $rootScope.currentUser = result.success;
                    switch ($rootScope.currentUser.type){
                        case UserType.STUDENT:
                            $rootScope.currentPage = STUDENT_HOME;
                            break;
                        case UserType.TEACHER:
                            $rootScope.currentPage = TEACHER_HOME;
                            break;
                        case UserType.MANAGER:
                            $rootScope.currentPage = MANAGER_HOME;
                            break;
                    }
                    $rootScope.currentPageName = PageNames[$rootScope.currentPage];
                    setTimeout(function () {
                        loaderVisibility(false);
                    }, 600);
                });
            }
        });


        $scope.openHome = function () {
            switch ($rootScope.currentUser.type) {
                case UserType.STUDENT:
                    $rootScope.currentPage = STUDENT_HOME;
                    break;
                case UserType.TEACHER:
                    $rootScope.currentPage = TEACHER_HOME;
                    break;
                case UserType.MANAGER:
                    $rootScope.currentPage = MANAGER_HOME;
                    break;
            }
            $rootScope.currentPageName = PageNames[$scope.currentPage];
        };

        $scope.logout = function () {
            Http.get("/user/LogoutUser", null, function (success, error) {
                window.location = "login.html";
            });
        };


        function loaderVisibility(show) {
            if (show)
                $("#loader").fadeIn(50);
            else
                $("#loader").fadeOut(200);
        }
    }]);
})();