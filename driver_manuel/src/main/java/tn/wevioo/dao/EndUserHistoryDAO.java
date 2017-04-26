package tn.wevioo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.wevioo.entities.EndUserHistory;

public interface EndUserHistoryDAO extends JpaRepository<EndUserHistory, Integer> {

}
