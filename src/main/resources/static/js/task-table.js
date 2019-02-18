function getInputs() {
    var filesDiv = document.querySelector('#upfileSrc');
    var srcPath = filesDiv.value;

    var filesDivOne = document.querySelector('#upfileOne');
    var p1Path = filesDivOne.value;

    var filesDivTwo = document.querySelector('#upfileTwo');
    var p2Path = filesDivTwo.value;

    var data = [];
    // console.log(srcPath);
    console.log(p1Path);
    console.log(p2Path);
    return data;
}

function executeTask() {
    if($("#inside").css("display")=="none"){
        $("#inside").show();
    }

    var inputs = getInputs();

    // $.ajax({
    //     type: "POST",
    //     url: "/TF/detect",
    //     contentType: "application/json;charset=utf8",
    //     data: {},
    //     dataType: "json",
    //     success: function (data) {
    //         var length = data.length;
    //         var dom = document.getElementById("simValues");
    //         var str = "抄袭片段数量为："+length+"，";
    //         for(var i=0;i<length;i++){
    //             str += "片段"+i+"的相似度为"+data[i]+"%";
    //             if(i!=length-1){
    //                 str += "，";
    //             }else{
    //                 str += "。";
    //             }
    //         }
    //         var text = document.createTextNode(str);
    //         dom.appendChild(text);
    //     }
    // })


    // getChart();

    var option1 = {
        value:100,//百分比,必填
        name:'相似度',//必填
        title:'相似度最高的三个片段',

        backgroundColor:'#f0fff4',
        color:['#38a8da','#d4effa'],
        fontSize:32,
        domEle:document.getElementById("chart1")//必填
    }
    percentPie1 = new PercentPie(option1);
    percentPie1.init();

    var option2 = {
        value:95,//百分比,必填
        name:'相似度',//必填
        // title:'相似度第一',
        backgroundColor:'#f0fff4',
        color:['#38a8da','#d4effa'],
        fontSize:32,
        domEle:document.getElementById("chart2")//必填
    }
    percentPie2 = new PercentPie(option2);
    percentPie2.init();

    var option3 = {
        value:93,//百分比,必填
        name:'相似度',//必填
        // title:'相似度第一',
        backgroundColor:'#f0fff4',
        color:['#38a8da','#d4effa'],
        fontSize:32,
        domEle:document.getElementById("chart3")//必填
    }
    percentPie3 = new PercentPie(option3);
    percentPie3.init();
    var myselect = document.getElementById("setting-threshold");
    // var index = myselect.selectedIndex;
    // console.log(myselect.options[index].value);
    // if(myselect.options[index].value=="1.0"){
    //     $(".threshold1-0").show();
        // $(".otherthreshold").css("display")
    // }
    // var taskGroup = getTableContent("task-table");
    //
    // console.log(taskGroup)
    // var longestTime = 0;
    // for (var i = 0; i < taskGroup.length; i++) {
    //     longestTime = longestTime > parseInt(taskGroup[i][3]) ? longestTime : parseInt(taskGroup[i][3]);
    // }
    // localStorage.setItem("longestTime", longestTime);
    // console.log("longestTime:"+longestTime);
    // $.ajax({
    //     method: "POST",
    //     url: "/api/start",
    //     contentType: "application/json;charset=utf8",
    //     data: JSON.stringify({
    //         taskGroup: taskGroup
    //     }),
    //     dataType: "json",
    //     success: function (data) {
    //         window.open("./result.html");
    //     }
    // });
}

// $(function() {
//     var sWidth = $("#focus").width(); //获取焦点图的宽度（显示面积）
//     var len = $("#focus ul li").length; //获取焦点图个数
//     var index = 0;
//     var picTimer;
//
// //以下代码添加数字按钮和按钮后的半透明条，还有上一页、下一页两个按钮
//     var btn = "<div class='btnBg'></div><div class='btn'>";
//     for(var i=0; i < len; i++) {
//         btn += "<span></span>";
//     }
//     btn += "</div><div class='preNext pre'></div><div class='preNext next'></div>";
//     $("#focus").append(btn);
//     $("#focus .btnBg").css("opacity",0.5);
//
// //为小按钮添加鼠标滑入事件，以显示相应的内容
//     $("#focus .btn span").css("opacity",0.4).mouseenter(function() {
//         index = $("#focus .btn span").index(this);
//         showPics(index);
//     }).eq(0).trigger("mouseenter");
//
// //上一页、下一页按钮透明度处理
//     $("#focus .preNext").css("opacity",0.2).hover(function() {
//         $(this).stop(true,false).animate({"opacity":"0.5"},300);
//     },function() {
//         $(this).stop(true,false).animate({"opacity":"0.2"},300);
//     });
//
// //上一页按钮
//     $("#focus .pre").click(function() {
//         index -= 1;
//         if(index == -1) {index = len - 1;}
//         showPics(index);
//     });
//
// //下一页按钮
//     $("#focus .next").click(function() {
//         index += 1;
//         if(index == len) {index = 0;}
//         showPics(index);
//     });
//
// //本例为左右滚动，即所有li元素都是在同一排向左浮动，所以这里需要计算出外围ul元素的宽度
//     $("#focus ul").css("width",sWidth * (len));
//
// //鼠标滑上焦点图时停止自动播放，滑出时开始自动播放
//     $("#focus").hover(function() {
//         clearInterval(picTimer);
//     },function() {
//         picTimer = setInterval(function() {
//             showPics(index);
//             index++;
//             if(index == len) {index = 0;}
//         },4000); //此4000代表自动播放的间隔，单位：毫秒
//     }).trigger("mouseleave");
//
// //显示图片函数，根据接收的index值显示相应的内容
//     function showPics(index) { //普通切换
//         var nowLeft = -index*sWidth; //根据index值计算ul元素的left值
//         $("#focus ul").stop(true,false).animate({"left":nowLeft},300); //通过animate()调整ul元素滚动到计算出的position
// //$("#focus .btn span").removeClass("on").eq(index).addClass("on"); //为当前的按钮切换到选中的效果
//         $("#focus .btn span").stop(true,false).animate({"opacity":"0.4"},300).eq(index).stop(true,false).animate({"opacity":"1"},300); //为当前的按钮切换到选中的效果
//     }
// });


/**
 * 遍历表格内容返回数组
 *
 * @param tableId
 * @returns {Array}
 */
function getTableContent(tableId) {
    var mytable = document.getElementById(tableId);
    var data = [];
    var rows = mytable.rows.length;
    for (var i = 1; i < rows; i++) {
        for (var j = 1, cells = mytable.rows[i].cells.length; j < cells; j++) {
            if (!data[i - 1]) {
                data[i - 1] = [];
            }
            data[i - 1][j - 1] = mytable.rows[i].cells[j].innerHTML;
        }
    }
    return data;
}