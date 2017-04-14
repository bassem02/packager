package tn.wevioo.authentication.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Qualifier;

import tn.wevioo.authentication.dao.UsersDAOCustom;

public class UsersDAOImpl implements UsersDAOCustom {

	@PersistenceContext
	@Qualifier("authenticationEntityManagerFactory")
	private EntityManager entityManager;

	@Override
	public boolean checkUserPass(String user, String pass) {

		String select = "FROM Users user WHERE user.username=:username and user.password=:password";

		Query query = entityManager.createQuery(select);
		query.setParameter("username", user);
		query.setParameter("password", pass);

		if (query.getSingleResult() == null)
			return false;
		else

			return true;
	}
}
