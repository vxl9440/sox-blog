import "./JQuery"

function checkAccount(){
    $.post({
        url:"@{/user/toLogin}",
        data:{"name":$("#username").val()},
        success:function(data){
           if(data === 1){ //ok
               $.get({
                   url:"@{/user/toHome}" // success, redirect to home page
               })
           }else{// fail
               document.getElementById("tipText").style.display = "inline";
           }
        }
        /*error:function (status) {
            alert(status);
        }*/
    });
}

