package tn.wevioo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.wevioo.dao.EndUserHistoryDAO;
import tn.wevioo.entities.EndUserHistory;
import tn.wevioo.service.EndUserHistoryService;

@Service("endUserHistoryService")
public class EndUserHistoryServiceImpl implements EndUserHistoryService {

	@Autowired
	public EndUserHistoryDAO endUserHistoryDAO;

	@Override
	public EndUserHistory saveOrUpdate(EndUserHistory endUserHistory) {
		return endUserHistoryDAO.saveAndFlush(endUserHistory);
	}

	@Override
	public void delete(EndUserHistory endUserHistory) {
		endUserHistoryDAO.delete(endUserHistory);
	}

	@Override
	public EndUserHistory findById(int id) {
		return endUserHistoryDAO.findOne(id);
	}

	@Override
	public List<EndUserHistory> findAll() {
		return endUserHistoryDAO.findAll();
	}

}
