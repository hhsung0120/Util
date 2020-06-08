package com.heeseong.util.model;

public class PageNavigator {
    /** 한 페이지당 게시글 수 **/
    private int pageSize = 10;

    /** 한 블럭 사이즈 **/
    private int blockSize = 10;

    /** 현재 페이지 **/
    private int currentPage = 1;

    /** 현재 블럭(range) **/
    private int currentBlock = 1;

    /** 총 게시글 수 **/
    private int totalCount;

    /** 총 페이지 수 **/
    private int totalPageCount;

    /** 총 블럭 수 **/
    private int totalBlockCount;

    /** 시작 페이지 **/
    private int startPage = 1;

    /** 끝 페이지 **/
    private int endPage;

    /** 시작 index **/
    private int startIndex = 0;

    /** 이전 페이지 **/
    private int previousPage;

    /** 다음 페이지 **/
    private int nextPage;

    /** 페이징 처리 **/
    public void setListCount(int totalCount) {
        /** 총 게시물 수 **/
        this.totalCount = totalCount;

        /** 총 페이지 수 **/
        setTotalPageCount(totalCount);

        /** 총 블럭 수 **/
        setBlockCount(totalPageCount);

        /** 블럭 설정 **/
        blockSetting(currentPage);

        /** DB 질의를 위한 startIndex 설정 **/
        setStartIndex(currentPage);
    }

    public void setTotalPageCount(int listCount) {
        this.totalPageCount = (int) Math.ceil(listCount*1.0/pageSize);
    }
    public void setBlockCount(int pageCount) {
        this.totalBlockCount = (int) Math.ceil(pageCount*1.0/blockSize);
    }
    public void blockSetting(int currentPage){
        setCurrentBlock(currentPage);
        this.startPage = (currentBlock - 1) * blockSize + 1;
        this.endPage = startPage + blockSize - 1;
        if(endPage > totalPageCount){
            this.endPage = totalPageCount;
        }
        this.previousPage = currentPage - 1;
        this.nextPage = currentPage + 1;
    }
    public void setCurrentBlock(int currentPage) {
        this.currentBlock = (int)((currentPage-1)/blockSize) + 1;
    }
    public void setStartIndex(int currentPage) {
        this.startIndex = (currentPage-1) * pageSize;
    }
}
