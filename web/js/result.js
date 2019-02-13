window.setInterval(getResult, 10000);
var time = -1;
var chart = echarts.init(document.getElementById('chart'));
var timeList = [0];
var transverse = 1;

function getResult() {
    time++;
    if (time === 0) {
        var option = {
            title: {
                text: '分数趋势'
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'cross',
                    label: {
                        backgroundColor: '#6a7985'
                    }
                }
            },
            legend: {
                data: ['Randoop', 'Evosuite', 'human1', 'human2', 'human3'],
                x: 'right',
                show: 'true'
            },
            toolbox: {
                feature: {
                    // saveAsImage: {}
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: [
                {
                    type: 'category',
                    boundaryGap: false,
                    data: timeList,
                    name: '时间',
                    nameLocation: 'end',
                    nameGap: 0
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    name: '分数',
                    nameLocation: 'end',
                    nameGap: 10
                }
            ],
            series: [
                {
                    name: 'Randoop',
                    type: 'line',
                    label: {
                        normal: {
                            show: true,
                            position: 'top'
                        }
                    },
                    data: [0]
                },
                {
                    name: 'Evosuite',
                    type: 'line',
                    label: {
                        normal: {
                            show: true,
                            position: 'top'
                        }
                    },
                    data: [0]
                },
                {
                    name: 'human1',
                    type: 'line',
                    label: {
                        normal: {
                            show: true,
                            position: 'top'
                        }
                    },
                    data: []
                },
                {
                    name: 'human2',
                    type: 'line',
                    label: {
                        normal: {
                            show: true,
                            position: 'top'
                        }
                    },
                    data: []
                },
                {
                    name: 'human3',
                    type: 'line',
                    label: {
                        normal: {
                            show: true,
                            position: 'top'
                        }
                    },
                    data: []
                }
            ]
        };
        chart.setOption(option);
    } else if (time === getTotalTime(1) || time === getTotalTime(2) || time === getTotalTime(3) || time === getTotalTime(4) || time === getTotalTime(5) || time === getTotalTime(6) ||
        time === getTotalTime(12) || time === getTotalTime(18) || time === getTotalTime(24) || time === getTotalTime(30) || time === getTotalTime(60) || time === 120+1 ||
        time === 210+1 || time === 330+1 || time === 480+1 || time === 660+1 || time === 690+1 || time === 720+1) {
        //console.log("time:"+time * 10);
        console.log("logestTime:"+getTotalTime(parseInt(localStorage.getItem("longestTime"))/10));
        if (time <= getTotalTime(parseInt(localStorage.getItem("longestTime"))/10)) {
            timeList.push(transverse * 10);
            transverse++;
            $.ajax({
                type: "GET",
                url: "/api/score",
                contentType: "application/x-www-form-urlencoded",
                data: {},
                dataType: "json",
                success: function (data) {
                    var randoopList = [];
                    var evosuiteList = [];
                    var randoopScoreList = [0];
                    var evosuiteScoreList = [0];

                    for (var i = 0; i < data.length; i++) {
                        switch (data[i].tool) {
                            case "Randoop":
                                randoopList.push(data[i]);
                                randoopScoreList.push(Math.round(data[i].total));
                                break;
                            case "Evosuite":
                                evosuiteList.push(data[i]);
                                evosuiteScoreList.push(Math.round(data[i].total));
                                break;
                        }
                    }

                    $("#evosuite-total").text(Math.round(evosuiteScoreList[evosuiteScoreList.length - 1]));
                    $("#evosuite-bc").text(Math.round(evosuiteList[evosuiteList.length - 1].bc));
                    $("#evosuite-mc").text(Math.round(evosuiteList[evosuiteList.length - 1].mc));

                    $("#randoop-total").text(Math.round(randoopScoreList[randoopScoreList.length - 1]));
                    $("#randoop-bc").text(Math.round(randoopList[randoopList.length - 1].bc));
                    $("#randoop-mc").text(Math.round(randoopList[randoopList.length - 1].mc));

                    var option = {
                        title: {
                            text: '分数趋势'
                        },
                        tooltip: {
                            trigger: 'axis',
                            axisPointer: {
                                type: 'cross',
                                label: {
                                    backgroundColor: '#6a7985'
                                }
                            }
                        },
                        legend: {
                            data: ['Randoop', 'Evosuite', 'human1', 'human2', 'human3'],
                            x: 'right',
                            show: 'true'
                        },
                        toolbox: {
                            feature: {
                                // saveAsImage: {}
                            }
                        },
                        grid: {
                            left: '3%',
                            right: '4%',
                            bottom: '3%',
                            containLabel: true
                        },
                        xAxis: [
                            {
                                type: 'category',
                                boundaryGap: false,
                                data: timeList,
                                name: '时间',
                                nameLocation: 'end',
                                nameGap: 0
                            }
                        ],
                        yAxis: [
                            {
                                type: 'value',
                                name: '分数',
                                nameLocation: 'end',
                                nameGap: 10
                            }
                        ],
                        series: [
                            {
                                name: 'Randoop',
                                type: 'line',
                                label: {
                                    normal: {
                                        show: true,
                                        position: 'top'
                                    }
                                },
                                data: randoopScoreList
                            },
                            {
                                name: 'Evosuite',
                                type: 'line',
                                label: {
                                    normal: {
                                        show: true,
                                        position: 'top'
                                    }
                                },
                                data: evosuiteScoreList
                            },
                            {
                                name: 'human1',
                                type: 'line',
                                label: {
                                    normal: {
                                        show: true,
                                        position: 'top'
                                    }
                                },
                                data: []
                            },
                            {
                                name: 'human2',
                                type: 'line',
                                label: {
                                    normal: {
                                        show: true,
                                        position: 'top'
                                    }
                                },
                                data: []
                            },
                            {
                                name: 'human3',
                                type: 'line',
                                label: {
                                    normal: {
                                        show: true,
                                        position: 'top'
                                    }
                                },
                                data: []
                            }
                        ]
                    };
                    chart.setOption(option);
                }
            })
        }
    }
}

function getTotalTime(time){
    var totalTime = 0;
    while(time>=1){
        totalTime+=time;
        time--;
    }
    totalTime+=1;
    return totalTime;
}

