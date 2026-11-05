package com.green.r_board.mapper;

import com.green.r_board.dto.BoardDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

  //게시글 목록 조회
  List<BoardDTO> selectBoardList();

  //게시글 상세 조회
  BoardDTO selectBoard(Long boardNum);

  //게시글 등록
  void insertBoard(BoardDTO boardDTO);

  //게시글 삭제
  void deleteBoard(Long boardNum);

  //게시글 수정
  void updateBoard(BoardDTO boardDTO);
}
