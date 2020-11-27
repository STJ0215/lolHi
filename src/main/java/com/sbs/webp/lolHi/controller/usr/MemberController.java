package com.sbs.webp.lolHi.controller.usr;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sbs.webp.lolHi.dto.Member;
import com.sbs.webp.lolHi.dto.ResultData;
import com.sbs.webp.lolHi.service.MemberService;
import com.sbs.webp.lolHi.util.Util;


@Controller
public class MemberController {
	@Autowired
	private MemberService memberService;
	
	@RequestMapping("/usr/member/join")
	public String showJoin() {
		return "usr/member/join";
	}
	
	@RequestMapping("/usr/member/doJoin")
	public String doJoin(Model model, @RequestParam Map<String ,Object> param) {
		String loginId = Util.getAsStr(param.get("loginId"), "");
		String name = Util.getAsStr(param.get("name"), "");
		String email = Util.getAsStr(param.get("email"), "");
		
		if (loginId.length() == 0) {
			model.addAttribute("msg", "로그인 아이디를 입력해주세요.");
			model.addAttribute("historyBack", true);
			
			return "common/redirect";
		}
		
		boolean isJoinAvailableLoginId = memberService.isJoinAvailableLoginId(loginId);
		
		if (isJoinAvailableLoginId == false) {
			model.addAttribute("msg", String.format("%s(은)는 이미 사용중인 로그인 아이디입니다.", loginId));
			model.addAttribute("historyBack", true);
			
			return "common/redirect";
		}
		
		boolean isJoinAvailableNameAndEmail = memberService.isJoinAvailableNameAndEmail(name, email);

		if (isJoinAvailableNameAndEmail == false) {
			model.addAttribute("msg", String.format("이미 가입된 회원의 정보입니다."));
			model.addAttribute("redirectUri", "/usr/member/findLoginId");
			return "common/redirect";
		}
		
		int id = memberService.join(param);
		
		model.addAttribute("msg", "회원 가입이 완료되었습니다");
		model.addAttribute("redirectUri", "/usr/article-free/list");
		
		return "common/redirect";
	}
	
	@RequestMapping("/usr/member/login")
	public String showLogin() {
		return "usr/member/login";
	}
	
	@RequestMapping("/usr/member/doLogin")
	public String doLogin(HttpSession session, Model model, String loginId, String loginPw) {
		if (loginId.length() == 0) {
			model.addAttribute("msg", "로그인 아이디를 입력해주세요.");
			model.addAttribute("historyBack", true);
			
			return "common/redirect";
		}
		
		Member member = memberService.getMemberByLoginId(loginId);
		
		if (member == null) {
			model.addAttribute("msg", String.format("%s(은)는 존재하지 않는 로그인 아이디입니다.", loginId));
			model.addAttribute("historyBack", true);
			
			return "common/redirect";
		}
		
		if (member.getLoginPw().equals(loginPw) == false) {
			model.addAttribute("msg", "로그인 패스워드가 일치하지 않습니다.");
			model.addAttribute("historyBack", true);
			
			return "common/redirect";
		}
		
		session.setAttribute("loginedMemberId", member.getId());
		
		model.addAttribute("msg", String.format("%s님 환영합니다.", member.getName()));
		model.addAttribute("redirectUri", "/usr/article-free/list");
		
		return "common/redirect";
	}
	
	@RequestMapping("/usr/member/doLogout")
	public String doLogout(HttpSession session, Model model) {
		session.removeAttribute("loginedMemberId");
		
		model.addAttribute("redirectUri", "/usr/article-free/list");
		
		return "common/redirect";
	}
	
	@RequestMapping("/usr/member/findLoginId")
	public String showFindLoginId() {
		return "usr/member/findLoginId";
	}
	
