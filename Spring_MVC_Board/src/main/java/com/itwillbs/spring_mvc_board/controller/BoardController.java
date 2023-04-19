package com.itwillbs.spring_mvc_board.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.itwillbs.spring_mvc_board.service.BoardService;
import com.itwillbs.spring_mvc_board.vo.BoardVO;
import com.itwillbs.spring_mvc_board.vo.PageInfo;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	// 글쓰기 폼
	// => 세션 아이디가 존재하지 않으면 "로그인 필수!", "MemberLoginForm.me" 저장 후 success 로 이동
	@GetMapping("/BoardWriteForm.bo")
	public String writeForm(HttpSession session, Model model) {
		if(session.getAttribute("sId") == null) {
			model.addAttribute("msg", "로그인 필수!");
			model.addAttribute("target", "MemberLoginForm.me");
			return "success";
		}
		
		return "board/board_write_form";
	}
	
	// -----------------------------------------------------------------------------------
	// 파일 업로드 기능이 포함된(enctype="multipart/form-data") 폼 파라미터 처리할 경우
	// 1) 각 파라미터를 각각의 변수로 처리하면서 
	//    업로드 파일을 매핑 메서드의 MultipartFile 타입으로 직접 처리하는 경우
//	@PostMapping("/BoardWritePro.bo")
//	public String writePro(
//			String board_name, String board_subject, String board_content, MultipartFile file) {
//		System.out.println(board_name + ", " + board_subject + ", " + board_content);
//		System.out.println("업로드 파일명 : " + file.getOriginalFilename());
//		
//		
//		return "";
//	}
	
	// 2) 파일을 제외한 나머지 파라미터를 Map 타입으로 처리하고, 파일은 MultipartFile 타입 변수로 처리
	//    => 주의! Map 타입 파라미터 선언 시 @RequestParam 어노테이션 필수!
