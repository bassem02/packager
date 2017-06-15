package tn.wevioo.packager.tools.xml.impl;

import nordnet.architecture.exceptions.explicit.MalformedXMLException;
import nordnet.architecture.exceptions.explicit.MalformedXMLException.MalformedCases;
import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.implicit.NullException.NullCases;
import nordnet.tools.xml.merger.impl.XmlMergeImpl;
import tn.wevioo.packager.tools.xml.PackagerXmlMerger;

/**
 * The class {@link PackagerXmlMergerImpl} provides a specific implementation of
 * {@link PackagerXmlMerger}, dedicated to Packager Xml Management and based on
 * XmlMerge engine.
 */
public class PackagerXmlMergerImpl extends XmlMergeImpl implements PackagerXmlMerger {

	/**
	 * {@inheritDoc}
	 */
	public String merge(final String priorityXml, final String secondaryXml)
			throws MalformedXMLException, NotRespectedRulesException {

		if (priorityXml == null) {
			return secondaryXml;
		}

		if (secondaryXml == null) {
			return priorityXml;
		}

		if (priorityXml.trim().length() == 0) {
			throw new NullException(NullCases.EMPTY, "priorityXml parameter");
		}

		if (secondaryXml.trim().length() == 0) {
			throw new NullException(NullCases.EMPTY, "secondaryXml parameter");
		}

		try {
			return super.merge(priorityXml, secondaryXml);
		} catch (IllegalArgumentException ex) {
			String faultyXml = "Priority xml: \n\n" + priorityXml + "\nSecondary Xml:\n\n" + secondaryXml;
			throw new MalformedXMLException(MalformedCases.INVALID_SCHEMA, faultyXml, ex);
		}
	}
}