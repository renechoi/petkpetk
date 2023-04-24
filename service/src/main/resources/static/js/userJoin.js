function checkSameWithPass() {
    var password = document.getElementById("password");
    var re_password = document.getElementById("re-password");
    var check_password = document.getElementById("check-password");

    if (re_password.value.length == 0) {
        check_password.innerHTML = "";
    } else {

        if (password.value === re_password.value) {
            check_password.innerHTML = "";
        } else {
            check_password.innerHTML = "비밀번호가 일치하지 않습니다.<br>password does not match.";
        }

    }
}

function checkPhoneNumber() {
    var phoneNumber = document.getElementById("phoneNumber");

    if (phoneNumber.value.length > 12) {
        phoneNumber.value = phoneNumber.value.slice(0, 12);
    }

}

var errorMessage = document.getElementById("errorMessage");
function checkSpace(str, title, event) {  // todo : 소셜 로그인시 이미 공백을 포함하여 들어오는 경우가 있음
    // if(str.value.search(/\s/) !== -1) {
    //     errorMessage.innerHTML= title + "은/는 공백을 포함할 수 없습니다."
    //     errorMessage.scrollIntoView({ behavior: "smooth", block: "start" });
    //     str.focus();
    //     event.preventDefault();
    //     return false; // 스페이스가 있는 경우
    // }else{
    //     return true; // 스페이스 없는 경우
    // }
}

function checkNull(str,title , event){
    // if (str.value == null || str.value == "") {
    //     errorMessage.innerHTML = title + "을/를 입력해주세요.";
    //     errorMessage.scrollIntoView({behavior: "smooth", block: "start"});
    //     str.focus();
    //     event.preventDefault();
    //     return false;
    // } else {
    //     return true;
    // }

}

var JoinForm = document.getElementById("JoinForm");
JoinForm.addEventListener("submit", function (event) {
    var email = document.getElementById("email");
    var name = document.getElementById("name");
    var nickName = document.getElementById("nickName");
    var password = document.getElementById("password");
    var re_password = document.getElementById("re-password");
    var zipCode = document.getElementById("zipCode");
    var detailAddress = document.getElementById("detailAddress");

    checkSpace(email, "이메일", event);
    checkSpace(name, "이름", event);
    checkSpace(nickName, "닉네임", event);
    checkSpace(password, "비밀번호", event);

    checkNull(name,"이름", event);
    checkNull(nickName,"닉네임", event);
    checkNull(password,"비밀번호", event);
    checkNull(re_password,"비밀번호 확인란", event);
    checkNull(zipCode,"주소", event);
    checkNull(detailAddress,"상세 주소", event);

    if (password.value.length < 8 || password.value.length > 16) {
        errorMessage.innerHTML = "비밀번호는 8자 이상 16자 이하입니다.";
        errorMessage.scrollIntoView({behavior: "smooth", block: "start"});
        password.focus();
        event.preventDefault();
        return false;
    }


});

var profile;
$("#file").on('change',function(e){
    var file = e.target.files;
    var fileArr = Array.prototype.slice.call(file);
    fileArr.forEach(function (a) {
        profile = a;
        var reader = new FileReader();

        reader.onload = function (z) {
            $("#signupProfileImg").attr("src", z.target.result);
        };

        reader.readAsDataURL(a);

    });

    var sign_upProfileImg = document.getElementById("signupProfileImg");
    sign_upProfileImg.style.display = "block";

});



