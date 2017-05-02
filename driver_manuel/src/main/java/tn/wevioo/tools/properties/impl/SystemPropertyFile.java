package tn.wevioo.tools.properties.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import nordnet.architecture.exceptions.explicit.NotFoundException;
import nordnet.architecture.exceptions.implicit.NullException;
import nordnet.architecture.exceptions.implicit.NullException.NullCases;
import nordnet.architecture.exceptions.utils.ErrorCode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;












public class SystemPropertyFile
  extends AbstractPropertyFile
{
  private static final Log LOGGER = LogFactory.getLog(SystemPropertyFile.class);
  








  protected SystemPropertyFile() {}
  







  public SystemPropertyFile(String fileUri)
    throws NotFoundException
  {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Starting method.");
    }
    

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Verifying the address is not null or empty...");
    }
    if ((fileUri == null) || (fileUri.trim().length() == 0))
    {
      if (LOGGER.isWarnEnabled()) {
        LOGGER.warn("The properties file address can not be null or empty.");
      }
      throw new NullException(NullException.NullCases.NULL_EMPTY, "properties file address");
    }
    

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Getting the input stream on the properties file...");
    }
    
    InputStream inputFile;
    try
    {
      inputFile = new FileInputStream(fileUri);
    }
    catch (FileNotFoundException ex)
    {
      if (LOGGER.isErrorEnabled()) {
        LOGGER.error("The properties file [" + fileUri + "] has not been found.");
      }
      throw new NotFoundException(new ErrorCode("0.1.1.2.1"), new Object[] { fileUri }, ex);
    }
    

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Verifying the input stream is not null...");
    }
    if (null == inputFile)
    {
      if (LOGGER.isErrorEnabled()) {
        LOGGER.error("The properties file [" + fileUri + "] has not been found.");
      }
      throw new NotFoundException(new ErrorCode("0.1.1.2.1"), new Object[] { fileUri });
    }
    

    setPropertyFile(inputFile);
  }
  








  public SystemPropertyFile(InputStream inputFile)
    throws NotFoundException
  {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Starting method.");
    }
    

    setPropertyFile(inputFile);
  }
  








  public void setPropertyFile(InputStream inputFile)
    throws NotFoundException
  {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Starting method.");
    }
    

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Verifying the input stream is not null or empty...");
    }
    if (inputFile == null) {
      throw new NullException(NullException.NullCases.NULL_EMPTY, "input stream");
    }
    

    try
    {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("Loading the properties file...");
      }
      Properties file = new Properties();
      file.load(inputFile);
      

      super.setPropertyFile(file);
    }
    catch (IOException ex)
    {
      if (LOGGER.isErrorEnabled()) {
        LOGGER.error("The properties file [" + inputFile.toString() + "] has not been found.");
      }
      throw new NotFoundException(new ErrorCode("0.1.1.2.1"), new Object[] { inputFile.toString() }, ex);
    }
    
    if (LOGGER.isInfoEnabled()) {
      LOGGER.info("The properties file [" + inputFile.toString() + "] has been correctly loaded.");
    }
  }
}
