package com.sbs.webp.lolHi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sbs.webp.lolHi.dto.Article;


@Service
public class ArticleService {

	public List<Article> getArticles() {
		List<Article> articles = new ArrayList<>();
		Article article1 = new Article(1, "2020-10-17 15:00:00", "2020-10-17 18:00:00", "제목1", "내용1");
		Article article2 = new Article(2, "2020-10-18 15:00:00", "2020-10-18 18:00:00", "제목2", "내용2");

		articles.add(article1);
		articles.add(article2);

		return articles;
	}
}
