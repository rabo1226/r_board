package com.green.r_board.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
@Data
public class BoardDTO {
  //게시글 DTO
  private Long boardNum;
  private Long cateNum;
  private String title;
  private String content;
  private String writer;
  private LocalDateTime createDate;
  private CategoryDTO categoryDTO;
}
