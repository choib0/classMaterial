package com.itwillbs.test2.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.itwillbs.test2.vo.PersonVO;
import com.itwillbs.test2.vo.TestVO;

@Controller
public class TestController2 {

	// "main3" 서블릿 요청 시 "test2/main.jsp" 페이지로 포워딩
	@GetMapping(value = "main3")
	public String main() {
		return "test2/main";
	}
	
	// ======================================================================================
	/*
	 * 스프링에서 다른 곳으로 Dispatcher 방식 포워딩 시 데이터를 전달하는 방법 2가지
	 *  1) 기존에 사용하던 HttpServletRequest 객체의 setAttribute() 사용
	 *     => JSP 가 아니므로 내장 객체 request 가 존재하지도 않고
	 *        컨트롤러 파라미터에 HttpServletRequest 객체가 명시되어 있지도 않음
	 *        따라서, 외부로부터 request 객체를 전달받아야 함
	 *     => 스프링에서는 의존주입(DI = Dependency Injection) 을 통해 전달받을 수 있음
	 *     => push() 메서드 내의 파라미터 타입으로 HttpServletRequest 타입을 선언하면
	 *        스프링에 의해 해당 객체가 자동으로 전달(= 주입) 됨
	 *  2) 스프링 전용 Model 객체의 addAttribute() 사용
	 *     => org.springframework.ui.Model 타입을 파라미터로 지정 시
	 *        데이터 저장이 가능한 Model 객체를 자동으로 주입받을 수 있음
	 *     => HttpServletRequest 객체와 성격이 유사하며,
	 *        java.util.Map 객체 기반으로 만든 스프링에서 제공하는 데이터 공유 객체
	 */
//	@GetMapping(value = "push")
//	public String push(HttpServletRequest request) { // 1번 방법(request 객체 주입 받기)
//		// request 객체에 "msg" 라는 속성명으로 "Hello, World! - request" 문자열 저장
//		request.setAttribute("msg", "Hello, World! - request");
//		
//		// request 객체에 "test" 라는 속성명으로 TestVO 객체 저장
//		request.setAttribute("test", new TestVO("제목 - request", "내용 - request"));
//		
//		// Dispatcher 방식 포워딩을 통해 "/WEB-INF/views/test2/push.jsp" 페이지로 포워딩
//		// => URL 이 유지되고, request 객체가 유지됨
//		return "test2/push"; // Dispatcher 방식
//	}
	
	
	@GetMapping(value = "push")
	public String push(Model model) { // 2번 방법(Model 객체 주입 받기)
		// request.setAttribute() 와 마찬가지로 Model 객체의 addAttribute() 로 데이터 저장
		// => request 객체와 범위(Scope) 동일(하나의 요청에 대한 응답 발생 지점까지)
		// => request 객체와 동시 사용 불가(일반적인 데이터 저장 시 request 객체보다 Model 객체 사용)
		// => 파라미터가 완벽하게 동일
		model.addAttribute("msg", "Hello, World! - Model 객체");
		model.addAttribute("test", new TestVO("제목 - Model 객체", "내용 - Model 객체"));
		
		// Dispatcher 방식 포워딩을 통해 "/WEB-INF/views/test2/push.jsp" 페이지로 포워딩
		// => URL 이 유지되고, request 객체가 유지됨
		// => Model 객체로 저장한 데이터도 request 객체와 동일하게 접근 가능
		//    (즉, View 페이지에서는 request 객체에 저장된 채로 사용됨)
		return "test2/push"; // Dispatcher 방식
	}
	
	// ======================================================================================
	// 스프링 컨트롤러에서 리다이렉트 방식 포워딩 처리를 수행하려면
	// return 문에 "redirect:/리다이렉트할주소" 형식으로 지정
	// => 주의! 리다이렉트 방식의 경우 request 또는 model 객체를 통해 데이터 전달 불가
	//    (URL 에 파라미터를 붙여서 전달해야함)
	// => 리다이렉트 방식은 새로운 서블릿 주소로 요청이 발생할 때 사용
//	@GetMapping(value = "redirect")
//	public String redirect() {
//		// 리다이렉트 방식으로 포워딩 할 새 서블릿 주소 "redirectServlet" 지정
//		return "redirect:/redirectServlet";
//		// => 리다이렉트 방식 포워딩 시 주소표시줄의 요청 URL 이 
//		//    기존의 "redirect" 주소가 "redirectServlet" 으로 변경됨
//	}
	
