package com.itwillbs.test3_mybatis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itwillbs.test3_mybatis.mapper.MyBatisMapper;
import com.itwillbs.test3_mybatis.vo.BoardVO;

// 스프링의 서비스 클래스 역할 수행을 위한 클래스 정의 시 @Service 어노테이션을 지정
// => @Service 어노테이션이 붙은 클래스는 컨트롤러 클래스에서
//    @Autowired 어노테이션 등으로 자동 주입에 활용할 수 있다!
@Service
public class BoardService {
	// MyBatis 를 통해 SQL 구문 처리를 담당할 XXXMapper.xml 파일과 연동되는
	// XXXMapper 객체를 자동 주입받기 위해 @Autowired 어노테이션으로 멤버변수 선언
	@Autowired
	private MyBatisMapper mapper;

	// 글 등록 요청 작업을 위한 registBoard() 메서드
//	public int registBoard(BoardVO board) {
////		System.out.println("BoardService - registBoard()");
//		
//		// DB 작업을 수행할 Mapper 객체의 메서드를 호출하여 SQL 구문 실행 요청
//		// => DAO 없이 마이바티스 활용을 위한 Mapper 객체의 메서드 실행 후
//		//    리턴되는 결과값을 전달받아 Controller 클래스로 다시 리턴해주는 역할 수행
//		// => 단, 별도의 추가적인 작업이 없으므로 return 문 뒤에 메서드 호출 코드를 직접 기술하고
//		//    만약, 메서드 호출 전후 추가적인 작업이 필요한 경우 호출코드와 리턴문을 분리
//		// Mapper 역할을 수행하는 XXXMapper 인터페이스는 인스턴스 생성이 불가능하며
//		// 스프링에서 자동 주입으로 객체를 전달받을 수 있도록 @Autowired 어노테이션 사용하여 선언
//		return mapper.insertBoard(board);
//	}
	
//	public int registBoard(BoardVO board) {
//		// 기존 게시물의 최대 글번호 조회를 위한 selectMaxBoardNum() 메서드 호출
//		// => 파라미터 : 없음   리턴타입 : int
//		int board_num = mapper.selectMaxBoardNum();
////		System.out.println(board_num);
//		
//		// BoardVO 객체의 글번호(board_num) 값을 조회된 결과값으로 설정
//		board.setBoard_num(board_num);
//		
//		return mapper.insertBoard(board);
//	}
	
	// 별도로 최대 글번호를 조회하여 사용하지 않고, Mapper.xml 에서 직접 조회하여 사용할 경우
	public int registBoard(BoardVO board) {
		return mapper.insertBoard2(board);
	}

	public List<BoardVO> getBoardList() {
		// 글목록 조회를 위해 selectBoardList() 메서드 호출
		// => 파라미터 : 없음   리턴타입 : List<BoardVO>
		return mapper.selectBoardList();
	}
	
}











