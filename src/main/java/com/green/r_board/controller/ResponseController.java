package com.green.r_board.controller;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResponseController {

  @GetMapping("/t1")
  public ResponseEntity<String> text1(){
    String result = "hello";
    //status.header.body의 구성으로 이루어졌다. but 3단 구성 필수는 아님
    //ResponseEntity<응답할 데이터의 자료형>
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
  }

  @GetMapping("/t2")
  public ResponseEntity<Integer> test2(){
    HttpHeaders headers = new HttpHeaders();
    //headers.add("키", "벨류") <- 문자열만 가능
    headers.add("myName", "hong");
    headers.add("myAge", "30");
    return ResponseEntity
            .status(HttpStatus.OK)
            .headers(headers)
            .body(10);
  }

  // ? -> 와일드카드, 과장하면 '모든 자료형'이라는 의미가 될 수 있다.
  @GetMapping("/t3")
  public ResponseEntity<?> test3(){
    //insert 처럼 return이 없는 데이터는? build()로 return을 끝내고/ 리턴타입은 ResponseEntity<?>로 정리!
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }



}
