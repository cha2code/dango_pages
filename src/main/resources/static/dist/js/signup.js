$("document").ready(() => {
    // csrf 토큰 저장 변수
    const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");

    // ajax post 통신을 위해 header에 csrf 토큰 삽입 후 전송
    $(function() {
        $(document).ajaxSend(function(e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });
    });

    /* DOM Selector 호출 결과를 저장하는 변수 */
    const userId = $("#userId");
    const userPw = $("#password");
    const userNick = $("#nickname");
    const checkBtn = $("#checkBtn");
    const mailCheck = $("#mailCheck");
    const joinBtn = $("#joinBtn");

    /* 항목별 유효성 체크를 위한 변수 */
    let passId = false;
    let passPw = false;
    let passNick = false;
    let passMail = false;

    /* 아이디 중복 및 유효성 검사 */
    userId.blur(() => {
        const inputId = $("#userId").val(); // 입력 받은 아이디
        const message = $("#idCheckInputBox"); // 아이디 적합 유무 메세지

        // 아이디 유효성 검사 결과가 false 일 때
        if(idRegex(inputId) === false) {
            message.html("아이디는 6~20자의 영문 소문자, 숫자만 가능합니다.");
            passId = false;
        }

        else {
            $.ajax({
                type: "post",
                url: "/inputId",
                // JSON으로 변환 후 객체로 만들어서 전달
                data: JSON.stringify({
                    "userId" : inputId
                }),
                contentType : "application/json",
                success: function (data){
                    if(data) {
                        message.html("사용 가능한 아이디입니다.");
                        passId = true;
                    }

                    else {
                        message.html("중복된 아이디가 있습니다.");
                        passId = false;
                    }
                }
            });
        }
    });

    // 아이디 형식 검사
    function idRegex(inputId) {
        const form = /^[a-z0-9_\-]{6,20}$/;

        // 정규표현식 조건에 적합하면 true, 아니면 false 반환
        return form.test(inputId);
    }

    /* 비밀번호 유효성 검사 */
    userPw.blur(() => {
        const inputPw = $("#password").val(); // 입력 받은 비밀번호
        const message = $("#pwCheckInputBox"); // 비밀번호 적합 유무 메세지

        // 비밀번호 유효성 검사 결과가 true 일 때
        if(pwRegex(inputPw)) {
            message.html("사용 가능한 비밀번호입니다.");
            passPw = true;
        }

        else {
            message.html("비밀번호는 8자리 이상의 영문+숫자+특수문자(@$!%*#?&)로 입력하세요.");
            passPw = false;
        }
    });

    // 비밀번호 형식 검사
    function pwRegex(inputPw) {
        const form = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$/;

        // 정규표현식 조건에 적합하면 true, 아니면 false 반환
        return form.test(inputPw);
    }

    /* 닉네임 중복 및 유효성 검사 */
    userNick.blur(() => {
        const inputNick = $("#nickname").val(); // 입력 받은 닉네임
        const message = $("#nickCheckInputBox"); // 닉네임 적합 유무 메세지

        // 닉네임 유효성 검사 결과가 false 일 때
        if(nickRegex(inputNick) === false) {
            message.html("닉네임은 2~6자의 영문, 숫자, 한글만 가능합니다.");
            passNick = false;
        }

        else {
            $.ajax({
                type: "post",
                url: "/inputNick",
                // JSON으로 변환 후 객체로 만들어서 전달
                data: JSON.stringify({
                    "nickname" : inputNick
                }),
                contentType : "application/json",
                success: function (data){
                    if(data) {
                        message.html("사용 가능한 닉네임입니다.");
                        passNick = true;
                    }

                    else {
                        message.html("중복된 닉네임이 있습니다.");
                        passNick = false;
                    }
                }
            });
        }
    });

    // 닉네임 형식 검사
    function nickRegex(inputNick) {
        const form = /^[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9|]{2,6}$/;

        // 정규표현식 조건에 적합하면 true, 아니면 false 반환
        return form.test(inputNick);
    }

    var checkCode = ""; // 생성된 인증 코드

    /* 이메일 유효성 검사 및 인증 */
    checkBtn.click(() => {
        const inputEmail = $("#email").val(); // 입력 받은 이메일
        const mailMessage = $("#mailCheckInputBox"); // 이메일 적합 유무 메세지

        // 이메일 유효성 검사 결과가 false 일 때
        if(mailRegex(inputEmail) === false) {
            mailMessage.html("이메일 형식으로 입력하세요.");
            passMail = false;
        }

        else {
            $.ajax({
                type: "post",
                url: "/inputEmail",
                // JSON으로 변환 후 객체로 만들어서 전달
                data: JSON.stringify({
                    "email" : inputEmail
                }),
                contentType : "application/json",
                success: function (data){
                    if(data === "false") {
                        mailMessage.html("중복된 이메일이 있습니다.");
                        passMail = false;
                    }

                    else {
                        mailMessage.html("인증 코드가 전송되었습니다.");
                        checkCode = data;
                    }
                }
            });
        }
    });

    /* 인증 코드 비교 */
    mailCheck.blur(() => {
        const inputCode = $("#mailCheck").val(); // 입력 받은 인증 코드
        const codeMessage = $("#codeCheckInputBox"); // 인증 코드 일치 유무 메세지

        // 발송된 코드와 입력 받은 코드 비교
        if(checkCode === inputCode) {
            codeMessage.html("인증 번호가 일치합니다.");
            passMail = true;
        }

        else {
            codeMessage.html("인증 번호를 확인해주세요.");
            passMail = false;
        }
    });

    // 이메일 형식 검사
    function mailRegex(inputEmail) {
        const form = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;

        // 정규표현식 조건에 적합하면 true, 아니면 false 반환
        return form.test(inputEmail);
    }

    /* form 제출 이벤트 */
    $("#registrationForm").submit(function(event) {
        // form 제출 시 재실행을 막아 입력 내용이 사라지지 않음
        event.preventDefault();

        // 모든 필드의 유효성 검사 통과 시
        if(passId && passPw && passNick && passMail) {
            $.ajax({
                type: "post",
                url: "/createUser",
                data: JSON.stringify({
                    userId: $("#userId").val(),
                    userPassword: $("#password").val(),
                    nickname: $("#nickname").val(),
                    email: $("#email").val()
                }),
                contentType: "application/json",
                success: function(response) {
                    if(response) {
                        alert("회원 가입이 완료되었습니다.");
                        // form 제출 후 로그인 페이지로 redirect
                        window.location.href = "/login";
                    }

                    else {
                        alert("가입에 실패했습니다. 다시 시도해 주세요.");
                    }
                },
                error: function(xhr, status, error) {
                    console.log("Error registering user:", xhr.responseText);
                }
            });
        }
    });
});