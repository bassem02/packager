package tn.wevioo.packager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.implicit.NullException.NullCases;
import nordnet.architecture.exceptions.utils.ErrorCode;
import tn.wevioo.packager.dao.PackagerActionHistoryDao;
import tn.wevioo.packager.entities.PackagerActionHistory;
import tn.wevioo.packager.service.PackagerActionHistoryService;

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
	public PackagerActionHistory findById(int id) throws NotFoundException {

		if (((Integer) id == null)) {
			throw new NullException(NullCases.NULL_EMPTY, "id parameter");
		}
		PackagerActionHistory result = packagerActionHistoryDao.findOne(id);

		if (result == null) {
			throw new NotFoundException(new ErrorCode("0.2.1.3.2"),
					new Object[] { "packager action history", " id", id });
		}
		return result;
	}

	@Override
	public List<PackagerActionHistory> findAll() throws NotFoundException {
		List<PackagerActionHistory> result = packagerActionHistoryDao.findAll();

		if (result == null) {
			throw new NotFoundException(new ErrorCode("0.2.1.3.2"), new Object[] { "packager action histories" });
		}
		return result;
	}

	@Override
	public Long getMaxIdPackagerActionHistory() {
		return packagerActionHistoryDao.getMaxIdPackagerActionHistory();
	}

}
