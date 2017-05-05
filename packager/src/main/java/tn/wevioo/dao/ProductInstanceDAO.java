package tn.wevioo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.wevioo.entities.ProductInstance;

public interface ProductInstanceDAO extends JpaRepository<ProductInstance, Integer> {

}
