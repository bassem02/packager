package tn.wevioo.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nordnet.architecture.exceptions.explicit.DataSourceException;
import nordnet.architecture.exceptions.explicit.NotFoundException;
import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.implicit.NullException.NullCases;
import tn.wevioo.asynchronous.ActivatePackagerAsync;
import tn.wevioo.asynchronous.CancelPackagerAsync;
import tn.wevioo.asynchronous.CreatePackagerAsync;
import tn.wevioo.asynchronous.ReactivatePackagerAsync;
import tn.wevioo.asynchronous.SuspendPackagerAsync;
import tn.wevioo.entities.ActionTicket;
import tn.wevioo.exceptions.PackagerException;
import tn.wevioo.model.request.PackagerRequest;
import tn.wevioo.service.ActionTicketService;

@RestController
@EnableAsync
@Configuration
public class AsynchronousPackagerManagementController {

	private ThreadPoolTaskExecutor taskExecutor;

	@Autowired
	private ActionTicketService actionTicketService;

	@Autowired
	private ActivatePackagerAsync activatePackagerAsync;

	@Autowired
	private ReactivatePackagerAsync reactivatePackagerAsync;

	@Autowired
	private CancelPackagerAsync cancelPackagerAsync;

	@Autowired
	private CreatePackagerAsync createPackagerAsync;

	@Autowired
	private SuspendPackagerAsync suspendPackagerAsync;

