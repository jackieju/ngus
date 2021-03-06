package com.ngus.config;



import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ngus.resengine.JCRContentRepository;


/**
 * This class is an entry point to all config.
 * @author Sameer Charles
 * @version 1.1
 */
public class ConfigLoader {

    /**
     * Logger.
     */
    protected static Logger log = LoggerFactory.getLogger(ConfigLoader.class);

    /**
     * Is this magnolia istance configured?
     */
    private static boolean configured;

    /**
     * Set to true when bootstrapping is in progress or if it has failed.
     */
    private static boolean bootstrapping;

    /**
     * Initialize a ConfigLoader instance. All the supplied parameters will be set in
     * <code>info.magnolia.cms.beans.runtime.SystemProperty</code>
     * @param context ServletContext
     * @param config contains initialization parameters
     * @see SystemProperty
     */
    public ConfigLoader(ServletContext context, Map config) {

        String rootDir = context.getRealPath(StringUtils.EMPTY);

        if (log.isInfoEnabled()) {
            log.info("Assuming paths relative to " + rootDir); //$NON-NLS-1$
        }
        SystemProperty.setProperty(SystemProperty.NGUS_APP_ROOTDIR, rootDir);

        Iterator it = config.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry param = (Map.Entry) it.next();
            SystemProperty.setProperty((String) param.getKey(), (String) param.getValue());
        }

        if (StringUtils.isEmpty(System.getProperty("java.security.auth.login.config"))) { //$NON-NLS-1$
            try {
                System.setProperty("java.security.auth.login.config", Path //$NON-NLS-1$
                    .getAbsoluteFileSystemPath("WEB-INF/config/jaas.config")); //$NON-NLS-1$
            } catch (SecurityException se) {
                log.error("Failed to set java.security.auth.login.config, check application server settings"); //$NON-NLS-1$
                log.error(se.getMessage(), se);
                log.info("Aborting startup");
                return;
            }
        }
        else {
            if (log.isInfoEnabled()) {
                log.info("JAAS config file set by parent container or some other application"); //$NON-NLS-1$
                log.info("Config in use " + System.getProperty("java.security.auth.login.config")); //$NON-NLS-1$ //$NON-NLS-2$
                log.info("Please make sure JAAS config has all necessary modules (refer config/jaas.config) configured"); //$NON-NLS-1$
            }
        }

        this.load(context);
    }

    /**
     * Load magnolia configuration from repositories.
     * @param context ServletContext
     */
    private void load(ServletContext context) {
        // first check for the license information, will fail if this class does not exist
//        LicenseFileExtractor license = LicenseFileExtractor.getInstance();
//        license.init();
//        printVersionInfo(license);

        log.info("Init content repositories"); //$NON-NLS-1$
        new JCRContentRepository();

//        // check for initialized repositories
//        boolean initialized;
//
//        try {
//            initialized = ContentRepository.checkIfInitialized();
//        }
//        catch (RepositoryException re) {
//            log.error("Unable to initialize repositories. Magnolia can't start.", re); //$NON-NLS-1$
//            return;
//        }
//
//        if (initialized) {
//            log.info("Repositories are initialized (some content found). Loading configuration"); //$NON-NLS-1$
//        }
//        else {
//            log.warn("Repositories are not initialized (no content found)."); //$NON-NLS-1$
//
//            String bootdirProperty = SystemProperty.getProperty(SystemProperty.MAGNOLIA_BOOTSTRAP_ROOTDIR);
//            if (StringUtils.isEmpty(bootdirProperty)) {
//                enterListeningMode();
//                return;
//            }
//
//            bootstrapping = true;
//
//            String[] bootDirs = StringUtils.split(bootdirProperty);
//
//            // converts to absolute paths
//            for (int j = 0; j < bootDirs.length; j++) {
//                bootDirs[j] = Path.getAbsoluteFileSystemPath(bootDirs[j]);
//            }
//
//            // a bootstrap directory is configured, trying to initialize repositories
//            Bootstrapper.bootstrapRepositories(bootDirs);
//        }
//
//        log.info("Set system context"); //$NON-NLS-1$
//        MgnlContext.setInstance(MgnlContext.getSystemContext());
//
//        log.info("Init i18n"); //$NON-NLS-1$
//        MessagesManager.init(context);
//
//        log.info("Init secureURI"); //$NON-NLS-1$
//        SecureURI.init();

//        try {
//            Server.init();
//            ModuleRegistration.getInstance().init();
//            ModuleLoader.getInstance().init();
//            Listener.init();
//            Subscriber.init();
//            MIMEMapping.init();
//            VersionConfig.init();
//            
//            // finished
//            setConfigured(true);
//            log.info("Configuration loaded!"); //$NON-NLS-1$
//
//            if(ModuleRegistration.getInstance().isRestartNeeded()){
//                printSystemRestartInfo();
//            }
//
//        }
//        catch (ConfigurationException e) {
//            log.error("An error occurred during initialization",e); //$NON-NLS-1$
//            enterListeningMode();
//        }

    }

