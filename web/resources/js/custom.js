"use strict";

$(function (){
    var arrRoute = [];
    $('#addEvent').click(function () {
        $('.add-event').toggle();
    });

    $('#alertEvent').click(function () {
        $('.alert').toggle();
    });

    $.ajax({
        "url": "/Event?action=upcomingList",
        "type": "GET",
        "success": upcomingList,
        "error": failureFunction
    });
    /*$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
        var target = $(e.target).attr("href");
        switch (target){
            case "#ongoing":
                $.ajax({
                    "url": "/Event?action=ongoingList",
                    "type": "GET",
                    "success": ongoingList
                });
                break;
            case "#members":
                $.ajax({
                    "url": "/Event?action=membersList",
                    "type": "GET",
                    "success": membersList
                });
                break;
            default :
                $.ajax({
                    "url": "/Event?action=upcomingList",
                    "type": "GET",
                    "success": upcomingList
                });
        }
    });*/

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

/*Added by deegii, upcoming event list*/
function upcomingList(data) {
    for (let item in data) {
        $('<div>').attr({ 'class': "card","id": "card" + data[item].eventId }).appendTo('#columns');
        if(data[item].img != ""){
            $('<img>').attr({ 'class': "card-img-top", 'src': data[item].img}).appendTo('.card');
        }
        $('<div>').attr({ 'class': "card-body", "id":"card-body" + data[item].eventId}).appendTo('#card' + data[item].eventId);
        $('<h5>').attr({ 'class': "card-title", "id": "card-title" + data[item].eventId}).text(data[item].name).appendTo('#card-body' + data[item].eventId);
        $('<span>').attr({ 'class': "badge badge-secondary" }).text("New").appendTo('#card-title' + data[item].eventId);
        $('<div>').attr({ 'class': "card-text" }).text(data[item].startDate).appendTo('#card-body' + data[item].eventId);
        $('<div>').attr({ 'class': "card-text" }).text(data[item].endDate).appendTo('#card-body' + data[item].eventId);
        $('<a>').attr({ 'class': "card-link", 'href':'/Event?action=get?id=' + data[item].eventId}).text("more ...").appendTo('#card-body' + data[item].eventId);
    }
}

/*Added by deegii, ongoing event list*/
function ongoingList(data) {
    console.log("ongoing");
}

function failureFunction() {
    console.log("Couldn't load ajax");
}