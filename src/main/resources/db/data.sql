-- 유저 더미 데이터
INSERT INTO user_tb (username, password, created_at) VALUES ('ssar', '$2a$10$f8XhWN0vCOo1FyKfLoJXT./Cpl9Bjxqn7qR5CwP8AI9zcDz8ddMN6', NOW());
INSERT INTO user_tb (username, password, created_at) VALUES ('cos', '$2a$10$f8XhWN0vCOo1FyKfLoJXT./Cpl9Bjxqn7qR5CwP8AI9zcDz8ddMN6', NOW());

-- 게시글 더미 데이터
INSERT INTO board_tb (title, content, user_id, created_at) VALUES ('첫 번째 게시글', '안녕하세요. ssar의 첫 번째 글입니다.', 1, NOW());
INSERT INTO board_tb (title, content, user_id, created_at) VALUES ('두 번째 게시글', '안녕하세요. ssar의 두 번째 글입니다.', 1, NOW());
INSERT INTO board_tb (title, content, user_id, created_at) VALUES ('세 번째 게시글', '안녕하세요. cos의 첫 번째 글입니다.', 2, NOW());
INSERT INTO board_tb (title, content, user_id, created_at) VALUES ('네 번째 게시글', '테스트를 위한 네 번째 게시글입니다.', 1, NOW());
INSERT INTO board_tb (title, content, user_id, created_at) VALUES ('다섯 번째 게시글', '테스트를 위한 다섯 번째 게시글입니다.', 2, NOW());
INSERT INTO board_tb (title, content, user_id, created_at) VALUES ('여섯 번째 게시글', '테스트를 위한 여섯 번째 게시글입니다.', 1, NOW());
INSERT INTO board_tb (title, content, user_id, created_at) VALUES ('일곱 번째 게시글', '테스트를 위한 일곱 번째 게시글입니다.', 2, NOW());
INSERT INTO board_tb (title, content, user_id, created_at) VALUES ('여덟 번째 게시글', '테스트를 위한 여덟 번째 게시글입니다.', 1, NOW());
INSERT INTO board_tb (title, content, user_id, created_at) VALUES ('아홉 번째 게시글', '테스트를 위한 아홉 번째 게시글입니다.', 2, NOW());
INSERT INTO board_tb (title, content, user_id, created_at) VALUES ('열 번째 게시글', '테스트를 위한 열 번째 게시글입니다.', 1, NOW());
INSERT INTO board_tb (title, content, user_id, created_at) VALUES ('열한 번째 게시글', '테스트를 위한 열한 번째 게시글입니다.', 2, NOW());
INSERT INTO board_tb (title, content, user_id, created_at) VALUES ('열두 번째 게시글', '테스트를 위한 열두 번째 게시글입니다.', 1, NOW());
INSERT INTO board_tb (title, content, user_id, created_at) VALUES ('열세 번째 게시글', '테스트를 위한 열세 번째 게시글입니다.', 2, NOW());
INSERT INTO board_tb (title, content, user_id, created_at) VALUES ('열네 번째 게시글', '테스트를 위한 열네 번째 게시글입니다.', 1, NOW());
INSERT INTO board_tb (title, content, user_id, created_at) VALUES ('열다섯 번째 게시글', '테스트를 위한 열다섯 번째 게시글입니다.', 2, NOW());
INSERT INTO board_tb (title, content, user_id, created_at) VALUES ('열여섯 번째 게시글', '테스트를 위한 열여섯 번째 게시글입니다.', 1, NOW());
INSERT INTO board_tb (title, content, user_id, created_at) VALUES ('열일곱 번째 게시글', '테스트를 위한 열일곱 번째 게시글입니다.', 2, NOW());
INSERT INTO board_tb (title, content, user_id, created_at) VALUES ('열여덟 번째 게시글', '테스트를 위한 열여덟 번째 게시글입니다.', 1, NOW());
INSERT INTO board_tb (title, content, user_id, created_at) VALUES ('열아홉 번째 게시글', '테스트를 위한 열아홉 번째 게시글입니다.', 2, NOW());
INSERT INTO board_tb (title, content, user_id, created_at) VALUES ('스무 번째 게시글', '테스트를 위한 스무 번째 게시글입니다.', 1, NOW());
INSERT INTO board_tb (title, content, user_id, created_at) VALUES ('스물한 번째 게시글', '테스트를 위한 스물한 번째 게시글입니다.', 2, NOW());
INSERT INTO board_tb (title, content, user_id, created_at) VALUES ('스물두 번째 게시글', '테스트를 위한 스물두 번째 게시글입니다.', 1, NOW());
INSERT INTO board_tb (title, content, user_id, created_at) VALUES ('스물세 번째 게시글', '테스트를 위한 스물세 번째 게시글입니다.', 2, NOW());

-- 댓글 더미 데이터
INSERT INTO reply_tb (comment, user_id, board_id, created_at) VALUES ('첫 번째 게시글에 ssar이 작성한 댓글입니다.', 1, 1, NOW());
INSERT INTO reply_tb (comment, user_id, board_id, created_at) VALUES ('첫 번째 게시글에 cos가 작성한 댓글입니다.', 2, 1, NOW());
INSERT INTO reply_tb (comment, user_id, board_id, created_at) VALUES ('두 번째 게시글에 cos가 작성한 댓글입니다.', 2, 2, NOW());
