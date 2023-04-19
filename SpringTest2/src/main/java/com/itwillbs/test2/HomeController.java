package com.itwillbs.test2;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/*
	 * @RequestMapping 어노테이션을 사용하여 URL 매핑 작업 수행
	 * => 파라미터 중 value 속성값에 지정된 URL 을 매핑 주소로 사용하며
	 *    method 속성값에 지정된 RequestMethod.XXX 방식을 요청 메서드로 인식
	 *    (이 때, method 는 복수개 지정 가능하며, 중괄호로 요청 방식을 묶어줌)
	 *    (ex. method = {RequestMethod.GET, RequestMethod.POST})
	 * => 단, 스프링4 버전부터 지원되는 @GetMapping, @PostMapping 어노테이션을 통해
	 *    좀 더 간단히 매핑이 가능하나 유연성은 떨어짐(자신에게 맞는 방식 사용)
	 * => 형식 상 메서드를 정의하여 해당 URL 에 대해 자동으로 메서드가 호출되도록 정의
	 *    메서드 파라미터 : 상황에 따라 없을 수도 있고, 객체 등의 변수 선언 가능
	 *    메서드 리턴타입 : String 또는 다른 타입 지정 가능
	 */
	
	// 서블릿 주소("/") 가 GET 방식으로 요청되면 자동으로 home() 메서드가 호출됨
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		/*
		 * WEB-INF/views/index.jsp 페이지로 포워딩(Dispatcher 방식) 할 경우
		 * return 문 뒤에 "index" 형식으로 포워딩 대상 파일명만 지정(확장자 생략)
		 * => serviet-context.xml 파일 내의 prefix, suffic 부분에 지정된 이름과 결합하여 경로 생성
		 * 
		 *<!-- Resolves views selected for rendering by @Controllers to .jsp resources in the /WEB-INF/views directory -->
		 *<beans:bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		 *	<beans:property name="prefix" value="/WEB-INF/views/" />
		 *	<beans:property name="suffix" value=".jsp" />
		 *</beans:bean>
		 *=> beans:property 태그의 prefix 의 값으로 지정된 "/WEB-INF/views/" 문자열을 
		 *   return 문에 기술된 "home" 문자열의 앞에 결합하고
		 *   suffix 부분에 기술된 ".jsp" 문자열을 뒤에 결합하여
		 *   "/WEB-INF/views/home.jsp" 문자열이 생성되고, Dispatcher 방식으로 포워딩할 경로로 사용됨
		 */
		return "home";
	}
	
	// GET 방식의 "/main" 서블릿 주소가 요청되면
	// WEB-INF/views/index.jsp 페이지로 포워딩(Dispatcher 방식)
//	@RequestMapping(value = "/main", method = RequestMethod.GET)
//	public String main() {
//		return "index";
//	}
	
}