//	@PostMapping("/BoardWritePro.bo")
//	public String writePro(
//			@RequestParam Map<String, String> map, MultipartFile file) {
//		System.out.println(map.get("board_name") + " " + map.get("board_subject") + " " + map.get("board_content"));
//		System.out.println("업로드 파일명 : " + file.getOriginalFilename());
//		
//		
//		return "";
//	}
	
	// 3) MultipartFile 타입 멤버변수를 포함하는 BoardVO 타입으로 모든 파라미터를 한꺼번에 처리
	// => BoardVO 클래스에 MultipartFile 타입 멤버변수 선언 시
	//    반드시 <input type="file"> 태그의 name 속성명과 동일한 이름의 멤버변수를 선언하고
	//    Getter/Setter 정의 필수!
	@PostMapping("/BoardWritePro.bo")
	public String writePro(BoardVO board, HttpSession session, Model model) {
//		System.out.println(board);
//		System.out.println("업로드 파일명 : " + board.getFile().getOriginalFilename());
		
		// 이클립스 프로젝트 상에 업로드 폴더(upload) 생성 필요 - resources 폴더에 생성(외부 접근용)
		// 이클립스가 관리하는 프로젝트 상의 가상 업로드 경로에 대한 실제 업로드 경로 알아내기
		// => request 또는 session 객체의 getServletContext() 메서드를 호출하여 서블릿 컨텍스트 객체를 얻어낸 후
		//    다시 getRealPath() 메서드를 호출하여 실제 업로드 경로 알아낼 수 있다!
		//    (JSP 일 경우 request 객체로 getRealPath() 메서드 호출이 가능함)
		String uploadDir = "/resources/upload"; // 프로젝트 상의 업로드 경로
//		String saveDir = request.getServletContext().getRealPath(uploadDir);
		String saveDir = session.getServletContext().getRealPath(uploadDir);
		// => D:\Shared\Spring\workspace_spring3\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Spring_MVC_Board\resources\ upload
//		System.out.println("실제 업로드 경로 : " + saveDir);
		
		try {
			// -----------------------------------------------------------------------
			// 업로드 디렉토리를 날짜별 디렉토리로 분류하기
			// => 하나의 디렉토리에 너무 많은 파일이 존재하면 로딩 시간 길어짐
			//    따라서, 날짜별로 디렉토리를 구별하기 위해 java.util.Date 클래스 활용
			Date date = new Date();
			// SimpleDateFormat 클래스를 활용하여 날짜 형식을 "연연연연-월월-일일" 로 지정
			// => 단, 편의상 디렉토리 구조를 그대로 나타내기 위해 - 대신 / 기호 사용
			//    (가장 정확히 표현하려면 디렉토리 구분자를 File.seperator 상수로 사용)
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			board.setBoard_file_path("/" + sdf.format(date));
			
			// 기본 업로드 경로와 서브 디렉토리 경로 결합하여 저장
			saveDir = saveDir + board.getBoard_file_path();
			// -----------------------------------------------------------------------
			
			// java.nio.file.Paths 클래스의 get() 메서드를 호출하여  
			// 실제 경로를 관리하는 java.nio.file.Path 타입 객체를 리턴받기(파라미터 : 실제 업로드 경로)
			Path path = Paths.get(saveDir);
			// Files 클래스의 createDirectories() 메서드를 호출하여 Path 객체가 관리하는 경로 없으면 생성
			// => 거쳐가는 경로들 중 없는 경로는 모두 생성
			Files.createDirectories(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// BoardVO 객체에 전달된 MultipartFile 객체 꺼내기
		// => 단, 복수개의 파일(파라미터)이 관리되는 경우 MultipartFile[] 타입으로 꺼내기
		MultipartFile mFile = board.getFile(); // 단일 파일
//		MultipartFile[] mFiles = board.getFiles(); // 복수 파일
		
		String originalFileName = mFile.getOriginalFilename();
//		System.out.println("원본 파일명 : " + originalFileName);
		
		// 파일명 중복 방지를 위한 대책
		// 현재 시스템(서버)에서 랜덤ID 값을 추출하여 파일명 앞에 붙여
		// "랜덤ID값_파일명.확장자" 형식으로 중복 파일명 처리
		// => 랜덤ID 생성은 java.util.UUID 클래스 활용(UUID = 범용 고유 식별자)
		String uuid = UUID.randomUUID().toString();
//		System.out.println("UUID : " + uuid);
		
		// 생성된 UUID 값을 원본 파일명 앞에 결합(파일명과 구분을 위해 _ 기호 추가)
		// => 나중에 사용자에게 다운로드 파일명 표시할 때 원래 파일명 표시를 위해 분리할 때 사용
		//    (가장 먼저 만나는 _ 기호를 기준으로 문자열 분리하여 처리)
		// => 단, 파일명 길이 조절을 위해 UUID 중 맨 앞의 8자리 문자열만 활용
		//    (substring(0, 8) 메서드 활용)
//		originalFileName = UUID.randomUUID().toString() + "_" + originalFileName;
		// => 생성된 파일명을 BoardVO 객체의 board_file 변수에 저장
		board.setBoard_file(uuid.substring(0, 8) + "_" + originalFileName);
		System.out.println("실제 업로드 될 파일명 : " + board.getBoard_file());
		
		// -------------------------------------------------------------------------------
		
		// BoardService - registBoard() 메서드를 호출하여 게시물 등록 작업 요청
		// => 파라미터 : BoardVO 객체    리턴타입 : int(insertCount)
		int insertCount = service.registBoard(board);
		
		// 게시물 등록 작업 결과 판별
		// => 성공 시 업로드 파일을 실제 폴더에 이동시킨 후 BoardList.bo 서블릿 리다이렉트
		// => 실패 시 "글 쓰기 실패!" 메세지를 저장 후 fail_back.jsp 페이지로 포워딩
		if(insertCount > 0) { // 성공
			// 업로드 된 파일은 MultipartFile 객체에 의해 임시 폴더에 저장되어 있으며
			// 글쓰기 작업 성공 시 임시 위치에 저장된 파일을 실제 폴더로 옮기는 작업 필요
			// => MultipartFile 객체의 transferTo() 메서드를 호출하여 실제 위치로 이동(업로드)
			//    (파라미터 : java.io.File 객체 => new File(업로드 경로, 업로드 파일명))
			try {
				mFile.transferTo(new File(saveDir, board.getBoard_file()));
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return "redirect:/BoardList.bo";
		} else { // 실패
			model.addAttribute("msg", "글 쓰기 실패!");
			return "fail_back";
		}
		
	}
	
	// "/BoardList.bo" 서블릿 요청에 대한 글 목록 조회 비즈니스 로직 list() - GET
	// => 파라미터 : 검색타입(searchType) => 기본값 널스트링("")으로 설정
	//               검색어(searchKeyword) => 기본값 널스트링("")으로 설정
	//               현재 페이지번호(pageNum) => 기본값 1 로 설정(파라미터 없을 경우 대비)
	//               데이터 공유 객체 Model(model)
	// => 검색어를 입력하지 않고 검색버튼 클릭했을 때에도 전체 게시물 목록 조회
	//    (필요에 따라 검색어 미입력 시 경고창 출력 또는 아무 동작 수행하지 않을 수 있음)
	@GetMapping("/BoardList.bo")
	public String list(
			@RequestParam(defaultValue = "") String searchType, 
			@RequestParam(defaultValue = "") String searchKeyword, 
			@RequestParam(defaultValue = "1") int pageNum, 
			Model model) {
//		System.out.println("검색타입 : " + searchType);
//		System.out.println("검색어 : " + searchKeyword);
//		System.out.println("페이지번호 : " + pageNum);
		
		// -----------------------------------------------------------------------
		// 페이징 처리를 위해 조회 목록 갯수 조절 시 사용될 변수 선언
		int listLimit = 10; // 한 페이지에서 표시할 게시물 목록 갯수(10개로 제한)
		int startRow = (pageNum - 1) * listLimit; // 조회 시작 행번호(startRow) 계산 => 0, 10, 20...
		// -----------------------------------------------------------------------
		// BoardService - getBoardList() 메서드를 호출하여 게시물 목록 조회
		// => 파라미터 : 검색타입, 검색어, 시작행번호, 목록갯수
		// => 리턴타입 : List<BoardVO>(boardList)
		List<BoardVO> boardList = 
				service.getBoardList(searchType, searchKeyword, startRow, listLimit);
		// -----------------------------------------------------------------------
		// 페이징 처리를 위한 계산 작업
		// 한 페이지에서 표시할 페이지 목록(번호) 갯수 계산
		// 1. BoardListService - getBoardListCount() 메서드를 호출하여
		//    전체 게시물 수 조회(페이지 목록 갯수 계산에 사용)
		//    => 파라미터 : 검색타입, 검색어   리턴타입 : int(listCount)
		int listCount = service.getBoardListCount(searchType, searchKeyword);
//		System.out.println("총 게시물 수 : " + listCount);
		
		// 2. 한 페이지에서 표시할 페이지 목록 갯수 설정
		int pageListLimit = 5; // 페이지 목록 갯수를 5개로 제한
		
		// 3. 전체 페이지 목록 수 계산
		// => 전체 게시물 수를 목록 갯수로 나누고, 남은 나머지가 0보다 클 경우 페이지 수 + 1
		//    (페이지수 + 1 계산하기 위해 삼항연산자 활용)
		int maxPage = listCount / listLimit + (listCount % listLimit > 0 ? 1 : 0);
		
		// 4. 시작 페이지 번호 계산
		// => 페이지 목록 갯수가 3일 때
		//    1 ~ 3 페이지 사이일 경우 시작 페이지 번호 : 1
		//    4 ~ 6 페이지 사이일 경우 시작 페이지 번호 : 4
		int startPage = (pageNum - 1) / pageListLimit * pageListLimit + 1;
		
		// 5. 끝 페이지 번호 계산
		// => 시작 페이지 번호에 페이지 목록 갯수를 더한 후 - 1
		int endPage = startPage + pageListLimit - 1;
		
		// 만약, 끝 페이지 번호(endPage) 가 최대 페이지 번호(maxPage) 보다 클 경우
		// 끝 페이지 번호를 최대 페이지 번호로 교체
		if(endPage > maxPage) {
			endPage = maxPage;
		}
		
		// 페이징 처리 정보를 저장하는 PageInfo 클래스 인스턴스 생성 및 데이터 저장
		PageInfo pageInfo = new PageInfo(listCount, pageListLimit, maxPage, startPage, endPage);
		System.out.println(pageInfo);
		// ------------------------------------------------------------------------------------
		// 조회된 게시물 목록 객체(boardList) 와 페이징 정보 객체(pageInfo)를 Model 객체에 저장
		model.addAttribute("boardList", boardList);
		model.addAttribute("pageInfo", pageInfo);
		
		return "board/board_list";
	}
	
	// "/BoardDetail.bo" 서블릿 요청에 대한 글 상세정보 조회 
	// => Serivice - getBoard()
	@GetMapping(value = "/BoardDetail.bo")
	public String detail(@RequestParam int board_num, Model model) {
		// Service 객체의 getBoard() 메서드를 호출하여 게시물 상세 정보 조회
		// => 파라미터 : 글번호    리턴타입 : BoardVO(board)
		BoardVO board = service.getBoard(board_num);
//		System.out.println(board);
		
		// 조회된 게시물 정보가 존재할 경우 조회수 증가 - increaseReadcount()
		// => 파라미터 : 글번호    리턴타입 : void
		if(board != null) {
			service.increaseReadcount(board_num);
			// 조회수 증가 작업 완료 후 BoardVO 객체의 조회수(board_readcount)값 1 증가
			board.setBoard_readcount(board.getBoard_readcount() + 1);
		}
		
		// Model 객체에 BoardVO 객체 추가
		model.addAttribute("board", board);
		
		return "board/board_view";
	}
	
	
}













