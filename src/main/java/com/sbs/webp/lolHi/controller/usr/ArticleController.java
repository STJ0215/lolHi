package com.sbs.webp.lolHi.controller.usr;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sbs.webp.lolHi.dto.Article;
import com.sbs.webp.lolHi.service.ArticleService;
import com.sbs.webp.lolHi.util.Util;


@Controller
public class ArticleController {
	@Autowired
	private ArticleService articleService;
	
	@RequestMapping("/usr/article/list")
	public String showList(Model model, @RequestParam Map<String, Object> param) {
		List<Article> articles = articleService.getArticles(param);
		
		int totalCount = articleService.getTotalCount();
		int itemsCountInAPage = 10;
		int totalPage = (int)Math.ceil(totalCount / (double)itemsCountInAPage);
		int pageMenuArmSize = 5;
		int page = Util.getAsInt(param.get("page"), 1);
		int pageMenuStart = page - pageMenuArmSize;
		if (pageMenuStart < 1) {
			pageMenuStart = 1;
		}
		int pageMenuEnd = page + pageMenuArmSize;
		if (pageMenuEnd > totalPage) {
			pageMenuEnd = totalPage;
		}
		
		param.put("itemsCountInAPage", itemsCountInAPage);
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("page", page);
		model.addAttribute("pageMenuArmSize", pageMenuArmSize);
		model.addAttribute("pageMenuStart", pageMenuStart);
		model.addAttribute("pageMenuEnd", pageMenuEnd);
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
	public String showWrite(HttpSession session, Model model) {
		int loginedMemberId = 0;
		
		if (session.getAttribute("loginedMemberId") != null) {
			loginedMemberId = (int)session.getAttribute("loginedMemberId");
		}
		
		if (loginedMemberId == 0) {
			model.addAttribute("msg", "로그인 후 이용해주세요.");
			model.addAttribute("replaceUri", "/usr/member/login");
			
			return "common/redirect";
		}
		
		return "usr/article/write";
	}
	
	@RequestMapping("/usr/article/doWrite")
	public String doWrite(HttpSession session, Model model, @RequestParam Map<String, Object> param) {
		int loginedMemberId = 0;
		
		if (session.getAttribute("loginedMemberId") != null) {
			loginedMemberId = (int)session.getAttribute("loginedMemberId");
		}
		
		if (loginedMemberId == 0) {
			model.addAttribute("msg", "로그인 후 이용해주세요.");
			model.addAttribute("replaceUri", "/usr/member/login");
			
			return "common/redirect";
		}
		
		param.put("memberId", loginedMemberId);
		int id = articleService.writeArticle(param);
		
		model.addAttribute("msg", String.format("%d번 게시물이 생성되었습니다.", id));
		model.addAttribute("replaceUri", String.format("/usr/article/detail?id=%d", id));
		
		return "common/redirect";
	}
	
	@RequestMapping("/usr/article/modify")
	public String showModify(HttpSession session, Model model, int id) {
		int loginedMemberId = 0;
		
		if (session.getAttribute("loginedMemberId") != null) {
			loginedMemberId = (int)session.getAttribute("loginedMemberId");
		}
		
		if (loginedMemberId == 0) {
			model.addAttribute("msg", "로그인 후 이용해주세요.");
			model.addAttribute("replaceUri", "/usr/member/login");
			
			return "common/redirect";
		}
		
		Article article = articleService.getArticleById(id);
		
		model.addAttribute("article", article);
		
		return "usr/article/modify";
	}
	
	@RequestMapping("/usr/article/doModify")
	public String doModify(HttpSession session, Model model, int id, String title, String body) {
		int loginedMemberId = 0;
		
		if (session.getAttribute("loginedMemberId") != null) {
			loginedMemberId = (int)session.getAttribute("loginedMemberId");
		}
		
		if (loginedMemberId == 0) {
			model.addAttribute("msg", "로그인 후 이용해주세요.");
			model.addAttribute("replaceUri", "/usr/member/login");
			
			return "common/redirect";
		}
		
		articleService.modifyArticle(id, title, body);
		
		model.addAttribute("msg", String.format("%d번 게시물이 수정되었습니다.", id));
		model.addAttribute("replaceUri", String.format("/usr/article/detail?id=%d", id));
		
		return "common/redirect";
	}
	
	@RequestMapping("/usr/article/doDelete")
	public String doDelete(HttpSession session, Model model, int id) {
		int loginedMemberId = 0;
		
		if (session.getAttribute("loginedMemberId") != null) {
			loginedMemberId = (int)session.getAttribute("loginedMemberId");
		}
		
		if (loginedMemberId == 0) {
			model.addAttribute("msg", "로그인 후 이용해주세요.");
			model.addAttribute("replaceUri", "/usr/member/login");
			
			return "common/redirect";
		}
		
		articleService.deleteArticleById(id);
		
		model.addAttribute("msg", String.format("%d번 게시물이 삭제되었습니다.", id));
		model.addAttribute("replaceUri", "/usr/article/list");
		
		return "common/redirect";
	}
}
