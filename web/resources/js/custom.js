$(function (){
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
            data:{eventName: $('#eventName').val(), route:$('#route').val(), start:$('#start').val(), end:$('#end').val()}
        }).done(function(data) {
            alert(data);
        });
    });
});


function showSignUpPage(){
    window.location.assign("signup.jsp")
}

function showloginPage(){
    window.location.assign("login.jsp")
}

