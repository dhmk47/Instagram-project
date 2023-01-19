function statusChangeCallback(response) {
    console.log(response.authResponse)
    if (response.status === 'connected') {
        alert('로그인 성공!');

        $.ajax({
            async: false,
            type: "post",
            url: `/api/v1/auth/oauth/facebook`,
            contentType: "application/json",
            data: JSON.stringify(response.authResponse),
            dataType: "json",
            success: (response) => {
                alert("로그인 성공");
            },
            error: (request, status, error) => {
                console.log(request.status);
                console.log(request.responseText);
                console.log(error);
            }
        })

    } else if (response.status === 'not_authorized') {
        alert('Please log ' + 'into this app.');
    } else {
        alert('Please log ' + 'into Facebook.');
    }
}

function checkLoginState() {
    FB.getLoginStatus(function(response) {
        statusChangeCallback(response);
    });
}

window.fbAsyncInit = function() {
    FB.init({
        appId : '1349782719148406',
        cookie: true,  
        xfbml : true,
        version : 'v15.0'
    });

    FB.getLoginStatus(function(response) {
        statusChangeCallback(response);
    });

    FB.Event.subscribe('auth.logout', function(response) {
            document.location.reload();
    });

};


(function(d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id))
        return;
    js = d.createElement(s);
    js.id = id;
    js.src = "//connect.facebook.net/en_US/sdk.js";
    fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));