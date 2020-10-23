package com.sbs.webp.lolHi.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.webp.lolHi.dao.MemberDao;
import com.sbs.webp.lolHi.util.Util;


@Service
public class MemberService {
	@Autowired
	private MemberDao memberDao;
	
	public int join(Map<String, Object> param) {
		memberDao.join(param);
		
		int id = Util.getAsInt(param.get("id"));
		
		return id;
	}
}