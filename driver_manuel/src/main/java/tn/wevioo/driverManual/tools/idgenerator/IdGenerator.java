package tn.wevioo.driverManual.tools.idgenerator;

import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import nordnet.tools.idgenerator.exception.AlgorithmException;

public abstract interface IdGenerator {
	public abstract String generateIdentifier(Object paramObject) throws AlgorithmException, NotRespectedRulesException;

	public abstract String getAlgorithmName();
}
