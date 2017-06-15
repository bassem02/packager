package tn.wevioo.packager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nordnet.architecture.exceptions.explicit.DataSourceException;
import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.implicit.NullException.NullCases;
import tn.wevioo.packager.asynchronous.CancelProductAsync;
import tn.wevioo.packager.asynchronous.ResetProductAsync;
import tn.wevioo.packager.entities.ActionTicket;
import tn.wevioo.packager.exceptions.PackagerException;
import tn.wevioo.packager.model.request.ProductRequest;
import tn.wevioo.packager.service.ActionTicketService;

@RestController
@EnableAsync
@Configuration
public class AsynchronousProductManagementController {
	private ThreadPoolTaskExecutor taskExecutor;

	@Autowired
	private ActionTicketService actionTicketService;

	@Autowired
	private CancelProductAsync cancelProductAsync;

	@Autowired
	private ResetProductAsync resetProductAsync;

	@RequestMapping(value = "/cancelProductAsynchronous", method = RequestMethod.PUT)
	public String cancelProduct(@RequestBody ProductRequest request) throws DataSourceException, PackagerException {
		ActionTicket actionTicket = null;

		if (request == null) {
			throw new NullException(NullCases.NULL, "request parameter");
		}

		if ((request.getProductId() == null)) {
			throw new NullException(NullCases.NULL, "reuqest.productId parameter");
		}

		try {
			actionTicket = actionTicketService.instantiateNewActionTicket("cancelProduct",
					String.valueOf(request.getProductId()));
		} catch (NotRespectedRulesException e) {
			throw new PackagerException(e);
		}

		cancelProductAsync.setRequest(request);
		cancelProductAsync.setTicketIdentifier(actionTicket.getIdActionTicket());
		taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(5);
		taskExecutor.setMaxPoolSize(10);
		taskExecutor.setQueueCapacity(25);
		taskExecutor.initialize();
		taskExecutor.execute(cancelProductAsync);

		return actionTicket.getIdActionTicket();
	}

	@RequestMapping(value = "/resetProductAsynchronous", method = RequestMethod.PUT)
	public String resetProduct(@RequestBody ProductRequest request) throws DataSourceException, PackagerException {
		ActionTicket actionTicket = null;

		if (request == null) {
			throw new NullException(NullCases.NULL, "request parameter");
		}

		if ((request.getProductId() == null)) {
			throw new NullException(NullCases.NULL, "reuqest.productId parameter");
		}

		try {
			actionTicket = actionTicketService.instantiateNewActionTicket("resetProduct",
					String.valueOf(request.getProductId()));
		} catch (NotRespectedRulesException e) {
			throw new PackagerException(e);
		}

		resetProductAsync.setRequest(request);
		resetProductAsync.setTicketIdentifier(actionTicket.getIdActionTicket());
		taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(5);
		taskExecutor.setMaxPoolSize(10);
		taskExecutor.setQueueCapacity(25);
		taskExecutor.initialize();
		taskExecutor.execute(resetProductAsync);

		return actionTicket.getIdActionTicket();
	}
}
