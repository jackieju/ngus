package com.ngus.servlet;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.MessageFormat;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ngus.config.ConfigLoader;
import com.ngus.config.Path;
import com.ngus.config.SystemProperty;
import com.ns.log.Log;


public class PropertyInitializer implements ServletContextListener {

    /**
     * Stable serialVersionUID.
     */
    private static final long serialVersionUID = 222L;

    /**
     * Logger.
     */
    private static Logger log = LoggerFactory.getLogger(PropertyInitializer.class);

    /**
     * Context parameter name.
     */
    public static final String NGUS_INITIALIZATION_FILE = "ngus.initialization.file"; //$NON-NLS-1$

    /**
     * Default value for the MAGNOLIA_INITIALIZATION_FILE parameter.
     */
    public static final String DEFAULT_INITIALIZATION_PARAMETER = //
    "WEB-INF/config/${servername}/${webapp}/ngus.properties," //$NON-NLS-1$
        + "WEB-INF/config/${servername}/ngus.properties," //$NON-NLS-1$
        + "WEB-INF/config/${webapp}/ngus.properties," //$NON-NLS-1$
        + "WEB-INF/config/default/ngus.properties," //$NON-NLS-1$
        + "WEB-INF/config/ngus.properties"; //$NON-NLS-1$

    /**
     * Environment-specific properties.
     */
    protected Properties envProperties = new Properties();

    /**
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce) {
//        Log4jConfigurer.shutdownLogging(envProperties);
    }

    /**
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce) {
    	Log.trace("enter contextInitialized");
        final ServletContext context = sce.getServletContext();

        String propertiesLocationString = context.getInitParameter(NGUS_INITIALIZATION_FILE);

        if (log.isDebugEnabled()) {
            log.debug("{} value in web.xml is :[{}]", NGUS_INITIALIZATION_FILE, propertiesLocationString); //$NON-NLS-1$
        }
        if (StringUtils.isEmpty(propertiesLocationString)) {
            propertiesLocationString = DEFAULT_INITIALIZATION_PARAMETER;
        }

        String[] propertiesLocation = StringUtils.split(propertiesLocationString, ',');

        String servername = null;

        try {
            servername = StringUtils.lowerCase(InetAddress.getLocalHost().getHostName());
        }
        catch (UnknownHostException e) {
            log.error(e.getMessage());
        }

        String rootPath = StringUtils.replace(context.getRealPath(StringUtils.EMPTY), "\\", "/"); //$NON-NLS-1$ //$NON-NLS-2$
        String webapp = StringUtils.substringAfterLast(rootPath, "/"); //$NON-NLS-1$

        Path.APP_ROOT_PATH = rootPath;
        
        if (log.isDebugEnabled()) {
            log.debug("rootPath is {}, webapp is {}", rootPath, webapp); //$NON-NLS-1$ 
        }
        Log.log("rootPath is {"+rootPath+"}, webapp is {"+webapp+"}");

        for (int j = 0; j < propertiesLocation.length; j++) {
            String location = StringUtils.trim(propertiesLocation[j]);
            location = StringUtils.replace(location, "${servername}", servername); //$NON-NLS-1$
            location = StringUtils.replace(location, "${webapp}", webapp); //$NON-NLS-1$

            File initFile = new File(rootPath, location);

            if (!initFile.exists() || initFile.isDirectory()) {
                if (log.isDebugEnabled()) {
                    log.debug("Configuration file not found with path [{}]", //$NON-NLS-1$
                        initFile.getAbsolutePath());
                }
                continue;
            }

            InputStream fileStream;
            try {
                fileStream = new FileInputStream(initFile);
            }
            catch (FileNotFoundException e1) {
                log.debug("Configuration file not found with path [{}]", //$NON-NLS-1$
                    initFile.getAbsolutePath());
                return;
            }

            try {
                envProperties.load(fileStream);

                log.info("Loading configuration at {}", initFile.getAbsolutePath());//$NON-NLS-1$

//                Log4jConfigurer.initLogging(context, envProperties);

                new ConfigLoader(context, envProperties);

            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            finally {
                IOUtils.closeQuietly(fileStream);
            }
            Log.trace("leave contextInitialized");
            
           
            String log_file = context.getInitParameter("ngus.log.file");
            Log.setFile(log_file);
            try{
            Log.enableTrace(SystemProperty.getProperty("ngus.log.trace").equalsIgnoreCase("enable"));
            Log.setTraceLevel(Integer.parseInt(SystemProperty.getProperty("ngus.log.trace_level")));
            Log.enableError(SystemProperty.getProperty("ngus.log.error").equalsIgnoreCase("enable"));
            Log.enableLog(SystemProperty.getProperty("ngus.log.log").equalsIgnoreCase("enable"));
            Log.enableWarning(SystemProperty.getProperty("ngus.log.warning").equalsIgnoreCase("enable"));
            }catch(Exception e){
            	Log.error(e);
            }
            
          
            return;

        }

        log
            .error(MessageFormat
                .format(
                    "No configuration found using location list {0}. [servername] is [{1}], [webapp] is [{2}] and base path is [{3}]", //$NON-NLS-1$
                    new Object[]{ArrayUtils.toString(propertiesLocation), servername, webapp, rootPath}));
        Log.trace("leave contextInitialized");

    }
}
