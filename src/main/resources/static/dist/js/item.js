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
    const title = $("#title");
    const price = $("#price");
    const contents = $("#contents");
    const imageFile = $("#imageFile");

    /* 항목별 유효성 체크를 위한 변수 */
    let passTitle = false;
    let passPrice = false;
    let passContents = false;
    let passImage = false;

    /* categoryId 저장 */
    const categoryId = $("#categoryId").val();

    /* 상품명 유효성 검사 */
    title.blur(() => {
        const inputTitle = $("#title").val();
        const message = $("#titleCheckInputBox");

        if(inputTitle !== "") {
            message.html("");
            passTitle = true;
        }

        else {
            message.html("상품명은 필수 항목입니다.");
            passTitle = false;
        }
    });

    /* 가격 유효성 검사 */
    price.blur(() => {
        const inputPrice = $("#price").val();
        const message = $("#priceCheckInputBox");

        if(inputPrice !== "") {
            if(priceRegex(inputPrice) === true){
                message.html("");
                passPrice = true;
            }

            else {
                message.html("가격은 숫자로 입력하세요.");
                passPrice = false;
            }
        }

        else {
            message.html("가격은 필수 항목입니다.");
            passPrice = false;
        }
    });

    // 가격 숫자 유효성 검사
    function priceRegex(inputPrice) {
        const form = /^[0-9]$/;

        // 정규표현식 조건에 적합하면 true, 아니면 false 반환
        return form.test(inputPrice);
    }

    /* 상품 설명 유효성 검사 */
    contents.blur(() => {
        const inputContents = $("#contents").val();
        const message = $("#contentsCheckInputBox");

        if(inputContents !== "") {
            message.html("");
            passContents = true;
        }

        else {
            message.html("상세 설명은 필수 항목입니다.");
            passContents = false;
        }
    });

    imageFile.blur(() => {
        const inputImage = $("#imageFile").val();
        const message = $("#imageCheckInputBox");

        if(inputImage !== "") {
            message.html("");
            passImage = true;
        }

        else {
            message.html("사진은 필수 항목입니다.");
            passImage = false;
        }
    });

    $("#itemForm").submit(function(event) {
       event.preventDefault();

       if(passTitle && passPrice && passContents && passImage) {
           $.ajax({
               type: "post",
               url: "/saveItem",
               data: JSON.stringify({
                   categoryId: $("#categoryId").val(),
                   nickname: $("#nickname").val(),
                   imageUrl: $("#imageFile").val(),
                   title: $("#title").val(),
                   price: $("#price").val(),
                   contents: $("#contents").val()
               }),
               contentType: "application/json",
               success: function(response) {
                   if(response) {
                       alert("등록되었습니다.")
                       if(categoryId === 1) {
                           window.location.href = "/ad";
                       }
                   }

                   else {
                       alert("등록에 실패했습니다. 다시 시도해 주세요.");
                   }
               }
           });
       }
    });
});