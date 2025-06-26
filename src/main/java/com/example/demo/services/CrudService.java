package com.example.demo.services;

import java.util.List;

public interface CrudService<T, ID>{
	default List<T> findAll(){
		return List.of();
	}
	default T findByID(ID id) {
		return null;
	}
	
	default T create(T entity) {
		return entity;
	}
	default void Update(T entity) {}
	default void delete(T entity) {}
	default boolean existsById(ID id) {
		return false;
	}
	// them method save
	default T save(T entity) {
		return create(entity);
	}
}
