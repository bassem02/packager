package tn.wevioo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.wevioo.entities.ActionTicket;

public interface ActionTicketDAO extends JpaRepository<ActionTicket, Integer> {

	public ActionTicket findByIdActionTicket(String idActionTicket);

}
