package tn.wevioo.driverManual.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.wevioo.driverManual.entities.ProductHistory;

public interface ProductHistoryDAO extends JpaRepository<ProductHistory, Integer> {

}
