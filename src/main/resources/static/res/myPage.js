function exeData(num, type) {
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
        first: '<li class="first"><a href="javascript:;">First</a></li>',
        prev: '<li class="prev"><a href="javascript:;"><i class="arrow arrow2"></i>Prev</a></li>',
        next: '<li class="next"><a href="javascript:;">Next<i class="arrow arrow3"></i></a></li>',
        last: '<li class="last"><a href="javascript:;">Last</a></li>',
        page: '<li class="page"><a href="javascript:;">{{page}}</a></li>',
        onPageChange: function (num, type) {
            if (type == "change"&& num==2) {
                //num:当前页码
                //console.log(num);
                // console.log(type);
                // var src = document.getElementById("src-code");
                // // console.log(src_pre);
                // src.innerHTML = "" +
                //     "         Predicate(String name) {\n" +
                //     "            if (name == null) {\n" +
                //     "                throw new NullPointerException(\"Name cannot be null\");\n" +
                //     "            }\n" +
                //     "            this.name = name;\n" +
                //     "        }";
                exeData(num, type);
                // jQuery("pre").addClass("prettyprint linenums");
                // prettyPrint();
                window.location.href="detail-2.html";
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