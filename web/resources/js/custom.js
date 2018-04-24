"use strict";

$(function (){
    $('#addEvent').click(function () {
        $('.add-event').toggle();
    });

    $('#alertEvent').click(function () {
        $('.alert').toggle();
    });

    $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
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
    });

    $("#btnAddEvent").click(function(){
        $.ajax({
            url: '/Event?action=add',
            method:"POST",
            data:{eventName: $('#eventName').val(), route:$('#route').val(), start:$('#start').val(), end:$('#end').val()}
        }).done(function(data) {
            alert(data);
        });
    });

    $.ajax({
        "url": "/Event?action=upcomingList",
        "type": "GET",
        "success": eventUpcomingList
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
        $('<div>').attr({ 'class': "card" }).appendTo('#columns');
        if(data[item].img != null){
            $('<img>').attr({ 'class': "card-img-top", 'src': data[item].src}).appendTo('.card');
        }
        $('<div>').attr({ 'class': "card-body" }).appendTo('.card');
        $('<h5>').attr({ 'class': "card-title" }).text(data[item].name).appendTo('.card-body');
        $('<span>').attr({ 'class': "badge badge-secondary" }).text("New").appendTo('.card-title');
        $('<div>').attr({ 'class': "card-text" }).text(data[item].name).appendTo('.card-body');
        $('<a>').attr({ 'class': "card-link", 'href':''}).text("more ...").appendTo('.card-body');
    }
}

/*Added by deegii, ongoing event list*/
function ongoingList(data) {
    console.log("ongoing");
}

