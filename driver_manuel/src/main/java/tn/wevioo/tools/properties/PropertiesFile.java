package tn.wevioo.tools.properties;

import java.util.Map;
import nordnet.architecture.exceptions.explicit.NotFoundException;

public abstract interface PropertiesFile
{
  public abstract Map<String, String> getAllProperties();
  
  public abstract String getProperty(String paramString)
    throws NotFoundException;
}
