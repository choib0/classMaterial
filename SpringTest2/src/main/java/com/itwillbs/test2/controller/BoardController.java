package com.itwillbs.test2.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwillbs.test2.vo.BoardVO;

@Controller
public class BoardController {
	
	@GetMapping(value = "write.bo")
	public String write() {
		return "test2/write_form";
	}
	
	// writePro.me 서블릿 요청을 처리할 writePro() 메서드 정의
	// => 전달받은 파라미터 출력
	// => 주의! POST 방식 요청은 @PostMapping 또는 @RequestMapping(method = RequestMethod.POST) 사용
	// 1) 각각의 파라미터에 해당하는 매개변수를 선언하는 방법
//	@PostMapping(value = "writePro.bo")
//	public String writePro(String board_name, String board_pass, String board_subject, String board_content, String board_file) {
//		System.out.println("작성자 : " + board_name);
//		System.out.println("패스워드 : " + board_pass);
//		System.out.println("제목 : " + board_subject);
//		System.out.println("내용 : " + board_content);
//		System.out.println("파일명 : " + board_file);
//		
//		return "redirect:/main3";
//	}
	
	// 2) Map 객체 활용
//	@PostMapping(value = "writePro.bo")
//	public String writePro(@RequestParam Map<String, String> map) {
//		System.out.println("작성자(Map) : " + map.get("board_name"));
//		System.out.println("패스워드(Map) : " + map.get("board_pass"));
//		System.out.println("제목(Map) : " + map.get("board_subject"));
//		System.out.println("내용(Map) : " + map.get("board_content"));
//		System.out.println("파일명(Map) : " + map.get("board_file"));
//		
//		return "redirect:/main3";
//	}
	
	// 3) VO 객체(BoardVO) 활용
	@PostMapping(value = "writePro.bo")
	public String writePro(BoardVO board) {
		System.out.println("작성자(BoardVO) : " + board.getBoard_name());
		System.out.println("패스워드(BoardVO) : " + board.getBoard_pass());
		System.out.println("제목(BoardVO) : " + board.getBoard_subject());
		System.out.println("내용(BoardVO) : " + board.getBoard_content());
		System.out.println("파일명(BoardVO) : " + board.getBoard_file());
		
//		return "redirect:/main3";
		
		// 글쓰기 완료 후 글목록 표시를 위해 list.bo 서블릿으로 리다이렉트
		return "redirect:/list.bo";
	}
	
	// 글쓰기 작업 완료 후 요청되는 list.bo(Redirect 방식)에 대한 매핑 - GET
	// => 글목록 가져오는 작업 생략하고 Dispatch 방식으로 "board_list.jsp" 페이지 요청
	@GetMapping(value = "list.bo")
	public String list(Model model) {
		// 데이터베이스 글목록 조회 비즈니스 로직 수행했다고 가정하고 임의로 글목록 데이터 생성
		// 1. 전체 글목록 저장 위한 List 객체 생성 => 제네릭타입 BoardVO 타입 선언
		List<BoardVO> boardList = new ArrayList<BoardVO>();
		
		// 2. 1개 게시물 정보를 저장하는 BoardVO 객체 생성 후 List 객체에 추가
		boardList.add(new BoardVO(1, "관리자", "1234", "제목1", "내용1", "", 1, 0, 0, 0, new Timestamp(2023, 4, 4, 10, 0, 0, 0)));
		boardList.add(new BoardVO(2, "관리자", "1234", "제목2", "내용2", "", 2, 0, 0, 0, new Timestamp(2023, 4, 4, 10, 0, 0, 0)));
		boardList.add(new BoardVO(3, "관리자", "1234", "제목3", "내용3", "", 3, 0, 0, 0, new Timestamp(2023, 4, 4, 10, 0, 0, 0)));
		
		// 3. 전체 글목록이 저장된 List 객체를 뷰페이지로 전달하기 위해 Model 객체에 추가(저장)
		// => list() 메서드 파라미터에 Model 타입 매개변수 선언 필요
		model.addAttribute("boardList", boardList);
		
		// test2/board_list.jsp 페이지로 포워딩(Dispatch)
		return "test2/board_list";
		
	}
	
}










