$(function (){
    var arrRoute = [];
    $('#addEvent').click(function () {
        $('.add-event').toggle();
    });

    $('#alertEvent').click(function () {
        $('.alert').toggle();
    });
    $("#btnAddEvent").click(function(){
        $.ajax({
            url: '/Event?action=add',
            method:"POST",
            data:{name:  $('#eventName').val(), routeImg:$('#routeImg').val(), start:$('#start').val(), end: $('#end').val(), route: JSON.stringify(arrRoute)}
        }).done(function(data) {
            alert(data);
        });
    });
    $("#btnAddRoute").click(function(){
        arrRoute.push({
            startPosition: $("#startPosition").val(),
            endPosition: $("#endPosition").val(),
            duration: $("#duration").val()
        })
        var table = $("<table/>");
        var thead = $("<thead/>");
        var tr = $("<tr/>");
        tr.append($("<th/>").text("Start position"));
        tr.append($("<th/>").text("End position"));
        tr.append($("<th/>").text("Duration"));
        thead.append(tr);
        table.append(thead);
        var tbody = $("<tbody/>");
        for(var i=0; i<arrRoute.length; i++){
            tr = $("<tr/>");
            tr.append($("<td/>").text(arrRoute[i].startPosition));
            tr.append($("<td/>").text(arrRoute[i].endPosition));
            tr.append($("<td/>").text(arrRoute[i].duration));
            tbody.append(tr);
            table.append(tbody);
        }
        $("#dynamicTable").empty();
        $("#dynamicTable").append(table);
        $("#startPosition").val("");
        $("#endPosition").val("");
        $("#duration").val("");
    });
});


function showSignUpPage(){
    window.location.assign("signup.jsp")
}

function showloginPage(){
    window.location.assign("login.jsp")
}

