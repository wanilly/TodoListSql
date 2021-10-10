package com.todo.dao;

import java.util.Date;
import java.text.SimpleDateFormat;

public class TodoItem { // 멤버함수
	private int id;
    private String title;
    private String desc;
    private String current_date;  // current_date을 string 변경 (스트링)
    private String cate;
    private String due_date;
    
    
    // ,,
    
    public TodoItem(String cate) {
    	this.cate = cate;
    }
    
    // construct 생성자 
    public TodoItem(String cate, String title, String desc, String due_date){
    	this.cate = cate;
        this.title=title;
        this.desc=desc;  
        //SimpleDateFormat d = new SimpleDateFormat("yyyy/MM/dd");
        //this.due_date = d.format(new Date());
        this.due_date = due_date;
        
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");  // import를 해주어야 함... 
        this.current_date = f.format(new Date());  // 생성자에서 SimpleDateFormat을 사용하여 문자열로 저
        
    }
    

	// 카테고리 추가함..
    public String getCate() {
    	return cate;
    }
    
    public void setCate(String cate) {
    	this.cate = cate;
    }
    
    // 마감일 추가함..
    public String getDueDate() {
    	return due_date;
    }
    
    public void setDueDate(String due_date) {
    	this.due_date = due_date;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }
    
    
    public int getId() {
    	return id;
    }
    public void setId(int id) {
    	this.id = id;
    }
    
    //is_completed
    
    // 오버라이딩 화면상에 객체를 출력하기 위함...
    @Override
    public String toString() {
    	return id +". "+ "[" + cate + "] " + "<" + title + ">" + " - " + desc + " - " + due_date + " - " + current_date + "\n";  // 문자열...
    }
    
    public String toSaveString() {
    	return cate + "##"+ title + "##" + desc + "##" + due_date + "##"+ current_date + "\n";
    }
    
    
    public String toComString() {
    	return "[" + cate + "] " + "<" + title + ">" + " - " + desc + " - " + due_date + " - " + current_date + "\n";
    }

    
}