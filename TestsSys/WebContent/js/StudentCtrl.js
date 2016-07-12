(function (angular) {
    app.controller('StudentCtrl', ["$scope", function ($scope) {


        $scope.generateDocxFile = function () {
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

            var ExamHeaderTemplate = '<w:p><w:ppr><w:pstyle w:val="Body"></w:pstyle><w:jc w:val="right"></w:jc><w:rpr><w:b w:val="1"></w:b><w:bcs w:val="1"></w:bcs><w:sz w:val="62"></w:sz><w:szcs w:val="62"></w:szcs></w:rpr></w:ppr><w:r><w:rpr><w:b w:val="1"></w:b><w:bcs w:val="1"></w:bcs><w:rtl w:val="0"></w:rtl><w:lang w:val="de-DE"></w:lang></w:rpr><w:t>Date:</w:t></w:r><w:r><w:rpr><w:rtl w:val="0"></w:rtl></w:rpr><w:t xml:space="preserve"></w:t></w:r><w:r><w:rpr><w:rtl w:val="0"></w:rtl><w:lang w:val="en-US"></w:lang></w:rpr><w:t>{EXAM_DATE}</w:t></w:r></w:p><w:p><w:ppr><w:pstyle w:val="Body"></w:pstyle><w:jc w:val="center"></w:jc><w:rpr><w:b w:val="1"></w:b><w:bcs w:val="1"></w:bcs><w:sz w:val="62"></w:sz><w:szcs w:val="62"></w:szcs></w:rpr></w:ppr><w:r><w:rpr><w:b w:val="1"></w:b><w:bcs w:val="1"></w:bcs><w:sz w:val="62"></w:sz><w:szcs w:val="62"></w:szcs><w:rtl w:val="0"></w:rtl><w:lang w:val="en-US"></w:lang></w:rpr><w:t>{EXAM_NAME}</w:t></w:r></w:p><w:p><w:ppr><w:pstyle w:val="Body"></w:pstyle></w:ppr><w:r><w:rpr><w:b w:val="1"></w:b><w:bcs w:val="1"></w:bcs><w:rtl w:val="0"></w:rtl><w:lang w:val="en-US"></w:lang></w:rpr><w:t>Teacher:</w:t></w:r><w:r><w:rpr><w:rtl w:val="0"></w:rtl><w:lang w:val="en-US"></w:lang></w:rpr><w:t xml:space="preserve">{TEACHER_NAME}</w:t></w:r></w:p><w:p><w:ppr><w:pstyle w:val="Body"></w:pstyle></w:ppr><w:r><w:rpr><w:b w:val="1"></w:b><w:bcs w:val="1"></w:bcs><w:rtl w:val="0"></w:rtl><w:lang w:val="en-US"></w:lang></w:rpr><w:t>Duration:</w:t></w:r><w:r><w:rpr><w:rtl w:val="0"></w:rtl></w:rpr><w:t xml:space="preserve"></w:t></w:r><w:r><w:rpr><w:rtl w:val="0"></w:rtl><w:lang w:val="en-US"></w:lang></w:rpr><w:t>{EXAM_DURATION}</w:t></w:r><w:r><w:rpr><w:rtl w:val="0"></w:rtl></w:rpr><w:t xml:space="preserve">mins</w:t></w:r></w:p>';
            ExamHeaderTemplate = ExamHeaderTemplate.replace("{EXAM_DATE}", exam.date);
            ExamHeaderTemplate = ExamHeaderTemplate.replace("{EXAM_NAME}", exam.course.name + " Exam");
            ExamHeaderTemplate = ExamHeaderTemplate.replace("{TEACHER_NAME}", exam.author.firstName +" " + exam.author.lastName);
            ExamHeaderTemplate = ExamHeaderTemplate.replace("{EXAM_DURATION}", exam.duration + "");
            //ExamHeaderTemplate.replace("{STUDENTS_NOTE}", exam.description);

            var doc = new DOCXjs();
            doc.text(ExamHeaderTemplate);

            doc.output('datauri');
        };

    }]);
})();