package com.todo.service;
import java.util.Comparator;

import com.todo.dao.TodoItem;

// comparator 인터페이스 사용..
public class TodoSortByName implements Comparator<TodoItem> {

    @Override
    public int compare(TodoItem o1, TodoItem o2) {
        return o1.getTitle().compareTo(o2.getTitle());

    }
}
