package com.green.r_board.mapper;

import com.green.r_board.dto.CategoryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
  //게시글 카테고리 목록조회 쿼리
  List<CategoryDTO> selectCategoryList();
}
