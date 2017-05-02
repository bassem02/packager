package tn.wevioo.tools.idgenerator.algorithms.stringcomputing;

import java.util.Date;

import org.springframework.stereotype.Component;

import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import nordnet.architecture.exceptions.utils.ErrorCode;
import nordnet.tools.idgenerator.IdGenerator;
import nordnet.tools.idgenerator.algorithms.IdGeneratorAlgorithm;
import nordnet.tools.idgenerator.exception.AlgorithmException;

@Component
public class UniqueStringGenerator extends IdGeneratorAlgorithm implements IdGenerator {
	public static final String ALGORITHM_NAME = "UNIQUE_STRING_GENERATOR";
	private final int DEFAULT_IDENTIFIER_LENGTH = 8;

	public UniqueStringGenerator() {
		super("UNIQUE_STRING_GENERATOR");
	}

	public String generateIdentifier(Object parameters) throws AlgorithmException, NotRespectedRulesException {
		int idLength = 8;

		if (parameters != null) {
			try {
				idLength = ((Integer) parameters).intValue();
			} catch (ClassCastException ex) {
				throw new AlgorithmException(new ErrorCode("3.2.2.1"),
						new String[] { getClass().getName(), "parameter must be an Integer" }, ex);
			}
		}

		String generatedID = new String();
		while (generatedID.length() < idLength) {

			generatedID = generatedID
					+ new ShaStringComputingGenerator().generateIdentifier(String.valueOf(new Date().getTime()));
		}

		return generatedID.substring(0, idLength);
	}
}
