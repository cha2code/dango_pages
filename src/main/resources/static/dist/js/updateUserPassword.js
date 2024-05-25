$("document").ready(() => {
    // csrf 토큰 저장 변수
    const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");

    // ajax post 통신을 위해 header에 csrf 토큰 삽입 후 전송
    $(function () {
        $(document).ajaxSend(function (e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });
    });

    /* DOM Selector 호출 결과를 저장하는 변수 */
    const existPw = $("#existPw");
    const updatePw = $("#updatePw");
    const checkPw = $("#checkPw");

    /* 항목별 유효성 체크를 위한 변수 */
    let existCheck = false;
    let updateCheck = false;
    let checkUpdate = false;

    /* 기존 비밀번호 일치 여부 */
    existPw.blur(() => {
        const existMessage = $("#existPwInputBox"); // 비밀번호 일치 유무 메세지

        $.ajax({
            type: "post",
            url: "/existPwCheck",
            // JSON으로 변환 후 객체로 만들어서 전달
            data: JSON.stringify({
                "userId": $("#userId").val(),
                "userPassword": $("#existPw").val()
            }),
            contentType: "application/json",
            success: function (response) {
                if (response) {
                    existMessage.html("기존 비밀번호와 일치합니다.");
                    existCheck = true;
                } else {
                    existMessage.html("기존 비밀번호를 확인해주세요.");
                    existCheck = false;
                }
            }
        });
    });

    /* 비밀번호 유효성 검사 */
    updatePw.blur(() => {
        const updateMessage = $("#updatePwInputBox") // 유효성 검사 결과 메세지
        const updateInputPw = $("#updatePw").val();

        // 비밀번호 유효성 검사 결과가 true 일 때
        if (pwRegex(updateInputPw)) {
            updateMessage.html("사용 가능한 비밀번호입니다.");
            updateCheck = true;
        } else {
            updateMessage.html("비밀번호는 8자리 이상의 영문+숫자+특수문자(@$!%*#?&)로 입력하세요.");
            updateCheck = false;
        }
    });

    // 비밀번호 형식 검사
    function pwRegex(inputPw) {
        const form = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$/;

        // 정규표현식 조건에 적합하면 true, 아니면 false 반환
        return form.test(inputPw);
    }

    /* 수정할 비밀번호 및 확인할 비밀번호 일치 여부 검사 */
    checkPw.blur(() => {
        let checkMessage = $("#checkPwInputBox") // 일치 여부 검사 결과

        if($("#updatePw").val() === $("#checkPw").val()) {
            checkMessage.html("비밀번호가 일치합니다.");
            checkUpdate = true;
        }

        else {
            checkMessage.html("비밀번호가 일치하지 않습니다.");
            checkUpdate = false;
        }
    });

    /* form 제출 이벤트 */
    $("#updateForm").submit(function (event) {
        // form 제출 시 재실행을 막아 입력 내용이 사라지지 않음
        event.preventDefault();

        // 입력 받은 값이 모두 정상일 경우 form 제출
        if (existCheck && updateCheck && checkUpdate) {
            $.ajax({
                type: "post",
                url: "/updateUserPassword",
                data: JSON.stringify({
                    userId: $("#userId").val(),
                    userPassword: $("#updatePw").val()
                }),
                contentType: "application/json",
                success: function (response) {
                    if (response) {
                        alert("비밀번호 수정이 완료되었습니다.");
                        // form 제출 후 회원 정보 페이지로 redirect
                        window.location.href = "/userInfo";
                    } else {
                        alert("비밀번호 수정에 실패했습니다. 다시 시도해 주세요.");
                    }
                },
                error: function (xhr, status, error) {
                    console.log("Error registering user:", xhr.responseText);
                }
            });
        }
    });
});