package com.itwillbs.test2.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwillbs.test2.vo.MemberVO;

@Controller
public class MemberController {
	
	@GetMapping(value = "login.me")
	public String login() {
		return "test2/login_form";
	}
	
	// loginPro.me 서블릿 요청을 처리할 loginPro() 메서드 정의
	// => 전달받은 파라미터 출력
	// => 주의! POST 방식 요청은 @PostMapping 또는 @RequestMapping(method = RequestMethod.POST) 사용
//	@PostMapping(value = "loginPro.me")
//	public String loginPro() {
//		System.out.println("loginPro()");
//		
//		// 메인페이지로 리다이렉트(main3)
//		return "redirect:/main3";
//	}
	// ==============================================================================
	// [ 요청 파라미터 전달받아 처리하는 방법 ]
	// 1. 파라미터명과 동일한 매개변수를 각각 선언하여 자동으로 전달받는 방법
//	@PostMapping(value = "loginPro.me")
//	public String loginPro(@RequestParam String id, @RequestParam String passwd) {
//		System.out.println("아이디 : " + id + ", 패스워드 : " + passwd);
//		
//		// 메인페이지로 리다이렉트(main3)
//		return "redirect:/main3";
//	}
	
	// 메서드 오버로딩을 통해 GET 방식과 POST 방식의 메서드명을 동일하게 사용할 수도 있다!
//	@PostMapping(value = "loginPro.me")
//	public String login(@RequestParam String id, @RequestParam String passwd) {
//		System.out.println("아이디 : " + id + ", 패스워드 : " + passwd);
//		
//		// 메인페이지로 리다이렉트(main3)
//		return "redirect:/main3";
//	}
	// ----------------------------------------------------------------------------------
	// 2. Map 객체를 통해 파라미터명을 Key, 파라미터값을 Value 로 전달받는 방법
	// => 주의! Map 객체에는 일치하는 변수가 존재하지 않으므로
	//    파라미터를 매핑시키는 용도로 Map 을 선언했다고 알려주기 위해 @RequestParam 어노테이션 사용
//	@PostMapping(value = "loginPro.me")
//	public String login(@RequestParam Map<String, String> map) {
//		System.out.println("아이디(Map) : " + map.get("id") + ", 패스워드(Map) : " + map.get("passwd"));
//		
//		// 메인페이지로 리다이렉트(main3)
//		return "redirect:/main3";
//	}
	
	// 3. VO 객체를 통해 일치하는 멤버변수에 파라미터값을 자동으로 전달받는 방법
	// => com.itwillbs.test2.vo.MemberVO 클래스 정의 필요
//	@PostMapping(value = "loginPro.me")
//	public String login(MemberVO member) {
//		System.out.println(
//				"아이디(MemberVO) : " + member.getId() + ", 패스워드(MemberVO) : " + member.getPasswd());
//		
//		// 메인페이지로 리다이렉트(main3)
//		return "redirect:/main3";
//	}
	
	// 로그인 처리를 위해 HttpSession 객체 활용
	@PostMapping(value = "loginPro.me")
	public String login(MemberVO member, HttpSession session) {
		System.out.println(
				"아이디(MemberVO) : " + member.getId() + ", 패스워드(MemberVO) : " + member.getPasswd());
		
		// 로그인 성공했다고 가정
		// 세션 객체에 "sId" 속성명으로 아이디 저장
		session.setAttribute("sId", member.getId());
		
		// 메인페이지로 리다이렉트(main3)
		return "redirect:/main3";
	}
	
	// logout.me 서블릿 요청 시 로그아웃 처리를 수행하는 logout() 메서드 매핑 - GET
	// => session 객체의 invalidate() 메서드를 통해 세션 초기화 후 메인페이지(main3) 요청
	@GetMapping(value = "logout.me")
	public String logout(HttpSession session) {
		session.invalidate();
		
		return "redirect:/main3";
	}
	
}













