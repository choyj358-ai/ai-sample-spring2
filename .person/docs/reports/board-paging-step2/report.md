# 🚩 작업 보고서: 게시글 페이징 Step 2 (SQL LIMIT/OFFSET)

- **작업 일시**: 2026-03-20
- **진행 단계**: 완료

## 1. 🌊 전체 작업 흐름 (Workflow)

1. **Repository 계층**: SQL의 `LIMIT`와 `OFFSET`을 직접 사용하는 네이티브 쿼리(`findNativeAll`)를 `BoardRepository`에 추가했습니다.
2. **Service 계층**: `게시글목록보기` 메서드가 `page` 번호를 인자로 받도록 수정하고, 한 페이지당 3개씩(`limit=3`) 가져오도록 `offset`을 계산하여 Repository를 호출하게 했습니다.
3. **Controller 계층**: 클라이언트로부터 `page` 쿼리 파라미터를 입력받고(기본값 0), `gemini.md` 규칙에 따라 조회된 데이터를 `models`라는 이름으로 Model에 담았습니다.
4. **View 계층**: `list.mustache`에서 기존 `boardList` 대신 `models`를 사용하여 데이터를 출력하도록 수정했습니다.
5. **문서 업데이트**: `todo.md`의 Step 2 항목을 완료 처리했습니다.

## 2. 🧩 변경된 모든 코드 포함

### 1) BoardRepository.java (Native Query 추가)
```java
public interface BoardRepository extends JpaRepository<Board, Integer> {
    // ... 기존 코드

    // nativeQuery = true를 통해 실제 DB SQL을 그대로 사용합니다.
    // :limit과 :offset은 메서드의 파라미터와 매핑됩니다.
    @Query(value = "SELECT * FROM board_tb b INNER JOIN user_tb u ON b.user_id = u.id ORDER BY b.id DESC LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Board> findNativeAll(int limit, int offset);
}
```

### 2) BoardService.java (페이징 로직 구현)
```java
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public List<BoardResponse.ListDTO> 게시글목록보기(int page) {
        int limit = 3; // 한 페이지에 보여줄 게시글 수
        int offset = page * limit; // 건너뛸 게시글 수 계산
        
        // 직접 계산한 limit과 offset을 넘겨 필요한 데이터만 가져옵니다.
        List<Board> boardList = boardRepository.findNativeAll(limit, offset);
        
        return boardList.stream()
                .map(BoardResponse.ListDTO::new)
                .collect(Collectors.toList());
    }
}
```

### 3) BoardController.java (파라미터 수신 및 데이터 전달)
```java
@GetMapping("/board/list")
public String list(Model model, Integer page) {
    // page 파라미터가 없으면 0번째 페이지부터 시작합니다.
    if (page == null) {
        page = 0;
    }
    List<BoardResponse.ListDTO> boardList = boardService.게시글목록보기(page);
    
    // RULE: gemini.md 규칙 B에 따라 컬렉션 데이터는 "models"라는 키로 담습니다.
    model.addAttribute("models", boardList);
    return "board/list";
}
```

### 4) list.mustache (템플릿 변수명 수정)
```html
<tbody>
    {{#models}} <!-- boardList에서 models로 변경 -->
    <tr>
        <td>{{id}}</td>
        <td>{{title}}</td>
        <td>{{content}}</td>
        <td>{{username}}</td>
    </tr>
    {{/models}}
</tbody>
```

## 3. 🍦 상세비유 쉬운 예시 (Easy Analogy)

"이번 작업은 **'두꺼운 책장에서 필요한 페이지만 딱 집어서 가져오기'**와 같습니다."

처음에는 책장에 있는 모든 책(게시글)을 한꺼번에 바닥에 쏟아놓고 봤다면(Step 1), 이제는 **'한 번에 3권씩만(LIMIT 3)', '앞에서 몇 권을 건너뛰고(OFFSET)'** 가져오기로 약속한 것입니다. 예를 들어 2페이지를 보고 싶다면, 앞의 6권을 건너뛰고 그 다음 3권을 가져오는 식이죠. 이렇게 하면 책장이 아무리 무거워도 우리는 항상 가볍게 필요한 책만 골라 볼 수 있습니다.

## 4. 📚 기술 딥다이브 (Technical Deep-dive)

- **LIMIT**: 결과로 가져올 행(row)의 최대 개수를 지정합니다. 우리 프로젝트에서는 한 페이지에 3개씩 보여주기로 설정했습니다.
- **OFFSET**: 결과에서 몇 번째 행부터 가져올지를 결정합니다. `page * limit` 수식을 통해 현재 페이지에 맞는 시작 위치를 계산합니다.
- **Native Query**: JPA의 추상화된 쿼리(JPQL) 대신 실제 데이터베이스가 이해하는 SQL을 직접 작성하는 방식입니다. 특정 DB의 기능을 백분 활용하거나 쿼리 튜닝이 필요할 때 유용합니다.
- **Model Attribute Naming**: 프로젝트 규칙(`gemini.md`)에 따라 단일 오브젝트는 `model`, 컬렉션(리스트 등)은 `models`라는 이름으로 뷰에 전달하여 일관성을 유지합니다.
