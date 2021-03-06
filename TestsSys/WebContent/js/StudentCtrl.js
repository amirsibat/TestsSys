var studentScope = null;
(function (angular) {
    app.controller('StudentCtrl', ["$scope", "$rootScope", "PageNames", function ($scope, $rootScope, PageNames) {

        studentScope = $scope;
        $scope.studentExams = [];
        $scope.studentCourses = [];
        $scope.ExamHolder = [];
        $scope.submittedExams = [];
        $scope.CourseDetailsHolder = {};
        $scope.performExamHolder = {
            pageStatus: IN_PROGRESS_WORD,
            exam: null,
            timerText: "",
            startTime: 0
        };

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

        $scope.loadStudentExams = function () {
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

        $scope.loadStudentCourses = function () {
            Http.get("/student/GetStudentCourses", null, function (result, error) {
                $scope.$apply(function () {
                    if (error != null) {
                        $scope.studentCourses = [];
                        return;
                    }
                    $scope.studentCourses = result.success;
                    /*console.log($scope.studentCourses);*/

                });
            });

        };

        $scope.loadCourseDetails = function (course) {
            $scope.openCourseDetails();

            Http.get("/record/GetCourseDetails", {courseId: course.id}, function (result, error) {
                $scope.$apply(function () {
                    if (error != null) {
                        $scope.submittedExams = [];
                        return;
                    }
                    $scope.submittedExams = result.success;
                    console.log($scope.submittedExams);
                });

            });
        };

        $scope.isCorrect = function (question, index) {

            if ($scope.selectedExam.extraData.answers[index] >= 4)
                return false;
            if ($scope.selectedExam.extraData.answers[index] == question.question.correctAnswer - 1)
                return true;
            else
                return false;
        };

        $scope.openExamDetailsModal = function (exam) {
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

        $scope.openExamModal = function (exam) {
            $scope.ExamHolder = exam;
            $('#startExamModal').modal("show");
            $("#examCodeInput").val("");
        };

        $scope.getExamName = function () {
            if ($scope.ExamHolder.exam == null)
                return "";
            return $scope.ExamHolder.exam.profession.name +
                ", " + $scope.ExamHolder.exam.course.name + ", " + $scope.ExamHolder.exam.id;
        };

        $scope.loadStudentExams();
        $scope.loadStudentCourses();

        $scope.openCourseDetails = function (updateH) {
            $rootScope.currentPage = STUDENT_COURSE_DETAILS;
            $rootScope.currentPageName = PageNames[$rootScope.currentPage];
            if (updateH == null || updateH == true)
                updateHash($rootScope.currentPageName);
        };

        $scope.openStartExam = function (updateH) {
            $rootScope.currentPage = STUDENT_EXAM;
            $rootScope.currentPageName = PageNames[$rootScope.currentPage];
            if (updateH == null || updateH == true)
                updateHash($rootScope.currentPageName);
        };

        $scope.checkExamCode = function () {
            var code = $("#examCodeInput").val();
            $("#examCodeInput").val("");
            if (code == $scope.ExamHolder.exam.code) {
                $('#startExamModal').modal("hide");
                $scope.performExamHolder.pageStatus = WAITING_STUDENT_ID;
                $scope.performExamHolder.exam = $scope.ExamHolder;
                localStorage.setItem("EXAM", JSON.stringify($scope.performExamHolder));
                setTimeout(function () {
                    $scope.$apply(function () {
                        $scope.openStartExam(true);
                    });
                }, 300);
            } else {
                alert("Invalid code");
            }
        };


        $scope.startPerformExam = function () {
            var studentId = $('#examStudentInput').val();
            $('#examStudentInput').val("");
            if ($scope.performExamHolder.exam.student.stId != studentId) {
                alert("Invalid student id");
                return;
            }
            $scope.performExamHolder.exam.extraData.answers = [];
            for (var i = 0; i < $scope.performExamHolder.exam.exam.questionsList.length; i++) {
                $scope.performExamHolder.exam.extraData.answers.push(4);
            }
            Http.post("/exam/StartExam", null, {
                recordId: $scope.performExamHolder.exam.id,
                answers: $scope.performExamHolder.exam.extraData.answers
            }, function (result, error) {
                $scope.$apply(function () {
                    if (error != null) {
                        alert("Server Error");
                        return;
                    }
                    if ($scope.performExamHolder.exam.exam.type == 1) {
                        $scope.performExamHolder.pageStatus = IN_PROGRESS;
                    } else {
                        $scope.performExamHolder.pageStatus = IN_PROGRESS_WORD;
                    }
                    $scope.performExamHolder.startTime = new Date().getTime();
                    $scope.performExamHolder.exam.extraData.startDate = new Date().getTime();
                    $scope.performExamHolder.endTime = $scope.performExamHolder.startTime + $scope.performExamHolder.exam.exam.duration * 60 * 1000;
                    localStorage.setItem("EXAM", JSON.stringify($scope.performExamHolder));
                });
            });
        };

        $('body').on('change', 'input:radio', function () {
            if ($scope.performExamHolder.exam != null && $scope.performExamHolder.exam.exam != null) {
                var questionId = $(this).attr("questionId");
                var answer = $(this).attr("answerIndex");
                for (var i = 0; i < $scope.performExamHolder.exam.exam.questionsList.length; i++) {
                    if ($scope.performExamHolder.exam.exam.questionsList[i].question.id == questionId) {
                        $scope.performExamHolder.exam.extraData.answers[i] = answer - 1 + 1;
                        return;
                    }
                }
                localStorage.setItem("EXAM", JSON.stringify($scope.performExamHolder));
                Http.post("/exam/SaveLastChanges", null, {
                    recordId: $scope.performExamHolder.exam.id,
                    answers: $scope.performExamHolder.exam.extraData.answers
                }, function (result, error) {
                    if (error != null) {
                        alert("Server Error");
                        return;
                    }
                });
            }
        });

        $scope.submitExam = function () {
            Http.post("/exam/SubmitExam", null, $scope.performExamHolder.exam, function (result, error) {
                $scope.$apply(function () {
                    if (error != null) {
                        alert("Server Error");
                        return;
                    }
                    localStorage.removeItem("EXAM");
                    $scope.performExamHolder = {
                        pageStatus: WAITING_STUDENT_ID,
                        exam: null,
                        timerText: "",
                        startTime: 0
                    };
                    $scope.loadStudentExams();
                    $rootScope.currentPage = STUDENT_HOME;
                    $rootScope.currentPageName = PageNames[$rootScope.currentPage];
                    updateHash($rootScope.currentPageName);
                });
            });
        };

        $scope.uploadAndSubmit = function () {
            if ($('#uploadExamInput')[0].files.length != 0) {
                $scope.submitExam();
                return;
            }
            alert("Choose the exam file");
        };


        var currentHash = decodeURI(window.location.hash.substring(2));
        switch (currentHash) {
            case PageNames[STUDENT_COURSE_DETAILS]:
                $scope.openCourseDetails(false);
                break;
            case PageNames[STUDENT_EXAM]:
                if (localStorage.getItem("EXAM") == null) {
                    $rootScope.currentPage = STUDENT_HOME;
                    $rootScope.currentPageName = PageNames[$rootScope.currentPage];
                } else {
                    $scope.performExamHolder = JSON.parse(localStorage.getItem("EXAM"));
                    $scope.openStartExam(false);
                }
                break;
            default:
                $rootScope.currentPage = STUDENT_HOME;
                $rootScope.currentPageName = PageNames[$rootScope.currentPage];
                break;
        }


        setInterval(function () {
            if ($scope.performExamHolder != null && $scope.performExamHolder.exam != null) {
                var time = ($scope.performExamHolder.endTime - new Date()) / 1000;
                var hours = parseInt(time / 60 / 60);
                var minutes = parseInt(time / 60 % 60);
                var seconds = parseInt(time % 60 % 60);
                minutes = minutes > 9 ? minutes : "0" + minutes;
                seconds = seconds > 9 ? seconds : "0" + seconds;
                $scope.$apply(function () {
                    $scope.performExamHolder.timerText = hours + ":" + minutes + ":" + seconds;
                });

                Http.post("/exam/CheckExtraTime", null, {recordId: $scope.performExamHolder.exam.id}, function (result, error) {
                    if (error != null) {
                        //alert("Server Error");
                        return;
                    }
                    $scope.$apply(function () {
                        if (result.success != -1) {
                            $scope.performExamHolder.endTime = $scope.performExamHolder.startTime + $scope.performExamHolder.exam.exam.duration * 60 * 1000 + result.success * 1000 * 60;
                        } else {
                            ocalStorage.removeItem("EXAM");
                            $scope.performExamHolder = {
                                pageStatus: WAITING_STUDENT_ID,
                                exam: null,
                                timerText: "",
                                startTime: 0
                            };
                            $scope.loadStudentExams();
                            $rootScope.currentPage = STUDENT_HOME;
                            $rootScope.currentPageName = PageNames[$rootScope.currentPage];
                            updateHash($rootScope.currentPageName);
                        }
                    });
                });
            }
        }, 1000);

        window.addEventListener('popstate', function (event) {
            var currentHash = decodeURI(window.location.hash.substring(2));
            setTimeout(function () {
                $scope.$apply(function () {
                    switch (currentHash) {
                        case PageNames[STUDENT_COURSE_DETAILS]:
                            $scope.openCourseDetails(false);
                            break;
                        case PageNames[STUDENT_EXAM]:
                            $scope.openStartExam(false);
                            break;
                        default:
                            $rootScope.currentPage = STUDENT_HOME;
                            $rootScope.currentPageName = PageNames[$rootScope.currentPage];
                            break;
                    }
                });
            }, 100);
            event.preventDefault();
        });

    }]);
})();