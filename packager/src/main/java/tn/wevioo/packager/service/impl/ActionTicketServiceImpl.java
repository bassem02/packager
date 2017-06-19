package tn.wevioo.packager.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nordnet.architecture.exceptions.explicit.DataSourceException;
import nordnet.architecture.exceptions.explicit.NotFoundException;
import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.implicit.NullException.NullCases;
import tn.wevioo.packager.dao.ActionTicketDAO;
import tn.wevioo.packager.entities.ActionTicket;
import tn.wevioo.packager.service.ActionTicketService;

@Service("actionTicketService")
public class ActionTicketServiceImpl implements ActionTicketService {

	@Autowired
	public ActionTicketDAO actionTicketDao;

	@Override
	public ActionTicket saveOrUpdate(ActionTicket actionTicket) {

		return actionTicketDao.saveAndFlush(actionTicket);

	}

	@Override
	public void delete(ActionTicket actionTicket) {

		actionTicketDao.delete(actionTicket);

	}

	@Override
	public ActionTicket findById(Integer id) throws NotFoundException {

		return actionTicketDao.findOne(id);

	}

	@Override
	public List<ActionTicket> findAll() throws NotFoundException {

		return actionTicketDao.findAll();

	}

	@Override
	public ActionTicket findByIdActionTicket(String idActionTicket) {

		return actionTicketDao.findByIdActionTicket(idActionTicket);

	}

	@Override
	public ActionTicket instantiateNewActionTicket(String methodName, String entityIdentifier)
			throws NotRespectedRulesException, DataSourceException {

		if ((methodName == null) || (methodName.trim().length() == 0)) {
			throw new NullException(NullCases.NULL_EMPTY, "methodName parameter");
		}

		if ((entityIdentifier != null) && (entityIdentifier.trim().length() == 0)) {
			throw new NullException(NullCases.EMPTY, "entityIdentifier parameter");
		}

		ActionTicket ticket = new ActionTicket();

		ticket.setCreationDate(new Date());
		ticket.setIdActionTicket(ActionTicket.generateIdentifier());
		ticket.setMethodName(methodName);
		ticket.setEntityId(entityIdentifier);
		ticket.setCompletionDate(null);
		ticket.setFinished(false);
		ticket.setSuccessed(false);
		ticket.setLastUpdate(new Date());

		this.saveOrUpdate(ticket);

		return ticket;
	}

}
