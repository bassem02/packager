package tn.wevioo.tools.properties.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import nordnet.architecture.exceptions.explicit.NotFoundException;
import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.implicit.NullException.NullCases;
import nordnet.architecture.exceptions.utils.ErrorCode;
import nordnet.tools.properties.PropertiesFile;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;











public abstract class AbstractPropertyFile
  implements PropertiesFile
{
  private static final Log LOGGER = LogFactory.getLog(AbstractPropertyFile.class);
  

  public AbstractPropertyFile() {}
  
  private Properties innerPropertyFile = null;
  
  protected Properties getPropertyFile() {
    return innerPropertyFile; }
  

  protected void setPropertyFile(Properties file)
  {
    if (file == null) {
      throw new NullException(NullException.NullCases.NULL, "property file");
    }
    
    innerPropertyFile = file;
  }
  



  public Map<String, String> getAllProperties()
  {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Starting method...");
    }
    
    Map<String, String> result = new HashMap();
    
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Verifying there are properties in the file...");
    }
    if (innerPropertyFile.entrySet().size() == 0)
    {
      if (LOGGER.isWarnEnabled()) {
        LOGGER.warn("There are no properties in the file.");
      }
    }
    else
    {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("Browsing all the properties...");
      }
      Iterator<Map.Entry<Object, Object>> ite = innerPropertyFile.entrySet().iterator();
      while (ite.hasNext())
      {
        if (LOGGER.isDebugEnabled()) {
          LOGGER.debug("Getting the current property...");
        }
        Map.Entry<Object, Object> currentProperty = (Map.Entry)ite.next();
        
        if (LOGGER.isDebugEnabled()) {
          LOGGER.debug("Converting the property [" + currentProperty.getKey() + "]...");
        }
        
        result.put((String)currentProperty.getKey(), (String)currentProperty.getValue());
      }
    }
    
    return result;
  }
  


  public String getProperty(String pKey)
    throws NotFoundException
  {
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
    if (innerPropertyFile.containsKey(pKey))
    {

      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("Returning the property value...");
      }
      return innerPropertyFile.getProperty(pKey).trim();
    }
    

    if (LOGGER.isWarnEnabled()) {
      LOGGER.warn("The property [" + pKey + "] has not been found.");
    }
    throw new NotFoundException(new ErrorCode("0.1.1.8.3"), new Object[] { pKey });
  }
}
