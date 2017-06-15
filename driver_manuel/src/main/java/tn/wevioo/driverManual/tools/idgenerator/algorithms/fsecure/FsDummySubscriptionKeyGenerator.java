package tn.wevioo.driverManual.tools.idgenerator.algorithms.fsecure;

import java.io.CharArrayWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.utils.ErrorCode;
import nordnet.tools.idgenerator.IdGenerator;
import nordnet.tools.idgenerator.algorithms.IdGeneratorAlgorithm;
import nordnet.tools.idgenerator.exception.AlgorithmException;

@Component
public class FsDummySubscriptionKeyGenerator extends IdGeneratorAlgorithm implements IdGenerator {
	private static final String ALPHABET = "AZERTYUIOPQSDFGHJKLMWXCVBN123456789";
	private static final int NUMBER_OF_CHAR_FOR_GENERATION = 200;
	private String pattern = "{0}{1}{2}{3}-{4}{5}{6}{7}-{8}{9}{10}{11}-{12}{13}{14}{15}-{16}{17}{18}{19}";

	public static final String ALGORITHM_NAME = "FS_DUMMY_SUBSCRIPTION_KEY";

	public FsDummySubscriptionKeyGenerator() {
		super("FS_DUMMY_SUBSCRIPTION_KEY");
	}

	public String generateIdentifier(Object parameters)
			throws AlgorithmException, NullException, NotRespectedRulesException {
		String content = new String();
		CharArrayWriter writer = new CharArrayWriter();
		String firstchars = new String();

		if (parameters != null) {
			try {
				firstchars = (String) parameters;
				for (int i = 0; i < firstchars.length(); i++) {
					pattern = pattern.replaceFirst("\\{[\\d]+\\}",
							Character.toString(firstchars.charAt(i)).toUpperCase());
				}
			} catch (Exception ex) {
				throw new NotRespectedRulesException(new ErrorCode("3.2.2.1"),
						new String[] { getClass().getName(), "incorrect parameter : a String is better" });
			}
		}

		if (("AZERTYUIOPQSDFGHJKLMWXCVBN123456789".length() > 0) || ("AZERTYUIOPQSDFGHJKLMWXCVBN123456789" != null)) {
		}

		while (writer.size() < 200) {
			writer.append("AZERTYUIOPQSDFGHJKLMWXCVBN123456789");
			continue;
		}
		writer.flush();
		writer.close();

		MessageFormat messageFormat = new MessageFormat(pattern);

		Character[] randomizedAlphabet = randomize(writer.toCharArray());

		content = messageFormat.format(randomizedAlphabet);

		if ((content == null) || (content.trim().length() == 0)) {
			throw new AlgorithmException(new ErrorCode("0.2.1.1.3"), new String[] { "generated ID" });
		}
		return content;
	}

	private Character[] randomize(char[] table) {
		List<Character> randomizedAlphabet = new ArrayList();
		for (char c : table)
			randomizedAlphabet.add(Character.valueOf(c));
		Collections.shuffle(randomizedAlphabet);
		return (Character[]) randomizedAlphabet.toArray(new Character[0]);
	}
}