	@RequestMapping(value = "/findActionTicket", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ActionTicket findActionTicket(@QueryParam("actionId") String actionId)
			throws NotFoundException, PackagerException, DataSourceException {

		if ((actionId == null) || (actionId.trim().length() == 0)) {
			throw new NullException(NullCases.NULL_EMPTY, "actionId parameter");
		}

		ActionTicket actionTicket = actionTicketService.findByIdActionTicket(actionId);

		return actionTicket;
	}

	@RequestMapping(value = "/activatePackagerAsynchronous", method = RequestMethod.PUT)
	public String activatePackager(@RequestBody PackagerRequest request) throws PackagerException, DataSourceException {

		if (request == null) {
			throw new NullException(NullCases.NULL, "request parameter");
		}

		if ((request.getRetailerPackagerId() == null) || (request.getRetailerPackagerId().trim().length() == 0)) {
			throw new NullException(NullCases.NULL_EMPTY, "request.retailerPackagerId parameter");
		}

		ActionTicket actionTicket = null;

		try {
			actionTicket = actionTicketService.instantiateNewActionTicket("activatePackager",
					request.getRetailerPackagerId());
		} catch (NotRespectedRulesException e) {
			throw new PackagerException(e);
		}

		activatePackagerAsync.setRequest(request);
		activatePackagerAsync.setTicketIdentifier(actionTicket.getIdActionTicket());
		taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(5);
		taskExecutor.setMaxPoolSize(10);
		taskExecutor.setQueueCapacity(25);
		taskExecutor.initialize();
		taskExecutor.execute(activatePackagerAsync);

		return actionTicket.getIdActionTicket();
	}

	public void completeAction(String actionId, Boolean successed, Throwable thrownException)
			throws NotFoundException, DataSourceException, PackagerException {

		if ((actionId == null) || (actionId.trim().length() == 0)) {
			throw new NullException(NullCases.NULL_EMPTY, "actionId parameter");
		}

		if (successed == null) {
			throw new NullException(NullCases.NULL, "successed parameter");
		}

		ActionTicket actionTicket = actionTicketService.findByIdActionTicket(actionId);

		actionTicket.setCompletionDate(new Date());
		actionTicket.setFinished(true);
		actionTicket.setSuccessed(successed);

		if (thrownException != null) {
			actionTicket.setThrownExceptionName(thrownException.getClass().getSimpleName());
			actionTicket.setThrownExceptionMessage(thrownException.getMessage());

			StringWriter stackTrace = new StringWriter();
			PrintWriter stackTraceWriter = new PrintWriter(stackTrace);
			thrownException.printStackTrace(stackTraceWriter);
			actionTicket.setCompleteStackTrace(stackTrace.toString());
		}

		actionTicketService.saveOrUpdate(actionTicket);
	}

	@RequestMapping(value = "/cancelPackagerAsynchronous", method = RequestMethod.PUT)
	public String cancelPackager(@RequestBody PackagerRequest request) throws PackagerException, DataSourceException {
		ActionTicket actionTicket = null;

		if (request == null) {
			throw new NullException(NullCases.NULL, "request parameter");
		}

		if ((request.getRetailerPackagerId() == null) || (request.getRetailerPackagerId().trim().length() == 0)) {
			throw new NullException(NullCases.NULL_EMPTY, "request.retailerPackagerId parameter");
		}

		try {
			actionTicket = actionTicketService.instantiateNewActionTicket("cancelPackager",
					request.getRetailerPackagerId());
		} catch (NotRespectedRulesException e) {
			throw new PackagerException(e);
		}

		cancelPackagerAsync.setRequest(request);
		cancelPackagerAsync.setTicketIdentifier(actionTicket.getIdActionTicket());
		taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(5);
		taskExecutor.setMaxPoolSize(10);
		taskExecutor.setQueueCapacity(25);
		taskExecutor.initialize();
		taskExecutor.execute(cancelPackagerAsync);

		return actionTicket.getIdActionTicket();
	}

	@RequestMapping(value = "/createPackagerAsynchronous", method = RequestMethod.PUT)
	public String createPackager(@RequestBody PackagerRequest request) throws DataSourceException, PackagerException {
		ActionTicket actionTicket = null;

		if (request == null) {
			throw new NullException(NullCases.NULL, "request parameter");
		}

		if ((request.getRetailerPackagerId() == null) || (request.getRetailerPackagerId().trim().length() == 0)) {
			throw new NullException(NullCases.NULL_EMPTY, "request.retailerPackagerId parameter");
		}

		if ((request.getModel() == null) || (request.getModel().trim().length() == 0)) {
			throw new NullException(NullCases.NULL_EMPTY, "request.model parameter");
		}

		try {
			actionTicket = actionTicketService.instantiateNewActionTicket("createPackager",
					request.getRetailerPackagerId());
		} catch (NotRespectedRulesException e) {
			throw new PackagerException(e);
		}

		createPackagerAsync.setRequest(request);
		createPackagerAsync.setTicketIdentifier(actionTicket.getIdActionTicket());
		taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(5);
		taskExecutor.setMaxPoolSize(10);
		taskExecutor.setQueueCapacity(25);
		taskExecutor.initialize();
		taskExecutor.execute(createPackagerAsync);

		return actionTicket.getIdActionTicket();
	}

	@RequestMapping(value = "/reactivatePackagerAsynchronous", method = RequestMethod.PUT)
	public String reactivatePackager(@RequestBody PackagerRequest request)
			throws DataSourceException, PackagerException {
		ActionTicket actionTicket = null;

		if (request == null) {
			throw new NullException(NullCases.NULL, "request parameter");
		}

		if ((request.getRetailerPackagerId() == null) || (request.getRetailerPackagerId().trim().length() == 0)) {
			throw new NullException(NullCases.NULL_EMPTY, "request.retailerPackagerId parameter");
		}

		try {
			actionTicket = actionTicketService.instantiateNewActionTicket("reactivatePackager",
					request.getRetailerPackagerId());
		} catch (NotRespectedRulesException e) {
			throw new PackagerException(e);
		}

		reactivatePackagerAsync.setRequest(request);
		reactivatePackagerAsync.setTicketIdentifier(actionTicket.getIdActionTicket());
		taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(5);
		taskExecutor.setMaxPoolSize(10);
		taskExecutor.setQueueCapacity(25);
		taskExecutor.initialize();
		taskExecutor.execute(reactivatePackagerAsync);

		return actionTicket.getIdActionTicket();
	}

	@RequestMapping(value = "/suspendPackagerAsynchronous", method = RequestMethod.PUT)
	public String suspendPackager(@RequestBody PackagerRequest request) throws DataSourceException, PackagerException {
		ActionTicket actionTicket = null;

		if (request == null) {
			throw new NullException(NullCases.NULL, "request parameter");
		}

		if ((request.getRetailerPackagerId() == null) || (request.getRetailerPackagerId().trim().length() == 0)) {
			throw new NullException(NullCases.NULL_EMPTY, "request.retailerPackagerId parameter");
		}

		try {
			actionTicket = actionTicketService.instantiateNewActionTicket("suspendPackager",
					request.getRetailerPackagerId());
		} catch (NotRespectedRulesException e) {
			throw new PackagerException(e);
		}

		suspendPackagerAsync.setRequest(request);
		suspendPackagerAsync.setTicketIdentifier(actionTicket.getIdActionTicket());
		taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(5);
		taskExecutor.setMaxPoolSize(10);
		taskExecutor.setQueueCapacity(25);
		taskExecutor.initialize();
		taskExecutor.execute(suspendPackagerAsync);

		return actionTicket.getIdActionTicket();
	}

}
