package com.sbs.webp.lolHi.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.webp.lolHi.dao.ReplyDao;
import com.sbs.webp.lolHi.dto.Reply;
import com.sbs.webp.lolHi.util.Util;


@Service
public class ReplyService {
	@Autowired
	private ReplyDao replyDao;

	public int writeReply(Map<String, Object> param) {
		replyDao.writeReply(param);
		
		int id = Util.getAsInt(param.get("id"));
		
		return id;
	}

	public List<Reply> getForPrintReplies(String relTypeCode, int relId) {
		return replyDao.getForPrintReplies(relTypeCode, relId);
	}
}
