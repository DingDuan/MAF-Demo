function clickAll(){
    // if (this.checked) {
    console.log("dafsasd");
    $("input[name='selectFlag']:checkbox").each(function () { //遍历所有的name为selectFlag的 checkbox
        $(this).attr("checked", true);
    })
    // }
}

function check() {
    // if($("#inside").css("display")=="none"){
    //     setTimeout( function(){
    //         // $("#inside").show();
    //         window.open("result.html");
    //     }, 1 * 1000 )

    // }

}

function datalog() {
    var project = document.getElementById("correspond1");
    project.innerHTML = "";

    var trNode = project.insertRow();
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "1";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "Argument";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "8";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "<button class=\"button-blue\" id=\"Argument\" style=\"font-size: 20px;\" onclick=\"{location.href='code/Datalog/Argument.html'}\">detail</button>";

    var trNode = project.insertRow();
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "2";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "Datalog";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "8";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "<button class=\"button-blue\" id=\"Argument\" style=\"font-size: 20px;\" onclick=\"{location.href='code/Datalog/Datalog.html'}\">detail</button>";

    var trNode = project.insertRow();
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "3";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "Fact";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "6";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "<button class=\"button-blue\" id=\"Argument\" style=\"font-size: 20px;\" onclick=\"{location.href='code/Datalog/Fact.html'}\">detail</button>";

    var trNode = project.insertRow();
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "4";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "Predicate";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "5";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "<button class=\"button-blue\" id=\"Argument\" style=\"font-size: 20px;\" onclick=\"{location.href='code/Datalog/Predicate.html'}\">detail</button>";

    var trNode = project.insertRow();
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "5";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "Program";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "6";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "<button class=\"button-blue\" id=\"Argument\" style=\"font-size: 20px;\" onclick=\"{location.href='code/Datalog/Program.html'}\">detail</button>";

    var trNode = project.insertRow();
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "6";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "Rule";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "6";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "<button class=\"button-blue\" id=\"Argument\" style=\"font-size: 20px;\" onclick=\"{location.href='code/Datalog/Rule.html'}\">detail</button>";

    var trNode = project.insertRow();
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "7";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "Substitution";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "5";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "<button class=\"button-blue\" id=\"Argument\" style=\"font-size: 20px;\" onclick=\"{location.href='code/Datalog/Substituion.html'}\">detail</button>";


    var student = document.getElementById("correspond2");
    student.innerHTML = "";

    var trNode = student.insertRow();
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "<input name=\"selectFlag\" type=\"checkbox\">";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "1";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "90";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "<button class=\"button-blue\" id=\"Stu1\" style=\"font-size: 20px;\" onclick=\"{location.href='code/Datalog/Stu1.html'}\">detail</button>";

    var trNode = student.insertRow();
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "<input name=\"selectFlag\" type=\"checkbox\">";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "2";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "89";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "<button class=\"button-blue\" id=\"Stu1\" style=\"font-size: 20px;\" onclick=\"{location.href='code/Datalog/Stu2.html'}\">detail</button>";

    var trNode = student.insertRow();
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "<input name=\"selectFlag\" type=\"checkbox\">";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "3";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "84";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "<button class=\"button-blue\" id=\"Stu1\" style=\"font-size: 20px;\" onclick=\"{location.href='code/Datalog/Stu3.html'}\">detail</button>";

    var trNode = student.insertRow();
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "<input name=\"selectFlag\" type=\"checkbox\">";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "4";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "74";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "<button class=\"button-blue\" id=\"Stu1\" style=\"font-size: 20px;\" onclick=\"{location.href='code/Datalog/Stu4.html'}\">detail</button>";

    var trNode = student.insertRow();
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "<input name=\"selectFlag\" type=\"checkbox\">";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "5";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "75";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "<button class=\"button-blue\" id=\"Stu1\" style=\"font-size: 20px;\" onclick=\"{location.href='code/Datalog/Stu5.html'}\">detail</button>";

}

