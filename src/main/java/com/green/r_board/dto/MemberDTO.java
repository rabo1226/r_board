package com.green.r_board.dto;

import lombok.Data;

@Data
public class MemberDTO {
  //회원 DTO
  private String memEmail;
  private String memPw;
  private String memName;
  private String memTEl;
  private String memTel;
  private String memRole;
}
