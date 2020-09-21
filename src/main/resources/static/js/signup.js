// false for exist, true for no exist
let state = false;

//validate form
function validateSignUp(){
    let tipText = document.getElementById("tipText");
    tipText.style.display = "inline";
    let allInput = document.getElementsByClassName("form-control");
    let user = allInput[0].value; // user
    let password = allInput[1].value; // password
    let re_password = allInput[2].value; // confirm password

    if(state === false) return false; // user exists

    //check user, length between 6 and 18
    if(user.length < 6){
        tipText.innerText = "Length of username at least 6";
        return false;
    }

    //check password, length between 6 and 18
    if(password.length < 6){
        tipText.innerText = "Length of password at least 6";
        return false;
    }

    if(password === re_password){
        tipText.style.display = "none";
        return true;
    }else{
        tipText.innerText = "Two passwords don't match";
        return false;
    }

}

//check username existence
function checkExist() {
    $.post({
        url:'/exist',
        data:{"username":$("#username").val()},
        success:function (data) {
            console.log(data);
            if(data === 1){
                $("#tipText").css("display","none");
                state = true;
            }else{
                $("#tipText").css("display","inline").html("This username already existed");
                state = false;
            }
        }
    })
}

function addRegex(element,regex){
    element.oninput = function(){
        element.value= element.value.replace(regex,'');
    }
}

//add pattern to input tag
let elements = document.getElementsByClassName("form-control");
addRegex(elements[0],/[^a-z0-9]/g);
addRegex(elements[1],/[^a-zA-Z0-9_!@#$%^&*()]/g);
addRegex(elements[2],/[^a-zA-Z0-9_!@#$%^&*()]/g);

//add onblur to username input
document.getElementById("username").onblur = checkExist;