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
    const summernote = $("#summernote");
    const itemForm = $("#itemForm");

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
        const form = /^[0-9]+$/;

        // 정규표현식 조건에 적합하면 true, 아니면 false 반환
        return form.test(inputPrice);
    }

    /* summernote 적용 */
    summernote.summernote({
        height: 500,
        // 에디터 한글 설정
        lang: "ko-KR",
        focus : false,
        toolbar: [
            // 글꼴 설정
            ['fontname', ['fontname']],
            // 글자 크기 설정
            ['fontsize', ['fontsize']],
            // 굵기, 기울임꼴, 밑줄,취소 선, 서식지우기
            ['style', ['bold', 'italic', 'underline','strikethrough', 'clear']],
            // 글자색
            ['color', ['forecolor','color']],
            // 글머리 기호, 번호매기기, 문단정렬
            ['para', ['ul', 'ol', 'paragraph']],
            // 줄간격
            ['height', ['height']],
            // 이미지 첨부
            ['insert',['picture']]
        ],
        // 추가한 글꼴
        fontNames: ['Arial', 'Arial Black', 'Comic Sans MS', 'Courier New','맑은 고딕','궁서','굴림체','굴림','돋음체','바탕체'],
        // 추가한 폰트사이즈
        fontSizes: ['8','9','10','11','12','14','16','18','20','22','24','28','30','36','50','72']
    });

    /* 상품 설명 유효성 검사 */
    summernote.blur(() => {
        let inputContents = summernote.summernote("code");
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

    /* 작성한 form 제출 */
    itemForm.submit(function(event) {
        event.preventDefault();
        const message = $("#contentsCheckInputBox");

        // Summernote 내용 가져오기
        let contents = summernote.summernote("code");

        if(contents !== "") {
            message.html("");
            passContents = true;
        }

        else {
            message.html("상세 설명은 필수 항목입니다.");
            passContents = false;
            return false;
        }

        if(passTitle && passPrice && passContents && passImage) {
            let formData = new FormData(this); // FormData를 사용하여 파일 데이터 포함
            formData.set("contents", contents);

            $.ajax({
                type: "post",
                url: "/saveItem",
                data: formData,
                contentType: false,
                processData: false,
                success: function(response) {
                    if(response) {
                        alert("등록되었습니다.");
                        window.location.href = "/ad";
                    }

                    else {
                        alert("등록에 실패했습니다. 다시 시도해 주세요.");
                    }
                },
                error: function() {
                    alert("서버 오류로 등록에 실패했습니다.");
                }
            });
        }
    });
});