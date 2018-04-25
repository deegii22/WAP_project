"use strict";

$(function (){
    upcomingAjaxList();
    ongoingAjaxList();
    memberAjaxList();
    redflagAjaxList();
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
        var id = $('#btnStartEvent').attr('data-eventId')
        $.ajax({
            url: '/Event?action=startEvent&id=' + id,
            type:"POST",
            success: function () {
                upcomingAjaxList();
                ongoingAjaxList();
            },
            error: failureFunction
        });
    })

    $(document).on('click','#redFlag', function () {
        var eventId = $('#redFlag').attr('data-eventId');
        var priority = $('#redFlag').attr('data-priority');
        $.ajax({
            url: '/Event?action=setEFlag&id=' + eventId + '&priority=' + priority + '&info=' + "",
            type:"POST",
            success: redflagAjaxList,
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
            dataType: 'script',
            }).done(function(data) {
                alert(data);
                arrRoute = [];
                upcomingAjaxList();
                $('.add-event').toggle();
                $('#eventName').val("");
                $('#start').val("");
                $('#end').val("");
            });
    });

    $("#btnAddRoute").click(function(){
        arrRoute.push({
            startPosition: $("#startPosLong").val()+", "+$("#startPosLong").val(),
            endPosition: $("#endPosLong").val()+", "+$("#endPosLat").val(),
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
        $("#startPosLong").val("");
        $("#startPosLat").val("");
        $("#endPosLong").val("");
        $("#endPosLat").val("");
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
        $('<a>').attr({ 'class': "card-link", 'href':'#','data-toggle':'modal','data-target':'#exampleModalCenter', 'data-eventId':data[item].eventId}).text("more ...").appendTo('#card-body' + data[item].eventId);
    }
}

/*Added by Delgersaikhan, Member list - Start*/
function memberList(data) {
    let array = JSON.parse(data);

    for(let i=0; i<array.length; i++){
        $('<div>').attr({ 'class': "card","id": "card" + i }).appendTo('#columns2');
        $('<div>').attr({ 'class': "card-body", "id":"card-body" + i}).appendTo('#card' + i);
        $('<h5>').attr({ 'class': "card-title", "id": "card-title" + i}).text(array[i].name).appendTo('#card-body' + i);
        $('<div>').attr({ 'class': "card-text" }).text("Gender: " + array[i].sex).appendTo('#card-body' + i);
        $('<div>').attr({ 'class': "card-text" }).text("Email: " + array[i].email).appendTo('#card-body' + i);
        $('<div>').attr({ 'class': "card-text" }).text("Phone: " + array[i].phone).appendTo('#card-body' + i);
        $('<div>').attr({ 'class': "card-text" }).text("Birthday: " + array[i].birthday).appendTo('#card-body' + i);

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
            if(data.img != ""){
                $('.modal-body').append($('<img>').attr({"src": data.img, "style":"width:100%"}));
            }
            $('<p>').text("Start date: " + data.startDate).appendTo('.modal-body');
            $('<p>').text("End date: " + data.endDate).appendTo('.modal-body');
            $('.modal-footer').empty();
            if(data.myEvent === 1 && data.status === 0){
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
            $('<table>').attr({"class":"table table-bordered","id":"table" + id}).appendTo('#routes' + id);
            for (let item in data) {
                $('<tr>').attr({"id": "tr" + data[item].priority}).appendTo('#table' + id);
                $('<td>').text(data[item].startPosition).appendTo('#tr' + data[item].priority);
                $('<td>').text(data[item].endPosition).appendTo('#tr' + data[item].priority);
                $('<td>').text(data[item].duration).appendTo('#tr' + data[item].priority);
                $('<td>').attr({"id":"td" + data[item].priority}).appendTo('#tr' + data[item].priority);
                $('<a>').attr({ 'class': "alert alert-warning",'id': 'redFlag','data-eventid': id, 'data-priority': data[item].priority}).text(data[item].status === 0 ? "Red flag":"finished").appendTo('#td' + data[item].priority);
            }
        }
    });

    $.ajax({
        url: '/Event?action=getMembers&id=' + id,
        type:"GET",
        success: function(data){
            $('<div>').attr({ 'class': "members", 'id':"members" + id}).text("Members").appendTo('.modal-body');
            $('<ul>').attr({"id":"ul" + id}).appendTo('#members' + id);
            for (let item in data) {
                $('<li>').text(data[item].userName).appendTo('#ul' + id);
            }
        }
    });
}

function getEFlags(data) {
    console.log(data);
    if(data != null){
        $('#alertEvent').attr({"class":"btn btn-danger"});
    }

}

function hideEvent() {
    $('#exampleModalLongTitle').empty()
    $('.modal-body').empty()
}

/*Added by Deegii*/
function upcomingAjaxList() {
    $('#columns').empty();

    $('<div>').attr({"class":"card bg-primary text-white text-center p-3"}).appendTo("#columns");
    $('<blockquote>').attr({"class":"blockquote mb-0"}).appendTo('.card.bg-primary.text-white.text-center.p-3');
    $('<p>').text("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer posuere erat.").appendTo('.blockquote.mb-0');
    $('<footer>').attr({"class":"blockquote-footer"}).appendTo('.blockquote.mb-0');
    $('<small>').attr({"class":"small"}).text("Someone famous in").appendTo('.blockquote-footer');
    $('<cite>').attr({"title":"Source Title"}).text("Source Title").appendTo('.small');

    $.ajax({
        url: "/Event?action=upcomingList",
        type: "GET",
        success: upcomingList,
        error: failureFunction
    });
}

/*Added by Deegii*/
function ongoingAjaxList() {
    $('#columns1').empty();
    $.ajax({
        url: "/Event?action=ongoingList",
        type: "GET",
        success: ongoingList,
        error: failureFunction
    });
}

// Added by Delgersaikhan  - Start
function memberAjaxList(){
    $('#columns2').empty();
    $.ajax({
        url: "/Members",
        type: "GET",
        success: memberList,
        error: failureFunction
    });
}

/*Added by Deegii*/
function redflagAjaxList() {
    $.ajax({
        url: "/Event?action=getEFlags",
        type: "GET",
        success: getEFlags,
        error: failureFunction
    });
}