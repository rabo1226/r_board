package com.green.r_board.mapper;

import com.green.r_board.dto.ReplyDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyMapper {
  //댓글 목록 조회
  List<ReplyDTO> replyList(Long boardNum);
}
