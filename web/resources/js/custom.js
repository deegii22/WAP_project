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

    // Added by Delgersaikhan  - Start
    $.ajax({
        url: "/Members",
        type: "GET",
        success: memberList,
        error: failureFunction
    });
    // Added by Delgersaikhan  - End

    var arrRoute = [];

    $('#addEvent').click(function () {
        $('.add-event').toggle();
    });

    $('#alertEvent').click(function () {
        $('.alert').toggle();
    });

    $('#exampleModalCenter').on('shown.bs.modal', getEvent);

    $('#exampleModalCenter').on('hidden.bs.modal', hideEvent);

    $(document).on('click', '#btnJoinRide', function () {
        var id = $('#btnJoinRide').attr('data-eventId')
        $.ajax({
            url: '/Event?action=joinEvent&id=' + id,
            type:"POST",
            //success: upcomingList,
            error: failureFunction
        });
    })

    $(document).on('click', '#btnStartEvent', function () {
        var id = $('#btnStartRide').attr('data-eventId')
        $.ajax({
            url: '/Event?action=startEvent&id=' + id,
            type:"POST",
            //success: upcomingList,
            error: failureFunction
        });
    })

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
        var file_data = $("#file").prop("files")[0];   // Getting the properties of file from file field
        var form_data = new FormData();                  // Creating object of FormData class
        form_data.append("file", file_data)              // Appending parameter named file with properties of file_field to form_data
        form_data.append("name", $('#eventName').val())                 // Adding extra parameters to form_data
        form_data.append("start", $('#start').val())                 // Adding extra parameters to form_data
        form_data.append("end", $('#end').val())                 // Adding extra parameters to form_data
        form_data.append("route", JSON.stringify(arrRoute))                 // Adding extra parameters to form_data
        $.ajax({
            url: '/Event?action=add',
            method:"POST",
            enctype: 'multipart/form-data',
            data: form_data,
            processData: false,
            contentType: false,
            dataType: 'script'
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
        var table = $("<table>").attr({"class":"table table-dark"});
        var thead = $("<thead>");
        var tr = $("<tr>");
        tr.append($("<th>").text("Start position"));
        tr.append($("<th>").text("End position"));
        tr.append($("<th>").text("Duration"));
        thead.append(tr);
        table.append(thead);
        var tbody = $("<tbody>");
        for(var i=0; i<arrRoute.length; i++){
            tr = $("<tr>");
            tr.append($("<td>").text(arrRoute[i].startPosition));
            tr.append($("<td>").text(arrRoute[i].endPosition));
            tr.append($("<td>").text(arrRoute[i].duration));
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
            $('<img>').attr({ 'class': "card-img-top", 'src': data[item].img}).appendTo('#card'+ data[item].eventId );
        }
        $('<div>').attr({ 'class': "card-body", "id":"card-body" + data[item].eventId}).appendTo('#card' + data[item].eventId);
        $('<h5>').attr({ 'class': "card-title", "id": "card-title" + data[item].eventId}).text(data[item].name).appendTo('#card-body' + data[item].eventId);
        $('<span>').attr({ 'class': "badge badge-secondary" }).text("New").appendTo('#card-title' + data[item].eventId);
        $('<p>').attr({ 'class': "card-text" }).text("Start date: " + data[item].startDate).appendTo('#card-body' + data[item].eventId);
        $('<p>').attr({ 'class': "card-text" }).text("End date: " + data[item].endDate).appendTo('#card-body' + data[item].eventId);
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
            $('<img>').attr({ 'class': "card-img-top", 'src': data[item].img}).appendTo('#card'+ data[item].eventId );
        }
        $('<div>').attr({ 'class': "card-body", "id":"card-body" + data[item].eventId}).appendTo('#card' + data[item].eventId);
        $('<h5>').attr({ 'class': "card-title", "id": "card-title" + data[item].eventId}).text(data[item].name).appendTo('#card-body' + data[item].eventId);
        $('<span>').attr({ 'class': "badge badge-secondary" }).text("New").appendTo('#card-title' + data[item].eventId);
        $('<div>').attr({ 'class': "card-text" }).text(data[item].startDate).appendTo('#card-body' + data[item].eventId);
        $('<div>').attr({ 'class': "card-text" }).text(data[item].endDate).appendTo('#card-body' + data[item].eventId);
        $('<a>').attr({ 'class': "card-link", 'data-toggle':'modal','data-target':'#exampleModalCenter','href':'/Event?action=get?id=' + data[item].eventId}).text("more ...").appendTo('#card-body' + data[item].eventId);
    }
}