function alu() {
    var project = document.getElementById("correspond1");
    project.innerHTML = "";

    var trNode = project.insertRow();
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "1";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "ALU";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "18";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "<button class=\"button-blue\" id=\"Argument\" style=\"font-size: 20px;\" onclick=\"{location.href='code/ALU/ALU.html'}\">detail</button>";

    var trNode = project.insertRow();
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "2";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "Abstract";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "3";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "<button class=\"button-blue\" id=\"Argument\" style=\"font-size: 20px;\" onclick=\"{location.href='code/ALU/Abstract.html'}\">detail</button>";

    var trNode = project.insertRow();
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "3";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "Legend";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "8";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "<button class=\"button-blue\" id=\"Argument\" style=\"font-size: 20px;\" onclick=\"{location.href='code/ALU/Legend.html'}\">detail</button>";


    var student = document.getElementById("correspond2");
    student.innerHTML = "";

    var trNode = student.insertRow();
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "<input name=\"selectFlag\" type=\"checkbox\">";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "1";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "96";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "<button class=\"button-blue\" id=\"Stu1\" style=\"font-size: 20px;\" onclick=\"{location.href='code/ALU/Stu1.html'}\">detail</button>";

    var trNode = student.insertRow();
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "<input name=\"selectFlag\" type=\"checkbox\">";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "2";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "90";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "<button class=\"button-blue\" id=\"Stu1\" style=\"font-size: 20px;\" onclick=\"{location.href='code/ALU/Stu2.html'}\">detail</button>";

    var trNode = student.insertRow();
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "<input name=\"selectFlag\" type=\"checkbox\">";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "3";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "89";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "<button class=\"button-blue\" id=\"Stu1\" style=\"font-size: 20px;\" onclick=\"{location.href='code/ALU/Stu3.html'}\">detail</button>";

    var trNode = student.insertRow();
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "<input name=\"selectFlag\" type=\"checkbox\">";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "4";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "86";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "<button class=\"button-blue\" id=\"Stu1\" style=\"font-size: 20px;\" onclick=\"{location.href='code/ALU/Stu4.html'}\">detail</button>";

    var trNode = student.insertRow();
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "<input name=\"selectFlag\" type=\"checkbox\">";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "5";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "79";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "<button class=\"button-blue\" id=\"Stu1\" style=\"font-size: 20px;\" onclick=\"{location.href='code/ALU/Stu5.html'}\">detail</button>";

}

function rbt() {
    var project = document.getElementById("correspond1");
    project.innerHTML = "";
    var trNode = project.insertRow();
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "1";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "AbstractBinaryTree";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "26";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "<button class=\"button-blue\" id=\"Argument\" style=\"font-size: 20px;\" onclick=\"{location.href='code/RBT/AbstractBinaryTree.html'}\">detail</button></td>\n";

    var trNode = project.insertRow();
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "2";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "Node";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "4";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "<button class=\"button-blue\" id=\"Argument\" style=\"font-size: 20px;\" onclick=\"{location.href='code/RBT/Node.html'}\">detail</button></td>\n";

    var trNode = project.insertRow();
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "3";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "RedBlackTree";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "13";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "<button class=\"button-blue\" id=\"Argument\" style=\"font-size: 20px;\" onclick=\"{location.href='code/RBT/RedBlackTree.html'}\">detail</button></td>\n";

    var student = document.getElementById("correspond2");
    student.innerHTML = "";

    var trNode = student.insertRow();
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "<input name=\"selectFlag\" type=\"checkbox\">";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "1";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "93";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "<button class=\"button-blue\" id=\"Stu1\" style=\"font-size: 20px;\" onclick=\"{location.href='code/RBT/Stu1.html'}\">detail</button>";

    var trNode = student.insertRow();
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "<input name=\"selectFlag\" type=\"checkbox\">";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "2";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "89";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "<button class=\"button-blue\" id=\"Stu1\" style=\"font-size: 20px;\" onclick=\"{location.href='code/RBT/Stu2.html'}\">detail</button>";

    var trNode = student.insertRow();
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "<input name=\"selectFlag\" type=\"checkbox\">";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "3";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "87";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "<button class=\"button-blue\" id=\"Stu1\" style=\"font-size: 20px;\" onclick=\"{location.href='code/RBT/Stu3.html'}\">detail</button>";

    var trNode = student.insertRow();
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "<input name=\"selectFlag\" type=\"checkbox\">";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "4";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "83";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "<button class=\"button-blue\" id=\"Stu1\" style=\"font-size: 20px;\" onclick=\"{location.href='code/RBT/Stu4.html'}\">detail</button>";

    var trNode = student.insertRow();
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "<input name=\"selectFlag\" type=\"checkbox\">";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "5";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "78";
    var tdNode = trNode.insertCell();
    tdNode.innerHTML = "<button class=\"button-blue\" id=\"Stu1\" style=\"font-size: 20px;\" onclick=\"{location.href='code/RBT/Stu5.html'}\">detail</button>";


}