	// "redirectServlet" 주소 요청에 대해 
	// Dispatcher 방식으로 포워딩 수행할 redirectServlet() 메서드 정의
	// => test2/redirect.jsp 페이지로 포워딩
//	@GetMapping(value = "redirectServlet")
//	public String redirectServlet() {
//		// Dispatcher 방식으로 포워딩 할 뷰페이지의 이름을 return 문에 기술
//		return "test2/redirect";
//		// => Dispatcher 방식이므로 주소표시줄의 요청 URL 이 그대로(redirectServlet) 유지됨
//	}
	// -------------------------------------------------------------------------------------
	// 스프링에서 리다이렉트 방식일 때 데이터 전달 방법
	// => request 객체나 model 객체 사용 불가능하며, URL 을 통해서 파라미터로 전달
//	@GetMapping(value = "redirect")
//	public String redirect() {
//		String name = "hong";
//		int age = 20;
//		
//		// redirectServlet 서블릿 주소 요청 시 이름과 나이를 URL 파라미터로 전달 
//		return "redirect:/redirectServlet?name=" + name + "&age=" + age;
//	}
	
	// 리다이렉트 요청 시 전달받은 파라미터 데이터를 컨트롤러 메서드 내에서 접근할 경우
	// 1) HttpServletRequest 객체를 활용한 기존 방법(스프링에서 사용하지 않는 방식)
	//    => 컨트롤러 메서드 파라미터에 HttpServletRequest 타입 파라미터 선언 필요
//	@GetMapping(value = "redirectServlet")
//	public String redirectServlet(HttpServletRequest request) {
//		// request 객체로부터 파라미터(name, age) 꺼내서 변수에 저장 및 콘솔에 출력
//		String name = request.getParameter("name");
//		int age = Integer.parseInt(request.getParameter("age"));
//		System.out.println("이름 : " + name + ", 나이 : " + age);
//		
//		return "test2/redirect";
//	}
	
	// 2) 전달받은 파라미터명과 동일한 이름의 파라미터만 선언하면
	//    이름이 일치하는 파라미터 데이터를 자동으로 저장(= 자동 주입) => GET or POST 무관
//	@GetMapping(value = "redirectServlet")
//	public String redirectServlet(String name, int age) {
//		// 데이터가 자동으로 파라미터에 저장되므로 즉시 사용 가능
//		System.out.println("각각의 파라미터(변수) 선언 방식");
//		System.out.println("이름 : " + name + ", 나이 : " + age);
//		
//		return "test2/redirect";
//	}
	
	// 3) 전달받은 파라미터명과 동일한 이름의 변수가 선언된 VO 클래스를 파라미터로 지정하면
	//    해당 VO 클래스의 인스턴스 생성 및 Setter 호출을 통한 데이터 저장까지 자동으로 수행
	//    => 주의! VO 클래스에 기본 생성자와 Setter 정의 필수!
//	@GetMapping(value = "redirectServlet")
//	public String redirectServlet(PersonVO person) {
//		// PersonVO 객체 생성 및 데이터가 자동으로 저장되므로 즉시 객체 접근 가능
//		System.out.println("PersonVO 타입 파라미터 선언 방식");
//		System.out.println("이름 : " + person.getName() + ", 나이 : " + person.getAge());
//		
//		return "test2/redirect";
//	}
	
