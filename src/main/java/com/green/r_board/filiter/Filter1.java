package com.green.r_board.filiter;

import jakarta.servlet.*;

import java.io.IOException;

//요청이 들어오면 컨트롤러가 실행하기 전 사용할 검문소를 생성
//클래스가 Filter 역할을 하기 위해서는
// 1. Filter interface(servlet)를 구현한다.
// 2. Filter interface안에 정의된 doFilter 메서드를 구현한다.
public class Filter1 implements Filter {
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    System.out.println("Filter1 실행~");
    chain.doFilter(request, response);  //filter 실행 후 다음 단계 진행 /  doFilter 없으면 Filter 단계에서 멈춘당!
  }
}
