package com.green.r_board.mapper;

import com.green.r_board.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

  void join(MemberDTO memberDTO);

}
