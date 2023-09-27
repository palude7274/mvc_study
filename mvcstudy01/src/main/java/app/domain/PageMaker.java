package app.domain;

//하단 페이지 네비게이션에 필요한 변수들의 집합데이터 클래스
public class PageMaker {
	
	//하단 페이지 목록번호 개수 1 2 3 4 5 6 7 8 9
	private int displayPageNum = 10;
	private int startPage;	// 목록의 시작번호
	private int endPage;	// 목록의 끝번호
	private int totalCount; // 총 게시물 수 담는 변수
	
	private boolean prev;	// 이전 버튼 존재 유무변수
	private boolean next;	// 다음 버튼 존재 유무변수
	
	private SearchCriteria scri;
	
	public SearchCriteria getScri() {
		return scri;
	}

	public void setScri(SearchCriteria scri) {
		this.scri = scri;
	}

	public int getDisplayPageNum() {
		return displayPageNum;
	}

	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		calcData();		//페이지 목록 개수를 나타내주기 위한 계산식
	}

	private void calcData() {
	
		// 1. 기본적으로 1에서 10까지 나타나게 설정
		endPage = (int)(Math.ceil(scri.getPage()/(double)displayPageNum)*displayPageNum);
				
		// 2. endPage를 설정했으면 시작페이지도 설정
		startPage = (endPage-displayPageNum)+1;
		
		// 3. 실제 페이지 값을 뽑는다
		int tempEndPage = (int)Math.ceil(totalCount/(double)scri.getPerPageNum());
		
		// 4. 설정 endPage와 실제 endPage를 비교한다
		if(endPage > tempEndPage) {
			endPage = tempEndPage;
		}

		
		// 5. 이전다음버튼 유무
		prev = (startPage == 1 ? false:true);
		next = (endPage*scri.getPerPageNum() >= totalCount ? false:true);

	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}



	
}
