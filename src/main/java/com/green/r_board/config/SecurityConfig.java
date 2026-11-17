package com.green.r_board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password4j.BcryptPassword4jPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//spring security의 인증(로그인)과 인가(권한) 관련 설정 파일
@Configuration  //객체생성 + 설정 관련 파일임을 인지
@EnableWebSecurity //Spring Security 설정파일임을 인지
public class SecurityConfig {

  //인증 및 인가 설정 메소드, 형태 고정!
  @Bean  //프로젝트 run하면 return 데이터를 객체로 생성해주는 @어노테이션
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //람다(lambda) 방식 -> 쓸 수 있어야한다!!!!! ,  코드의 효율성이 급상승한다!!!!!
    //전통적인 session방식 로그인이면 cookie에 sessionId 자동저장이지만 session방식을 사용하지 않으면 csrf에 대비할 수 있다!
    http.csrf( csrf -> csrf.disable() )
        .formLogin( form -> form.disable() )
        .httpBasic( basic -> basic.disable() )
        .sessionManagement(
          session ->
          session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .authorizeHttpRequests(
          auth ->
            auth.anyRequest().permitAll()
        );

    return http.build();
  }
  //return에는 http(인증, 인가에 대한 설정을 가진) 데이터를 Bean이 객체로 생성해준다!

  //비밀번호 암호화 기능을 제공하는 PasswordEncoder 객체 생성
  @Bean
  public PasswordEncoder getPasswordEncoder(){
    return new BCryptPasswordEncoder();
  }


}
