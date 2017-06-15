package tn.wevioo.driverManual.tools.xml.transformers;

import nordnet.architecture.exceptions.explicit.MalformedXMLException;
import nordnet.architecture.exceptions.explicit.ResourceAccessException;

public abstract interface XMLParser
{
  public abstract Object parse(String paramString)
    throws ResourceAccessException, MalformedXMLException;
  
  public abstract Object parse(String paramString1, String paramString2, String paramString3)
    throws ResourceAccessException, MalformedXMLException;
}
