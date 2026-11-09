package com.green.r_board.controller;

import com.green.r_board.dto.ReplyDTO;
import com.green.r_board.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/replies")
@RestController
public class ReplyController {
  private final ReplyService replyService;

  //댓글 목록 조회 API
  //url : replies/글번호
  @GetMapping("/{boardNum}")
  public ResponseEntity<?> replyList(@PathVariable("boardNum") Long boarNum){
    try {
      List<ReplyDTO> result = replyService.replyList(boarNum);
      return ResponseEntity
              .status(result != null ? HttpStatus.OK : HttpStatus.NOT_FOUND)
              .body(result != null ? result : "댓글 목록이 없습니다.");
    } catch (Exception e) {
      log.error("댓글 목록 조회 서버 오류!");
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
