package tn.wevioo.authentication.service;

import java.util.List;

import tn.wevioo.authentication.entities.Users;

public interface UsersService {

	public Users saveOrUpdate(Users users);

	public void delete(Users users);

	public Users findById(int id);

	public List<Users> findAll();

	public boolean checkUserPass(String user, String pass);
}
