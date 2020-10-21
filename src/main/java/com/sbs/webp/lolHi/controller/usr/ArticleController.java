package com.sbs.webp.lolHi.controller.usr;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sbs.webp.lolHi.dto.Article;


@Controller
public class ArticleController {
	@RequestMapping("/usr/article/list")
	public String showList() {
		List<Article> articles = new ArrayList<>();
		Article article1 = new Article(1, "2020-10-17 15:00:00", "2020-10-17 18:00:00", "제목1", "내용1");
		Article article2 = new Article(2, "2020-10-18 15:00:00", "2020-10-18 18:00:00", "제목2", "내용2");

		articles.add(article1);
		articles.add(article2);

		return "usr/article/list";
	}
}
