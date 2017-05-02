package tn.wevioo.tools.idgenerator.algorithms.filecomputing;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.stereotype.Component;

import nordnet.tools.idgenerator.IdGenerator;
import nordnet.tools.idgenerator.algorithms.IdGeneratorAlgorithm;

@Component
public abstract class FileComputingGenerator extends IdGeneratorAlgorithm implements IdGenerator {
	protected FileComputingGenerator(String algorithmName) {
		super(algorithmName);
	}

	protected static byte[] fileToByteTable(File file) throws FileNotFoundException, IOException {
		byte[] buffer = new byte[(int) file.length()];
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		bis.read(buffer);
		return buffer;
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
