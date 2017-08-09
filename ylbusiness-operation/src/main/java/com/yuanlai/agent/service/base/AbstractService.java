package com.yuanlai.agent.service.base;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.yuanlai.agent.dao.BaseDao;

/**
 * 鎶借薄鏈嶅姟绫�
 * 
 * @author Lee
 * @param <T, ID>
 *
 */
public abstract class AbstractService<T, ID> implements BaseService<T, ID> {
	
	public abstract BaseDao<T, ID> getDao();
	
	@Override
	public List<T> getList(T entity) throws Exception {
		return getDao().selectList(entity);
	}

	@Override
	public PageInfo<T> getPageList(T entity) throws Exception {
		List<T> list = getDao().selectList(entity);
		PageInfo<T> pageInfo = new PageInfo<T>(list);
		return pageInfo;
	}

	@Override
	public Integer getCount(T entity) throws Exception {
		return getDao().countEntity(entity);
	}

	@Override
	public T getEntity(T entity) throws Exception {
		return getDao().selectEntity(entity);
	}

	@Override
	public int addEntity(T entity) throws Exception {
		return getDao().insertEntity(entity);
	}

	@Override
	public int modifyEntity(T entity) throws Exception {
		return getDao().updateEntity(entity);
	}

	@Override
	public int removeEntity(ID id) throws Exception {
		return getDao().deleteEntity(id);
	}

	@Override
	public T getEntityById(ID id) throws Exception {
		return getDao().selectEntityById(id);
	}

}
