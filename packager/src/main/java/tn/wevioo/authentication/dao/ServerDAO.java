package tn.wevioo.authentication.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.wevioo.authentication.entities.Server;

public interface ServerDAO extends JpaRepository<Server, Integer> {
	public Server findByIp(String ip);
}
