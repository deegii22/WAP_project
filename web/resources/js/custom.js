$(function (){
    $('#addEvent').click(function () {
        $('.add-event').toggle();
    });

    $('#alertEvent').click(function () {
        $('.alert').toggle();
    });
});


function showSignUpPage(){
    window.location.assign("signup.jsp")
}

function showloginPage(){
    window.location.assign("login.jsp")
}

