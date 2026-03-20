package com.example.demo.board;

import lombok.Data;

public class BoardResponse {

    // 게시글 목록을 위한 DTO
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

    // RULE: Detail DTO는 상세 정보를 저장한다.
    @Data
    public static class Detail {

    }
}
