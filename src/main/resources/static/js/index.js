let withType = false;
function init(withType_t){
    withType = withType_t;
}

function changePage(){
    let pageNumber = $("#dropdown-menu").val();
    $('#blogs').load((!withType?"/changePage/":"/changePageWithType/")+pageNumber);
    $("#dropdown-menu").val(pageNumber);
}

function searchForType(typeID){
    $('#blogs').load("/searchForType/"+typeID);
}

function searchForBlogs(){
    let keywords = $("#search-input").val();
    if(keywords.trim().length !== 0){
        $('#blogs').load("/searchForBlogs/"+encodeURI(keywords));
    }
}