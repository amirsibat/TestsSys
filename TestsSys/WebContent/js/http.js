var Http = {};
Http.SERVER_URL = "http://localhost:8080/TestsSys";

Http.get = function (func, query, callback) {
    var queryString = "";
    if (query != null) {
        queryString = $.param(query);
    }
    $.ajax({
        method: "GET",
        url: Http.SERVER_URL + func + ((queryString != "") ? "?" + queryString : ""),
        success: function (s) {
            callback(s);
        },
        error: function (e) {
            console.error(e);
            callback(null, e);
        }
    });
};

Http.post = function (func, query, data, callback) {
    var queryString = "";
    if (query != null) {
        queryString = $.param(query);
    }
    $.ajax({
        method: "POST",
        url: Http.SERVER_URL + func + ((queryString != "") ? "?" + queryString : ""),
        data: JSON.stringify(data),
        contentType: "application/x-www-form-urlencoded",
        success: function (s) {
            callback(s);
        },
        error: function (e) {
            console.error(e);
            callback(null, e);
        }
    });
};
