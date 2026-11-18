package com.green.r_board.filiter;

import jakarta.servlet.*;

import java.io.IOException;

public class Filter2 implements Filter {
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    System.out.println("Filter2 실행");
    chain.doFilter(request, response);
  }
}
