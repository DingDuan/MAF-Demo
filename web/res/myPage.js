﻿function exeData(num, type) {
    loadData(num);
    loadpage();
}
function loadpage() {
    var myPageCount = parseInt($("#PageCount").val());
    var myPageSize = parseInt($("#PageSize").val());
    var countindex = myPageCount % myPageSize > 0 ? (myPageCount / myPageSize) + 1 : (myPageCount / myPageSize);
    $("#countindex").val(countindex);

    $.jqPaginator('#pagination', {
        totalPages: parseInt($("#countindex").val()),
        visiblePages: parseInt($("#visiblePages").val()),
        currentPage: 1,
        first: '<li class="first"><a href="javascript:;">首页</a></li>',
        prev: '<li class="prev"><a href="javascript:;"><i class="arrow arrow2"></i>上一页</a></li>',
        next: '<li class="next"><a href="javascript:;">下一页<i class="arrow arrow3"></i></a></li>',
        last: '<li class="last"><a href="javascript:;">末页</a></li>',
        page: '<li class="page"><a href="javascript:;">{{page}}</a></li>',
        onPageChange: function (num, type) {
            if (type == "change") {
                exeData(num, type);
                //num:当前页码
                // console.log(num);
                // console.log(type);
            }else {
                //这里放ajax请求，亲测有效
                //真实思路：首先获得num当前页码
                // $.ajax({
                //     type: "GET",
                //     url: "http://47.101.183.63:8089/api/v1/video/statistics/basic/5285890784414113572",
                //     success: function (data) {
                //         console.log(data);
                //     }
                // })
            }
        }
    });
}
$(function () {
    loadData(1);
    loadpage();
});

function testB(){
    //var current = sessionStorage.getItem("current");
    //console.log(current);
}