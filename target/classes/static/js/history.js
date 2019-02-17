function loadHistoryTask() {
    $.ajax({
        method: "GET",
        url: "/api/history",
        contentType: "application/json;charset=utf8",
        dataType: "json",
        success: function (data) {
            for (var i = 0; i < data.length; i++) {
                $("#history-task-group-table").append("<tr><td>历史任务组" + (i + 1) +
                    "</td><td><button class=\"button-blue\" onclick=\"showHistoryTask(" + i + ")\">查&nbsp;&nbsp;看</button>" +
                    "<button class=\"button-red\" onclick=\"deleteHistoryTask(" + i + ")\">删&nbsp;&nbsp;除</button></td></tr>");
                localStorage.setItem(String(i), data[i]);
            }
        }
    });
}

function deleteHistoryTask(i) {
    $.ajax({
        method: "POST",
        url: "/api/deleteHistory",
        contentType: "application/json;charset=utf8",
        data: localStorage.getItem(String(i)),
        dataType:
            "json",
        success:
            function () {
                console.log(localStorage.getItem(String(i)));
                location.reload();
            }
    })
    ;
}

function showHistoryTask(i) {
    var taskGroup = localStorage.getItem(String(i));
    $("#history-task-table-wrapper").empty();
    $("#history-task-table-wrapper").append("<table id=\"history-task-table\">\n" +
        "            <tr><th>ID</th>\n" +
        "            <th>subject</th>\n" +
        "            <th>tool</th>\n" +
        "            <th>time</th></tr>\n" +
        "            </table>")

    for (var j = 0; j < taskGroup.split(",").length / 4; j++) {
        $("#history-task-table").append("<tr><th>" + taskGroup.split(",")[j * 4] + "</th>\n" +
            "            <th>" + taskGroup.split(",")[j * 4 + 1] + "</th>\n" +
            "            <th>" + taskGroup.split(",")[j * 4 + 2] + "</th>\n" +
            "            <th>" + taskGroup.split(",")[j * 4 + 3] + "</th></tr>\n")
    }
}