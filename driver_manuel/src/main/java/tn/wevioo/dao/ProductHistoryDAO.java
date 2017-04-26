package tn.wevioo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.wevioo.entities.ProductHistory;

public interface ProductHistoryDAO extends JpaRepository<ProductHistory, Integer> {

}
