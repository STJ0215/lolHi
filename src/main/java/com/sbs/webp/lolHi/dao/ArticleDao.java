package com.sbs.webp.lolHi.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbs.webp.lolHi.dto.Article;


@Mapper
public interface ArticleDao {
	List<Article> getArticles();

	Article getArticleById(@Param("id") int id);
	
	void modifyArticle(@Param("id") int id, @Param("title") String title, @Param("body") String body);

	void deleteArticleById(@Param("id") int id);
}
