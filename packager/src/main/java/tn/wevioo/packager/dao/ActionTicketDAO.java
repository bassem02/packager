package tn.wevioo.packager.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.wevioo.packager.entities.ActionTicket;

public interface ActionTicketDAO extends JpaRepository<ActionTicket, Integer> {

	public ActionTicket findByIdActionTicket(String idActionTicket);

}
