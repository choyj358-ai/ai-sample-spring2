# 🚩 작업 보고서: [Step 1] 데이터 준비 및 기본 목록 보기

- **작업 일시**: 2026-03-20
- **진행 단계**: 완료

## 1. 🌊 전체 작업 흐름 (Workflow)

1. **데이터 확보**: 페이징 기능을 테스트하기 위해 `data.sql`에 20개 이상의 더미 데이터를 추가하였습니다.
2. **DTO 설계**: 화면에 필요한 정보(id, 제목, 내용, 작성자명)만 담을 수 있는 `BoardResponse.ListDTO`를 생성하였습니다.
3. **Repository 확장**: N+1 문제를 방지하기 위해 `JOIN FETCH`를 사용하는 전용 쿼리 메서드 `findFetchAll()`을 Repository에 추가하였습니다.
4. **Service 구현**: 핵심 비즈니스 로직인 `게시글목록보기` 메서드를 구현하였습니다. (한글 메서드명 규칙 준수)
5. **Controller 연결**: 루트 경로 요청 시 목록 페이지로 리다이렉트하고, `/board/list` 경로에서 목록 데이터를 모델에 담아 전달하도록 구현하였습니다.
6. **화면 구현**: Mustache 템플릿 엔진을 사용하여 게시글 목록을 표 형태로 출력하는 `list.mustache` 파일을 생성하였습니다.

## 2. 🧩 변경된 모든 코드 포함

### BoardResponse.java (DTO)
```java
public class BoardResponse {
    @Data
    public static class ListDTO {
        private Integer id;
        private String title;
        private String content;
        private String username;

        public ListDTO(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.username = board.getUser().getUsername();
        }
    }
}
```

### BoardRepository.java (Lazy Loading 방지)
```java
public interface BoardRepository extends JpaRepository<Board, Integer> {
    @Query("SELECT b FROM Board b JOIN FETCH b.user ORDER BY b.id DESC")
    List<Board> findFetchAll();
}
```

### BoardService.java (비즈니스 로직)
```java
@Transactional(readOnly = true)
public List<BoardResponse.ListDTO> 게시글목록보기() {
    List<Board> boardList = boardRepository.findFetchAll();
    return boardList.stream()
            .map(BoardResponse.ListDTO::new)
            .collect(Collectors.toList());
}
```

## 3. 🍦 상세비유 (Easy Analogy)

"이번 작업은 **도서관의 모든 책을 한눈에 볼 수 있는 카탈로그를 만드는 것**과 같습니다. 
원래는 책장에 책이 3권밖에 없어서 목록이 필요 없었지만, 이제 20권이 넘는 책을 들여놓았기 때문에(데이터 추가), 이 책들의 정보를 깔끔하게 정리한 장부(DTO)를 만들고, 사서 선생님이 한 번에 모든 책 정보를 가져와서(JOIN FETCH), 게시판에 공고문(mustache)을 붙인 것과 같습니다."

## 4. 📚 기술 딥다이브 (Technical Deep-dive)

- **N+1 문제와 JOIN FETCH**:
  - 기본적으로 `@ManyToOne(fetch = FetchType.LAZY)` 설정을 하면, 게시글 목록을 가져온 후 작성자 이름을 조회할 때마다 별도의 SELECT 쿼리가 발생합니다. (게시글이 20개면 20번의 추가 쿼리 발생)
  - `JOIN FETCH`를 사용하면 단 한 번의 JOIN 쿼리로 게시글과 작성자 정보를 모두 가져오므로 성능이 획기적으로 향상됩니다.
- **DTO(Data Transfer Object)**:
  - 엔티티(`Board`)를 직접 컨트롤러나 뷰로 넘기지 않고, 필요한 필드만 모은 `ListDTO`를 사용함으로써 보안을 강화하고 순환 참조 문제를 방지합니다.
