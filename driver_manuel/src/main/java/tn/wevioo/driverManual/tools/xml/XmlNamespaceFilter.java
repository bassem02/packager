package tn.wevioo.driverManual.tools.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.XMLFilterImpl;

public class XmlNamespaceFilter extends XMLFilterImpl {

	private boolean addNamespace = false;

	private String namespaceUri = "";

	private String prefix = null;

	private boolean addedNamespace = false;

	public XmlNamespaceFilter(boolean addNamespace, String namespaceUri, String prefix) {
		this.addNamespace = addNamespace;

		if (addNamespace) {
			this.namespaceUri = namespaceUri;
		}
		this.prefix = prefix;
	}

	public void startDocument() throws SAXException {
		super.startDocument();
		if ((addNamespace) && (addNamespace) && (!addedNamespace)) {
			super.startPrefixMapping(prefix, namespaceUri);
			addedNamespace = true;
		}
	}

	public void startElement(String arg0, String arg1, String arg2, Attributes arg3) throws SAXException {
		super.startElement(namespaceUri, arg1, prefix + ":" + arg2, arg3);
	}

	public void endElement(String arg0, String arg1, String arg2) throws SAXException {
		super.endElement(namespaceUri, arg1, prefix + ":" + arg2);
	}

	public void startPrefixMapping(String prefix, String uri) throws SAXException {
		super.startPrefixMapping(this.prefix, uri);
	}

	public void endPrefixMapping(String prefix) throws SAXException {
		super.endPrefixMapping(prefix);
	}
}
