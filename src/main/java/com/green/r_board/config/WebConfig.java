package com.green.r_board.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS(Cross-Origin Resource Sharing) 설정 클래스
 *
 * CORS란?
 * - 다른 출처(origin)에서 실행 중인 웹 애플리케이션이
 *   현재 출처의 리소스에 접근할 수 있도록 허용하는 메커니즘
 * - 예: http://localhost:5173(React)에서 http://localhost:8080(Spring)으로 요청
 */
@Configuration  // Spring 설정 클래스임을 명시
public class WebConfig implements WebMvcConfigurer {
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    // CorsRegistry
    // CORS 규칙을 모아두는 등록소입니다. 여기에 규칙을 하나씩 추가합니다.


    registry
            // addMapping("/**")
            // CORS를 적용할 서버 주소(경로)를 정합니다.
            // "/**"는 모든 경로를 뜻합니다. (예: /members, /boards/1 등)
            // 특정 경로만 열고 싶다면 "/members/**"처럼 좁혀서 쓰면 됩니다.
            .addMapping("/**")

            // allowedOrigins(...)
            // 요청을 허락할 "프론트엔드 주소"를 적습니다.
            // 주의: 끝에 슬래시(/)를 붙이면 안 됩니다.
            //   O  http://localhost:3000
            //   X  http://localhost:3000/
            // 여러 개를 쓸 수 있으니 본인이 쓰는 개발 서버 주소를 넣어주세요.
            //   - 3000 : Create React App 기본 포트
            //   - 5173 : Vite 기본 포트
            // 나중에 서비스를 배포하면 실제 도메인(예: https://myapp.com)을 추가합니다.
            .allowedOrigins("http://localhost:5173")

  // allowedMethods(...)
  // 허락할 HTTP 메서드(요청 종류)를 정합니다.
  // 조회(GET), 등록(POST), 수정(PUT, PATCH), 삭제(DELETE)를 모두 열어둡니다.
  // OPTIONS는 "사전 요청(Preflight)"에 쓰입니다.
  //   브라우저는 POST, PUT, DELETE 같은 요청을 보내기 전에
  //   "이 요청 보내도 되나요?"라고 OPTIONS로 먼저 물어봅니다.
  //   OPTIONS를 빼먹으면 이 확인 단계에서 막히니 꼭 넣어주세요.
            .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")

  // allowedHeaders("*")
  // 요청에 담을 수 있는 헤더를 허락합니다.
  // "*"는 모든 헤더를 뜻합니다. (예: Content-Type, Authorization 등)
  // JSON을 보내거나 토큰을 헤더에 담을 때 필요합니다.
            .allowedHeaders("*")

  // allowCredentials(true)
  // 쿠키, 세션, 인증 정보를 요청에 함께 보낼 수 있게 허락합니다.
  // 로그인 세션(JSESSIONID)을 쓴다면 반드시 true여야 합니다.
  // 주의: true로 하면 allowedOrigins에 "*"를 쓸 수 없고,
  //       위처럼 정확한 주소를 적어야 합니다.
  // React 쪽에서도 axios 요청에 { withCredentials: true }를 함께 써야 쿠키가 전달됩니다.
            .allowCredentials(true)

  // maxAge(3600)
  // 사전 요청(OPTIONS)의 결과를 브라우저가 기억하는 시간(초)입니다.
  // 3600초 = 1시간 동안은 같은 요청에 대해 OPTIONS를 다시 보내지 않습니다.
  // 불필요한 요청이 줄어서 속도에 도움이 됩니다.
            .maxAge(3600);
    }

}