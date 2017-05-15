package tn.wevioo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.wevioo.entities.WebServiceUser;

public interface WebServiceUserDAO extends JpaRepository<WebServiceUser, Integer> {

	public WebServiceUser findByLogin(String login);

}
