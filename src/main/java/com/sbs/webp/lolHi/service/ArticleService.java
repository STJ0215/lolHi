package com.sbs.webp.lolHi.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.webp.lolHi.dao.ArticleDao;
import com.sbs.webp.lolHi.dto.Article;
import com.sbs.webp.lolHi.util.Util;


@Service
public class ArticleService {
	@Autowired
	private ArticleDao articleDao;

	public List<Article> getForPrintArticles(Map<String, Object> param) {
		int page = Util.getAsInt(param.get("page"), 1);
		
		int itemsCountInAPage = Util.getAsInt(param.get("itemsCountInAPage"), 10);
		
		if (itemsCountInAPage > 100) {
			itemsCountInAPage = 100;
		}
		else if (itemsCountInAPage < 1) {
			itemsCountInAPage = 1;
		}
		
		int limitFrom = (page - 1) * itemsCountInAPage;
		int limitTake = itemsCountInAPage;
		
		param.put("limitFrom", limitFrom);
		param.put("limitTake", limitTake);
		
		return articleDao.getForPrintArticles(param);
	}

	public Article getForPrintArticleById(int id) {
		return articleDao.getForPrintArticleById(id);
	}
	
	public int writeArticle(Map<String, Object> param) {
		articleDao.writeArticle(param);
		
		int id = Util.getAsInt(param.get("id"));
		
		return id;
	}
	
	public void modifyArticle(int id, String title, String body) {
		articleDao.modifyArticle(id, title, body);
	}

	public void deleteArticleById(int id) {
		articleDao.deleteArticleById(id);
	}

	public int getTotalCount(Map<String, Object> param) {
		return articleDao.getTotalCount(param);
	}
}
