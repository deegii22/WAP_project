"use strict";

$(function (){

    $.ajax({
        url: "/Event?action=upcomingList",
        type: "GET",
        success: upcomingList,
        error: failureFunction
    });

    $.ajax({
        url: "/Event?action=ongoingList",
        type: "GET",
        success: ongoingList,
        error: failureFunction
    });

    var arrRoute = [];

    $('#addEvent').click(function () {
        $('.add-event').toggle();
    });

    $('#alertEvent').click(function () {
        $('.alert').toggle();
    });

    $('#exampleModalCenter').on('shown.bs.modal', getEvent);

    $('#exampleModalCenter').on('hidden.bs.modal', hideEvent);


/*Added by deegii, tab change event*/
    /*    $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
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
    */

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
        $('<p>').attr({ 'class': "card-text" }).text("Start date: " + "2018-4-22").appendTo('#card-body' + data[item].eventId);
        $('<p>').attr({ 'class': "card-text" }).text("End date: " + "2018-4-23").appendTo('#card-body' + data[item].eventId);
        $('<p>').attr({ 'class': "card-text","id":"card-text" + data[item].eventId}).appendTo('#card-body' + data[item].eventId);
        $('<small>').attr({ 'class': "text-muted" }).text("Last updated 3 mins ago").appendTo('#card-text' + data[item].eventId);
        $('<a>').attr({ 'class': "card-link", 'href':'#','data-toggle':'modal','data-target':'#exampleModalCenter', 'data-eventId':data[item].eventId}).text("more ...").appendTo('#card-body' + data[item].eventId);
    }
}

/*Added by deegii, ongoing event list*/
function ongoingList(data) {
    for (let item in data) {
        $('<div>').attr({ 'class': "card","id": "card" + data[item].eventId }).appendTo('#columns1');
        if(data[item].img != ""){
            $('<img>').attr({ 'class': "card-img-top", 'src': data[item].img}).appendTo('.card');
        }
        $('<div>').attr({ 'class': "card-body", "id":"card-body" + data[item].eventId}).appendTo('#card' + data[item].eventId);
        $('<h5>').attr({ 'class': "card-title", "id": "card-title" + data[item].eventId}).text(data[item].name).appendTo('#card-body' + data[item].eventId);
        $('<span>').attr({ 'class': "badge badge-secondary" }).text("New").appendTo('#card-title' + data[item].eventId);
        $('<div>').attr({ 'class': "card-text" }).text(data[item].startDate).appendTo('#card-body' + data[item].eventId);
        $('<div>').attr({ 'class': "card-text" }).text(data[item].endDate).appendTo('#card-body' + data[item].eventId);
        $('<a>').attr({ 'class': "card-link", 'data-toggle':'modal','data-target':'#exampleModalCenter','href':'/Event?action=get?id=' + data[item].eventId}).text("more ...").appendTo('#card-body' + data[item].eventId);
    }
}

function failureFunction() {
    console.log("Couldn't load ajax");
}

/*Added by deegii, show event description in the modal*/
function getEvent(e) {
    var id = e.relatedTarget.attributes.getNamedItem('data-eventId').value;
    $.ajax({
        url: '/Event?action=get&id=' + id,
        type:"GET",
        success: function(data){
            $('#exampleModalLongTitle').append(data.name);
            $('.modal-body').append(data.startDate + '<br/>' + data.endDate);
        },
    });
}

function hideEvent() {
    $('#exampleModalLongTitle').empty()
    $('.modal-body').empty()
}