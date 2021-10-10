package com.todo.dao;

import java.io.BufferedReader;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.todo.service.DbConnect;
import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByName;

public class TodoList { // list는 array 리스트로 되어있음...
	
	private List<TodoItem> list;  // 하나의 객체를 가지고 있음...
	
	Connection con;
	
	public void importData(String filename) {
		try {
			String line; 
			String sql = "insert into list(title, memo, category, curent_date,  due_date)" +
					"values (?, ?, ?, ?, ?);";
			BufferedReader bf = new BufferedReader(new FileReader(filename)); 
			
			int count = 0;
			while((line = bf.readLine()) != null) {
				
				StringTokenizer st = new StringTokenizer(line, "##");
				String category = st.nextToken();
				String title = st.nextToken();
				String description = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();
				
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, title);
				ps.setString(2, description);
				ps.setString(3, category);
				ps.setString(4, current_date);
				ps.setString(5, due_date);
				int re = ps.executeUpdate();
				if (re > 0) count ++;
				ps.close();
			}
			System.out.println("몇 "+ count+ "읽었습니다.\n");
			bf.close();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public int addItem(TodoItem t) {
		String sql = "insert into list(title, memo, category, due_date, current_date)" +
				"values (?, ?, ?, ?, ?);";
		PreparedStatement ps;
		int linecount = 0;
		
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, t.getTitle());
			ps.setString(2, t.getDesc());
			ps.setString(3, t.getCate());
			ps.setString(4, t.getDueDate());
			ps.setString(5, t.getCurrent_date());
			linecount = ps.executeUpdate();
			ps.close();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return linecount;
	}
	
	public int updateItem(TodoItem t) {
		String sql = "update list set title = ?, memo = ?, category = ?, current_date = ?, due_date = ?;"
				+ " where id = ?;";
		PreparedStatement st;
		int count = 0;
		
		try {
			st = con.prepareStatement(sql);
			st.setString(1, t.getTitle());
			st.setString(2, t.getDesc());
			st.setString(3, t.getCate());
			st.setString(4, t.getCurrent_date());
			st.setString(5, t.getDueDate());
			st.setInt(6, t.getId());
			count = st.executeUpdate();
			st.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	public int deleteItem(int index) {
		int count = 0;
		String sql = "delete from list where id = ?;";
		PreparedStatement st;
		
		try {
			st = con.prepareStatement(sql);
			st.setInt(1, index);
			count = st.executeUpdate();
			st.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	public TodoList() {
		this.con = DbConnect.getConnection();
		this.list = new ArrayList<TodoItem>();
	}

	//public void addItem(TodoItem t) {
	//	list.add(t);
	//}
	
	//cate따로 추가.
	public void addCateItem(TodoItem t) {
		list.add(t);
	}
	
	
	public void deleteItem(TodoItem t) {
		int index = list.indexOf(t);
		list.remove(t);
	}
	
	

	void editItem(TodoItem t, TodoItem updated) {
		int index = list.indexOf(t);
		list.remove(index);
		list.add(updated);
	}

	
	public int getCount() {
		Statement st;
		int count = 0;
		try {
			st = con.createStatement();
			String sql = "SELECT count(id) FROM list;";
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			count = rs.getInt("count(id)");
			st.close();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public ArrayList<TodoItem> getOrderedList(String oderby, int ordering) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement st;
		try {
			st = con.createStatement();
			String sql = "SELECT * FROM list ORDER BY " + oderby;
			if (ordering == 0) {
				sql += " desc";
			}
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				TodoItem t = new TodoItem(category, title, description, due_date);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			st.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getList(String key) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement st;
		key = "%" + key + "%";
		
		try {
			String sql = "SELECT * FROM list WHERE title like ? or memo like ?";
			//String sql1 = "SELECT * FROM list WHERE category = ?;";
		
			st = con.prepareStatement(sql);
			//st = con.prepareStatement(sql1);
			st.setString(1, key);
			st.setString(2, key);
			//st.setString(3, key);
			//st.setString(4, key);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				TodoItem t = new TodoItem(category, title, description, due_date);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	public ArrayList<TodoItem> getList() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement st;
		try {
			st = con.createStatement();
			String sql = "SELECT * FROM list;";
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				TodoItem t = new TodoItem(category, title, description, due_date);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			st.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	public ArrayList<String> getCategories() {
		ArrayList<String> list = new ArrayList<String>();
		Statement st;
		try {
			st = con.createStatement();
			String sql = "SELECT DISTINCT category FROM list;";
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				String category = rs.getString("category");
				list.add(category);
			}
			//rs.next();
			//String category = rs.getString("category");
			//TodoItem t = new TodoItem(category);
			//list.add(category);
			st.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
		
	}

	
	
	public ArrayList<TodoItem> getListCategories(String keyword) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		
		PreparedStatement st;
		
		try {
			String sql = "SELECT * FROM list WHERE category = ?;";
			
			st = con.prepareStatement(sql);
			st.setString(1, keyword);
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				TodoItem t = new TodoItem(category, title, description, due_date);
				t.setCurrent_date(current_date);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		return list;
	}
	
	
	public void listAll() {
		int index = 1;
		int len = 0;
		//System.out.println("\n" + "[모든 항목 정렬]\n");
		len = list.size();
		System.out.println("[전체 목록], 총 " + len + " 개");
		for (TodoItem myitem : list) {
		//	System.out.println(myitem.getTitle() + myitem.getDesc());
			System.out.print(index+". "+ myitem.toString());
			index ++;
			
		}
	
	}
	
	public void sortByName() {
		Collections.sort(list, new TodoSortByName());

	}
	
	public void reverseList() {
		Collections.reverse(list);
	}

	public void sortByDate() {
		Collections.sort(list, new TodoSortByDate());
	}
	
	

	public int indexOf(TodoItem t) { // 객체가 몇 번째 있는지 알아
		return list.indexOf(t);
	}

	public Boolean isDuplicate(String title) { // 입력할 때 중복이 있는지 확인하여 중복이면 true...
		for (TodoItem item : list) {
			if (title.equals(item.getTitle())) return true;
		}
		return false;
	}
	
	
	
}
