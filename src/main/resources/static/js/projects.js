function showDatalog() {
    if($("#dataDetail").css("display")=="none"){
        var dataDetail = document.getElementById("dataDetail");
        dataDetail.style.display = "block";
    }

    if($("#ALUDetail").css("display")!="none"){
        var aluDetail = document.getElementById("ALUDetail");
        aluDetail.style.display = "none";
    }

    if($("#RBTDetail").css("display")!="none"){
        var rbtDetail = document.getElementById("RBTDetail");
        rbtDetail.style.display = "none";
    }
}

function showALU() {
    if($("#dataDetail").css("display")!="none"){
        // console.log(fasd);
        var dataDetail = document.getElementById("dataDetail");
        dataDetail.style.display = "none";
        // console.log($("#dataDetail").css("display"));
    }

    if($("#ALUDetail").css("display")=="none"){
        var aluDetail = document.getElementById("ALUDetail");
        aluDetail.style.display = "block";
    }

    if($("#RBTDetail").css("display")!="none"){
        var rbtDetail = document.getElementById("RBTDetail");
        rbtDetail.style.display = "none";
    }
}

function showRBT() {
    if($("#dataDetail").css("display")!="none"){
        // console.log(fasd);
        var dataDetail = document.getElementById("dataDetail");
        dataDetail.style.display = "none";
        // console.log($("#dataDetail").css("display"));
    }

    if($("#ALUDetail").css("display")!="none"){
        var aluDetail = document.getElementById("ALUDetail");
        aluDetail.style.display = "none";
    }

    if($("#RBTDetail").css("display")=="none"){
        var rbtDetail = document.getElementById("RBTDetail");
        rbtDetail.style.display = "block";
    }
}

function delayURL(url,time) {
    setTimeout("top.location.href = '" + url + "'",time);
}