/*Added by Delgersaikhan, Member list - Start*/
function memberList(data) {
    for (let item in data){
        $('<div>').attr({ 'class': "card","id": "member" + data[item].userId }).appendTo('#columns2');
        $('<div>').attr({ 'class': "card-body", "id":"member-body" + data[item].userId}).appendTo('#member' + data[item].userId);
        $('<h5>').attr({ 'class': "card-title", "id": "member-title" + data[item].userId}).text(data[item].name).appendTo('#member-body' + data[item].userId);
        $('<div>').attr({ 'class': "card-text" }).text("Gender: " + data[item].sex).appendTo('#member-body' + data[item].userId);
        $('<div>').attr({ 'class': "card-text" }).text("Email: " + data[item].email).appendTo('#member-body' + data[item].userId);
        $('<div>').attr({ 'class': "card-text" }).text("Phone: " + data[item].phone).appendTo('#member-body' + data[item].userId);
        $('<div>').attr({ 'class': "card-text" }).text("Birthday: " + data[item].birthday).appendTo('#member-body' + data[item].userId);
    }
}
/*Added by Delgersaikhan, Member list - End*/

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
            if(data.img != ""){
                $('.modal-body').append($('<img>').attr({"src": data.img, "style":"width:100%"}));
            }
            $('<p>').text("Start date: " + data.startDate).appendTo('.modal-body');
            $('<p>').text("End date: " + data.endDate).appendTo('.modal-body');
            $('.modal-footer').empty();
            if(data.status === 0){
                $('<button>').attr({'data-eventId':data.eventId, 'class':"btn btn-primary", 'id':"btnStartEvent"}).text("Start an event").appendTo('.modal-footer');
            }
            $('<button>').attr({'data-eventId':data.eventId, 'class':"btn btn-primary", 'id':"btnJoinRide"}).text("Join a ride").appendTo('.modal-footer');
            var aa = sessionStorage.getItem("user");

        },
    });
    $.ajax({
        url: '/Event?action=getAllRoutes&id=' + id,
        type:"GET",
        success: function(data){
            $('<div>').attr({ 'class': "routes", 'id':"routes"  + id}).text("Routes").appendTo('.modal-body');
            $('<table>').attr({"class":"table table-dark","id":"table" + id}).appendTo('#routes' + id);
            for (let item in data) {
                $('<tr>').attr({"id": "tr" + data[item].priority}).appendTo('#table' + id);
                $('<td>').text(data[item].startPosition).appendTo('#tr' + data[item].priority);
                $('<td>').text(data[item].endPosition).appendTo('#tr' + data[item].priority);
                $('<td>').text(data[item].duration).appendTo('#tr' + data[item].priority);
                $('<td>').attr({"id":"td" + id}).appendTo('#tr' + data[item].priority);
                $('<span>').attr({ 'class': "alert alert-warning"}).text(data[item].status === 0 ? "upcoming":"finished").appendTo('#td' + data[item].priority);
            }
        }
    });

    $.ajax({
        url: '/Event?action=getMembers&id=' + id,
        type:"GET",
        success: function(data){
            $('<div>').attr({ 'class': "btn", 'id':"members"}).text("Members").appendTo('.modal-body');
            for (let item in data) {
                $('<p>').attr({ 'class': "btn"}).text(data[item].userName).appendTo('#members');
            }
        }
    });
}

function hideEvent() {
    $('#exampleModalLongTitle').empty()
    $('.modal-body').empty()
}