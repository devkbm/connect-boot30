package com.like.cooperation.board.infra.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.like.cooperation.board.boundary.ResponseArticle;

@Mapper
public interface BoardMapper {
					
	/**
	 * 게시글 리스트 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<ResponseArticle> getArticleList(Map<String, Object> params);
			
}
