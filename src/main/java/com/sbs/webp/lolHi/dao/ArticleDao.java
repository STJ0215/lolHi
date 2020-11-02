package com.sbs.webp.lolHi.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbs.webp.lolHi.dto.Article;


@Mapper
public interface ArticleDao {
	List<Article> getForPrintArticles(Map<String, Object> param);

	Article getForPrintArticleById(@Param("id") int id);
	
	void writeArticle(Map<String, Object> param);
	
	void modifyArticle(@Param("id") int id, @Param("title") String title, @Param("body") String body);

	void deleteArticleById(@Param("id") int id);

	int getTotalCount(Map<String, Object> param);
}
