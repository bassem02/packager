package tn.wevioo.driverManual.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.wevioo.driverManual.entities.EndUserHistory;

public interface EndUserHistoryDAO extends JpaRepository<EndUserHistory, Integer> {

}
