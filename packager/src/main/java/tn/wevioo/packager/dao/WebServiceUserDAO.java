package tn.wevioo.packager.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.wevioo.packager.entities.WebServiceUser;

public interface WebServiceUserDAO extends JpaRepository<WebServiceUser, Integer> {

	public WebServiceUser findByLogin(String login);

}
