package com.green.r_board.controller;

import com.green.r_board.dto.MemberDTO;
import com.green.r_board.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/members")
@RequiredArgsConstructor
@RestController
public class MemberController {
  private final MemberService memberService;
  private final PasswordEncoder passwordEncoder;

  //컨트롤러에서는 요청에 대한 응답의 코드만 보이도록 한다!  나머지 실제적인 기능등릉 서비스에서 해야한다!!!!
  @GetMapping("/test")
  public void passwordEncoderTest(){
    String str1 = "java";
    String str2 = "java";

    //암호화
    String encoded1 = passwordEncoder.encode(str1);
    String encoded2 = passwordEncoder.encode(str2);

    System.out.println(encoded1);
    System.out.println(encoded2);

    //암호 전 데이터와 암호화 후 데이터 비교
    boolean result1 = passwordEncoder.matches("java", encoded2);
    boolean result2 = passwordEncoder.matches("java1", encoded2);
    System.out.println(result1);
    System.out.println(result2);
  }

  //회원가입 API
  //url : /members
  @PostMapping("")
  public ResponseEntity<?> join(@RequestBody MemberDTO memberDTO){
    try {
      log.info(memberDTO.toString());
      memberService.join(memberDTO);
      return ResponseEntity.status(HttpStatus.CREATED).build();
    } catch (Exception e) {
      log.error("회원가입 api 응답 실패");
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

}
