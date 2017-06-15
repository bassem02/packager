package tn.wevioo.packager.tools.xml;

import nordnet.architecture.exceptions.explicit.MalformedXMLException;
import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import nordnet.tools.xml.merger.XmlMerger;

/**
 * The interface {@link PackagerXmlMerger} defines the Xml Merger operation used
 * to perform specific Packager Xml Management.
 */
public interface PackagerXmlMerger extends XmlMerger {

	/**
	 * The method merge merges the given xml parameters into one xml document.
	 * <p>
	 * This packager method redefines the default behavior proposed by the
	 * eponym method on the interface XmlMerge. Indeed, contrary to this parent
	 * method, the given parameters can be null. If the priority Xml properties
	 * are null, the method will return the secondary Xml without any other
	 * modification. On the other hand, if the secondary Xml properties are
	 * null, the method will return the priority Xml without Xml modification.
	 * This small improvement should make easier the bigger processes into
	 * creation or change properties methods on packager.
	 * 
	 * @param priorityXml
	 *            The first Xml to be merged with the other one. This one is
	 *            considered as priority on the second parameter. The priority
	 *            management rules are let to the chosen implementation. If
	 *            null, the secondary Xml properties will be returned as a
	 *            result without any other modification.
	 * @param secondaryXml
	 *            The second Xml to be merged. This one is considered as
	 *            secondary regarding the other one. If null, the priority Xml
	 *            will be returned as a result without any other verification.
	 * @return The merged xml files represented as a String.
	 * @throws MalformedXMLException
	 *             custom exception.
	 * @throws NotRespectedRulesException
	 *             custom exception.
	 */
	public String merge(final String priorityXml, final String secondaryXml)
			throws MalformedXMLException, NotRespectedRulesException;
}