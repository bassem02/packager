package tn.wevioo.authentication.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.wevioo.authentication.dao.ServerDAO;
import tn.wevioo.authentication.entities.Server;
import tn.wevioo.authentication.service.ServerService;

@Service("serverService")
public class ServerServiceImpl implements ServerService {

	@Autowired
	public ServerDAO serverDAO;

	@Override
	public Server saveOrUpdate(Server server) {
		return serverDAO.saveAndFlush(server);
	}

	@Override
	public void delete(Server server) {
		serverDAO.delete(server);

	}

	@Override
	public Server findById(int id) {
		return serverDAO.findOne(id);
	}

	@Override
	public List<Server> findAll() {
		return serverDAO.findAll();
	}

	@Override
	public Server findByIp(String ip) {
		return serverDAO.findByIp(ip);
	}

}
