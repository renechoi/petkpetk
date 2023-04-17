function checkSameWithPass(){
    var password = document.getElementById("password");
    var re_password = document.getElementById("re-password");
    var check_password = document.getElementById("check-password");


    if (re_password.value.length == 0) {
        check_password.innerHTML = "";
    }else{

        if (password.value === re_password.value) {
            check_password.innerHTML = "";
        }else{
            check_password.innerHTML = "비밀번호가 일치하지 않습니다.<br>Password does not match.";
        }

    }

}
