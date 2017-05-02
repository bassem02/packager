package tn.wevioo.tools.idgenerator.algorithms.stringcomputing;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import nordnet.architecture.exceptions.utils.ErrorCode;
import nordnet.tools.idgenerator.exception.AlgorithmException;

@Component

public class Md5StringComputingGenerator extends StringComputingGenerator {
	public static final String ALGORITHM_NAME = "MD5_FROM_STRING";

	public Md5StringComputingGenerator() {
		super("MD5_FROM_STRING");
	}

	public String generateIdentifier(Object parameters) throws AlgorithmException, NotRespectedRulesException {
		String result = new String();
		String content = new String();

		if ((parameters == null) || (parameters.getClass().isArray())) {
			throw new AlgorithmException(new ErrorCode("3.2.2.1"),
					new String[] { getClass().getName(), "missing or incorrect parameter : String" });
		}
		try {
			content = (String) parameters;
		} catch (ClassCastException ex) {
			throw new AlgorithmException(new ErrorCode("3.2.2.1"),
					new String[] { getClass().getName(), "parameter must be a string" }, ex);
		}

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");

			md.update(content.getBytes());
			return convertToHex(md.digest());
		} catch (NoSuchAlgorithmException ex) {
			throw new AlgorithmException(new ErrorCode("0.2.2.1"), new String[0], ex);
		}
	}
}
