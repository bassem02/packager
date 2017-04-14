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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Users users) {
		// TODO Auto-generated method stub

	}

	@Override
	public Users findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Users> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkUserPass(String user, String pass) {
		return usersDAO.checkUserPass(user, pass);
	}

}
