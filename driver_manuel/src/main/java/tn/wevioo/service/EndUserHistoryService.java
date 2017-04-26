package tn.wevioo.service;

import java.util.List;

import tn.wevioo.entities.EndUserHistory;

public interface EndUserHistoryService {

	EndUserHistory saveOrUpdate(EndUserHistory endUserHistory);

	void delete(EndUserHistory endUserHistory);

	EndUserHistory findById(int id);

	List<EndUserHistory> findAll();

}
