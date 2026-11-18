package com.green.r_board.filiter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//필터 관련 설정 클래스 : 생성한 검문소를 어떻게 실행할지 결정
@Configuration  //객체생성 + 설정 관련 파일임을 인지
public class FilterConfig {

  @Bean
  public FilterRegistrationBean<Filter1> filterRegistration(){

    //필터 관련 설정 기능을 제공하는 객체
    FilterRegistrationBean<Filter1> registrationBean = new FilterRegistrationBean<>();

    //어떤 filter를 쓸지 지정 : filter1을 필터로 사용하겠다.
    registrationBean.setFilter(new Filter1());

    //filter를 어떤 요청이 들어오면 실행할지 세팅
    registrationBean.addUrlPatterns("/*");

    //filter 우선순위 지정, 낮을수록 우선순위는 높음  :  실행할 때 실행할 필터의 순서? 설정가능 1이면 1번으로 실행한다.
    registrationBean.setOrder(1);

    return registrationBean;
  }

  @Bean
  public FilterRegistrationBean<Filter2> filterRegistration2(){

    //필터 관련 설정 기능을 제공하는 객체
    FilterRegistrationBean<Filter2> registrationBean = new FilterRegistrationBean<>();

    //어떤 filter를 쓸지 지정 : filter2을 필터로 사용하겠다.
    registrationBean.setFilter(new Filter2());

    //filter를 어떤 요청이 들어오면 실행할지 세팅
    registrationBean.addUrlPatterns("/*");

    //filter 우선순위 지정, 낮을수록 우선순위는 높음 /순서를 지정 :  여러 Filter를 실행할 때 실행할 필터의 순서? 설정가능 1이면 1번으로 실행한다.
    registrationBean.setOrder(0);

    return registrationBean;
  }


}
