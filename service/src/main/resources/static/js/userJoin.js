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

function checkPass(e) {
    var check_password = document.getElementById("check-password");
    var re_password = document.getElementById("re-password");

    if (e.value == 0) {
        check_password.innerHTML = "";
    } else if (e.value === re_password.value) {
        check_password.innerHTML = "";
    } else if (re_password.value == "" || re_password.value == null) {
        check_password.innerHTML = "";
    } else {
        check_password.innerHTML = "비밀번호가 일치하지 않습니다.<br>password does not match.";
    }
}

function checkPhoneNumber() {
    var phoneNumber = document.getElementById("phoneNumber");

    if (phoneNumber.value.length > 12) {
        phoneNumber.value = phoneNumber.value.slice(0, 12);
    }

}

var errorMessage = document.getElementById("errorMessage");

function checkSpace(str, title, event) {
    if(str.value.search(/\s/) !== -1) {
        errorMessage.innerHTML= title + "은/는 공백을 포함할 수 없습니다."
        errorMessage.scrollIntoView({ behavior: "smooth", block: "start" });
        str.focus();
        return false; // 스페이스가 있는 경우
    }else{
        return true; // 스페이스 없는 경우
    }
}

function checkNull(str, title, event) {
    if (str.value == null || str.value == "") {
        errorMessage.innerHTML = title + "을/를 입력해주세요.";
        errorMessage.scrollIntoView({behavior: "smooth", block: "start"});
        str.focus();
        return false;
    } else {
        return true;
    }

}

var JoinForm = document.getElementById("JoinForm");
JoinForm.addEventListener("submit", function (event) {
    event.preventDefault();

    var email = document.getElementById("email");
    var name = document.getElementById("name");
    var nickName = document.getElementById("nickName");
    var password = document.getElementById("password");
    var re_password = document.getElementById("re-password");
    var zipCode = document.getElementById("zipCode");
    var detailAddress = document.getElementById("detailAddress");

    checkSpace(email, "이메일", event);
    checkSpace(password, "비밀번호", event);

    checkNull(name, "이름", event);
    checkNull(nickName, "닉네임", event);
    checkNull(password, "비밀번호", event);
    checkNull(re_password, "비밀번호 확인란", event);
    checkNull(zipCode, "주소", event);
    checkNull(detailAddress, "상세 주소", event);

    if (password.value.length < 8 || password.value.length > 16) {
        errorMessage.innerHTML = "비밀번호는 8자 이상 16자 이하입니다.";
        errorMessage.scrollIntoView({behavior: "smooth", block: "start"});
        password.focus();
        return false;
    }

    if ($("#checkEmail").val() == "") {
        alert("이메일을 확인해주세요.");
        email.scrollIntoView({behavior: "smooth", block: "start"});
        return false;
    }

    if ($("#checkNickName").val() == "") {
        alert("닉네임을 확인해주세요.");
        nickName.scrollIntoView({behavior: "smooth", block: "start"});
        return false;
    }

    JoinForm.submit();

});


$("#email").on("keyup", function () {
    var checkEmailTxt = document.getElementById("checkEmailTxt");
    checkEmailTxt.textContent = "";
    $("#checkEmail").val("");
});
$("#nickName").on("keyup", function () {
    var checkNickTxt = document.getElementById("checkNickTxt");
    checkNickTxt.textContent = "";
    $("#checkNickName").val("");
});


function checkNewEmail() {
    if ($("#email").val().indexOf("@") === -1) {
        alert("이메일 형식으로 입력해주세요.");
        $("#checkEmail").val("");
        return false;
    }

    var checkEmailTxt = document.getElementById("checkEmailTxt");
    $.ajax({
        url: "/api/checkEmail",
        type: "post",
        data: {
            email: $("#email").val()
        },
        dataType: "json",
        success: function (sameEmail) {
            console.log(sameEmail);
            if (sameEmail == true) {
                checkEmailTxt.textContent = "중복 된 이메일입니다.";
                checkEmailTxt.style.color = "#ff3b57";
                $("#checkEmail").val("");
            } else {
                checkEmailTxt.textContent = "사용 가능한 이메일입니다.";
                checkEmailTxt.style.color = "#9a9f73";
                $("#checkEmail").val(1);
            }
        }

    })
}

function checkNick() {
    var checkNickTxt = document.getElementById("checkNickTxt");
    $.ajax({
        url: "/api/checkNickName",
        type: "post",
        data: {
            nickName: $("#nickName").val(),
        },
        dataType: "json",
        success: function (sameNick) {
            if (sameNick == true) {
                checkNickTxt.textContent = "중복 된 닉네임입니다.";
                checkNickTxt.style.color = "#ff3b57";
                $("#checkNickName").val("");
            } else {
                checkNickTxt.textContent = "사용 가능한 닉네임입니다.";
                checkNickTxt.style.color = "#9a9f73";
                $("#checkNickName").val(1);
            }
        }

    })
}

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

function togglePass() {
    var PassToggle = document.getElementById("PassToggle");
    var newPassword = document.getElementById("password");

    if (newPassword.getAttribute("type") == "password") {
        newPassword.setAttribute("type", "text");
        PassToggle.setAttribute("src", "/images/passwordHide.png")
    } else {
        newPassword.setAttribute("type", "password");
        PassToggle.setAttribute("src", "/images/passwordShow.png")
    }
}


function toggleRePass() {
    var rePassToggle = document.getElementById("rePassToggle");
    var reNewPassword = document.getElementById("re-password");

    if (reNewPassword.getAttribute("type") == "password") {
        reNewPassword.setAttribute("type", "text");
        rePassToggle.setAttribute("src", "/images/passwordHide.png")
    } else {
        reNewPassword.setAttribute("type", "password");
        rePassToggle.setAttribute("src", "/images/passwordShow.png")
    }
}