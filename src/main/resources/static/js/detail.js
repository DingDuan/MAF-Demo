// $(function () {
//       createMyPage(1,5);
//      // deleteMyPage();
//
// })

function createMyPage(start,end) {
    var i = start;
    var myPage = $("#myPage");
    var li_left = $("<li class='page-li'><a onclick='pre()'>&laquo;</a></li>\n");
    myPage.append(li_left);
    for(i; i <= end; i++){
        var l = $("<li class='page-li'><a onclick='testA("+i+")'>"+i+"</a></li>");
        myPage.append(l);
    }
    var li_right = $("<li class='page-li'><a onclick='next()'>&raquo;</a></li>");
    myPage.append(li_right);
}

function deleteMyPage() {
    var list = $(".page-li");
    // console.log(list.length);
    var i = 0;
    for(i; i<list.length;i++){
        list[i].remove();
    }
}

function testA(index) {
    // console.log(data)
    sessionStorage.setItem("current",index);
    if(index===5||(index>5 && index%4===1)){
        deleteMyPage();
        createMyPage(index,index+4);
    }
}

function pre(){
    var current = sessionStorage.getItem("current");
    console.log(current);
    testA(current-1);
}

function next(){
    var current = sessionStorage.getItem("current");
    console.log(current);
    testA(current+1);
}

function display(index){

}