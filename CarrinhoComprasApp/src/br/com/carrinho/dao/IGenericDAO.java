package br.com.carrinho.dao;

import java.util.List;

public interface IGenericDAO<T> {

	boolean insert(T obj);
	int update(T obj);
	void delete(T obj);
	T get(Object id);
	List<T> getAll();
	int getCount();
}
