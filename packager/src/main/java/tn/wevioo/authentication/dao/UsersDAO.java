package tn.wevioo.authentication.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.wevioo.authentication.entities.Users;

public interface UsersDAO extends JpaRepository<Users, Integer>, UsersDAOCustom {

}
