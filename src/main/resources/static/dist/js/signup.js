$('document').ready(() => {
    // csrf 토큰 저장 변수
    const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");

    // ajax post 통신을 위해 header에 csrf 토큰 삽입 후 전송
    $(function() {
        $(document).ajaxSend(function(e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });
    });

    /* 아이디 중복 및 유효성 검사 */
    $("#userId").blur(() => {
        const inputId = $("#userId").val(); // 입력 받은 아이디
        const message = $("#idCheckInputBox"); // 아이디 적합 유무 메세지

        // 아이디 유효성 검사 결과가 false 일 때
        if(idRegex(inputId) === false) {
            message.html("아이디는 6~20자의 영문 소문자, 숫자만 가능합니다.");
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
                    message.html(data ? "사용 가능한 아이디입니다." : "중복된 아이디가 있습니다.");
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
    $("#password").blur(() => {
        const inputPw = $("#password").val(); // 입력 받은 아이디
        const message = $("#pwCheckInputBox"); // 비밀번호 적합 유무 메세지

        // 비밀번호 유효성 검사 결과가 false 일 때
        if(pwRegex(inputPw)) {
            message.html("사용 가능한 비밀번호입니다.");
        }

        else {
            message.html("비밀번호는 8자리 이상의 영문+숫자+특수문자(@$!%*#?&)로 입력하세요.");
        }
    });

    // 비밀번호 형식 검사
    function pwRegex(inputPw) {
        const form = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$/;

        // 정규표현식 조건에 적합하면 true, 아니면 false 반환
        return form.test(inputPw);
    }

    /* 닉네임 중복 및 유효성 검사 */
    $("#nickname").blur(() => {
        const inputNick = $("#nickname").val(); // 입력 받은 닉네임
        const message = $("#nickCheckInputBox"); // 닉네임 적합 유무 메세지

        // 아이디 유효성 검사 결과가 false 일 때
        if(nickRegex(inputNick) === false) {
            message.html("닉네임은 2~6자의 영문, 숫자, 한글만 가능합니다.");
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
                    console.log(data);
                    message.html(data ? "사용 가능한 닉네임입니다." : "중복된 닉네임이 있습니다.");
                },
                error: function (data){
                    console.log(data);
                }
            });
        }
    });

    // 닉네임 형식 검사
    function nickRegex(inputNick) {
        const form = /^[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9|]{2,6}$/;

        console.log("form : ", form.test(inputNick))

        // 정규표현식 조건에 적합하면 true, 아니면 false 반환
        return form.test(inputNick);
    }
});