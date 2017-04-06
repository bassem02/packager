package tn.wevioo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.wevioo.dao.PackagerActionHistoryDao;
import tn.wevioo.entities.PackagerActionHistory;
import tn.wevioo.service.PackagerActionHistoryService;

@Service("packagerActionHistoryService")
public class PackagerActionHistoryServiceImpl implements PackagerActionHistoryService {

	@Autowired
	public PackagerActionHistoryDao packagerActionHistoryDao;

	@Override
	public PackagerActionHistory saveOrUpdate(PackagerActionHistory packagerActionHistory) {
		return packagerActionHistoryDao.saveAndFlush(packagerActionHistory);
	}

	@Override
	public void delete(PackagerActionHistory packagerActionHistory) {
		packagerActionHistoryDao.delete(packagerActionHistory);
	}

	@Override
	public PackagerActionHistory findById(int id) {
		return packagerActionHistoryDao.findOne(id);
	}

	@Override
	public List<PackagerActionHistory> findAll() {
		return packagerActionHistoryDao.findAll();
	}

}