	// 4) java.util.Map 타입을 파라미터로 선언하는 방법
	//    => 파라미터명을 key 로, 파라미터값을 value 한 쌍으로 갖는 Map 객체를 자동으로 생성 
//	@GetMapping(value = "redirectServlet")
//	public String redirectServlet(@RequestParam Map<String, String> map) {
//		// PersonVO 객체 생성 및 데이터가 자동으로 저장되므로 즉시 객체 접근 가능
//		System.out.println("Map 타입 파라미터 선언 방식");
//		System.out.println("이름 : " + map.get("name") + ", 나이 : " + map.get("age"));
//		
//		return "test2/redirect";
//	}
	
	
	// @RequestParam 어노테이션 테스트용 age 파라미터 삭제를 위한 redirect() 메서드 정의
	@GetMapping(value = "redirect")
	public String redirect() {
		String name = "hong";
		int age = 20;
		
		// redirectServlet 서블릿 주소 요청 시 이름과 나이를 URL 파라미터로 전달(age 제외시키기)
		return "redirect:/redirectServlet?name=" + name;
	}
	
	// 만약, 파라미터를 전달받을 변수를 선언했을 경우
	// 수치 데이터 타입(int, long, float, double 등)이 파라미터 타입일 경우
	// 문자열을 해당 타입으로 변환하는 과정이 추가되므로
	// 해당 파라미터가 전달되지 않았을 때는 변환 과정에서 오류가 발생한다!
	// => 웹상에서 상태코드 400번으로 표시됨
	// 1) 가능할 경우 파라미터 타입을 모두 String 타입으로 지정 후 필요에 따라 변환하는 작업 수행
	// 2) RequestParam 어노테이션을 사용하여 파라미터 데이터라는 표시를 달고,
	//    기본값 설정을 통해 값이 전달되지 않았을 때를 대비할 수 있다.
	//    => @RequestParam(defaultValue = "기본값") 형태로 기본값 지정
	//       (단, 모든 파라미터는 문자열 형태로 취급)
	//       (만약, 파라미터가 전달되었을 경우에는 defaultValue 는 사용하지 않음)
	@GetMapping(value = "redirectServlet")
	public String redirectServlet(
			@RequestParam(defaultValue = "") String name, 
			@RequestParam(defaultValue = "0") int age) {
		System.out.println("이름 : " + name + ", 나이 : " + age);
		// => 전달되지 않은 파라미터가 있을 경우 defaultValue 로 설정된 값이 자동으로 저장됨
		
		return "test2/redirect";
	}
	
	// ======================================================================================
	// ModelAndView 객체
	// - 데이터 저장 용도의 Model 객체와 view 페이지 포워딩 기능을 함께 수행하는 객체
	// - 매핑 메서드 정의 시 리턴타입을 ModelAndView 타입으로 지정
	// - 데이터 저장 및 뷰페이지 포워딩을 일관된 방식으로 처리할 수 있음
	@GetMapping(value = "mav")
	public ModelAndView modelAndView() {
		// ModelAndView 객체 사용할 경우 데이터 저장용 객체로 Map 객체 사용
		// => Map<String, 저장데이터타입> 형식으로 제네릭타입 지정
		// Map 객체(map)에 "person" 이라는 속성명으로 PersonVO 객체 1개 저장
//		Map<String, PersonVO> map = new HashMap<String, PersonVO>();
//		map.put("person", new PersonVO("이순신", 44));
		
		// Map 객체(map)에 "test" 라는 속성명으로 TestVO 객체 1개 저장
		Map<String, TestVO> map = new HashMap<String, TestVO>();
		map.put("test", new TestVO("제목입니다.", "내용입니다."));
		
		// 기존 방식을 통해 객체 저장 후 뷰페이지로 이동하는 방법
		// => Model 객체에 Map 객체 저장 후 return 문을 통해 뷰페이지 지정
		// => 메서드 리턴타입 : String, 메서드 파라미터 : Model
//		model.addAttribute("map", map);
//		return "test2/model_and_view";
		// --------------------------------------------------------------
		// ModelAndView 객체를 사용하여 객체 저장 및 뷰페이지 지정 수행
		// => 객체 생성 : new ModelAndView("이동할 페이지", "저장데이터속성명", 저장데이터);
		// => 포워딩 방식은 기존과 동일한 Dispatch 방식
		// => 주의! 매핑 메서드 리턴타입을 반드시 ModelAndView 타입으로 변경
		return new ModelAndView("test2/model_and_view", "map", map);
		
	}
	
}













