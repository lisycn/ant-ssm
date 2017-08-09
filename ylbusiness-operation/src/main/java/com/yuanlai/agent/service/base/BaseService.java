package com.yuanlai.agent.service.base;

import java.util.List;

import com.github.pagehelper.PageInfo;


/**
 * 鍩虹鏈嶅姟绫�
 * 
 * @author Lee
 *
 */
public interface BaseService<T, ID> {
	
	public List<T> getList(T entity) throws Exception;
	
	public PageInfo<T> getPageList(T entity) throws Exception;

	public Integer getCount(T entity) throws Exception;

	public T getEntity(T entity) throws Exception;
	
	public T getEntityById(ID id) throws Exception;

	public int addEntity(T entity) throws Exception;

	public int modifyEntity(T entity) throws Exception;

	public int removeEntity(ID id) throws Exception;

}
