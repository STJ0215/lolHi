package com.sbs.webp.lolHi.controller.usr;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.webp.lolHi.dto.Article;
import com.sbs.webp.lolHi.service.ArticleService;


@Controller
public class ArticleController {
	@Autowired
	private ArticleService articleService;
	
	@RequestMapping("/usr/article/list")
	public String showList(Model model) {
		List<Article> articles = articleService.getArticles();
		
		model.addAttribute("articles", articles);
		
		return "usr/article/list";
	}
	
	@RequestMapping("/usr/article/detail")
	public String showDetail(Model model, int id) {
		Article article = articleService.getArticleById(id);
		
		model.addAttribute("article", article);
		
		return "usr/article/detail";
	}
	
	@RequestMapping("/usr/article/write")
	public String showWrite() {
		return "usr/article/write";
	}
	
	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	public String doWrite(@RequestParam Map<String, Object> param) {
		int id = articleService.writeArticle(param);
		
		return String.format("<script> alert('%d번 게시물이 생성되었습니다.'); location.replace('/usr/article/detail?id=%d'); </script>", id, id);
	}
	
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public String doModify(int id, String title, String body) {
		articleService.modifyArticle(id, title, body);
		
		return String.format("<script> alert('%d번 게시물이 수정되었습니다.'); location.replace('/usr/article/detail?id=%d'); </script>", id, id);
	}
	
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {
		articleService.deleteArticleById(id);
		
		return String.format("<script> alert('%d번 게시물이 삭제되었습니다.'); location.replace('/usr/article/list'); </script>", id);
	}
}
