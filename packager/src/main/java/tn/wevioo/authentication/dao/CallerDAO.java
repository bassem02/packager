package tn.wevioo.authentication.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.wevioo.authentication.entities.Caller;

public interface CallerDAO extends JpaRepository<Caller, Integer> {

}
