package com.like.cooperation.todo.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.like.system.core.jpa.domain.AbstractAuditEntity;

import lombok.Getter;
import lombok.ToString;

@ToString(callSuper=true)
@Getter
@Entity
@Table(name = "grtodogroup")
@EntityListeners(AuditingEntityListener.class)
public class TodoGroup extends AbstractAuditEntity {		

	static String DEFAULT_GROUP_NAME = "기본일정";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="pk_todo_group")
	Long pkTodoGroup;	
	
	@Column(name="task_group_name")
	String todoGroupName;		
	
	@OneToMany(mappedBy = "todoGroup", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	List<Todo> todoList = new ArrayList<>();	
	
	public TodoGroup() {
		this.todoGroupName = DEFAULT_GROUP_NAME;
	}
	
	public TodoGroup(String todoGroupName) {
		this.todoGroupName = todoGroupName;
	}
	
	public void modify(String todoGroupName) {
		this.todoGroupName = todoGroupName;		
	}
	
	public Todo getTodo(Long id) {
		return this.todoList.stream().filter(e -> e.pkTodo.equals(id)).findFirst().orElse(null);
	}
	
	public Todo getLastTodo() {
		if (this.todoList.isEmpty()) return null;
		
		return this.todoList.get(this.getTodoList().size() - 1);
	}
	
	public void addTodo(Todo todo) {
		if (this.todoList == null) this.todoList = new ArrayList<>();
		
		this.todoList.add(todo);
	}
	
	public void removeTodo(Long id) {
		this.todoList.removeIf(e -> e.pkTodo.equals(id));		
	}
	
}
