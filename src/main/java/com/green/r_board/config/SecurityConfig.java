package com.green.r_board.config;

import com.green.r_board.jwt.LoginFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password4j.BcryptPassword4jPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

//spring security의 인증(로그인)과 인가(권한) 관련 설정 파일
@Configuration  //객체생성 + 설정 관련 파일임을 인지
@EnableWebSecurity //Spring Security 설정파일임을 인지
public class SecurityConfig {

  //인증 및 인가 설정 메소드, 형태 고정!
  @Bean  //프로젝트 run하면 return 데이터를 객체로 생성해주는 @어노테이션
  public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationConfiguration authConfig) throws Exception {
    //로그인 인증 처리를 담당하는 핵심 객체 생성
    AuthenticationManager authenticationManager = authConfig.getAuthenticationManager();

    //람다(lambda) 방식 -> 쓸 수 있어야한다!!!!! ,  코드의 효율성이 급상승한다!!!!!
    //전통적인 session방식 로그인이면 cookie에 sessionId 자동저장이지만 session방식을 사용하지 않으면 csrf에 대비할 수 있다!
    http.cors(Customizer.withDefaults()) //아래 Bean으로 생성된 cor: scorsConfigurationSource 객체 할당
        .csrf( csrf -> csrf.disable() )
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

    //로그인 기능을 담당하는 필터 UsernamePasswordAuthenticationFilter 클래스를
    //우리가 만든 LoginFilter 클래스로 대체
    http.addFilterAt(new LoginFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
  //return에는 http(인증, 인가에 대한 설정을 가진) 데이터를 Bean이 객체로 생성해준다!

  //비밀번호 암호화 기능을 제공하는 PasswordEncoder 객체 생성
  @Bean
  public PasswordEncoder getPasswordEncoder(){
    return new BCryptPasswordEncoder();
  }

  //CORS 설정 Bean
  @Bean //런 되면 리턴 데이터를 객체로 생성하는 어노테이션
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true); //쿠키 정보를 통신하기 위한 설정
    config.addAllowedOrigin("http://localhost:5173"); //리액트에서의 요청 허용
    config.addAllowedHeader("*"); //모든 헤더 정보 허용
    config.addAllowedMethod("*"); //get, post, delete, put 등의 요청 메서드 허용

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
  }
}