//    /**
//     * Print version info to console.
//     * @param license loaded License
//     */
//    private void printVersionInfo(LicenseFileExtractor license) {
//        System.out.println("---------------------------------------------"); //$NON-NLS-1$
//        System.out.println("MAGNOLIA LICENSE"); //$NON-NLS-1$
//        System.out.println("---------------------------------------------"); //$NON-NLS-1$
//        System.out.println("Version number : " + license.get(LicenseFileExtractor.VERSION_NUMBER)); //$NON-NLS-1$
//        System.out.println("Build          : " + license.get(LicenseFileExtractor.BUILD_NUMBER)); //$NON-NLS-1$
//        System.out.println("Provider       : " //$NON-NLS-1$
//            + license.get(LicenseFileExtractor.PROVIDER)
//            + " (" //$NON-NLS-1$
//            + license.get(LicenseFileExtractor.PRIVIDER_EMAIL)
//            + ")"); //$NON-NLS-1$
//    }
//    
    /**
     * Print the list of modules needing a restart of the container.
     */
//    private void printSystemRestartInfo() {
//        ModuleLoader loader = ModuleLoader.getInstance();    
//        System.out.println("-----------------------------------------------------"); //$NON-NLS-1$
//        System.out.println("One or more modules need a restart of the container:"); //$NON-NLS-1$
//        for (Iterator iter = loader.getModuleInstances().keySet().iterator(); iter.hasNext();) {
//            String moduleName = (String) iter.next();
//            Module module = loader.getModuleInstance(moduleName);
//            if(module.isRestartNeeded()){
//                System.out.println(" - " +module.getName() +" ("+ module.getModuleDefinition().getVersion() + ")");
//            }
//        }
//        System.out.println("-----------------------------------------------------"); //$NON-NLS-1$
//    }
    
    /**
     * Returns true is magnolia is running with all basic configuration.
     * @return <code>true</code> if Magnolia is configured
     */
    public static boolean isConfigured() {
        return ConfigLoader.configured;
    }

    /**
     * Returns true if repository bootstrapping has started but the configuration has not been loaded successfully.
     * @return <code>true</code> if repository bootstrapping has started but the configuration has not been loaded
     * successfully
     */
    public static boolean isBootstrapping() {
        return bootstrapping;
    }

    /**
     * Set the current state of Magnolia.
     * @param configured <code>true</code> if Magnolia is configured
     */
    protected static void setConfigured(boolean configured) {
        ConfigLoader.configured = configured;

        // if we are here, bootstrapping has completed or never started
        ConfigLoader.bootstrapping = false;
    }

    /**
     * Set the configured propery to false and print out an informative message to System.out.
     */
    private void enterListeningMode() {
        System.out.println("\n-----------------------------------------------------------------"); //$NON-NLS-1$
        System.out.println("Server not configured, entering in listening mode for activation."); //$NON-NLS-1$
        System.out.println("You can now activate content from an existing magnolia instance."); //$NON-NLS-1$
        System.out.println("-----------------------------------------------------------------\n"); //$NON-NLS-1$
        setConfigured(false);
    }

}
