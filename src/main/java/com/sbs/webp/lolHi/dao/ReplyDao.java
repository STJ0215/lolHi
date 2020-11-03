package com.sbs.webp.lolHi.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbs.webp.lolHi.dto.Reply;


@Mapper
public interface ReplyDao {
	void writeReply(Map<String, Object> param);

	List<Reply> getForPrintReplies(@Param("relTypeCode") String relTypeCode, @Param("relId") int relId);

	Reply getForPrintReplyById(@Param("id") int id);

	void deleteReplyById(@Param("id") int id);
}
