package com.itwillbs.spring_mvc_board.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itwillbs.spring_mvc_board.mapper.BoardMapper;
import com.itwillbs.spring_mvc_board.vo.BoardVO;

@Service
public class BoardService {

	@Autowired
	private BoardMapper mapper;

	// 글 등록 작업 요청
	// BoardMapper - insertBoard()
	public int registBoard(BoardVO board) {
		return mapper.insertBoard(board);
	}

	// 게시물 목록 조회
	// BoardMapper - selectBoardList()
	public List<BoardVO> getBoardList(String searchType, String searchKeyword, int startRow, int listLimit) {
		return mapper.selectBoardList(searchType, searchKeyword, startRow, listLimit);
	}

	// 글 목록 총 갯수 조회
	public int getBoardListCount(String searchType, String searchKeyword) {
		return mapper.selectBoardListCount(searchType, searchKeyword);
	}

	// 글 상세정보 조회
	public BoardVO getBoard(int board_num) {
		return mapper.selectBoard(board_num);
	}

	// 글 조회수 증가
	public void increaseReadcount(int board_num) {
		mapper.updateReadcount(board_num);
	}

	
}













