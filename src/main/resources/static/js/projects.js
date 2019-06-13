function showDatalog() {
    if($("#dataDetail").css("display")=="none"){
        $("#dataDetail").show();
    }
}

function delayURL(url,time) {
    setTimeout("top.location.href = '" + url + "'",time);
}