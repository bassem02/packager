package tn.wevioo.packager.service;

import tn.wevioo.packager.entities.PackagerActionHistory;

public interface PackagerActionHistoryService extends CrudService<PackagerActionHistory, Integer> {

	public Long getMaxIdPackagerActionHistory();

}
