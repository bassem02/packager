package tn.wevioo.packager.service;

import nordnet.architecture.exceptions.explicit.DataSourceException;
import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import tn.wevioo.packager.entities.ActionTicket;

public interface ActionTicketService extends CrudService<ActionTicket, Integer> {

	public ActionTicket findByIdActionTicket(String idActionTicket);

	public ActionTicket instantiateNewActionTicket(String string, String retailerPackagerId)
			throws NotRespectedRulesException, DataSourceException;
}
