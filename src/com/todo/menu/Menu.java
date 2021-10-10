package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
    	System.out.println();
        System.out.println("< TodoList 프로그램 명령어 >\n");
        System.out.println("[add] - 항목 추가");
        System.out.println("[del] - 검색 목록 삭제");
        System.out.println("[edit] - 항목 업데이트");
        System.out.println("[ls] - 모든 항목");
        System.out.println("[ls_cate] - 카테고리별 항목");
        System.out.println("[ls_name_asc] - 이름 순으로 정렬");
        System.out.println("[ls_name_desc] - 이름 역순으로 정렬");
        System.out.println("[ls_date] - 날짜 순으로 정렬");
        System.out.println("[ls_date_desc] - 날짜 역순으로 정렬");
        System.out.println("[find] - 검색 <카테고리, 주제, 내용, 마감일(0000/00/00)>");
        System.out.println("[exit] - 종료하기");
    }
    
    public static void prompt() {
    	System.out.print("명령어 >> ");
    	
    }
    

}
