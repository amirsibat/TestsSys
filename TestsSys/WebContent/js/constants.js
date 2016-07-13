var app = angular.module('TestsSysApp', []);

// Pages
var STUDENT_HOME = "STUDENT_HOME";
var STUDENT_COURSES = "STUDENT_COURSES";
var STUDENT_COURSE_DETAILS = "STUDENT_COURSE_DETAILS";
var STUDENT_EXAM = "STUDENT_EXAM";
var TEACHER_HOME = "TEACHER_HOME";
var TEACHER_CREATE_EXAM = "TEACHER_CREATE_EXAM";
var TEACHER_CREATE_QUESTION = "TEACHER_CREATE_QUESTION";
var TEACHER_SEND_REQUEST = "TEACHER_SEND_REQUEST";
var TEACHER_CHECK_EXAMS = "TEACHER_CHECK_EXAMS";
var TEACHER_MANAGE_EXAMS = "TEACHER_MANAGE_EXAMS";
var TEACHER_STATISTICS = "TEACHER_STATISTICS";
var MANAGER_HOME = "MANAGER_HOME";
var MANAGER_STATISTICS = "MANAGER_STATISTICS";
var MANAGER_EXAMS = "MANAGER_EXAMS";
var MANAGER_PENDING_REQUESTS = "MANAGER_PENDING_REQUESTS";
var MANAGER_QUESTION = "MANAGER_QUESTION";
var MANAGER_GRADES = "MANAGER_GRADES";

var ONLINE = "ONLINE";
var WORD_FILE = "WORD_FILE";

app.value("PageNames", {
    STUDENT_HOME : "Home",
    MANAGER_HOME : "Home",
    TEACHER_HOME : "Home",

    TEACHER_STATISTICS : "Statistics",
    MANAGER_STATISTICS : "Statistics",

    STUDENT_COURSES : "My courses",
    STUDENT_COURSE_DETAILS : "Course details",
    STUDENT_EXAM : "Exam",

    TEACHER_CREATE_EXAM : "Create new exam",
    TEACHER_CREATE_QUESTION : "Create new question",
    TEACHER_SEND_REQUEST : "Send new request",
    TEACHER_CHECK_EXAMS : "Check exams",
    TEACHER_MANAGE_EXAMS : "Manage exams",

    MANAGER_PENDING_REQUESTS : "Pending requests",
    MANAGER_EXAMS : "Explore exams",
    MANAGER_QUESTION : "Explore questions",
    MANAGER_GRADES : "Explore grades"
});


// UserTypes
app.value("UserType", {
    STUDENT: "STUDENT",
    TEACHER: "TEACHER",
    MANAGER: "MANAGER"
});

function updateHash(hash) {
    event.preventDefault();
    window.location.hash = "/"+ hash.replace(/ /g, "%20");
}