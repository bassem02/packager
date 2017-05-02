package tn.wevioo.tools.properties.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import nordnet.architecture.exceptions.explicit.NotFoundException;
import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.implicit.NullException.NullCases;
import nordnet.architecture.exceptions.utils.ErrorCode;
import nordnet.tools.properties.PropertiesFile;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;














public class PropertyPropertiesFile
  implements PropertiesFile
{
  private static final Log LOGGER = LogFactory.getLog(PropertyPropertiesFile.class);
  private Map<String, String> properties;
  
  public PropertyPropertiesFile(Properties properties)
  {
    setProperties(properties);
  }
  
  public Map<String, String> getAllProperties() {
    return properties;
  }
  
  public String getProperty(String pKey) throws NotFoundException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Starting method.");
    }
    

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Verifying the key is not null or empty...");
    }
    if ((pKey == null) || (pKey.trim().length() == 0))
    {
      if (LOGGER.isWarnEnabled()) {
        LOGGER.warn("The property key can not be null or empty...");
      }
      throw new NullException(NullException.NullCases.NULL_EMPTY, "key");
    }
    

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Verifying the properties file contains the key...");
    }
    if (properties.containsKey(pKey))
    {

      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("Returning the property value...");
      }
      return ((String)properties.get(pKey)).trim();
    }
    

    if (LOGGER.isWarnEnabled()) {
      LOGGER.warn("The property [" + pKey + "] has not been found.");
    }
    throw new NotFoundException(new ErrorCode("0.1.1.8.3"), new Object[] { pKey });
  }
  


  public void setProperties(Properties properties)
  {
    this.properties = new HashMap(properties);
  }
}
