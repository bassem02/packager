package tn.wevioo.tools.idgenerator.factory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.utils.ErrorCode;
import nordnet.tools.idgenerator.IdGenerator;
import nordnet.tools.idgenerator.IdGeneratorFactory;
import nordnet.tools.idgenerator.exception.AlgorithmException;

@Component
public class IdGeneratorFactoryImpl implements IdGeneratorFactory {
	private Map<String, IdGenerator> algorithms;

	public IdGeneratorFactoryImpl() {
	}

	public void setAlgorithms(List<IdGenerator> algorithms) {
		if (this.algorithms == null) {
			this.algorithms = new HashMap();
		}

		for (IdGenerator algorithm : algorithms) {
			this.algorithms.put(algorithm.getAlgorithmName(), algorithm);
		}
	}

	public Set<String> getPossibleAlgorithms() {
		return algorithms.keySet();
	}

	public String generateIdentifier(String algorithmName, Object algorithmParameters)
			throws AlgorithmException, NotRespectedRulesException {
		if ((algorithmName == null) || (algorithmName.trim().length() == 0)) {
			throw new NullException(NullException.NullCases.NULL_EMPTY, "algorithm name");
		}

		if (!algorithms.keySet().contains(algorithmName)) {
			throw new AlgorithmException(new ErrorCode("0.2.1.3.2"),
					new Object[] { "algorithm", "name", algorithmName });
		}

		return ((IdGenerator) algorithms.get(algorithmName)).generateIdentifier(algorithmParameters);
	}
}
