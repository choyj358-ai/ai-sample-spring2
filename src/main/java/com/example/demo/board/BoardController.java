package com.example.demo.board;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;
    private final HttpSession session;

    @GetMapping("/")
    public String home() {
        return "redirect:/board/list";
    }

    @GetMapping("/board/list")
    public String list(Model model, @RequestParam(name = "page", defaultValue = "0") Integer page) {
        List<BoardResponse.ListDTO> boardList = boardService.게시글목록보기(page);
        // RULE: gemini.md 규칙 B에 따라 컬렉션은 "models"에 담는다.
        model.addAttribute("models", boardList);
        return "board/list";
    }
}
