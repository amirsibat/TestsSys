var teacherScope = null;
(function (angular) {
    app.controller('TeacherCtrl', ["$scope", "$rootScope", "PageNames", function ($scope, $rootScope, PageNames) {

        teacherScope = $scope;
        $scope.newExamHolder = {
            profession: null,
            course: null,
            type: ONLINE,
            teacherNote: "",
            studentsNote: "",
            questions: []
        };
        $scope.newQuestionHolder = {
            profession: null,
            selectedCourses: [],
            questionText: "",
            firstAnswer: "",
            secondAnswer: "",
            thirdAnswer: "",
            fourthAnswer: "",
            correctAnswer: ""
        };
        $scope.options = [
            {
                option: "firstAnswerd",
                val: "1"
            },
            {
                option: "secondAnswer",
                val: "2"
            },
            {
                option: "thirdAnswer",
                val: "3"
            },
            {
                option: "fourthAnswer",
                val: "4"
            }
        ];

        $scope.newRequestHolder = {
            exam: null,
            requestText: "",
            duration: ""
        };

        $scope.allOnlineExams = [];

        $scope.examsWaitingForCheck = [];
        $scope.statistics = {};
        $scope.allQuestionsByCourse = [];
        $scope.allExams = [];
        $scope.allProfessions = [];
        $scope.allCoursesBySelectedProfession = [];
        $scope.examsToCheck = [];
        $scope.oldTeacherRequest = [];
        $scope.checkingExamHolder = {};
        $('#alertView').fadeOut(0);


        $scope.loadTeacherProfessions = function () {
            Http.get("/profession/GetTeacherProfessions", null, function (result, error) {
                if (error != null) {
                    $scope.allProfessions = [];
                    return;
                }
                $scope.$apply(function () {
                    $scope.allProfessions = result.success;
                });
            });
        };


        $scope.loadCourses = function (profession) {
            Http.get("/course/GetCoursesByProfession", {professionsId: profession.id}, function (result, error) {
                $scope.$apply(function () {
                    if (error != null) {
                        $scope.allCoursesBySelectedProfession = [];
                        return;
                    }
                    for (var i = 0; i < result.success.length; i++) {
                        result.success[i].isChecked = false;
                    }
                    $scope.allCoursesBySelectedProfession = result.success;
                });
            });
        };

        $scope.loadQuestionsByCourse = function (course) {
            Http.get("/question/GetQuestionsByCourse", {courseId: course.id}, function (result, error) {
                $scope.$apply(function () {
                    if (error != null) {
                        $scope.allQuestionsByCourse = [];
                        return;
                    }
                    $scope.allQuestionsByCourse = [];
                    for (var i = 0; i < result.success.length; i++) {
                        var found = false;
                        for (var j = 0; j < $scope.newExamHolder.questions.length; j++) {
                            if (result.success[i].id == $scope.newExamHolder.questions[j].id) {
                                found = true;
                            }
                        }
                        if (found == false) {
                            result.success[i].checked = false;
                            $scope.allQuestionsByCourse.push(result.success[i]);
                        }
                    }
                });
            });
        };

        $scope.selectQAnswer = function (answer) {
            $scope.newQuestionHolder.correctAnswer = answer;
        };

        $scope.selectQProfession = function (profession) {
            $scope.newQuestionHolder.profession = profession;
            $scope.newQuestionHolder.selectedCourses = [];
            $scope.loadCourses(profession);
        };

        $scope.selectQCourses = function () {
            for (var i = 0; i > $scope.allCoursesBySelectedProfession.length; i++) {
                if ($scope.allCoursesBySelectedProfession[i].checked == true) {
                    $scope.newQuestionHolder.selectedCourses.push($scope.allCoursesBySelectedProfession[i]);
                }
            }
        };

        $('body').on('click', '.multi li', function (event) {
            event.stopPropagation();
        });

        $scope.getSelectedCourses = function () {
            var coursesString = "";
            var countChecked = 0;
            for (var i = 0; i < $scope.allCoursesBySelectedProfession.length; i++) {
                if ($scope.allCoursesBySelectedProfession[i].isChecked) {
                    countChecked++;
                }
            }
            for (var i = 0; i < $scope.allCoursesBySelectedProfession.length; i++) {
                if ($scope.allCoursesBySelectedProfession[i].isChecked) {
                    countChecked--;
                    coursesString += $scope.allCoursesBySelectedProfession[i].name + (countChecked == 0 ? "" : ", ");
                }
            }
            if (coursesString == "") {
                coursesString = "Choose Courses";
            }
            return coursesString;
        }


        $scope.selectProfession = function (profession) {
            $scope.newExamHolder.profession = profession;
            $scope.newExamHolder.course = null;
            $scope.newExamHolder.questions = [];
            $scope.loadCourses(profession);
        };

        $scope.selectCourse = function (course) {
            $scope.newExamHolder.course = course;
            $scope.newExamHolder.questions = [];
            $scope.loadQuestionsByCourse(course);
        };

        $scope.addSelectedQuestions = function () {
            for (var i = 0; i < $scope.allQuestionsByCourse.length; i++) {
                if ($scope.allQuestionsByCourse[i].checked == true) {
                    $scope.newExamHolder.questions.push($scope.allQuestionsByCourse[i]);
                }
            }
            $scope.loadQuestionsByCourse($scope.newExamHolder.course);
            $scope.fixQuestionsGrades();
        };

        $scope.fixQuestionsGrades = function () {
            var avg = parseInt((1.0 / parseFloat($scope.newExamHolder.questions.length)) * 100);
            for (var i = 0; i < $scope.newExamHolder.questions.length; i++) {
                $scope.newExamHolder.questions[i].grade = avg;
            }
        };

        $scope.clearNewExamData = function () {
            $scope.newExamHolder = {
                profession: null,
                course: null,
                type: ONLINE,
                teacherNote: "",
                studentsNote: "",
                questions: []
            };
            $('#alertView').fadeOut();
        };


        $scope.clearNewQuestionData = function () {
            $scope.newQuestionHolder = {
                profession: null,
                selectedCourses: [],
                questionText: "",
                firstAnswer: "",
                secondAnswer: "",
                thirdAnswer: "",
                fourthAnswer: "",
                correctAnswer: ""
            };
            $('#alertView').fadeOut();
        };

        $scope.removeQuestionFromExam = function (question) {
            for (var i = 0; i < $scope.newExamHolder.questions.length; i++) {
                if ($scope.newExamHolder.questions[i].id == question.id) {
                    $scope.newExamHolder.questions.splice(i, 1);
                    break;
                }
            }
            $scope.loadQuestionsByCourse($scope.newExamHolder.course);
            $scope.fixQuestionsGrades();
        };


        $scope.createNewExam = function () {
            $('#alertView').fadeOut();
            console.log($scope.newExamHolder);
            Http.post("/exam/CreateNewExam", null, $scope.newExamHolder, function (result, error) {
                $scope.$apply(function () {
                    if (error != null) {
                        $('#alertView').html("Server error");
                        $('#alertView').fadeIn();
                        return;
                    }
                    $scope.openPublishExams();
                });
            });
        };


        $scope.loadOnlineExams = function () {
            $('#alertView').fadeOut();
            Http.get("/record/GetOnlineExams", null, function (result, error) {
                $scope.$apply(function () {
                    if (error != null) {
                        $scope.allOnlineExams = [];
                        return;
                    }
                    $scope.allOnlineExams = result.success;
                });
            });
        };

        $scope.createNewQuestion = function () {
            $('#alertView').fadeOut();
            $scope.newQuestionHolder.selectedCourses = [];
            for (var i = 0; i < $scope.allCoursesBySelectedProfession.length; i++) {
                if ($scope.allCoursesBySelectedProfession[i].isChecked) {
                    $scope.newQuestionHolder.selectedCourses.push($scope.allCoursesBySelectedProfession[i].id);
                }
            }
            Http.post("/question/CreateQuestion", null, $scope.newQuestionHolder, function (result, error) {
                $scope.$apply(function () {
                    if (error != null) {
                        $('#alertView').html("Server error");
                        $('#alertView').fadeIn();
                        return;
                    }
                    $scope.clearNewQuestionData();
                });
            });
        };


        $scope.selectRequestExam = function (exam) {
            $scope.newRequestHolder.exam = exam;
        };
        $scope.clearRequestData = function () {
            $scope.newRequestHolder = {
                exam: null,
                requestText: "",
                duration: ""
            };
        };

        $scope.createNewRequest = function () {
            $('#alertView').fadeOut();
            try {
                $scope.newRequestHolder.duration = parseInt($scope.newRequestHolder.duration);
            } catch (e) {
                console.log(e);
            }
            Http.post("/request/CreateNewRequest", null, $scope.newRequestHolder, function (result, error) {
                $scope.$apply(function () {
                    if (error != null) {
                        $('#alertView').html("Server error");
                        $('#alertView').fadeIn();
                        return;
                    }
                    $scope.clearRequestData();
                    $scope.loadExamsToCheck();
                    $scope.loadOldRequests();
                });
            });
        };


        $scope.loadOldRequests = function () {
            console.log("GetRequestsByTeacher");
            Http.get("/request/GetRequestsByTeacher", null, function (result, error) {
                $scope.$apply(function () {
                    if (error != null) {
                        console.error(error);
                        $scope.oldTeacherRequest = [];
                        return;
                    }
                    $scope.oldTeacherRequest = result.success;
                });
            });
        };

        $scope.loadExamsToCheck = function () {
            Http.get("/record/GetExamsToCheck", null, function (result, error) {
                $scope.$apply(function () {
                    if (error != null) {
                        $scope.examsToCheck = [];
                        return;
                    }
                    $scope.examsToCheck = result.success;
                });
            });
        };

        $scope.loadOldRequests();
        $scope.loadExamsToCheck();


        $scope.getCheckingExamName = function () {
            if ($scope.checkingExamHolder.record == null)
                return "";
            return $scope.checkingExamHolder.record.exam.profession.name +
                ", " + $scope.checkingExamHolder.record.course.name + ", " + $scope.checkingExamHolder.record.exam.id + " - " +
                $scope.checkingExamHolder.record.exam.code;
        };

        $scope.getHashExtraTime = function () {
            if ($scope.checkingExamHolder.record == null)
                return "";
            return (parseInt(parseInt($scope.checkingExamHolder.record.exam.duration) != parseInt($scope.checkingExamHolder.record.extraData.duration))) ?
                (parseInt(parseInt($scope.checkingExamHolder.record.exam.duration) != parseInt($scope.checkingExamHolder.record.extraData.duration)) + " minutes") : "N/A";
        };


        $scope.openCheckExamModal = function (record) {
            $scope.checkingExamHolder.record = record;
            for (var i = 0; i < $scope.checkingExamHolder.record.exam.questionsList.length; i++) {
                if ($scope.checkingExamHolder.record.exam.questionsList[i].question.correctAnswer == $scope.checkingExamHolder.record.extraData.answers[i]) {
                    $scope.checkingExamHolder.record.exam.questionsList[i].teacherGrade = $scope.checkingExamHolder.record.exam.questionsList[i].grade;
                } else {
                    $scope.checkingExamHolder.record.exam.questionsList[i].teacherGrade = 0;
                }
            }
            console.log($scope.checkingExamHolder);
            $('#checkExamModal').modal("show");
        };

        $scope.submitCheckedExam = function () {
            var objectToSend = {
                recordId: $scope.checkingExamHolder.record.id,
                teacherCheck: [],
                teacherGrade: 0
            };
            for (var i = 0; i < $scope.checkingExamHolder.record.exam.questionsList.length; i++) {
                var grade = $scope.checkingExamHolder.record.exam.questionsList[i].teacherGrade;
                objectToSend.teacherCheck.push(grade);
                objectToSend.teacherGrade += grade;
            }
            Http.post("/exam/SubmitExamCheck", null, objectToSend, function (result, error) {
                $scope.$apply(function () {
                    if (error) {
                        console.log(error);
                        alert("Server Error");
                        return
                    }
                    $scope.checkingExamHolder = {};
                    $scope.loadExamsToCheck();
                    $('#checkExamModal').modal("hide");
                });
            });
        };


        $scope.getCheckingExamDuration = function () {
            if ($scope.checkingExamHolder != null && $scope.checkingExamHolder.record != null) {
                return $scope.checkingExamHolder.record.exam.duration + " minutes";
            }
            return "-";
        };
        $scope.getCheckingExamFinishedIn = function () {
            if ($scope.checkingExamHolder != null && $scope.checkingExamHolder.record != null) {
                return ((new Date($scope.checkingExamHolder.record.extraData.endDate).getTime() -
                    new Date($scope.checkingExamHolder.record.extraData.startDate).getTime())
                    / 1000 / 60) + " minutes";
            }
            return "-";
        };

        /**
         * Paging
         */
        $scope.openCreateNewExam = function () {
            $scope.newExamHolder = {
                profession: null,
                course: null,
                type: ONLINE,
                teacherNote: "",
                studentsNote: "",
                questions: []
            };
            $rootScope.currentPage = TEACHER_CREATE_EXAM;
            $rootScope.currentPageName = PageNames[$rootScope.currentPage];
            $scope.loadTeacherProfessions();
            updateHash($rootScope.currentPageName);
        };
        $scope.openCreateNewQuestion = function () {
            $scope.newQuestionHolder = {};
            $rootScope.currentPage = TEACHER_CREATE_QUESTION;
            $rootScope.currentPageName = PageNames[$rootScope.currentPage];
            $scope.loadTeacherProfessions();
            updateHash($rootScope.currentPageName);
        };
        $scope.openRequestPermission = function () {
            $rootScope.currentPage = TEACHER_SEND_REQUEST;
            $rootScope.currentPageName = PageNames[$rootScope.currentPage];
            $scope.loadOnlineExams();
            updateHash($rootScope.currentPageName);
        };
        $scope.openCheckExams = function () {
            $rootScope.currentPage = TEACHER_CHECK_EXAMS;
            $rootScope.currentPageName = PageNames[$rootScope.currentPage];
            $('#checkExamModal').modal({show: false});
            updateHash($rootScope.currentPageName);
        };
        $scope.openPublishExams = function () {
            $rootScope.currentPage = TEACHER_MANAGE_EXAMS;
            $rootScope.currentPageName = PageNames[$rootScope.currentPage];
            updateHash($rootScope.currentPageName);
        };
        $scope.openStatistics = function () {
            $rootScope.currentPage = TEACHER_STATISTICS;
            $rootScope.currentPageName = PageNames[$rootScope.currentPage];
            updateHash($rootScope.currentPageName);
        };


        var currentHash = decodeURI(window.location.hash.substring(2));
        switch (currentHash) {
            case PageNames[TEACHER_CREATE_EXAM]:
                $scope.openCreateNewExam();
                break;
            case PageNames[TEACHER_CREATE_QUESTION]:
                $scope.openCreateNewQuestion();
                break;
            case PageNames[TEACHER_SEND_REQUEST]:
                $scope.openRequestPermission();
                break;
            case PageNames[TEACHER_CHECK_EXAMS]:
                $scope.openCheckExams();
                break;
            case PageNames[TEACHER_MANAGE_EXAMS]:
                $scope.openPublishExams();
                break;
            case PageNames[TEACHER_STATISTICS]:
                $scope.openStatistics();
                break;
            default:
                $rootScope.currentPage = TEACHER_HOME;
                $rootScope.currentPageName = PageNames[$rootScope.currentPage];
                break;
        }

    }]);
})();
