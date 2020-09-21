let typeList = [];

function addType(typeName,typeID){
    let typeField = document.getElementById("type-field");
    //create new node
    let newNode = document.createElement("span");
    newNode.innerText = typeName;
    newNode.setAttribute("class","type-item");

    //check existence
    let flag = 0;
    for(let i = 0,len = typeList.length;i < len;i++){
        if(typeList[i] === typeID){
            flag = 1;
            break;
        }
    }
    if(flag) return;
    typeList.push(typeID);

    console.log(typeList);
    //create a close icon
    let newCloseIcon = document.createElement("button");
    newCloseIcon.setAttribute("type","button");
    newCloseIcon.setAttribute("class","close");
    newCloseIcon.setAttribute("arial-label","Close");

    //create span to contain close icon
    let closeIcon = document.createElement("span");
    closeIcon.setAttribute("arial-hidden","true");
    closeIcon.innerHTML = "&times;";

    newCloseIcon.onclick = function (){ // remove type-item
        typeField.removeChild(newNode);
        for(let i = 0,len = typeList.length;i < len;i++){
            if(typeList[i] === typeID){
                typeList.splice(i,1);
                break;
            }
        }
    }

    newCloseIcon.appendChild(closeIcon);
    newNode.appendChild(newCloseIcon);
    typeField.appendChild(newNode);
}

function validator(){
    let subject = $("#subject").val().trim();
    let introduction = $("#introduction").val().trim();
    let blogContent = editor.txt.html().trim();
    if(subject.length > 0 && blogContent.length > 0 && introduction.length > 0){
        sendData();
    }else{
        if(subject.length === 0) $("#subject").css("border","1px solid red");
        if(editor.txt.html().length === 0) $("#text-bar").css("border","1px solid red");
        if(introduction.length === 0) $("#introduction").css("border","1px solid red");
    }
}

function sendData(){
    let allData = {
        "title":$("#subject").val().trim(),
        "introduction":$("#introduction").val().trim(),
        "content":editor.txt.html(),
        "types":typeList,
    }

    $.post({
        url:"/submitBlog",
        data: allData,
        contentType:"application/x-www-form-urlencoded",
        success:function(data){
            if(data !== "FAIL"){
                window.location.href = "/blog/readBlog/"+data;
            }else{
                alert("Something go wrong");
            }
        },
        error:function (status) {
            alert(status);
        }
    });
}