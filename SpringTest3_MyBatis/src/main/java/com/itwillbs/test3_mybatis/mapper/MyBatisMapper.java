package com.itwillbs.test3_mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.itwillbs.test3_mybatis.vo.BoardVO;
import com.itwillbs.test3_mybatis.vo.MemberVO;

public interface MyBatisMapper {
	// Service 로부터 호출받아 SQL 구문 실행을 위해 XML 파일과 연결될 추상메서드 정의
	// 주의! 메서드명과 XXXMapper.xml 파일 내의 태그에 지정된 id 속성명이 동일해야한다!
	// => 이 때, root-context.xml 파일 내에 지정된 다음 코드에 의해 현재 인터페이스와 
	//    XML 파일 내의 namespace 속성에 지정된 객체와 자동 연결됨
	//    (<property name="mapperLocations" value="classpath:/com/itwillbs/test3_mybatis/mapper/*Mapper.xml"></property>)
	//    (<mapper namespace="com.itwillbs.test3_mybatis.mapper.MyBatisMapper"></mapper>)
	
	// 1. 글 등록 작업을 수행하기 위핸 insertBoard() 메서드 정의 - 추상메서드
	// => 파라미터 : BoardVO 객체   리턴타입 : int
	int insertBoard(BoardVO board); // public abstract 생략됨
	// => 자동으로 MyBatisMapper.xml 파일의 <insert id="insertBoard"> 태그와 연결되어 실행됨
	// => 이 때, XML 에서 단일 파라미터일 경우 해당 객체에 그대로 접근 가능
	// 2. 최대 글번호 조회 작업 수행을 위한 selectMaxBoardNum() 메서드 정의
	int selectMaxBoardNum();
	// ---------------------------------------------------------
	// 최대 글번호 조회를 분리하지 않고 하나의 SQL 태그에서 작업
	int insertBoard2(BoardVO board);
	// ---------------------------------------------------------

	// 3. 로그인 작업 수행을 위한 selectUser() 메서드 정의
	// => 주의! MemberVO 객체를 파라미터로 지정하지 않고, 
	//    아이디와 패스워드를 별도의 변수로 각각 전달받을 경우(= 즉, 복수개의 파라미터 지정 시)
	//    각 파라미터에 @Param 어노테이션을 사용하여 파라미터명을 별도로 지정 필수!
	//    => @Param("파라미터명") 데이터타입 변수명
	MemberVO selectUser(@Param("id") String id, @Param("passwd") String passwd);

	// 4. 회원 가입 작업 수행을 위한 insertMember()
	int insertMember(MemberVO member);

	// 5. 게시물 목록 조회를 위한 selectBoardList()
	List<BoardVO> selectBoardList();
	
}














