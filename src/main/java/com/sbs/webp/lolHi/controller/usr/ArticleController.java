package com.sbs.webp.lolHi.controller.usr;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sbs.webp.lolHi.dto.Article;
import com.sbs.webp.lolHi.dto.Board;
import com.sbs.webp.lolHi.dto.Member;
import com.sbs.webp.lolHi.dto.Reply;
import com.sbs.webp.lolHi.service.ArticleService;
import com.sbs.webp.lolHi.service.ReplyService;
import com.sbs.webp.lolHi.util.Util;


@Controller
public class ArticleController {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private ReplyService replyService;
	
	@RequestMapping("/usr/article-{boardCode}/list")
	public String showList(HttpServletRequest req, Model model, @PathVariable("boardCode") String boardCode, @RequestParam Map<String, Object> param) {
		Board board = articleService.getBoardByCode(boardCode);
		
		if (board == null) {
			model.addAttribute("msg", "존재하지 않는 게시판입니다.");
			model.addAttribute("historyBack", true);
			
			return "common/redirect";
		}
		
		Member loginedMember = (Member)req.getAttribute("loginedMember");
		
		param.put("boardId", board.getId());
		
		List<Article> articles = articleService.getForPrintArticles(loginedMember, param);
		
		int totalCount = articleService.getTotalCount(param);
		int itemsCountInAPage = 10;
		int totalPage = (int)Math.ceil(totalCount / (double)itemsCountInAPage);
		
		int page = Util.getAsInt(param.get("page"), 1);
		int pageMenuArmSize = 5;
		int pageMenuStart = page - pageMenuArmSize;
		if (pageMenuStart < 1) {
			pageMenuStart = 1;
		}
		int pageMenuEnd = page + pageMenuArmSize;
		if (pageMenuEnd > totalPage) {
			pageMenuEnd = totalPage;
		}
		
		param.put("itemsCountInAPage", itemsCountInAPage);
		
		model.addAttribute("board", board);
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("totalPage", totalPage);
		
		model.addAttribute("page", page);
		model.addAttribute("pageMenuArmSize", pageMenuArmSize);
		model.addAttribute("pageMenuStart", pageMenuStart);
		model.addAttribute("pageMenuEnd", pageMenuEnd);
		
		model.addAttribute("articles", articles);
		
		return "usr/article/list";
	}
	
	@RequestMapping("/usr/article-{boardCode}/detail")
	public String showDetail(HttpServletRequest req, Model model, @PathVariable("boardCode") String boardCode, int id, String listUrl) {
		Board board = articleService.getBoardByCode(boardCode);
		Member loginedMember = (Member)req.getAttribute("loginedMember");
		
		Article article = articleService.getForPrintArticleById(loginedMember, id);
		List<Reply> replies = replyService.getForPrintReplies(loginedMember, "article", id);
		
		if (listUrl == null) {
			listUrl = "/usr/article-free/list";
		}
		
		model.addAttribute("board", board);
		model.addAttribute("article", article);
		model.addAttribute("replies", replies);
		model.addAttribute("listUrl", listUrl);
		
		return "usr/article/detail";
	}
	
	@RequestMapping("/usr/article-{boardCode}/write")
	public String showWrite(HttpServletRequest req, Model model, @PathVariable("boardCode") String boardCode) {
		Board board = articleService.getBoardByCode(boardCode);
		
		model.addAttribute("board", board);
		
		return "usr/article/write";
	}
	
	@RequestMapping("/usr/article-{boardCode}/doWrite")
	public String doWrite(HttpServletRequest req, Model model, @PathVariable("boardCode") String boardCode, @RequestParam Map<String, Object> param) {
		Board board = articleService.getBoardByCode(boardCode);
		int loginedMemberId = (int)req.getAttribute("loginedMemberId");
		
		param.put("boardId", board.getId());
		param.put("memberId", loginedMemberId);
		
		int id = articleService.writeArticle(param);
		
		model.addAttribute("msg", String.format("%d번 게시물이 생성되었습니다.", id));
		model.addAttribute("redirectUri", String.format("/usr/article-%s/detail?id=%d", boardCode, id));
		
		return "common/redirect";
	}
	
	@RequestMapping("/usr/article-{boardCode}/modify")
	public String showModify(HttpServletRequest req, Model model, @PathVariable("boardCode") String boardCode, int id) {
		Board board = articleService.getBoardByCode(boardCode);
		Member loginedMember = (Member)req.getAttribute("loginedMember");
		
		Article article = articleService.getForPrintArticleById(loginedMember, id);
		
		if ((boolean)article.getExtra().get("actorCanModify") == false) {
			model.addAttribute("msg", "수정 권한이 없습니다.");
			model.addAttribute("historyBack", true);
			
			return "common/redirect";
		}
		
		model.addAttribute("board", board);
		model.addAttribute("article", article);
		
		return "usr/article/modify";
	}
	
	@RequestMapping("/usr/article-{boardCode}/doModify")
	public String doModify(HttpServletRequest req, Model model, @PathVariable("boardCode") String boardCode, int id, String title, String body) {
		Member loginedMember = (Member)req.getAttribute("loginedMember");
		
		Article article = articleService.getForPrintArticleById(loginedMember, id);
		
		if ((boolean)article.getExtra().get("actorCanModify") == false) {
			model.addAttribute("msg", "수정 권한이 없습니다.");
			model.addAttribute("historyBack", true);
			
			return "common/redirect";
		}
		
		articleService.modifyArticle(id, title, body);
		
		model.addAttribute("msg", String.format("%d번 게시물이 수정되었습니다.", id));
		model.addAttribute("redirectUri", String.format("/usr/article-%s/detail?id=%d", boardCode, id));
		
		return "common/redirect";
	}
	
	@RequestMapping("/usr/article-{boardCode}/doDelete")
	public String doDelete(HttpServletRequest req, Model model, @PathVariable("boardCode") String boardCode, int id) {
		Member loginedMember = (Member)req.getAttribute("loginedMember");
		
		Article article = articleService.getForPrintArticleById(loginedMember, id);
		
		if ((boolean)article.getExtra().get("actorCanDelete") == false) {
			model.addAttribute("msg", "삭제 권한이 없습니다.");
			model.addAttribute("historyBack", true);
			
			return "common/redirect";
		}
		
		articleService.deleteArticleById(id);
		
		model.addAttribute("msg", String.format("%d번 게시물이 삭제되었습니다.", id));
		model.addAttribute("redirectUri", String.format("/usr/article-%s/list", boardCode));
		
		return "common/redirect";
	}
}
