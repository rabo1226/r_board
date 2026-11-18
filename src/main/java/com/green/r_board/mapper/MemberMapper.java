package com.green.r_board.mapper;

import com.green.r_board.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

  void join(MemberDTO memberDTO);

  //로그인 시도하는 회원정보 조회
  MemberDTO getMemberForLogin(String memEmail);

}
