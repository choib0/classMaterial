package com.itwillbs.spring_mvc_board.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.itwillbs.spring_mvc_board.vo.BoardVO;

public interface BoardMapper {

	// 글 쓰기
	int insertBoard(BoardVO board);

	// 글 목록 조회
	// => 복수개의 파라미터 구분을 위해 @Param 어노테이션을 통해 파라미터명 지정 필수!
	List<BoardVO> selectBoardList(
			@Param("searchType") String searchType, 
			@Param("searchKeyword") String searchKeyword, 
			@Param("startRow") int startRow, 
			@Param("listLimit") int listLimit);

	// 글 목록 총 갯수 조회
	int selectBoardListCount(
			@Param("searchType") String searchType, @Param("searchKeyword") String searchKeyword);

	// 글 상세 정보 조회
	BoardVO selectBoard(int board_num);

	// 글 조회수 증가
	void updateReadcount(int board_num);

}















