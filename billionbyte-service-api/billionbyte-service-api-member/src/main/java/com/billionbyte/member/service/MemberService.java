package com.billionbyte.member.service;

import com.alibaba.fastjson.JSONObject;
import com.billionbyte.base.BaseResponse;
import com.billionbyte.pojo.UserDo;
import io.swagger.annotations.*;
import org.apache.ibatis.annotations.Delete;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Response;
import org.elasticsearch.search.aggregations.Aggregation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public interface MemberService {

	String member();

	int updateMember(String userPassword,Long userId);

	UserDo  getMember(Long userId);

	int  deleteMember(Long userId);

	int insertMember(UserDo userDo);

	List<UserDo> searchByNameMember(String userName) throws IOException;
	String countGroupByuserIdandSex() throws IOException;
	String avgUserIdGroupBySex() throws IOException;
	String insertMemeberToES(UserDo userDo) throws IOException;
	String updateMemberToES(UserDo userDo) throws IOException;
	List<UserDo> boolSearchByAnyField(UserDo userDo) throws IOException;
//	int deleteAllMemberToES() throws  IOException;
}
