package com.green.r_board.service;

import com.green.r_board.dto.CategoryDTO;
import com.green.r_board.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
  private final CategoryMapper categoryMapper;

  //카테고리 목록
  public List<CategoryDTO> selectCategory(){
    return categoryMapper.selectCategoryList();
  }
}
