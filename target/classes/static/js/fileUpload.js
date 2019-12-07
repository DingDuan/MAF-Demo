function fileUploadSrc() {
    var fileEleSrc = document.getElementById("fileEleSrc");
    //console.log(fileEleSrc);
    fileEleSrc.click();
    // document.addEventListener('DOMContentLoaded', function () {
    var fileControl = document.querySelector('#fileEleSrc');
    var filesDiv = document.querySelector('#upfileSrc');

    fileControl.addEventListener('change', function (event) {
        // console.log(event.target.files);
         var files = event.target.files;
        // var fileList = [];
        // for (var i = 0, len = files.length; i < len; i++) {
        //     //console.log(files[i].webkitRelativePath.substring(0,3));
        //     //fileList.push(files[i].name);
        // }
        // // filesDiv.innerHTML = fileList.join('');
        // filesDiv.value=fileList.join(' ');
        filesDiv.value='/Users/dd/Desktop/MAF-Data/Datalog/'+files[0].webkitRelativePath.substring(0,3);
    });


}

function choose() {
    if($(".component").css("display")=="none"){
        setTimeout( function(){
            $(".component").show();
            // window.open("result.html");
        }, 0.5 * 1000 )

    }

}

function fileUploadOne() {
    var fileEleOne = document.getElementById("fileEleOne");
    //console.log(fileEleSrc);
    fileEleOne.click();
    // document.addEventListener('DOMContentLoaded', function () {
        var fileControl = document.querySelector('#fileEleOne');
        var filesDiv = document.querySelector('#upfileOne');

        fileControl.addEventListener('change', function (event) {
             var files = event.target.files;
            // var fileList = [];
            // for (var i = 0, len = files.length; i < len; i++) {
            //     // console.log("aa");
            //     fileList.push(files[i].name);
            // }
            // // filesDiv.innerHTML = fileList.join('');
            // filesDiv.value=fileList.join(' ');
            filesDiv.value='/Users/dd/Desktop/MAF-Data/Datalog/junit/'+files[0].webkitRelativePath.substring(0,1);
        });
    // });
}

function fileUploadTwo() {
    var fileEleTwo= document.getElementById("fileEleTwo");
    //console.log(fileEleSrc);
    fileEleTwo.click();
    // document.addEventListener('DOMContentLoaded', function () {
    var fileControl = document.querySelector('#fileEleTwo');
    var filesDiv = document.querySelector('#upfileTwo');

    fileControl.addEventListener('change', function (event) {
         var files = event.target.files;
        // var fileList = [];
        // for (var i = 0, len = files.length; i < len; i++) {
        //     // console.log("aa");
        //     fileList.push(files[i].name);
        // }
        // // filesDiv.innerHTML = fileList.join('');
        // filesDiv.value=fileList.join(' ');
        filesDiv.value='/Users/dd/Desktop/MAF-Data/Datalog/junit/'+files[0].webkitRelativePath.substring(0,1);

    });
    // });
}