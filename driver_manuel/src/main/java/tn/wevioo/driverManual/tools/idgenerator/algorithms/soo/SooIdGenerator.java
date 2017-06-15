package tn.wevioo.driverManual.tools.idgenerator.algorithms.soo;

import java.util.GregorianCalendar;
import java.util.Random;

import org.springframework.stereotype.Component;

import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import nordnet.tools.idgenerator.IdGenerator;
import nordnet.tools.idgenerator.algorithms.IdGeneratorAlgorithm;
import nordnet.tools.idgenerator.exception.AlgorithmException;

@Component
public class SooIdGenerator extends IdGeneratorAlgorithm implements IdGenerator {
	public static final String ALGORITHM_NAME = "SECURITOO_CUSTOMER_ID";

	public SooIdGenerator() {
		super("SECURITOO_CUSTOMER_ID");
	}

	private static Character mod23(long source) {
		Character tmp;

		if (source < 10L) {
			tmp = new Character((char) (int) (source + 79L));
		} else {
			tmp = new Character((char) (int) (source - 10L + 65L));
		}
		switch (tmp.hashCode()) {
		case 48:
			return new Character('P');
		case 49:
			return new Character('R');
		case 73:
			return new Character('T');
		}
		return tmp;
	}

	private static String chiffreToLettre(long source) {
		String retour = new String();

		while (source > 23L) {
			long entier = source / 23L;
			long reste = source % 23L;
			if (reste < 0L) {
				reste += 23L;
			}
			retour = retour + mod23(reste).toString();
			source = entier;
		}

		retour = retour + mod23((int) source).toString();

		return retour;
	}

	public String generateIdentifier(Object parameters) throws AlgorithmException, NotRespectedRulesException {
		try {
			Random r = new Random();
			Thread.sleep(r.nextInt(100));
		} catch (Exception e) {
		}

		long today = new GregorianCalendar().getTimeInMillis();
		long premierJanvier = new GregorianCalendar(2001, 1, 1).getTimeInMillis();

		return chiffreToLettre(today - premierJanvier);
	}
}
