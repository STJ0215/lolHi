package com.sbs.webp.lolHi.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sbs.webp.lolHi.dao.MemberDao;
import com.sbs.webp.lolHi.dto.Member;
import com.sbs.webp.lolHi.dto.ResultData;
import com.sbs.webp.lolHi.util.Util;


@Service
public class MemberService {
	@Autowired
	private MemberDao memberDao;
	
	@Autowired
	private MailService mailService;
	
	@Value("${custom.siteMainUri}")
	private String siteMainUri;
	
	@Value("${custom.siteName}")
	private String siteName;
	
	@Value("${custom.siteLoginUri}")
	private String siteLoginUri;
	
	
	public int join(Map<String, Object> param) {
		memberDao.join(param);
		
		int id = Util.getAsInt(param.get("id"));
		
		sendJoinCompleteMail((String)param.get("email"));
		
		return id;
	}
	
	private void sendJoinCompleteMail(String email) {
		String mailTitle = String.format("[%s] 가입이 완료되었습니다.", siteName);

		StringBuilder mailBodySb = new StringBuilder();
		mailBodySb.append("<h1>가입이 완료되었습니다.</h1>");
		mailBodySb.append(String.format("<p><a href=\"%s\" target=\"_blank\">%s</a>로 이동</p>", siteMainUri, siteName));

		mailService.send(email, mailTitle, mailBodySb.toString());
	}

	public boolean isJoinAvailableLoginId(String loginId) {
		Member member = memberDao.getMemberByLoginId(loginId);
		
		if (member == null) {
			return true;
		}
		
		return false;
	}
	
	public boolean isJoinAvailableNameAndEmail(String name, String email) {
		if (name == null || name.length() == 0) {
			return false;
		}
		if (email == null || email.length() == 0) {
			return false;
		}
		
		Member member = memberDao.getMemberByNameAndEmail(name, email);
		
		return member == null;
	}

	public Member getMemberByLoginId(String loginId) {
		return memberDao.getMemberByLoginId(loginId);
	}

	public Member getMemberById(int id) {
		return memberDao.getMemberById(id);
	}

	public void modify(Map<String, Object> param) {
		memberDao.modify(param);
	}

	public Member getMemberByNameAndEmail(String name, String email) {
		return memberDao.getMemberByNameAndEmail(name, email);
	}

	public ResultData setTempPasswordAndNotify(Member member) {
		Random r = new Random();
		
		// 10000 ~ 99999
		String tempLoginPw = (10000 + r.nextInt(90000)) + "";
		
		String mailTitle = String.format("[%s] 임시 패스워드가 발급되었습니다", siteName);
		String mailBody = "";
		mailBody += String.format("로그인 아이디 : %s", member.getLoginId());
		mailBody += "<br>";
		mailBody += String.format("임시 패스워드 : %s", tempLoginPw);
		mailBody += "<br>";
		mailBody += String.format("<a href=\"%s\" target==\"_blank\">로그인 하러가기</a>", siteLoginUri);
		
		ResultData sendResultData = mailService.send(member.getEmail(), mailTitle, mailBody);
		
		if (sendResultData.isFail()) {
			return new ResultData("F-1", "메일 발송에 실패했습니다.");
		}
		
		Map<String, Object> modifyParam = new HashMap<>();
		
		modifyParam.put("id", member.getId());
		modifyParam.put("loginPw", Util.sha256(tempLoginPw));
		
		memberDao.modify(modifyParam);
		
		return new ResultData("S-1", "임시 패스워드를 이메일로 발송했습니다.");
	}
}
