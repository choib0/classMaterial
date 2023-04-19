package com.itwillbs.test2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// 컨트롤러 역할을 수행하는 클래스 정의 시 @Controller 어노테이션을 클래스 선언부 윗줄에 지정
// => 컨트롤러 클래스는 복수개 정의 가능
@Controller
public class TestController {
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	// "/main" 서블릿 주소 요청 시 자동으로 호출되는 requestMain() 메서드 정의
	// => 파라미터 : 없음   리턴타입 : String
	// => @RequestMapping 어노테이션을 사용하여 "GET 방식" 의 "/main" 서블릿 주소 요청받기
	// => 포워딩 페이지 : index.jsp
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String requestMain() {
		// Dispatcher 방식으로 views 디렉토리 내의 "index.jsp" 페이지 요청
		// => web.xml 파일과 servlet-context.xml 파일 설정으로 인해
		//    이동할 페이지의 파일명(필요에 따라 경로 추가)만 지정하면
		//    디렉토리명("/WEB-INF/views") 과 확장자(".jsp") 가 자동으로 결합됨
		return "index";
	}
	
	// "/main2" 서블릿 주소 요청 시 자동으로 호출되는 requestMain2() 메서드 정의
	// => 파라미터 : 없음   리턴타입 : String
	// => @RequestMapping 어노테이션을 사용하여 "GET 방식" 의 "/main2" 서블릿 주소 요청받기
	// => 포워딩 페이지 : main.jsp
	@RequestMapping(value = "/main2", method = RequestMethod.GET)
	public String requestMain2() {
		return "main";
	}
	
	// "/test1" 서블릿 주소 요청을 처리할 test1() 메서드 정의
	// => @GetMapping 어노테이션을 사용하여 "GET 방식 요청" 전용 처리(test/test1.jsp 로 포워딩)
	@GetMapping(value = "/test1")
	public String test1() {
//		return "test1"; // "/WEB-INF/views/test1.jsp" 위치 지정됨(파일 없음. 404 에러)
		// 서브 디렉토리 사용 시 return "서브디렉토리명/파일명" 형식으로 지정 필요
		return "test/test1";
	}
	// 주의! main.jsp 에서 "test1" 서블릿 주소에 대한 요청을 2개의 폼에서 각각
	// GET 방식과 POST 방식의 요청을 발생시키고 있으나
	// 현재 컨트롤러에서 "test1" 서블릿 주소에 대해 GET 방식 요청만 처리하고 있다!
	// => 따라서, POST 방식으로 "test1" 서블릿 요청 시 요청 메서드가 일치하지 않으므로
	//    405 에러 발생한다! (HTTP 상태 405 – 허용되지 않는 메소드)
	
	// ----------------------------------------------------------------------------------
	// "/test2" 서블릿 주소 요청을 처리할 test2() 메서드 정의
	// => @GetMapping 어노테이션으로 GET 방식 요청을 처리할 test2_get() 메서드 정의, 
	//    @PostMapping 어노테이션으로 POST 방식 요청 처리할 test2_post() 메서드 정의
	// => 두 메서드 모두 "test/test2.jsp" 페이지로 포워딩
	@GetMapping(value = "/test2")
	public String test2_get() {
		logger.info("test2 서블릿에 대한 GET 방식 처리");
		
//		return "test1"; // "/WEB-INF/views/test1.jsp" 위치 지정됨(파일 없음. 404 에러)
		// 서브 디렉토리 사용 시 return "서브디렉토리명/파일명" 형식으로 지정 필요
		return "test/test2";
	}
	
	@PostMapping(value = "/test2")
	public String test2_post() {
		logger.info("test2 서블릿에 대한 POST 방식 처리");
		return "test/test2";
	}
	
	// @RequestMapping 어노테이션을 사용하여 GET 방식과 POST 방식 요청 모두에 대해
	// "test2.jsp" 페이지로 포워딩
//	@RequestMapping(value = "/test2", method = {RequestMethod.GET, RequestMethod.POST})
//	public String test2() {
//		return "test/test2";
//	}
}














