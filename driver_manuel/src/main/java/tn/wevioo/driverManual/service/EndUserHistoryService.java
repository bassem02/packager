package tn.wevioo.driverManual.service;

import java.util.List;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import tn.wevioo.driverManual.entities.EndUserHistory;

public interface EndUserHistoryService {

	EndUserHistory saveOrUpdate(EndUserHistory endUserHistory);

	void delete(EndUserHistory endUserHistory);

	EndUserHistory findById(int id) throws NotFoundException;

	List<EndUserHistory> findAll();

}
