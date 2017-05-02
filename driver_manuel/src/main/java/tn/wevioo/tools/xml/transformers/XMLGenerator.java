package tn.wevioo.tools.xml.transformers;

import nordnet.architecture.exceptions.explicit.NotRespectedRulesException;
import nordnet.architecture.exceptions.explicit.ResourceAccessException;

public abstract interface XMLGenerator
{
  public abstract String generate(Object paramObject)
    throws ResourceAccessException, NotRespectedRulesException;
}
