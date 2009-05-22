package com.ngus.config;

import java.util.Hashtable;
import java.util.Map;

import com.ns.log.Log;

public final class SystemProperty {
	public static final String NGUG_JCR_REPOSITORY_CONFIG = "ngus.jcr.repositories.config";
	public static final String NGUS_APP_ROOTDIR="ngus.app.rootdir";
	
    private static Map properties = new Hashtable();

    /**
     * Utility class, don't instantiate.
     */
    private SystemProperty() {
        // unused
    }

    /**
     * @param name
     * @param value
     */
    public static void setProperty(String name, String value) {
        SystemProperty.properties.put(name, value);
    }

    /**
     * @param name
     */
    public static String getProperty(String name) {
    	Log.trace("system property: "+ name + "="+(String) SystemProperty.properties.get(name));
        return (String) SystemProperty.properties.get(name);
    }

    /**
     *
     */
    public static Map getPropertyList() {
        return SystemProperty.properties;
    }

}
