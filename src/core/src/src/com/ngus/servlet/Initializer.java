package com.ngus.servlet;

import java.lang.reflect.Constructor;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.ngus.config.ConfigLoader;
import com.ngus.config.Path;
import com.ngus.dataengine.DataEngine;
import com.ngus.dataengine.IndexManager;
import com.ngus.dataengine.NGUSThread;
import com.ngus.dataengine.ThreadManager;
import com.ns.db.DB;
import com.ns.db.DBC;
import com.ns.log.Log;
import com.ns.util.XmlUtil;

public class Initializer implements ServletContextListener {

	/**
	 * Stable serialVersionUID.
	 */
	private static final long serialVersionUID = 222L;

	/**
	 * Logger.
	 */
	private static Logger log = LoggerFactory.getLogger(Initializer.class);
	
	PropertyInitializer pi = new PropertyInitializer();
	MailInitializer mi = new MailInitializer();
	UMInitializer ui = new UMInitializer();
	

	/**
	 * <p>
	 * load configuration parameters from servlet context, then:
	 * </p>
	 * <ol>
	 * <li>Initialize Log4j</li>
	 * <li>Instantiate a
	 * <code>info.magnolia.cms.beans.config.ConfigLoader</code> instance</li>
	 * </ol>
	 * 
	 * @see ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 * @see ConfigLoader
	 */
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();

		// copy all the initialization parameters in a Map, so that ConfigLoader
		// is not tied to a ServletConfig instance
		Map parameters = new HashMap();
		Enumeration configParams = context.getInitParameterNames();
		while (configParams.hasMoreElements()) {
			String paramName = (String) configParams.nextElement();
			parameters.put(paramName, context.getInitParameter(paramName));
		}

		// Log4jConfigurer.initLogging(context, parameters);

		// try {
		// new ConfigLoader(context, parameters);
		// }
		// catch (Exception e) {
		// log.error(e.getMessage(), e);
		// }
		try {			
			Log.trace("init property...");
			pi.contextInitialized(sce);
			Log.trace("Init DB...");
			DB.initialize(DBC.getDataSource("java:comp/env/jdbc/ngus"));
			Log.trace("Init DB successfully");
			Log.trace("Init Data Engine...");
			DataEngine.instance();
			IndexManager.instance();
			Log.trace("Init Data Engine successfully");
			initCrons();
			Log.trace("init mail...");
			mi.contextInitialized(sce);
			Log.trace("init um...");
			ui.contextInitialized(sce);
			Log.trace("Initializesuccessfully.");
			
			
		} catch (Exception e) {
			Log.error(e);
		}
	}

	public void initCrons() {
		String configFile = Path
				.getAbsoluteFileSystemPath("WEB-INF/config/default/cron.xml");
		Document doc = XmlUtil.loadDom(configFile);
		Element ele = doc.getDocumentElement();
		NodeList list = ele.getElementsByTagName("cron");		
		for (int i = 0; i < list.getLength(); i++) {
			Element e = (Element) list.item(i);
			String name = e.getAttribute("name");
			String className = e.getAttribute("class");
			String interval = e.getAttribute("interval");
			String threadNumber = e.getAttribute("threadNumber");
			Log.trace("starting cron "+ className + "...");
			try {// start spiders
				for (int j = 0; j < Integer.parseInt(threadNumber); j++) {
					Class c = Class.forName(className);
					Constructor ctor = c
							.getConstructor(new Class[] { HashMap.class });
					HashMap map = new HashMap();
					map.put("interval", Integer.parseInt(interval));
					
					NodeList params = e.getElementsByTagName("param");
					for (int k = 0; k < params.getLength(); k++){
						Element param = (Element)params.item(k);
						String p_name = param.getAttribute("name");
						String p_value = param.getAttribute("value");
						Log.trace("params: "+p_name+"="+p_value);
						map.put(p_name, p_value);						
					}
					Object o = ctor.newInstance(new Object[] { map });
					ThreadManager.instance().startThread((NGUSThread) o);
					Log.trace("start 1 cron for "+ className + " ok.");
				}
				
			} catch (Exception ex) {
				Log.error(ex);
			}
		}
		Log.trace("start all crons ok.");

	}

	/**
	 * Shutdown logging.
	 * 
	 * @see ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();

		// copy all the initialization parameters in a Map
		// Map parameters = new HashMap();
		// Enumeration configParams = context.getInitParameterNames();
		// while (configParams.hasMoreElements()) {
		// String paramName = (String) configParams.nextElement();
		// parameters.put(paramName, context.getInitParameter(paramName));
		// }

		// Log4jConfigurer.shutdownLogging(parameters);

		// stop all crons
		try {
			ThreadManager.instance().stopAll();			
		} catch (Exception e) {
			Log.error(e);
		}
		
		ui.contextDestroyed(sce);
		mi.contextDestroyed(sce);
		pi.contextDestroyed(sce);

	}

}
