
$("#registerbtn").bind("click",function(){
    var a=document.getElementById("pane");
    a.style.transform="rotateY(180deg)";
});
$("#loginbtn").bind("click",function(){
    var a=document.getElementById("pane");
    a.style.transform="rotateY(360deg)";
});


var Vcode;
function createCode() {
    var code = "";
    $.ajax({
        type: "post",
        url: "",
        success: function (data) {
            var s = data.toString();
            console.log(s);
            Vcode = s;
            var c = document.getElementById("myCanvas");
            var ctx = c.getContext("2d");
            ctx.clearRect(0, 0, 1000, 1000);
            ctx.font = "80px 'Hiragino Sans GB'";
            ctx.fillStyle = "#45f3ff";
            ctx.fillText(s, 0, 100);
        }
    });

}


function checkcode(){
    var icode = $("#validate").val();
    if(icode.toLowerCase()!=Vcode.toLowerCase()){
        alert("The Validate Code ERROR!!!");
        createCode();
        return false;
    }else
        return true;
}

createCode();

$("canvas").bind("click",function(){
    createCode();
});