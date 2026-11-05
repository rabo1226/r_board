package com.green.r_board.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ReplyDTO {
  //댓글 DTO
  private Long replyNum;
  private String replyContent;
  private String replyWriter;
  private Date createDate;
  private Long boardNum;
}
