package tn.wevioo.driverManual.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.implicit.NullException.NullCases;
import nordnet.architecture.exceptions.utils.ErrorCode;
import tn.wevioo.driverManual.dao.EndUserHistoryDAO;
import tn.wevioo.driverManual.entities.EndUserHistory;
import tn.wevioo.driverManual.service.EndUserHistoryService;

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
	public EndUserHistory findById(int id) throws NotFoundException {
		if (((Integer) id == null)) {
			throw new NullException(NullCases.NULL_EMPTY, "id parameter");
		}
		EndUserHistory result = endUserHistoryDAO.findOne(id);

		if (result == null) {
			throw new NotFoundException(new ErrorCode("0.2.1.3.2"), new Object[] { "End user history", " id", id });
		}
		return result;
	}

	@Override
	public List<EndUserHistory> findAll() {
		return endUserHistoryDAO.findAll();
	}

}
