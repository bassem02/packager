package tn.wevioo.authentication.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.wevioo.authentication.dao.UsersDAO;
import tn.wevioo.authentication.entities.Users;
import tn.wevioo.authentication.service.UsersService;

@Service("usersService")
public class UsersServiceImpl implements UsersService {

	@Autowired
	public UsersDAO usersDAO;

	@Override
	public Users saveOrUpdate(Users users) {
		return usersDAO.saveAndFlush(users);
	}

	@Override
	public void delete(Users users) {
		usersDAO.delete(users);

	}

	@Override
	public Users findById(int id) {
		return usersDAO.findOne(id);
	}

	@Override
	public List<Users> findAll() {
		return usersDAO.findAll();
	}

	@Override
	public boolean checkUserPass(String user, String pass) {
		return usersDAO.checkUserPass(user, pass);
	}

}
