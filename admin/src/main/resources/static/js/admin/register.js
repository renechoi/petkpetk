$(document).ready(function() {

    // name 필드가 비어 있는지 체크하는 함수
    function validateName() {
        if ($('#name-input').val() === '') {
            disableSubmitButton();
        } else {
            enableSubmitButton();
        }
    }

    // 비밀번호와 확인 비밀번호가 일치하는지 검증하는 함수
    function validatePassword() {
        if ($('#password').val() === $('#password-repeat').val()) {
            $('#password-identification').html('일치합니다.').css('color', 'green');
            enableSubmitButton();
        } else {
            $('#password-identification').html('일치하지 않습니다.').css('color', 'red');
            disableSubmitButton(); // 패스워드 일치하지 않을 경우에도 submit 버튼 비활성화
        }
    }

    // 중복 이메일 검증을 실행하는 함수
    function checkEmail() {
        let email = $('input[name="email"]').val();
        let csrfToken = $('meta[name="_csrf"]').attr('content');
        let csrfHeader = $('meta[name="_csrf_header"]').attr('content');
        let headers = {};
        headers[csrfHeader] = csrfToken;
        $.ajax({
            url: '/check-email',
            type: 'POST',
            headers: headers,
            data: {'email': email},
            success: function(data) {
                if (data) {
                    $('#email-check-msg').html('사용 가능한 Email입니다.').css('color', 'green');
                    enableSubmitButton();
                } else {
                    $('#email-check-msg').html('이미 사용 중인 Email입니다.').css('color', 'red');
                    enableSubmitButton();
                }
            },
            error: function() {
                alert('서버 오류로 인해 Email 중복 검사에 실패했습니다.');
                enableSubmitButton();
            }
        });
    }

    // submit 버튼을 활성화하는 함수
    function enableSubmitButton() {
        if ($('#password-identification').html() === '일치합니다.'
            && $('#email-check-msg').html() === '사용 가능한 Email입니다.'
            && $('#name-input').val() !== ''
        ) {
            $('button[type="submit"]').prop('disabled', false);
        }
    }

    // submit 버튼을 비활성화하는 함수
    function disableSubmitButton() {
        $('button[type="submit"]').prop('disabled', true);
    }

    $('#name-input').on('keyup', function () {
        validateName();
        enableSubmitButton();
    });

    $('#password-repeat').on('keyup', function () {
        validatePassword();
        enableSubmitButton();
    });

    $('#check-email-btn').on('click', function() {
        $('button[type="submit"]').prop('disabled', true);
        checkEmail();
    });

    $('input[name="email"]').on('input', function() {
        $('#email-check-msg').text('');
        disableSubmitButton();
    });

});





