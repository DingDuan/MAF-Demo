count = 0;

function addRow() {
    // console.log($("#setting-exam").val());
    // console.log($("#setting-subject").val());

    if ($("#setting-exam").val() != null && $("#setting-subject").val() != null && $("#setting-contestant").val() != null) {

        // if ($("#setting-time").val() <= 7200) {
            count++;

            $.ajax({
                method: "GET",
                url: "/api/id",
                contentType: "application/x-www-form-urlencoded",
                data: {},
                dataType: "json",
                success: function (data) {
                    $("#setting-table").append("<tr id=\"setting-table-tr-" + count + "\">\n" +
                        "                    <td id=\"setting-id-" + count + "\">" + count + "</td>\n" +
                        "                    <td id=\"setting-exam-" + count + "\">" + $("#setting-exam").val() + "</td>\n" +
                        "                    <td id=\"setting-subject-" + count + "\">" + $("#setting-subject").val() + "</td>\n" +
                        "                    <td id=\"setting-contestant-" + count + "\">" + $("#setting-contestant").val() + "</td>\n" +
                        "                    <td><button class=\"button-red\" id=\"delete-button" + count + "\" onclick=\"deleteRow()\">删除</button></td>\n" +
                        "                    <td id=\"setting-choose-button-" + count + "\"><button class=\"button-blue\" id=\"choose-button" + count + "\" onclick=\"choose()\">选入</button></td>\n" +
                        "                </tr>");
                    $("#setting-exam").val("");
                    $("#setting-subject").val("");
                    $("#setting-contestant").val("");
                    $("#setting-tip").text("");
                }
            });
        // } else {
        //     $("#setting-tip").text("time_budget必须不能超过7200秒");
        // }
    } else {
        $("#setting-tip").text("请将信息选择完整");
    }
}

function deleteRow() {
    var current = event.target.id.substr(13);
    $("#setting-table-tr-" + current).remove();
}

function choose() {
    var current = event.target.id.substr(13);
    $("#task-table").append("<tr id=\"task-table-tr-" + current + "\">\n" +
        "                    <td><input id=\"task-table-choose-" + current + "\"type='checkbox' /></td>\n" +
        "                    <td>" + $("#setting-id-" + current).text() + "</td>\n" +
        "                    <td>" + $("#setting-subject-" + current).text() + "</td>\n" +
        "                    <td>" + $("#setting-tool-" + current).text() + "</td>\n" +
        "                    <td>" + $("#setting-time-" + current).text() + "</td>\n" +
        "                </tr>");
    $("#choose-button" + current).hide();
}

function emptyTask() {
    $("#task-table").empty();
    $("#task-table").append("<tr>\n" +
        "                    <th></th>\n" +
        "                    <th>ID</th>\n" +
        "                    <th>subject</th>\n" +
        "                    <th>tool</th>\n" +
        "                    <th>time</th>\n" +
        "                </tr>");

    for (var i = 1; i <= count; i++) {
        console.log($("#setting-choose-button-" + i).children().length);
        $("#choose-button" + i).show();
    }
}

function removeTask() {
    for (var i = 1; i <= count; i++) {
        console.log($("#task-table-choose-" + i).prop("checked"))
        if ($("#task-table-choose-" + i).prop("checked")) {
            $("#task-table-tr-" + i).remove();
            $("#choose-button" + i).show();
        }
    }
}