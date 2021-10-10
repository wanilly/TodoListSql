package com.todo;


import java.io.File;
import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();

		boolean isList = false;
		boolean quit = false;
		//TodoUtil.loadList(l, "TodoList.txt");
		
		//l.importData("TodoList.txt");
		Menu.displaymenu();
		do {
			//Menu.displaymenu();
			Menu.prompt();
			isList = false;
			String choice = sc.next();
			switch (choice) {

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;
			
			// 카테고리별 항목 , 중복되지 않도록 카테고리 종류 개수와 카테고리 가져오기
			case "ls_cate":
			//	TodoUtil.cateList(l);
				TodoUtil.listcate(l);
				break;

			case "ls_name_asc":
				//l.sortByName();
				System.out.println("\n제목 순으로 정렬하였습니다.");
				TodoUtil.listAll(l, "title", 1);
				//isList = true;
				break;

			case "ls_name_desc":
				//l.sortByName();
				//l.reverseList();
				System.out.println("\n제목 역순으로 정렬하였습니다.");
				TodoUtil.listAll(l, "title", 0);
				//isList = true;
				break;
				
			case "ls_date":
				//l.sortByDate();
				System.out.println("\n날짜 순으로 정렬하였습니다.");
				TodoUtil.listAll(l, "due_date", 1);
				//isList = true;
				break;
			
			// 날짜 거꾸로 순으로 
			case "ls_date_desc":
				//l.sortByDate();
				//l.reverseList();
				System.out.println("\n날짜 역순으로 정렬하였습니다.");
				TodoUtil.listAll(l, "due_date", 0);
				//isList = true;
				break;
				
				
			// find function
			case "find": // find  cate는 find 안에 구
				// TodoUtil.find 만들어서 구현해볼까? 
				String keyword = sc.nextLine().trim();
				TodoUtil.find(l, keyword);
				break;
				
			case "help":
				Menu.displaymenu();
				break;

			case "exit":
				System.out.println("\n프로그램을 종료합니다.\n");
				quit = true;
				break;

			default:
				System.out.println("\n정확한 명령어를 입력하시오 (도움말 - help)");
				break;
			}
			if(isList) l.listAll();
			//if(isList) TodoUtil.listAll(l);
		} while (!quit);
		TodoUtil.saveList(l, "TodoList.txt");
	}
}
