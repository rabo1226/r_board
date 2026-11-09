package com.green.r_board.service;

import com.green.r_board.dto.BoardDTO;
import com.green.r_board.dto.ReplyDTO;
import com.green.r_board.mapper.BoardMapper;
import com.green.r_board.mapper.ReplyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {
  private final BoardMapper boardMapper;
  private final ReplyMapper replyMapper;

  //게시글 목록 조회 기능
  public List<BoardDTO> selectBoardList(){
    return boardMapper.selectBoardList();
  }

  //게시글 상세 조회
  public BoardDTO selectBoard(Long boardNum){
    return boardMapper.selectBoard(boardNum);
  }

  //게시글 등록
  public void insertBoard(BoardDTO boardDTO){
    boardMapper.insertBoard(boardDTO);
  }

  //게시글 삭제
 public void deleteBoard(Long boardNum){
  boardMapper.deleteBoard(boardNum);
 }

  //게시글 수정
  public void updateBoard(BoardDTO boardDTO){
    boardMapper.updateBoard(boardDTO);
  }
}
