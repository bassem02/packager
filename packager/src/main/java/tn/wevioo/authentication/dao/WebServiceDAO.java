package tn.wevioo.authentication.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.wevioo.authentication.entities.WebService;

public interface WebServiceDAO extends JpaRepository<WebService, Integer> {

}
