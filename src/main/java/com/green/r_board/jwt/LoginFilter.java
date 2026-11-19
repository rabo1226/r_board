package com.green.r_board.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.r_board.dto.MemberDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;
//UsernamePasswordAuthenticationFilter 로그인 담당하는 Spring Security 필터?

//LoginFilter 클래스는 로그인 처리를 담당하는 클래스
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
  //AuthenticationManager 객체는 로그인 처리에 필요한 핵심 객체
  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;

  public LoginFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil){
    this.authenticationManager = authenticationManager;
    this.jwtUtil = jwtUtil;

    // "/login"으로 지정되어 있는 로그인 요청 url변경
    setFilterProcessesUrl("/members/login");

    // 로그인 시 전달되는 ID 및 PW은 각각 username, password 라는 키값으로 전달됨!
    // 그래서 전달되는 로그인 정보를 받는 키값을 변경
    setUsernameParameter("memEmail");
    setPasswordParameter("memPw");

  }


  //로그인 요청이 들어오면 LoginFilter 클래스의 attemptAuthentication 메서드가 실행된다.
  // "/login"(Post 방식) url이 기본적인 로그인 요청 URL

  // /members/login 로그인 요청 시 전달되는 {memEmail, memPw} 데이터를 request에 받고, request에서 memEmail, memPw(id, pw)를 뽑아서 memberDTO vo에 저장(44~54)

  // "/members/login" 요청 보내면 -> LoginFilter 클래스 안의 attemptAuthentication 메서드 실행
  //  AuthenticationException 메서드는 id, pw도 받음
  // 로그인 처리는 우리가 하는게 아니라 authenticationManager 한테 전달
  //   -> 전달받은 id, pw을 authtoken(보안이 걸린 상자에 담아서) authenticationManager 한테 전달
  //   -> id, pw 전달받은 authenticationManager 는 바로 로그인 절차 진행
  //        -> 진행과 동시에 db에 저장된 id, pw를 요구
  //        -> 디비에 저장된 로그인 정보를 전달받기 위해서 authenticationManager가 UserDetailsService의 loadUserByUsername() 메서드를 호출

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    System.out.println("LoginFilter 클래스의 attemptAuthentication() 메서드 실행!");

    MemberDTO vo = new MemberDTO();
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      ServletInputStream inputStream = request.getInputStream();
      String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
      vo = objectMapper.readValue(messageBody, MemberDTO.class);
    }catch (IOException e){
      throw new RuntimeException(e);
    }
    System.out.println("입력받은 아이디 : " + vo.getMemEmail());
    System.out.println("입력받은 비밀번호 : " + vo.getMemPw());

    //전달받은 id, pw를 AuthenticationManager에게 전달하면 로그인 절차를 진행
    //우리가 입력한 아이디와 비밀번호를 데이터베이스에 저장한 정보와 일치하는지 검증하는 로직은
    //AuthenticationManager가 담당하기 때문에 전달받은 아이디와 비밀번호를 AuthenticationManager에 전달해줘야 한다.
    //이때 아이디와 비밀번호를 그냥 전달하는 것이 아니라 UsernamePasswordAuthenticationToken 객체에 실어 보낸다.
    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(vo.getMemEmail(), vo.getMemPw(), null);

    //아이디와 비번을 담고 있는 authToken객체를 authenticationManager에 전달, authenticationManager는 로그인을 검증하는 기능을 함
    //로그인을 검증하는 방법 -> UserDetailsService의 loadUserByUsername 메서드를 호출하여 검증
    //loadUserByUsername() 메서드의 실행 결과로 로그인 유저의 정보를 authentication 객체에 담아 옴
    Authentication authentication = authenticationManager.authenticate(authToken);   // = 로그인 데이터를 manager에 전달하고, 로그인 검증(디비의 아이디 비번과 비교)를 진행하고 그 결과를 리턴해서 authentication에 저장하는 코드!
    System.out.println("DB에서 로그인 가능 여부 확인 완료(UserDetailsService의 loadUserByUsername 메서드 정상 실행 됨). 만약 검증에 실패했다면 본 출력문 실행 안 됨");
    System.out.println("로그인 중인 유저 : " + authentication.getName());

    //로그인 유저의 정보가 담긴 authentication객체를 리턴하면 authentication객체가 session에 저장됨
    //세션에 저장하는 이유는 security의 권한 처리를 위해서는 세션에 로그인 정보가 있어야 되기 때문.
    return authentication;
  }

  //attemptAuthentication 메서드 실행결과 로그인 검증 성공하면 실행되는 메서드
  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
    System.out.println("로그인 검증 성공! successfulAuthentication 메서드 호출!");
    //authResult 에 로그인 정보가 들어있다! 이거를 뽑아서 사용하면 된다!!!
    //토큰 생성을 위한 아이디 정보 추출
    String username = authResult.getName();

    //검증 끝난 회원에게 JWT Token 발행
    //-> 서버 : 어떠한 정보도 가지지 않음 / 클라이언트 : 증명서?같은걸 가지고있다!  => 클라이언트가 로그인 시도를 하면 내가 발급한 증명서 진위여부(발행시간과 만료기간 등을)를 확인하는 방식으로 진행한다! 단, 토큰이 털리면 소유자가 누군지는 모른다...

    //토큰 생성을 위한 권한 정보 추출
    Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
    Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
    GrantedAuthority auth = iterator.next();
    String role = auth.getAuthority();

    //토큰 생성                                             1000 -> 1초 , *60 = 1분 * 30 = 30분
    String accessToken = jwtUtil.createJwt(username, role, (1000 * 60 * 30)); //30분

    //생성한 토큰을 응답 헤더에 담아 클라이언트에 전달
    response.setHeader("Access-Control-Expose-Headers", "Authorization");
    response.setHeader("Authorization", "Bearer " + accessToken);  //실제로 header에 accessToken을 가져간다!   -> Authorization : Bearer token값!  (Bearer은 jwt토큰이라는 의미표시!!!)
    response.setStatus(HttpStatus.OK.value()); //클라이언트에 200 응답
  }

  //attemptAuthentication 메서드 실행결과 로그인 검증 실패하면 실행되는 메서드
  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
    System.out.println("unsuccessfulAuthentication 메서드 실행 - 로그인 검증 실패");
  }
}
