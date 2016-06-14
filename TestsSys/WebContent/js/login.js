jQuery(document).ready(function () {


    Http.get("/user/SessionAuth", null, function (success, error) {
        if(error != null){
            setTimeout(function () {
                loaderVisibility(false);
            }, 600);
        }else{
            setTimeout(function () {
                window.location = "index.html";
            }, 200);
        }
    });





    function loaderVisibility(show) {
        if (show)
            $("#loader").fadeIn(50);
        else
            $("#loader").fadeOut(200);
    }
    /*
     Fullscreen background
     */
    $.backstretch("imgs/login_background.jpg");

    /*
     Form validation
     */
    $('.login-form input[type="text"], .login-form input[type="password"], .login-form textarea').on('focus', function () {
        $(this).removeClass('input-error');
    });

    $('.login-form').on('submit', function (e) {

        $(this).find('input[type="text"], input[type="password"], textarea').each(function () {
            if ($(this).val() == "") {
                e.preventDefault();
                $(this).addClass('input-error');
            }
            else {
                $(this).removeClass('input-error');
            }
        });

    });

    //Name can't be blank function called on user input
    $('#username').on('input', function () {
        var input = $(this);
        var is_username = input.val();
        // if username is empty add invalid class
        if (!is_username) {
            input.addClass("invalid");
        }
        else {
            input.removeClass("invalid");
        }
    });

    //function called on user input
    $('#password').on('input', function () {
        var input = $(this);
        var is_pass = input.val();
        //if password is empty add invalid class
        if (!is_pass) {
            input.addClass("invalid");
        }
        else {
            input.removeClass("invalid");
        }
    });


    // after form submission validate inputs
    $("#loginButton").click(function (event) {

        var form_data = $("#loginForm").serializeArray(); // Get All HTML objects inside the form
        var has_error = false;

        for (var input in form_data) { // loop over the objects
            var element = $("#" + form_data[input]['name']); // concatinate # to the form input name to get the object
            var invalid = element.hasClass("invalid");
            if (invalid || !element.val()) {
                element.addClass("invalid");
                has_error = true;
            }
        }
        // if has_error true prevent from from submiting and show alert error message
        if (has_error) {
            $('.alertUserInvalid').html("Enter username and password");
            $('.alertUserInvalid').show(200);
            event.preventDefault(); // prevent form from sumbmitting
        }
        else {
            //Make AJAX call with server

            $('.alertUserInvalid').hide();
            var user = {
                username: $('#username').val(),
                password: $('#password').val()
            };

            if(user.username == ""){
                $('.alertUserInvalid').html("Enter username");
                $('.alertUserInvalid').show(200);
                return;
            }
            if(user.password == ""){
                $('.alertUserInvalid').html("Enter password");
                $('.alertUserInvalid').show(200);
                return;
            }
            Http.post("/user/LoginUser", null, user, function (success, error) {
                if (error) {
                    $('.alertUserInvalid').html("Invalid user or password");
                    $('.alertUserInvalid').show(200);
                    console.log("Invalid user or password");
                    return;
                }

                window.location = "index.html";

            });
        }
    });

});
