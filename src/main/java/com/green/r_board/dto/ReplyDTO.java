package com.green.r_board.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class ReplyDTO {
  //댓글 DTO
  private Long replyNum;
  private String replyContent;
  private String replyWriter;
  private LocalDateTime createDate;
  private Long boardNum;
}
