package tn.wevioo.tools.idgenerator.algorithms.stringcomputing;

import java.util.UUID;

import org.springframework.stereotype.Component;

import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import nordnet.tools.idgenerator.algorithms.IdGeneratorAlgorithm;
import nordnet.tools.idgenerator.exception.AlgorithmException;

@Component
public class UUIDGenerator extends IdGeneratorAlgorithm {
	public static final String ALGORITHM_NAME = "UUID";

	public UUIDGenerator() {
		super("UUID");
	}

	public String generateIdentifier(Object parameters) throws AlgorithmException, NotRespectedRulesException {
		return UUID.randomUUID().toString();
	}
}
