function init(active){
    console.log(active);
    if(active !== '0'){
        $('#nav-profile-tab').removeClass("active");
        $('#nav-profile').removeClass("active").removeClass("show");
        if(active === '1'){
            $('#nav-subscriber-tab').addClass("active");
            $('#nav-subscriber').addClass("active").addClass("show");
        }else{
            $('#nav-follower-tab').addClass("active");
            $('#nav-follower').addClass("active").addClass("show");
        }
    }
}

function save(){
    let name = $("#name").val().trim();
    let email = $("#email").val().trim();
    let flag = true;
    if(name.length === 0) flag = false;
    if(email.length === 0) flag = false;
    if(!flag){
        $("#msg").html("Email or name can't be empty").css("color","red");
    }else{
        sendUserInformationData(name,email);
    }

}

function sendUserInformationData(name,email){
    $.post({
        url:"/saveUserInfo",
        data: {
            "name":name,
            "email":email
        },
        contentType:"application/x-www-form-urlencoded",
        success:function(data){
            if(data === 1) $("#msg").html("Save successfully").css("color","green");
            else $("#msg").html("Failed to save").css("color","red");
        },
        error:function (status) {
            $("#msg").html("Failed to save").css("color","red");
        }
    });
}

function changePassword(){
    let newPassword = $("#new-password").val();
    let confirmPassword = $("#confirm-new-password").val();

    if(newPassword.length < 6){
        $("#msg2").html("New password should be at least 6 characters").css("color","red");
        return;
    }

    if(newPassword !== confirmPassword){
        $("#msg2").html("New password doesn't match").css("color","red");
        return;
    }
    sendPasswordData(newPassword,true);
}


function checkPassword(){
    let oldPassword = $("#old-password").val();

    if(oldPassword.length === 0){
        $("#msg2").html("Old password is empty").css("color","red");
        return;
    }
    sendPasswordData(oldPassword,false);
}

function sendPasswordData(password,flag){
    $.post({
        url: flag === true ?"/changePassword":"/checkPassword",
        data: {
            "password":password
        },
        contentType:"application/x-www-form-urlencoded",
        success:function(data){
            if(flag){
                if(data === 1){
                    $("#msg2").html("Password changed").css("color","green");
                    $("#old-password").val('');
                    $("#new-password").val('');
                    $("#confirm-new-password").val('');
                }else{
                    $("#msg2").html("Failed to change password").css("color","red");
                }
            }else{
                if(data === 0){
                    $("#msg2").html("Old password invalid").css("color","red");
                }else{
                    changePassword();
                }
            }
        },
        error:function (status) {
            $("#msg2").html("An unexpected error occurred, failed to change password")
                .css("color","red");
        }
    });
}