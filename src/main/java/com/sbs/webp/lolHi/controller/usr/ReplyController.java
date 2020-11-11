package com.sbs.webp.lolHi.controller.usr;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sbs.webp.lolHi.dto.Member;
import com.sbs.webp.lolHi.dto.Reply;
import com.sbs.webp.lolHi.service.ReplyService;
import com.sbs.webp.lolHi.util.Util;


@Controller
public class ReplyController {
	@Autowired
	private ReplyService replyService;
	
	@RequestMapping("/usr/reply/doWrite")
	public String doWrite(HttpServletRequest req, Model model, @RequestParam Map<String, Object> param, String replaceUri) {
		int loginedMemberId = (int)req.getAttribute("loginedMemberId");
		param.put("memberId", loginedMemberId);
		
		int id = replyService.writeReply(param);
		String relTypeCode = (String)param.get("relTypeCode");
		int relId = Util.getAsInt(param.get("relId"));
		
		if (replaceUri == null || replaceUri.length() == 0) {
			replaceUri = String.format("/usr/%s/detail?id=%d", relTypeCode, relId);
		}
		
		model.addAttribute("msg", String.format("%d번 댓글이 생성되었습니다.", id));
		model.addAttribute("replaceUri", replaceUri);
		
		return "common/redirect";
	}
	
	@RequestMapping("/usr/reply/modify")
	public String showModify(HttpServletRequest req, Model model, int id, String replaceUri) {
		Member loginedMember = (Member)req.getAttribute("loginedMember"); 
		
		Reply reply = replyService.getForPrintReplyById(loginedMember, id);
		
		if (reply == null) {
			model.addAttribute("msg", "존재하지 않는 댓글입니다.");
			model.addAttribute("historyBack", true);
			
			return "common/redirect";
		}
		
		if ((boolean)reply.getExtra().get("actorCanModify") == false) {
			model.addAttribute("msg", "수정 권한이 없습니다.");
			model.addAttribute("historyBack", true);
			
			return "common/redirect";
		}
		
		if (replaceUri == null || replaceUri.length() == 0) {
			replaceUri = String.format("/usr/%s/detail?id=%d",reply.getRelTypeCode(), reply.getRelId());
		}

		model.addAttribute("reply", reply);
		
		return "usr/reply/modify";
	}
	
	@RequestMapping("/usr/reply/doModify")
	public String doModify(HttpServletRequest req, Model model, @RequestParam Map<String, Object> param, int id, String replaceUri) {
		Member loginedMember = (Member)req.getAttribute("loginedMember");
		
		Reply reply = replyService.getForPrintReplyById(loginedMember, id);
		
		if (reply == null) {
			model.addAttribute("msg", "존재하지 않는 댓글입니다.");
			model.addAttribute("historyBack", true);
			
			return "common/redirect";
		}
		
		if ((boolean)reply.getExtra().get("actorCanModify") == false) {
			model.addAttribute("msg", "수정 권한이 없습니다.");
			model.addAttribute("historyBack", true);
			
			return "common/redirect";
		}
		
		if (replaceUri == null || replaceUri.length() == 0) {
			replaceUri = String.format("/usr/%s/detail?id=%d",reply.getRelTypeCode(), reply.getRelId());
		}
		
		replyService.modifyReply(param);
		
		model.addAttribute("msg", String.format("%d번 댓글이 수정되었습니다.", id));
		model.addAttribute("replaceUri", replaceUri);
		
		return "common/redirect";
	}
	
	@RequestMapping("/usr/reply/doDelete")
	public String doDelete(HttpServletRequest req, Model model, int id, String replaceUri) {
		Member loginedMember = (Member)req.getAttribute("loginedMember"); 
		
		Reply reply = replyService.getForPrintReplyById(loginedMember, id);
		
		if (reply == null) {
			model.addAttribute("msg", "존재하지 않는 댓글입니다.");
			model.addAttribute("historyBack", true);
			
			return "common/redirect";
		}
		
		if ((boolean)reply.getExtra().get("actorCanDelete") == false) {
			model.addAttribute("msg", "삭제 권한이 없습니다.");
			model.addAttribute("historyBack", true);
			
			return "common/redirect";
		}
		
		if (replaceUri == null || replaceUri.length() == 0) {
			replaceUri = String.format("/usr/%s/detail?id=%d",reply.getRelTypeCode(), reply.getRelId());
		}
		
		replyService.deleteReplyById(id);
		
		model.addAttribute("msg", String.format("%d번 댓글이 삭제되었습니다.", id));
		model.addAttribute("replaceUri", replaceUri);
		
		return "common/redirect";
	}
}
