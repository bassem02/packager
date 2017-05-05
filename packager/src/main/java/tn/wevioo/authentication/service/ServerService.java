package tn.wevioo.authentication.service;

import java.util.List;

import tn.wevioo.authentication.entities.Server;

public interface ServerService {

	public Server saveOrUpdate(Server server);

	public void delete(Server server);

	public Server findById(int id);

	public List<Server> findAll();

	public Server findByIp(String ip);

}
