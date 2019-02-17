window.setInterval(getReport, 10000);

function getReport() {
    $.ajax({
        type: "GET",
        url: "/api/result",
        contentType: "application/x-www-form-urlencoded",
        data: {},
        dataType: "json",
        success: function (data) {
            $("#bottom-table").empty();
            $("#bottom-table").append("<tr>\n" +
                "                <th>ID</th>\n" +
                "                <th>subject</th>\n" +
                "                <th>tool</th>\n" +
                "                <th>time(s)</th>\n" +
                "                <th>coverage_score</th>\n" +
                "                <th>mutation_score</th>\n" +
                "                <th>total_score</th>\n" +
                "            </tr>");
            for (var i = 0; i < data.length; i++) {
                $("#bottom-table").append("<tr>\n" +
                    "                <th>" + data[i].id + "</th>\n" +
                    "                <th>" + data[i].subject + "</th>\n" +
                    "                <th>" + data[i].tool + "</th>\n" +
                    "                <th>" + data[i].time_budget + "</th>\n" +
                    "                <th>" + Math.round(data[i].bc) + "</th>\n" +
                    "                <th>" + Math.round(data[i].mc) + "</th>\n" +
                    "                <th>" + Math.round(data[i].total) + "</th>\n" +
                    "            </tr>"
                );
            }
        }
    })
}

function chooseTargetPath() {
    $("#excel-button").fadeOut(function () {
        $("#confirm-button").fadeIn(function () {
            $("#excel-path").fadeIn();
            $("#excel-path-input").fadeIn();
        });
    });

}

function excelGenerate() {
    console.log($("#excel-path-input").text())

    $.ajax({
        type: "GET",
        url: "/api/report",
        contentType: "application/x-www-form-urlencoded",
        data: {
            "targetPath": $("#excel-path-input").val()
        },
        dataType: "json",
        success: function (data) {
            $("#excel-path").fadeOut();
            $("#excel-path-input").fadeOut(function () {
                $("#confirm-button").fadeOut(function () {
                    $("#excel-success-button").fadeIn().delay(1000).fadeOut(function () {
                        $("#excel-button").fadeIn();
                    })
                });
            });
        }
    })
}

