package com.green.r_board.controller;

import com.green.r_board.dto.BoardDTO;
import com.green.r_board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/boards")
public class BoardController {
  private final BoardService boardService;

  //게시글 목록 조회 API
  //url : /boards
  @GetMapping("")
  public ResponseEntity<?> getBoardList(){
    //예외처리  -> 오류가 났을 때 멈추는 것이 아니라 다른 기능은 순서대로 진행되어야 바람직하다!
    //ex) try{p(1), p(2) 오류 발생, p(3)} -> p(2) 오류 발생하면 바로 catch(e){p(e)} 실행하고 리턴 no
    try {
      List<BoardDTO> boardList = boardService.selectBoardList();
      return ResponseEntity.status(HttpStatus.OK).body(boardList);
    }catch (Exception e){
      //e 오류에 대한 정보를 담고 있다. -> @slf4j 어노테이션 사용해서 log를 볼 수 있도록! 출력은 사용 xxx

      // console.log(e); = e.printStackTrace(); 오류가 발생한 코드위치, 파일위치, 이유
      log.error("오류 정보 - 게시글 목록 조회 API 호출 오류!");
      e.printStackTrace();

      log.info("데이터 정보");

      //오류가 나면 오류가 오류응답은 해줘야한다!
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("게시글 목록 조회 중 오류 발생");
    }

  }

  //게시글 상세 조회 API
  //url : /boards/{게시글번호}
  @GetMapping("/{boardNum}")
  public ResponseEntity<?> getBoard(@PathVariable("boardNum") Long boardNum){
    try {
      BoardDTO result = boardService.selectBoard(boardNum);

      //데이터가 조회됐을 때 = 데이터 조회 성공
      return ResponseEntity
              .status(result != null ? HttpStatus.OK : HttpStatus.NOT_FOUND)
              .body(result != null ? result : "조회된 데이터가 없습니다.");

    }catch (Exception e){
      log.error("게시글 상세조회 API 호출 오류!");
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  //게시글 등록 API
  //url : /boards
  @PostMapping("")
  public ResponseEntity<?> regBoard(@RequestBody BoardDTO boardDTO){
    try {
      boardService.insertBoard(boardDTO);
      return ResponseEntity.status(HttpStatus.CREATED).build();
    }catch (Exception e){
      log.error("게시글 등록 API 호출 오류!");
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  //게시글 삭제 API
  //url : /boards

  //게시글 수정 API
  //url : /boards/{게시글번호}



}


