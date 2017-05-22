package tn.wevioo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tn.wevioo.entities.PackagerActionHistory;

public interface PackagerActionHistoryDao extends JpaRepository<PackagerActionHistory, Integer> {

	@Query("SELECT MAX(id) FROM PackagerActionHistory")
	public long getMaxIdPackagerActionHistory();
}
