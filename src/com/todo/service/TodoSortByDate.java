package com.todo.service;
import java.util.Comparator;

import com.todo.dao.TodoItem;

// comparator를 구현
public class TodoSortByDate implements Comparator<TodoItem> {
    @Override // 두 객체를 가지고 비교..
    public int compare(TodoItem o1, TodoItem o2) {
        return o1.getCurrent_date().compareTo(o2.getCurrent_date());

    }
}
