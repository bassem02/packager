package tn.wevioo.driverManual.tools.idgenerator.algorithms;

import org.springframework.stereotype.Component;

import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.tools.idgenerator.IdGenerator;

@Component
public abstract class IdGeneratorAlgorithm implements IdGenerator {
	private String algorithmName = null;

	protected IdGeneratorAlgorithm(String algorithmName) {
		if ((algorithmName == null) || (algorithmName.trim().length() == 0)) {
			throw new NullException(NullException.NullCases.NULL_EMPTY, "algorithm name");
		}

		this.algorithmName = algorithmName;
	}

	public String getAlgorithmName() {
		return algorithmName;
	}
}