	@RequestMapping("/usr/member/doFindLoginId")
	public String doFindLoginId(Model model, String name, String email) {
		Member member = memberService.getMemberByNameAndEmail(name, email);
		
		if (member == null) {
			model.addAttribute("msg", "존재하지 않는 회원입니다.");
			model.addAttribute("historyBack", true);
			
			return "common/redirect";
		}
		
		model.addAttribute("msg", String.format("회원님의 아이디 : %s, 가입일 : %s", member.getLoginId(), member.getRegDate()));
		model.addAttribute("redirectUri", "/usr/member/login");
		
		return "common/redirect";
	}
	
	@RequestMapping("/usr/member/findLoginPw")
	public String showFindLoginPw() {
		return "usr/member/findLoginPw";
	}
	
	@RequestMapping("/usr/member/doFindLoginPw")
	public String doFindLoginPw(Model model, String loginId, String email) {
		Member member = memberService.getMemberByLoginId(loginId);
		
		if (member == null) {
			model.addAttribute("msg", "존재하지 않는 회원입니다.");
			model.addAttribute("historyBack", true);
			
			return "common/redirect";
		}
		
		if (member.getEmail().equals(email) == false) {
			model.addAttribute("msg", "이메일을 다시 확인해주세요.");
			model.addAttribute("historyBack", true);
			
			return "common/redirect";
		}
		
		ResultData setTempPasswordAndNotifyRsData = memberService.setTempPasswordAndNotify(member);
		
		model.addAttribute("msg", setTempPasswordAndNotifyRsData.getMsg());
		model.addAttribute("redirectUri", "/usr/member/login");
		
		return "common/redirect";
	}
	
	@RequestMapping("/usr/member/modify")
	public String showModify(HttpServletRequest req, Model model, String checkLoginPwAuthCode) {
		int loginedMemberId = (int) req.getAttribute("loginedMemberId");
		
		if (checkLoginPwAuthCode == null || checkLoginPwAuthCode.length() == 0) {
			model.addAttribute("msg", "비밀번호 체크 인증코드가 없습니다.");
			model.addAttribute("historyBack", true);
			
			return "common/redirect";
		}

		ResultData checkValidCheckPasswordAuthCodeResultData = memberService.checkValidCheckLoginPwAuthCode(loginedMemberId, checkLoginPwAuthCode);

		if (checkValidCheckPasswordAuthCodeResultData.isFail()) {
			model.addAttribute("msg", checkValidCheckPasswordAuthCodeResultData.getMsg());
			model.addAttribute("historyBack", true);
			
			return "common/redirect";
		}
		
		return "usr/member/modify";
	}
	
	@RequestMapping("/usr/member/doModify")
	public String doModify(HttpServletRequest req, Model model, @RequestParam Map<String, Object> param) {
		int loginedMemberId = (int)req.getAttribute("loginedMemberId");
		
		param.put("id", loginedMemberId);
		
		// 해킹방지
		param.remove("loginId");
		param.remove("loginPw");
		
		memberService.modify(param);
		
		model.addAttribute("msg", "수정되었습니다.");
		model.addAttribute("redirectUri", "/usr/article-free/list");
		
		return "common/redirect";
	}
	
	@RequestMapping("/usr/member/checkLoginPw")
	public String showCheckLoginPw() {
		return "usr/member/checkLoginPw";
	}
	
	@RequestMapping("/usr/member/doCheckLoginPw")
	public String doCheckLoginPw(HttpServletRequest req, Model model, String loginPw, String redirectUri) {
		Member loginedMember = (Member)req.getAttribute("loginedMember");
		
		if (loginedMember.getLoginPw().equals(loginPw) == false) {
			model.addAttribute("msg", "");
			model.addAttribute("historyBack", true);
			
			return "common/redirect";
		}
		
		String authCode = memberService.genCheckLoginPwAuthCode(loginedMember.getId());
		
		if (redirectUri == null || redirectUri.length() == 0) {
			redirectUri = "/usr/home/main";
		}
		
		redirectUri = Util.getNewUri(redirectUri, "checkLoginPwAuthCode", authCode);
		
		model.addAttribute("redirectUri", redirectUri);
		
		return "common/redirect";
	}
}
