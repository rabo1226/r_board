package com.green.r_board.dto;

import lombok.Data;

@Data
public class CategoryDTO {
  //카테고리 DTO
  private Long cateNum;
  private String cateName;
  private Long orderNum;
  private String useYn;
}
