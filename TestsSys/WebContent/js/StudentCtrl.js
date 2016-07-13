(function (angular) {
    app.controller('StudentCtrl', ["$scope", function ($scope) {

    	$scope.studentExams = [];
    	$scope.studentCourses = [];
    	
    	
    	
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

            function fixXmlChars(text) {
                return text.replace("<", "&#60;")
                    .replace(">", "&#62;")
                    .replace("&", "&#38;")
                    .replace("'", "&#39;")
                    .replace('"', "&#34");
            }

            var ExamHeaderTemplate = '<?xml version="1.0" encoding="UTF-8" standalone="yes"?><w:document xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships" xmlns:wp="http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing" xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main" xmlns:w10="urn:schemas-microsoft-com:office:word" xmlns:w14="http://schemas.microsoft.com/office/word/2010/wordml" xmlns:wps="http://schemas.microsoft.com/office/word/2010/wordprocessingShape" xmlns:wpg="http://schemas.microsoft.com/office/word/2010/wordprocessingGroup" xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006" xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office" mc:Ignorable="w14"><w:body><w:p><w:pPr><w:pStyle w:val="Body"/><w:jc w:val="right"/><w:rPr><w:b w:val="1"/><w:bCs w:val="1"/><w:sz w:val="62"/><w:szCs w:val="62"/></w:rPr></w:pPr><w:r><w:rPr><w:b w:val="1"/><w:bCs w:val="1"/><w:rtl w:val="0"/><w:lang w:val="de-DE"/></w:rPr><w:t>Date:</w:t></w:r><w:r><w:rPr><w:rtl w:val="0"/></w:rPr><w:t xml:space="preserve"> </w:t></w:r><w:r><w:rPr><w:rtl w:val="0"/><w:lang w:val="en-US"/></w:rPr><w:t>{EXAM_DATE}</w:t></w:r></w:p><w:p><w:pPr><w:pStyle w:val="Body"/><w:jc w:val="center"/><w:rPr><w:b w:val="1"/><w:bCs w:val="1"/><w:sz w:val="62"/><w:szCs w:val="62"/></w:rPr></w:pPr><w:r><w:rPr><w:b w:val="1"/><w:bCs w:val="1"/><w:sz w:val="62"/><w:szCs w:val="62"/><w:rtl w:val="0"/><w:lang w:val="en-US"/></w:rPr><w:t>{EXAM_NAME}</w:t></w:r></w:p><w:p><w:pPr><w:pStyle w:val="Body"/></w:pPr><w:r><w:rPr><w:b w:val="1"/><w:bCs w:val="1"/><w:rtl w:val="0"/><w:lang w:val="en-US"/></w:rPr><w:t>Teacher:</w:t></w:r><w:r><w:rPr><w:rtl w:val="0"/><w:lang w:val="en-US"/></w:rPr><w:t xml:space="preserve"> {TEACHER_NAME}</w:t></w:r></w:p><w:p><w:pPr><w:pStyle w:val="Body"/></w:pPr><w:r><w:rPr><w:b w:val="1"/><w:bCs w:val="1"/><w:rtl w:val="0"/><w:lang w:val="en-US"/></w:rPr><w:t>Duration:</w:t></w:r><w:r><w:rPr><w:rtl w:val="0"/></w:rPr><w:t xml:space="preserve"> </w:t></w:r><w:r><w:rPr><w:rtl w:val="0"/><w:lang w:val="en-US"/></w:rPr><w:t>{EXAM_DURATION}</w:t></w:r><w:r><w:rPr><w:rtl w:val="0"/></w:rPr><w:t xml:space="preserve"> mins</w:t></w:r></w:p><w:p><w:pPr><w:pStyle w:val="Body"/><w:rPr><w:b w:val="1"/><w:bCs w:val="1"/><w:color w:val="ab1500"/></w:rPr></w:pPr><w:r><w:rPr><w:b w:val="1"/><w:bCs w:val="1"/><w:color w:val="ab1500"/><w:rtl w:val="0"/><w:lang w:val="en-US"/></w:rPr><w:t>{STUDENTS_NOTE}</w:t></w:r><w:r><w:rPr><w:b w:val="1"/><w:bCs w:val="1"/><w:color w:val="ab1500"/></w:rPr><mc:AlternateContent><mc:Choice Requires="wps"><w:drawing><wp:anchor distT="152400" distB="152400" distL="152400" distR="152400" simplePos="0" relativeHeight="251659264" behindDoc="0" locked="0" layoutInCell="1" allowOverlap="1"><wp:simplePos x="0" y="0"/><wp:positionH relativeFrom="margin"><wp:posOffset>8679</wp:posOffset></wp:positionH><wp:positionV relativeFrom="line"><wp:posOffset>235188</wp:posOffset></wp:positionV><wp:extent cx="5480896" cy="0"/><wp:effectExtent l="0" t="0" r="0" b="0"/><wp:wrapTopAndBottom distT="152400" distB="152400"/><wp:docPr id="1073741825" name="officeArt object"/><wp:cNvGraphicFramePr/><a:graphic xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main"><a:graphicData uri="http://schemas.microsoft.com/office/word/2010/wordprocessingShape"><wps:wsp><wps:cNvSpPr/><wps:spPr><a:xfrm><a:off x="0" y="0"/><a:ext cx="5480896" cy="0"/></a:xfrm><a:prstGeom prst="line"><a:avLst/></a:prstGeom><a:noFill/><a:ln w="6350" cap="flat"><a:solidFill><a:srgbClr val="000000"/></a:solidFill><a:prstDash val="solid"/><a:round/></a:ln><a:effectLst/></wps:spPr><wps:bodyPr/></wps:wsp></a:graphicData></a:graphic></wp:anchor></w:drawing></mc:Choice><mc:Fallback><w:pict><v:line id="_x0000_s1026" style="visibility:visible;position:absolute;margin-left:0.7pt;margin-top:18.5pt;width:431.6pt;height:0.0pt;z-index:251659264;mso-position-horizontal:absolute;mso-position-horizontal-relative:margin;mso-position-vertical:absolute;mso-position-vertical-relative:line;mso-wrap-distance-left:12.0pt;mso-wrap-distance-top:12.0pt;mso-wrap-distance-right:12.0pt;mso-wrap-distance-bottom:12.0pt;"><v:fill on="f"/><v:stroke filltype="solid" color="#000000" opacity="100.0%" weight="0.5pt" dashstyle="solid" endcap="flat" joinstyle="round" linestyle="single" startarrow="none" startarrowwidth="medium" startarrowlength="medium" endarrow="none" endarrowwidth="medium" endarrowlength="medium"/><w10:wrap type="topAndBottom" side="bothSides" anchorx="margin"/></v:line></w:pict></mc:Fallback></mc:AlternateContent></w:r></w:p>';
            var ExamHeaderTemplateEnd = '<w:p><w:pPr><w:pStyle w:val="Body"/><w:spacing w:line="240" w:lineRule="auto"/><w:rPr><w:sz w:val="26"/><w:szCs w:val="26"/></w:rPr></w:pPr></w:p><w:p><w:pPr><w:pStyle w:val="Body"/></w:pPr><w:r/></w:p><w:sectPr><w:headerReference w:type="default" r:id="rId4"/><w:footerReference w:type="default" r:id="rId5"/><w:pgSz w:w="12240" w:h="15840" w:orient="portrait"/><w:pgMar w:top="1440" w:right="1800" w:bottom="1440" w:left="1800" w:header="720" w:footer="720"/><w:bidi w:val="0"/></w:sectPr></w:body></w:document>';
            ExamHeaderTemplate = ExamHeaderTemplate.replace("{EXAM_DATE}", exam.date);
            ExamHeaderTemplate = ExamHeaderTemplate.replace("{EXAM_NAME}", exam.course.name + " Exam");
            ExamHeaderTemplate = ExamHeaderTemplate.replace("{TEACHER_NAME}", exam.author.firstName + " " + exam.author.lastName);
            ExamHeaderTemplate = ExamHeaderTemplate.replace("{EXAM_DURATION}", exam.duration + "");
            ExamHeaderTemplate = ExamHeaderTemplate.replace("{STUDENTS_NOTE}", exam.description);

            var QuestionRow = '<w:p><w:pPr><w:pStyle w:val="Body"/><w:spacing w:line="240" w:lineRule="auto"/><w:rPr><w:b w:val="1"/><w:bCs w:val="1"/><w:sz w:val="26"/><w:szCs w:val="26"/></w:rPr></w:pPr><w:r><w:rPr><w:b w:val="1"/><w:bCs w:val="1"/><w:sz w:val="26"/><w:szCs w:val="26"/><w:rtl w:val="0"/><w:lang w:val="en-US"/></w:rPr><w:t>{QUESTION_NUMBER}. {QUESTION_TEXT}:</w:t></w:r></w:p>';
            var AnswerRow = '<w:p><w:pPr><w:pStyle w:val="Body"/><w:spacing w:line="240" w:lineRule="auto"/><w:rPr><w:sz w:val="26"/><w:szCs w:val="26"/></w:rPr></w:pPr><w:r><w:rPr><w:sz w:val="26"/><w:szCs w:val="26"/><w:rtl w:val="0"/><w:lang w:val="en-US"/></w:rPr><w:tab/><w:t>{ANSWER_NUMBER}) {ANSER_TEXT}</w:t></w:r></w:p>';
            var examBody = "";

            for(var i=0; i<exam.questionsList.length; i++){
                examBody += QuestionRow.replace("{QUESTION_NUMBER}", ""+(i+1))
                    .replace("{QUESTION_TEXT}", fixXmlChars(exam.questionsList[i].question.text) + " ("+ exam.questionsList[i].grade +" Points)");
                for(var j=0; j<exam.questionsList[i].question.options.length; j++){
                    examBody += AnswerRow.replace("{ANSWER_NUMBER}", "" + (j+1))
                        .replace("{ANSER_TEXT}", fixXmlChars(exam.questionsList[i].question.options[j]));
                }
            }
            var doc = new DOCXjs();
            doc.text(ExamHeaderTemplate + examBody + ExamHeaderTemplateEnd);
            doc.output('datauri');
        };
        
       
        
        $scope.loadStudentExams = function(){
        	
        	 Http.get("/record/GetPublishedExamsByCourse", null, function (result, error) {
                 $scope.$apply(function () {
                     if (error != null) {
                         $scope.studentExams = [];
                         return;
                     }
                     $scope.studentExams = result.success;
                 });
             });
        	
        };
        
        $scope.loadStudentCourses = function(){
        	 Http.get("/record/GetStudentCourses", null, function (result, error) {
                 $scope.$apply(function () {
                     if (error != null) {
                         $scope.studentCourses = [];
                         return;
                     }
                     $scope.studentCourses = result.success;
                 });
             });
        	
        };
        
       $scope.loadStudentExams();

    }]);
})();