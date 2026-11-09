package com.green.r_board.service;

import com.green.r_board.dto.ReplyDTO;
import com.green.r_board.mapper.ReplyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReplyService {
  private final ReplyMapper replyMapper;

  //댓글 목록 조회 기능
  public List<ReplyDTO> replyList(Long boarNum){
    return replyMapper.replyList(boarNum);
  }

}
