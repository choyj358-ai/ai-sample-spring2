package com.example.demo.board;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    void deleteByUserId(Integer userId);

    @Query("SELECT b FROM Board b JOIN FETCH b.user ORDER BY b.id DESC")
    List<Board> findFetchAll();
}
