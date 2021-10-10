package com.todo.service;


//import java.lang.IndexOutOfBoundsException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.*;
// 프로그램 시작 시 읽기, 종료 시 저장...
// TodoUtil.java 안에 클래스 메소드를 만들어야 함...
import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) { 
		
		String cate, title, desc, due_date;
		Scanner sc = new Scanner(System.in);
		
		// 카테고리는 중복 상관없음..
		System.out.print("\n" + "[항목 추가]\n" + "카테고리 >> ");
		cate = sc.next();
		
		System.out.print("주 제 >> ");
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.println("\n이미 목록에 있습니다.");
			return;
		}
		
		System.out.print("내 용 >> "); // 문장 전체를 입력 받아야한다.
		sc.nextLine();
		desc = sc.nextLine(); // trim을 사용하는 이유는 좌우 여백...
		desc = desc.trim();
		
		System.out.print("마감일(예시: 2000/00/00) >> ");
		due_date = sc.nextLine();
		due_date = due_date.trim();
		
		TodoItem t = new TodoItem(cate, title, desc, due_date);
		//list.addItem(t);
		//System.out.println("\n추가 되었습니다."); 
	
		if (list.addItem(t) > 0) {
			System.out.println("\n추가 되었습니다.");
		}
		
	}

	public static void deleteItem(TodoList l) {
		
		try {
			
			Scanner sc = new Scanner(System.in);
			System.out.print("\n" + "[항목 삭제]\n"+ "삭제할 항목번호 입력 >> ");
			int number = sc.nextInt();
			//l.indexOf(o); 만약 여기서 해당하는 오프젝트가 없으면 -1를 반환, 지정한 오브젝트 인덱스 반
			//catch (IndexOutOfBoundException)
 			//System.out.print(l.getList().get(number-1).toString());
 			System.out.print("삭제 하시겠습니까(y/n)? >> ");
 			String termDel;
 			termDel = sc.next();
 			
 			if (termDel.equals("y")) {
 				/*
 				String cate = l.getList().get(number-1).getCate();
 				String title = l.getList().get(number-1).getTitle();
 				String desc = l.getList().get(number-1).getDesc();
 				String dueDate = l.getList().get(number-1).getDueDate();
 				TodoItem t = new TodoItem(cate, title, desc, dueDate);
 				l.deleteItem(t);
 				l.getList().remove(number-1);
 				*/
 				if (l.deleteItem(number) > 0) {
 					//l.deleteItem(l.getList().get(number));
 					System.out.print("삭제되었습니다.\n");
 				}
 				//l.deleteItem(l.getList().get(number-1));
 			//	System.out.print("삭제되었습니다.\n");
 			}
 			else if (termDel.equals("n")){
 				System.out.print("삭제를 취소합니다.\n");
 			}
 			
 			else {
 				System.out.print("잘못 입력하셨습니다.\n");
 			}		
	 
	 		
			/*
			// 전체리스트 중에 아이템 하나씩 불러와서 그 항목이 일치하는지 점검..
			for (TodoItem item : l.getList()) {
				if (title.equals(item.getTitle())) {
					l.deleteItem(item);
					System.out.println("삭제되었습니다.");
					break;
				}
				else if (!l.isDuplicate(title)) {
					System.out.println("\n해당 목록이 없습니다.");
					return;
				}
			}
			*/
		} catch(IndexOutOfBoundsException e) {
			System.out.print("항목이 존재하지 않습니다.\n");
		}
	}


	public static void updateItem(TodoList l) {

		try {
			Scanner sc = new Scanner(System.in);
			System.out.print("\n" + "[업데이트]\n" + "수정할 항목번호를 입력 >> ");
			int number;
			number = sc.nextInt();
			//System.out.print(l.getList().get(number-1).toString());
			System.out.print("업데이트 하시겠습니까(y/n)? >> ");
			
			String termDel = sc.next();
			if (termDel.equals("y")) {
				System.out.print("새로운 카테고리 >> ");
				String new_cate = sc.next();
				
				System.out.print("새로운 주 제 >> ");
				//sc.nextLine();
				String new_title = sc.nextLine();
				new_title = new_title.trim();
				//if (l.isDuplicate(new_title)) { // 중복 검사를 통해 점검... 
				//	System.out.println("\n이미 목록에 있습니다.\n");
				//	return;
				//}
				sc.nextLine();
				System.out.print("새로운 내 용 >> "); 
				String new_description = sc.nextLine();
				new_description = new_description.trim(); // trim
				
				System.out.print("새로운 마감일 >> ");
				String new_dueDate = sc.next();
				
				
				//l.deleteItem(l.getList().get(number-1));
				TodoItem t = new TodoItem(new_cate, new_title, new_description, new_dueDate);
				//l.addItem(t);
				t.setId(number);
				if (l.updateItem(t)>0) {
					System.out.print("목록이 수정되었습니다.\n");
				}
			}
			else if (termDel.equals("n")) {
				System.out.print("업데이트 취소합니다.\n");
			}
			else {
				System.out.print("잘못 입력하셨습니다.");
			}
			
			/*
			System.out.print("새로운 카테고리 >> ");
			String new_cate = sc.next();
			System.out.print("새로운 주 제 >> ");
			String new_title = sc.next().trim();
			if (l.isDuplicate(new_title)) { // 중복 검사를 통해 점검... 
				System.out.println("\n이미 목록에 있습니다.\n");
				return;
			}
			sc.nextLine();
			System.out.print("새로운 내 용 >> "); 
			String new_description = sc.nextLine();
			new_description = new_description.trim(); // trim
			
			System.out.print("새로운 마감일 >> ");
			String new_dueDate = sc.next();
			for (TodoItem item : l.getList()) {
				if (item.getTitle().equals(title)) {
					l.deleteItem(item); // 원래 내용을 지워버리고...
					TodoItem t = new TodoItem(new_cate, new_title, new_description, new_dueDate); // 새로운 내용을 저장하기..
					l.addItem(t); 
					System.out.println("\n목록이 수정되었습니다.");
				}
			}
			*/
		} catch(IndexOutOfBoundsException e) {
			System.out.print("항목이 존재하지 않습니다.\n");
		}
	}
	
	
	
	// 검색 기능 구현하기... 카테고리, 주제, 내용, 마감일 등 모두 점검하게 할까?
	public static void find(TodoList l, String key) {
		int len = 0;
		Scanner sc = new Scanner(System.in);
		System.out.print("키워드 검색/날짜 검색(k/d) >> ");
		String termFind = sc.next();
		
		if (termFind.equals("k")) {
			System.out.print("키워드 입력 >> ");
			String keyword = sc.next();
			//title.contains(keyword);
			System.out.println("\n[검색 항목]");
			for (TodoItem item : l.getList()) {
				if (item.getTitle().contains(keyword) == true ||  
						item.getDesc().contains(keyword) == true || item.getCate().contains(keyword)) {
					//System.out.print(l.getList().toString());
					System.out.print(item.toString());
					len++;
					
				}
				
			}
			System.out.println("총"+ len + "개의 항목을 찾았습니다.\n");
		}
		else if (termFind.equals("d")) {
			System.out.print("날짜 입력 >> ");
			String date = sc.next();
			//title.contains(keyword);
			System.out.println("\n[검색 항목]");
			for (TodoItem item : l.getList()) {
				if (item.getDueDate().contains(date) == true) {
					//System.out.print(l.getList().toString());
					System.out.print(item.toString());
					len++;
				}
				
			}
			System.out.println("총"+ len + "개의 항목을 찾았습니다.\n");
		}
		else {
			System.out.println("잘못 입력하셨습니다.\n");
		}
	}
	

	public static void listAll(TodoList l) {
		int index = 1;
		int len = 0;
		len = l.getList().size();
		System.out.println("\n[전체 목록], 총 " + len + " 개");
	
		for (TodoItem item : l.getList()) {
			//System.out.println("[" + item.getTitle() + "]" + item.getDesc());
			//System.out.print(index+". "+item.toString());
			//index ++;
			System.out.print(item.toString());
		}
	}
	
	
	public static void listAll(TodoList l, String orderby, int odering) {
	
		System.out.printf("[전체 목록] 총 %d개\n", l.getCount());
		
		for (TodoItem item : l.getOrderedList(orderby, odering)) {
			System.out.print(item.toString());
		}
	}
	
	
	//Set<String> set = new Hashset(list);
	// 카테고리별 항목
	public static void cateList(TodoList l) {
		//
		ArrayList<String> c = new ArrayList<>();
		System.out.println("[카테고리별 항목]");
		for (TodoItem item : l.getList()) {
			c.add(item.getCate());
		}
		
		// 중복 허용 안함..
		HashSet<String> h = new HashSet<>();
		h.addAll(c);
		c.clear();
		c.addAll(h);
		
		System.out.println(c);
		System.out.println("총 "+c.size() + "개의 카테고리가 있습니다.\n");
	}
	
	
	public static void listcate(TodoList l) {
		int count = 0;
		
		for (String item : l.getCategories()) {
			System.out.print("["+item + "]");
			count ++;
		}
		System.out.printf("\n총 %d개의 카테고리가 등록되어 있습니다.\n", count);
	}
	
	
	public static void saveList(TodoList l, String filename) {
		//File file = new File("/Users/wani/Documents/workspace/TodoList.txt");
		try {
			FileWriter fl = new FileWriter(filename);
			for (TodoItem item : l.getList()) {
				fl.write(item.toSaveString());
			}
			fl.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("파일의 내용이 저장되었습니다.");
		
	}
	
	
	public static void loadList(TodoList l, String filename) {
	//	File f = new File("TodoList.txt");
	//	if (f.isFile()) {
		//	System.out.println("TodoList.txt의 파일이 없습니다.");
	//	}
		int lineCount = 0;
		try {
			File f = new File(filename);
			if (!f.exists()) {
				System.out.println("파일이 존재하지 않습니다.");
				return;
			}
			else {
				BufferedReader bf = new BufferedReader(new FileReader(filename)); 
				String s;
				while((s = bf.readLine()) != null) {
					lineCount ++;
					StringTokenizer st = new StringTokenizer(s, "##");
					String cate = st.nextToken();
					String title = st.nextToken();
					String desc = st.nextToken();
					String due_date = st.nextToken();
					TodoItem t = new TodoItem(cate, title, desc, due_date);
					l.addItem(t);
				}
			bf.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(lineCount + "개의 항목을 읽었습니다.");
	}

}