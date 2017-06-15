package tn.wevioo.driverManual.tools.idgenerator;

import java.util.Set;

import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import nordnet.tools.idgenerator.exception.AlgorithmException;

public abstract interface IdGeneratorFactory {
	public abstract Set<String> getPossibleAlgorithms();

	public abstract String generateIdentifier(String paramString, Object paramObject)
			throws AlgorithmException, NotRespectedRulesException;
}
