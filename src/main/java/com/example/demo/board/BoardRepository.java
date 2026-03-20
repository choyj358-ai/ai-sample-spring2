package com.example.demo.board;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    void deleteByUserId(Integer userId);

    @Query("SELECT b FROM Board b JOIN FETCH b.user ORDER BY b.id DESC")
    List<Board> findFetchAll();

    @Query(value = "SELECT b.* FROM board_tb b INNER JOIN user_tb u ON b.user_id = u.id ORDER BY b.id DESC LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Board> findNativeAll(@Param("limit") int limit, @Param("offset") int offset);
}
