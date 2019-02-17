var dom1 = document.getElementById("chart1");
var myChart1 = echarts.init(dom1);
var dom2 = document.getElementById("chart2");
var myChart2 = echarts.init(dom2);
var dom3 = document.getElementById("chart3");
var myChart3 = echarts.init(dom3);
var app = {};
option = null;
app.title = '环形图';

function getChart() {
    // option = {
    //     tooltip: {
    //         trigger: 'item',
    //         formatter: "{d}%"
    //     },
    //     // legend: {
    //     //     orient: 'vertical',
    //     //     x: 'left',
    //     //     data: ['相似', '不同']
    //     // },
    //     color:['#286BF0','#FDFFFE'],
    //     series: [
    //         {
    //             // name: '访问来源',
    //             type: 'pie',
    //             radius: ['50%', '70%'],
    //             avoidLabelOverlap: false,
    //             label: {
    //                 normal: {
    //                     show: true,
    //                     position: 'center',
    //                     fontSize: '30',
    //                     fontWeight: 'bold'
    //                 },
    //                 emphasis: {
    //                     show: true,
    //                     textStyle: {
    //                         fontSize: '30',
    //                         fontWeight: 'bold'
    //                     }
    //                 }
    //             },
    //             labelLine: {
    //                 normal: {
    //                     show: false
    //                 }
    //             },
    //             data: [
    //                 {value: 90, name: '90%'},
    //                 {value: 10, name: '10%'},
    //             ]
    //         }
    //     ]
    // };
    // ;
    // if (option && typeof option === "object") {
    //     myChart1.setOption(option, true);
    //     myChart2.setOption(option, true);
    //     myChart3.setOption(option, true);
    // }
    //标示文字
    var labelTop = {
        normal : {
            label : {
                show : false,
                position : 'center',
                formatter : '{b}',
                textStyle: {
                    baseline : 'bottom',  //垂直对齐方式
                    fontSize:12,
                    color:'#666',
                    align : 'center'  //水平对齐方式
                }
            },
            color:'#de4751',
            labelLine : {  //标示线
                show : false
            }
        }
    };
//百分比设置
    var labelFromatter = {
        normal : {
            label : {
                formatter : function (params){
                    return 100 - params.value + '/' + '158'
                },
                textStyle: {
                    baseline : 'middle',  //垂直对齐方式
                    fontSize:27,
                    color:'#666',
                    align : 'center'   //水平对齐方式
                }
            }
        }
    };
//数据位置
    var labelBottom = {
        normal : {
            color: '#ccc',
            label : {
                show : true,
                position : 'center'
            },
            labelLine : {
                show : false
            }
        },
        emphasis: {
            color: 'rgba(0,0,0,0)'
        }
    };
//环形图
    BeforeCountOption = {
        title: {
            text: '待批人员/全部人员',
            // subtext: '申请明细 >',
            // sublink: 'http://e.weibo.com/1341556070/AhQXtjbqh',
            x: 'center',
            y: '60',
            itemGap: 50,
            textStyle : {
                color : '#666',
                fontSize : 12,
                fontWeight : 'normal'
            },
            subtextStyle : {
                color : '#de4751',
                fontSize : 12
            }
        },
        series : [
            {
                type : 'pie',
                center : ['50%', '50%'],
                radius: ['78%', '85%'],
                // x: '0%', // for funnel
                itemStyle : labelFromatter,
                data : [
                    {name:'other', value:30, itemStyle : labelBottom},
                    {name:'待批人员/全部人员', value:70,itemStyle : labelTop}
                ]
            }
        ]
    };
    myChart1.setOption(BeforeCountOption,true);

}