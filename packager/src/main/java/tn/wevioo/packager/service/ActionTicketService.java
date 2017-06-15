package tn.wevioo.packager.service;

import java.util.List;

import nordnet.architecture.exceptions.explicit.DataSourceException;
import nordnet.architecture.exceptions.explicit.NotFoundException;
import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import tn.wevioo.packager.entities.ActionTicket;

public interface ActionTicketService {

	public ActionTicket saveOrUpdate(ActionTicket actionTicket);

	public void delete(ActionTicket actionTicket);

	public ActionTicket findById(int id) throws NotFoundException;

	public List<ActionTicket> findAll() throws NotFoundException;

	public ActionTicket findByIdActionTicket(String idActionTicket);

	public ActionTicket instantiateNewActionTicket(String string, String retailerPackagerId)
			throws NotRespectedRulesException, DataSourceException;
}
