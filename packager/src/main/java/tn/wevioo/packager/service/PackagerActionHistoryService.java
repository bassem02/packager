package tn.wevioo.packager.service;

import java.util.List;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import tn.wevioo.packager.entities.PackagerActionHistory;

public interface PackagerActionHistoryService {

	public PackagerActionHistory saveOrUpdate(PackagerActionHistory packagerActionHistory);

	public void delete(PackagerActionHistory packagerActionHistory);

	public PackagerActionHistory findById(int id) throws NotFoundException;

	public List<PackagerActionHistory> findAll() throws NotFoundException;

	public Long getMaxIdPackagerActionHistory();

}
