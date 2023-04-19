package com.itwillbs.test3_mybatis.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwillbs.test3_mybatis.service.BoardService;
import com.itwillbs.test3_mybatis.service.MemberService;
import com.itwillbs.test3_mybatis.vo.BoardVO;
import com.itwillbs.test3_mybatis.vo.MemberVO;

@Controller
public class MyBatisController {
	private static final Logger logger = LoggerFactory.getLogger(MyBatisController.class);
	
	/*
	 * 컨트롤러 클래스가 서비스 클래스에 의존적일 때
	 * 서비스 클래스의 인스턴스를 직접 생성하지 않고, 자동 주입 기능을 통해 접근할 수 있다.
	 * => @Inject(자바-플랫폼 공용) 또는 @Autowired(스프링 전용) 어노테이션 사용
	 * => 어노테이션 지정 후 자동 주입으로 객체 생성 시 객체가 저장될 클래스 타입 변수 선언
	 * => 주의! 서비스 클래스의 경우 클래스에 @Service 어노테이션을 지정해야한다! 
	 */
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private MemberService memberService;
	
	// "/main" 서블릿 주소를 매핑하는 main() 메서드 정의 - GET
	// => main.jsp 페이지로 포워딩
	@GetMapping(value = "/main")
	public String main() {
		return "main";
	}
	
	@GetMapping(value = "/login.me")
	public String loginForm() {
		return "login_form";
	}
	
	// 로그인 폼에서 입력받은 아이디 패스워드를 전달받는 loginPro() 메서드 정의
	// => 파라미터 : MemberVO 지정 가능하나 변수 2개 직접 선언
	@PostMapping(value = "/loginPro.me")
	public String loginPro(
			@RequestParam String id, 
			@RequestParam String passwd, 
			HttpSession session,
			Model model) {
		System.out.println("아이디 : " + id);
		System.out.println("패스워드 : " + passwd);
		
		// MemberService - checkUser() 메서드 호출하여 로그인 판별 작업 요청
		// => 파라미터 : 아이디, 패스워드    리턴타입 : MemberVO(member)
		MemberVO member = memberService.checkUser(id, passwd);
//		System.out.println(member);
		
		// 만약, 로그인 판별 후 리턴받은 MemberVO 객체가 null 이면 로그인 실패, 아니면 성공 판정
		if(member == null) {
			System.out.println("로그인 실패!");
			
			// "msg" 속성에 "로그인 실패!" 메세지를 저장(Model 객체 필요) 후 
			// fail_back.jsp 페이지로 포워딩(Dispatch)
			model.addAttribute("msg", "로그인 실패!");
			return "fail_back";
		} else {
			System.out.println("로그인 성공!");
			
			// 세션 객체에 "sId" 라는 속성명으로 로그인 아이디를 저장
			session.setAttribute("sId", id);
			
			// 작업 완료 후 메인페이지 리다이렉트
			return "redirect:/main";
		}
		
	}
	
	@GetMapping(value = "/write.bo")
	public String writeForm() {
		return "write_form";
	}
	
	@PostMapping(value = "/writePro.bo")
	public String writePro(BoardVO board) {
		// 입력된 폼 파라미터 데이터 가져와서 BoardVO 객체에 저장 후 콘솔에 출력
		// => 파라미터를 저장할 매개변수 BoardVO 타입을 메서드 파라미터로 선언
//		System.out.println(board);
		
		// BoardService - registBoard() 메서드를 호출하여 글 등록 작업 요청
		// => 파라미터 : BoardVO 객체   리턴타입 : int(insertCount)
//		BoardService service = new BoardService();
		
		// 컨트롤러에서 서비스 클래스의 인스턴스를 직접 생성해도 되지만
		// 스프링의 DI(의존 주입) 기능을 활용하면 자동으로 객체를 주입받을 수 있다!
		// 1. Service 클래스 정의 시 @Service 어노테이션을 적용
		// 2. Controller 클래스에서 @Autowired 어노테이션을 사용하여 Service 클래스를 멤버변수로 선언
		// => 해당 인스턴스 생성 없이도 자동 주입되므로 service 멤버변수 바로 사용 가능
		int insertCount = boardService.registBoard(board);
		
		// 작업 완료 후 글목록 페이지(list.bo) 리다이렉트
		return "redirect:/list.bo";
	}
	
	@GetMapping(value = "/list.bo")
	public String list(Model model) {
		// 글목록 조회를 위해 BoardService - getBoardList() 메서드 호출
		// => 파라미터 : 없음   리턴타입 : List<BoardVO>(boardList)
		List<BoardVO> boardList = boardService.getBoardList();
		
		// 조회 결과(List 객체)를 Model 객체에 추가("boardList") 후 board_list.jsp 로 포워딩
		model.addAttribute("boardList", boardList);
		
		return "board_list";
	}
	
	@GetMapping(value = "/join.me")
	public String joinForm() {
		return "join_form";
	}
	
	// 회원가입 정보를 전달받아 가입 요청 작업 수행
	// => 단, email 파라미터는 없고, email1 과 email2 파라미터가 존재하므로
	//    VO 클래스에 각 변수를 추가하거나 파라미터에 각 매개변수를 추가할 수 있다.
	@PostMapping(value = "/joinPro.me")
	public String joinPro(
			MemberVO member, 
			@RequestParam String email1, 
			@RequestParam String email2, 
			Model model) {
//		System.out.println(member);
//		System.out.println(email1);
//		System.out.println(email2);
		
		// email1 과 email2 를 하나의 문자열로 결합하여 MemberVO 객체의 email 에 저장
		member.setEmail(email1 + "@" + email2);
		
		// MemberService - registMember() 메서드를 호출하여 회원가입 작업 요청
		// => 파라미터 : MemberVO 객체    리턴타입 : int(insertCount)
		int insertCount = memberService.registMember(member);
		
		// insertCount 가 0보다 크면 가입 성공, 아니면 실패
		// => 가입 성공 시 메인페이지로 리다이렉트
		//    실패 시 fail_back.jsp 페이지를 통해 "회원 가입 실패!" 출력 후 이전페이지로 돌아가기
		if(insertCount > 0) {
			// 메인페이지로 리다이렉트
			return "redirect:/main";
		} else {
			// Model 객체에 메세지 저장
			model.addAttribute("msg", "회원 가입 실패!");
			return "fail_back";
		}
		
	}
	
	@GetMapping(value = "logout.me")
	public String logout(HttpSession session) {
		// 세션 초기화 후 메인페이지로 리다이렉트
		session.invalidate();
		
		return "redirect:/main";
	}
	
	
}














