package tn.wevioo.driverManual.service;

import java.util.List;

import nordnet.architecture.exceptions.explicit.NotFoundException;

public interface CrudService<T, ID> {

	public T saveOrUpdate(T t);

	public void delete(T t);

	public T findById(ID id) throws NotFoundException;

	public List<T> findAll() throws NotFoundException;
}
