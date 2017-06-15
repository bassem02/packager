package tn.wevioo.driverManual.tools.idgenerator.algorithms.filecomputing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import nordnet.architecture.exceptions.utils.ErrorCode;
import nordnet.tools.idgenerator.exception.AlgorithmException;

@Component
public class ShaFileComputingGenerator extends FileComputingGenerator {
	public static final String ALGORITHM_NAME = "SHA_FROM_FILE";

	public ShaFileComputingGenerator() {
		super("SHA_FROM_FILE");
	}

	public String generateIdentifier(Object parameters) throws AlgorithmException, NotRespectedRulesException {
		String result = new String();
		File file = null;

		if ((parameters == null) || (parameters.getClass().isArray())) {
			throw new AlgorithmException(new ErrorCode("3.2.2.1"),
					new String[] { getClass().getName(), "missing or incorrect parameter : File" });
		}
		try {
			file = (File) parameters;
		} catch (ClassCastException ex) {
			throw new AlgorithmException(new ErrorCode("3.2.2.1"),
					new String[] { getClass().getName(), "parameter must be a file" }, ex);
		}

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] fileToByte = new byte[0];

			fileToByte = fileToByteTable(file);

			md.update(fileToByte);
			return convertToHex(md.digest());
		} catch (FileNotFoundException ex) {
			throw new AlgorithmException(new ErrorCode("0.1.1.2.1"), new String[] { file.getName() }, ex);
		} catch (IOException ex) {
			throw new AlgorithmException(new ErrorCode("0.1.1.2.7"), new String[] { file.getName() }, ex);
		} catch (NoSuchAlgorithmException ex) {
			throw new AlgorithmException(new ErrorCode("0.2.2.1"), new String[0], ex);
		}
	}
}
