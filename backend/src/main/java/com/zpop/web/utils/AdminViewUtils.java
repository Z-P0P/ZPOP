package com.zpop.web.utils;

import org.springframework.stereotype.Component;

@Component
public class AdminViewUtils {

	// Admin 테이블에서 다음페이지로 이동하는 버튼의 a태그 링크 생성에 이용하는 메소드.
	public boolean hasNextPage(int page, int size, int currentResultNum, int totalNum) {
		// 한 번 조회 시 출력되는 검색결과가 10개 인데, 검색 결과는 4개인 경우
		if (currentResultNum < size) {
			return false;
		}
		// 현재 page가 5페이지고, 총 검색결과가 50인 경우 
		if (page*size==totalNum) {
			return false;
		}
		return true;
	}
}
