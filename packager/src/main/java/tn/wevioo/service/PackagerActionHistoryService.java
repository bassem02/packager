package tn.wevioo.service;

import java.util.List;

import tn.wevioo.entities.PackagerActionHistory;

public interface PackagerActionHistoryService {

	public PackagerActionHistory saveOrUpdate(PackagerActionHistory packagerActionHistory);

	public void delete(PackagerActionHistory packagerActionHistory);

	public PackagerActionHistory findById(int id);

	public List<PackagerActionHistory> findAll();

}
