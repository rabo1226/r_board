package com.green.r_board.service;

import com.green.r_board.dto.MemberDTO;
import com.green.r_board.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {
  private final MemberMapper memberMapper;
  private final PasswordEncoder passwordEncoder;

  //회원가입 기능
  public void join(MemberDTO memberDTO){
    //비밀번호 암호화
    String encodedPw = passwordEncoder.encode(memberDTO.getMemPw());
    memberDTO.setMemPw(encodedPw);  //회원가입 전 비밀번호 암호화 진행 후 setter호출로 암호화 완료!
    memberMapper.join(memberDTO);
  }

  //로그인 시도하는 회원 정보 조회
  public MemberDTO getMemberForLogin(String memEmail){
    return memberMapper.getMemberForLogin(memEmail);
  }
}
