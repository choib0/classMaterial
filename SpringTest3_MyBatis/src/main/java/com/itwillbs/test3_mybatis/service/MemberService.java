package com.itwillbs.test3_mybatis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itwillbs.test3_mybatis.mapper.MyBatisMapper;
import com.itwillbs.test3_mybatis.vo.BoardVO;
import com.itwillbs.test3_mybatis.vo.MemberVO;

@Service
public class MemberService {
	@Autowired
	private MyBatisMapper mapper;

	public MemberVO checkUser(String id, String passwd) {
		// 로그인을 위한 조회 작업 요청을 위해 Mapper 객체의 selectUser() 메서드 호출
		// => 파라미터 : 아이디, 패스워드   리턴타입 : MemberVO
		return mapper.selectUser(id, passwd);
	}

	public int registMember(MemberVO member) {
		// 회원 가입을 위한 INSERT 작업 요청을 위해 Mapper - insertMember() 메서드 호출
		// => 파라미터 : MemberVO 객체    리턴타입 : int(insertCount)
		return mapper.insertMember(member);
	}

	
}











