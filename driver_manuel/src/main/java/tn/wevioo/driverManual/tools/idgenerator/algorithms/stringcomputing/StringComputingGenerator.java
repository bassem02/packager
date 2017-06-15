package tn.wevioo.driverManual.tools.idgenerator.algorithms.stringcomputing;

import org.springframework.stereotype.Component;

import nordnet.tools.idgenerator.IdGenerator;
import nordnet.tools.idgenerator.algorithms.IdGeneratorAlgorithm;

@Component
public abstract class StringComputingGenerator extends IdGeneratorAlgorithm implements IdGenerator {
	protected StringComputingGenerator(String algorithmName) {
		super(algorithmName);
	}

	protected static String convertToHex(byte[] data) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			int halfbyte = data[i] >>> 4 & 0xF;
			int two_halfs = 0;
			do {
				if ((0 <= halfbyte) && (halfbyte <= 9)) {
					buf.append((char) (48 + halfbyte));
				} else
					buf.append((char) (97 + (halfbyte - 10)));
				halfbyte = data[i] & 0xF;
			} while (two_halfs++ < 1);
		}
		return buf.toString();
	}
}
