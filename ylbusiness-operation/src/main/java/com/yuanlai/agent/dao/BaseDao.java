package com.yuanlai.agent.dao;

import java.util.List;

/**
 * 基础dao
 * 
 * @author Lee
 *
 */
public interface BaseDao<T, ID> {
	
	public int insertEntity(T entity) throws Exception;

	public int deleteEntity(ID id) throws Exception;

	public int updateEntity(T entity) throws Exception;

	public int countEntity(T entity) throws Exception;

	public T selectEntity(T entity) throws Exception;
	
	public T selectEntityById(ID id) throws Exception;

	public List<T> selectList(T entity) throws Exception;
}
