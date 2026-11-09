package com.green.r_board.controller;

import com.green.r_board.dto.CategoryDTO;
import com.green.r_board.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categorys")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
  private final CategoryService categoryService;

  //게시글 카테고리 목록 조회
  @GetMapping("")
  public ResponseEntity<?> getCategoryList(){
    try {
      List<CategoryDTO> categoryList = categoryService.selectCategory();
      return ResponseEntity
              .status( categoryList != null ? HttpStatus.OK : HttpStatus.NOT_FOUND)
              .body(categoryList != null ? categoryList : "조회된 데이터가 없습니다.");
    }catch (Exception e){
      log.error("게시글 카테고리 목록 조회 API 실패!");
      log.info("데이터 정보");
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
