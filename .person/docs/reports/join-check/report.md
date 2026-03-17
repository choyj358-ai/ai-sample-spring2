# 🚩 작업 보고서: T-2.1 Step 1: AJAX 아이디 중복 체크

- **작업 일시**: 2026-03-17
- **진행 단계**: 완료

## 1. 🌊 전체 작업 흐름 (Workflow)

1. **백엔드 서비스 구현**: `UserService`에 중복 확인 로직(`checkUsername`)을 추가하였습니다.
2. **API 엔드포인트 생성**: `UserApiController`에 중복 체크를 위한 REST API(`GET /api/user/username-same-check`)를 구현하였습니다.
3. **회원가입 폼 매핑**: `UserController`에 회원가입 페이지 이동을 위한 핸들러를 추가하였습니다.
4. **프론트엔드 비동기 통신 구현**: `join-form.mustache`를 생성하고, JavaScript의 `fetch` API를 사용하여 버튼 클릭 시 실시간으로 아이디 중복 여부를 확인하는 기능을 구현하였습니다.

## 2. 🧩 핵심 코드 (Core Logic)

### 백엔드 (UserApiController.java)
```java
@GetMapping("/api/user/username-same-check")
public Resp<?> usernameSameCheck(String username) {
    // 사용자가 입력한 username이 DB에 있는지 확인합니다.
    boolean isSame = userService.checkUsername(username);
    // 결과를 Resp 객체에 담아 200 OK와 함께 반환합니다.
    return Resp.ok(isSame);
}
```

### 프론트엔드 (join-form.mustache)
```javascript
async function checkUsername() {
    let username = document.querySelector("#username").value;
    
    // 서버에 비동기 요청을 보냅니다.
    let response = await fetch(`/api/user/username-same-check?username=${username}`);
    let responseBody = await response.json();

    let msgElement = document.querySelector("#username-msg");
    // 중복 여부에 따라 메시지와 스타일을 변경합니다.
    if (responseBody.body === true) {
        msgElement.innerText = "중복된 아이디입니다.";
        msgElement.classList.add("text-danger");
    } else {
        msgElement.innerText = "사용 가능한 아이디입니다.";
        msgElement.classList.add("text-success");
    }
}
```

## 3. 🍦 상세비유 쉬운 예시 (Easy Analogy)

"이번 작업은 **도서관 회원 카드 발급**과 같습니다. 마치 새로운 회원이 이름을 말했을 때, 사서가 장부를 뒤져보지 않고 **무전기(AJAX)**로 즉시 본부에 물어봐서 사용 가능한 이름인지 확인해주는 것과 비슷해요! 페이지 전체를 새로고침(장부를 처음부터 다시 다 꺼내는 일)하지 않고도 필요한 정보만 쏙쏙 확인하는 방식입니다."

## 4. 📚 기술 딥다이브 (Technical Deep-dive)

- **AJAX (Asynchronous JavaScript and XML)**: 페이지 전체를 다시 불러오지 않고도 서버와 데이터를 주고받는 기술입니다. 여기서는 `fetch` API를 사용하여 구현되었습니다.
- **REST API**: 클라이언트와 서버가 데이터를 주고받기 위한 약속된 방식입니다. 이번 작업에서는 특정 URL을 통해 중복 여부(JSON 데이터)를 응답받았습니다.
- **Resp<T>**: 서버의 응답을 표준화된 형식(상태 코드, 메시지, 바디)으로 감싸서 보내는 공통 응답 객체입니다.
