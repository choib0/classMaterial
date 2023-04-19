package com.itwillbs.test;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
		
		// WEB-INF/views/index.jsp 페이지로 포워딩(Dispatcher 방식)하기 위해
		// return 문 뒤에 "index" 라는 파일의 이름만 명시
		// => serviet-context.xml 파일 내에 기술된 prefix, suffic 값을 결합하여 자동으로 파일명 생성
//		return "index";
	}
	
	
//	// GET 방식으로 "/main" 이라는 서블릿 요청이 발생하면
//	// WEB-INF/views/index.jsp 페이지로 포워딩(Dispatcher 방식)
//	@RequestMapping(value = "/main", method = RequestMethod.GET)
//	public String main() {
//		return "";
//	}
	// => TestController 클래스로 이동되었음.
	
	
}













