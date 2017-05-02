package tn.wevioo.tools.xml.merger;

import nordnet.architecture.exceptions.explicit.MalformedXMLException;
import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;

public abstract interface XmlMerger
{
  public abstract String merge(String paramString1, String paramString2)
    throws MalformedXMLException, NotRespectedRulesException;
}
