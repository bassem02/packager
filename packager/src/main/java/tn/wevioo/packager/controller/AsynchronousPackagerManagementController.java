package tn.wevioo.packager.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nordnet.architecture.exceptions.explicit.DataSourceException;
import nordnet.architecture.exceptions.explicit.NotFoundException;
import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.implicit.NullException.NullCases;
import tn.wevioo.packager.asynchronous.ActivatePackagerAsync;
import tn.wevioo.packager.asynchronous.CancelPackagerAsync;
import tn.wevioo.packager.asynchronous.ChangePackagerPropertiesAsync;
import tn.wevioo.packager.asynchronous.CreatePackagerAsync;
import tn.wevioo.packager.asynchronous.MergePackagerAsync;
import tn.wevioo.packager.asynchronous.ReactivatePackagerAsync;
import tn.wevioo.packager.asynchronous.ResetPackagerAsync;
import tn.wevioo.packager.asynchronous.SplitPackagerAsync;
import tn.wevioo.packager.asynchronous.SuspendPackagerAsync;
import tn.wevioo.packager.asynchronous.TranslocateProductInstancesAsync;
import tn.wevioo.packager.entities.ActionTicket;
import tn.wevioo.packager.exceptions.PackagerException;
import tn.wevioo.packager.model.request.MergePackagersRequest;
import tn.wevioo.packager.model.request.PackagerRequest;
import tn.wevioo.packager.model.request.PackagerTransformationRequest;
import tn.wevioo.packager.model.request.SplitPackagerRequest;
import tn.wevioo.packager.service.ActionTicketService;

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

	@Autowired
	private ChangePackagerPropertiesAsync changePackagerPropertiesAsync;

	@Autowired
	private ResetPackagerAsync resetPackagerAsync;

	@Autowired
	private MergePackagerAsync mergePackagerAsync;

	@Autowired
	private SplitPackagerAsync splitPackagerAsync;

	@Autowired
	private TranslocateProductInstancesAsync translocateProductInstancesAsync;

	@RequestMapping(value = "/actionTicket/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ActionTicket findActionTicket(@PathVariable("id") String actionId)
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

	@RequestMapping(value = "/changePackagerPropertiesAsynchronous", method = RequestMethod.PUT)
	public String changePackagerProperties(@RequestBody PackagerRequest request)
			throws DataSourceException, PackagerException {
		ActionTicket actionTicket = null;

		if (request == null) {
			throw new NullException(NullCases.NULL, "request parameter");
		}

		if ((request.getRetailerPackagerId() == null) || (request.getRetailerPackagerId().trim().length() == 0)) {
			throw new NullException(NullCases.NULL_EMPTY, "request.retailerPackagerId parameter");
		}

		try {
			actionTicket = actionTicketService.instantiateNewActionTicket("changePackagerProperties",
					request.getRetailerPackagerId());
		} catch (NotRespectedRulesException e) {
			throw new PackagerException(e);
		}

		changePackagerPropertiesAsync.setRequest(request);
		changePackagerPropertiesAsync.setTicketIdentifier(actionTicket.getIdActionTicket());
		taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(5);
		taskExecutor.setMaxPoolSize(10);
		taskExecutor.setQueueCapacity(25);
		taskExecutor.initialize();
		taskExecutor.execute(changePackagerPropertiesAsync);

		return actionTicket.getIdActionTicket();
	}

	@RequestMapping(value = "/resetPackagerAsynchronous", method = RequestMethod.PUT)
	public String resetPackager(@RequestBody PackagerRequest request) throws DataSourceException, PackagerException {
		ActionTicket actionTicket = null;

		if (request == null) {
			throw new NullException(NullCases.NULL, "request parameter");
		}

		if ((request.getRetailerPackagerId() == null) || (request.getRetailerPackagerId().trim().length() == 0)) {
			throw new NullException(NullCases.NULL_EMPTY, "request.retailerPackagerId parameter");
		}

		try {
			actionTicket = actionTicketService.instantiateNewActionTicket("resetPackager",
					request.getRetailerPackagerId());
		} catch (NotRespectedRulesException e) {
			throw new PackagerException(e);
		}

		resetPackagerAsync.setRequest(request);
		resetPackagerAsync.setTicketIdentifier(actionTicket.getIdActionTicket());
		taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(5);
		taskExecutor.setMaxPoolSize(10);
		taskExecutor.setQueueCapacity(25);
		taskExecutor.initialize();
		taskExecutor.execute(resetPackagerAsync);

		return actionTicket.getIdActionTicket();
	}

	@RequestMapping(value = "/mergePackagersAsynchronous", method = RequestMethod.POST)
	public String mergePackagers(@RequestBody MergePackagersRequest request)
			throws PackagerException, DataSourceException {

		ActionTicket actionTicket = null;

		if (request.getSource1() == null) {
			throw new NullException(NullCases.NULL, "source1 parameter");
		}

		if ((request.getSource1().getRetailerPackagerId() == null)
				|| (request.getSource1().getRetailerPackagerId().trim().length() == 0)) {
			throw new NullException(NullCases.NULL_EMPTY, "source1.retailerPackagerId parameter");
		}

		if (request.getSource2() == null) {
			throw new NullException(NullCases.NULL, "source2 parameter");
		}

		if ((request.getSource2().getRetailerPackagerId() == null)
				|| (request.getSource2().getRetailerPackagerId().trim().length() == 0)) {
			throw new NullException(NullCases.NULL_EMPTY, "source2.retailerPackagerId parameter");
		}

		try {
			actionTicket = actionTicketService.instantiateNewActionTicket("mergePackagers",
					request.getSource1().getRetailerPackagerId() + " - "
							+ request.getSource2().getRetailerPackagerId());
		} catch (NotRespectedRulesException e) {
			throw new PackagerException(e);
		}

		mergePackagerAsync.setRequest(request);
		mergePackagerAsync.setTicketIdentifier(actionTicket.getIdActionTicket());
		taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(5);
		taskExecutor.setMaxPoolSize(10);
		taskExecutor.setQueueCapacity(25);
		taskExecutor.initialize();
		taskExecutor.execute(mergePackagerAsync);

		return actionTicket.getIdActionTicket();
	}

	@RequestMapping(value = "/splitPackagerAsynchronous", method = RequestMethod.POST)
	public String splitPackager(@RequestBody SplitPackagerRequest request)
			throws PackagerException, DataSourceException {
		ActionTicket actionTicket = null;

		if (request.getSource() == null) {
			throw new NullException(NullCases.NULL, "source parameter");
		}

		if ((request.getSource().getRetailerPackagerId() == null)
				|| (request.getSource().getRetailerPackagerId().trim().length() == 0)) {
			throw new NullException(NullCases.NULL_EMPTY, "source.retailerPackagerId parameter");
		}

		try {
			actionTicket = actionTicketService.instantiateNewActionTicket("splitPackager",
					request.getSource().getRetailerPackagerId());
		} catch (NotRespectedRulesException e) {
			throw new PackagerException(e);
		}

		splitPackagerAsync.setRequest(request);
		splitPackagerAsync.setTicketIdentifier(actionTicket.getIdActionTicket());
		taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(5);
		taskExecutor.setMaxPoolSize(10);
		taskExecutor.setQueueCapacity(25);
		taskExecutor.initialize();
		taskExecutor.execute(splitPackagerAsync);

		return actionTicket.getIdActionTicket();
	}

	@RequestMapping(value = "/translocateProductInstancesAsynchronous", method = RequestMethod.POST)
	public String translocateProductInstances(@RequestBody PackagerTransformationRequest request)
			throws PackagerException, DataSourceException {
		ActionTicket actionTicket = null;

		if (request == null) {
			throw new NullException(NullCases.NULL, "request parameter");
		}

		if ((request.getRetailerPackagerId() == null) || (request.getRetailerPackagerId().trim().length() == 0)) {
			throw new NullException(NullCases.NULL_EMPTY, "request.retailerPackagerId parameter");
		}

		try {
			actionTicket = actionTicketService.instantiateNewActionTicket("translocateProductInstances",
					request.getRetailerPackagerId());
		} catch (NotRespectedRulesException e) {
			throw new PackagerException(e);
		}

		translocateProductInstancesAsync.setRequest(request);
		translocateProductInstancesAsync.setTicketIdentifier(actionTicket.getIdActionTicket());
		taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(5);
		taskExecutor.setMaxPoolSize(10);
		taskExecutor.setQueueCapacity(25);
		taskExecutor.initialize();
		taskExecutor.execute(translocateProductInstancesAsync);

		return actionTicket.getIdActionTicket();
	}

}
