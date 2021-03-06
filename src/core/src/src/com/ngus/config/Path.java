package com.ngus.config;


import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;


/**
 * @author Sameer Charles
 * @version 2.0
 * $Id: Path.java 2719 2006-04-27 14:38:44Z scharles $
 */
public final class Path {

    /**
     *
     */
    private static final String DEFAULT_UNTITLED_NODE_NAME = "untitled";

    private static final String ENCODING_DEFAULT = "UTF-8"; //$NON-NLS-1$

    private static final String JAVAX_FORWARD_SERVLET_PATH = "javax.servlet.forward.servlet_path"; //$NON-NLS-1$

    /**
     * Utility class, don't instantiate.
     */
    private Path() {
        // unused
    }

    public static String APP_ROOT_PATH = "";
    /**
     * Gets the cache directory path (cms.cache.startdir) as set with Java options while startup or in web.xml.
     * @return Cache directory path
     */
//    public static String getCacheDirectoryPath() {
//        return getCacheDirectory().getAbsolutePath();
//    }

//    public static File getCacheDirectory() {
//        String path = SystemProperty.getProperty(SystemProperty.MAGNOLIA_CACHE_STARTDIR);
//        File dir = isAbsolute(path) ? new File(path) : new File(Path.getAppRootDir(), path);
//        dir.mkdirs();
//        return dir;
//    }

    /**
     * Gets the temporary directory path (cms.upload.tmpdir) as set with Java options while startup or in web.xml.
     * @return Temporary directory path
     */
//    public static String getTempDirectoryPath() {
//        return getTempDirectory().getAbsolutePath();
//    }

//    public static File getTempDirectory() {
//        String path = SystemProperty.getProperty(SystemProperty.MAGNOLIA_UPLOAD_TMPDIR);
//        File dir = isAbsolute(path) ? new File(path) : new File(Path.getAppRootDir(), path);
//        dir.mkdirs();
//        return dir;
//    }

    /**
     * Gets cms.exchange.history file location as set with Java options while startup or in web.xml.
     * @return exchange history file location
     */
//    public static String getHistoryFilePath() {
//        return getHistoryFile().getAbsolutePath();
//    }

//    public static File getHistoryFile() {
//        String path = SystemProperty.getProperty(SystemProperty.MAGNOLIA_EXCHANGE_HISTORY);
//        return isAbsolute(path) ? new File(path) : new File(Path.getAppRootDir(), path);
//    }

    /**
     * Gets repositories file location as set with Java options while startup or in web.xml.
     * @return file location
     */
    public static String getRepositoriesConfigFilePath() {
        return getRepositoriesConfigFile().getAbsolutePath();
    }

    public static File getRepositoriesConfigFile() {
        String path = SystemProperty.getProperty(SystemProperty.NGUG_JCR_REPOSITORY_CONFIG);
        return isAbsolute(path) ? new File(path) : new File(Path.getAppRootDir(), path);
    }

    /**
     * Gets the root directory for the magnolia web application.
     * @return magnolia root dir
     */
    public static File getAppRootDir() {
      //  return new File(SystemProperty.getProperty(SystemProperty.NGUS_APP_ROOTDIR));
    	return new File(APP_ROOT_PATH);
    }

    /**
     * Gets absolute filesystem path, adds application root if path is not absolute
     */
    public static String getAbsoluteFileSystemPath(String path) {
        if (Path.isAbsolute(path)) {
            return path;
        }
        // using the file() constructor will allow relative paths in the form ../../apps
        return new File(Path.getAppRootDir(), path).getAbsolutePath();
    }

    /**
     * Returns the URI of the current request, without the context path.
     * @param req request
     * @return request URI without servlet context
     */
    public static String getURI(HttpServletRequest req) {
        return getDecodedURI(req);
    }

    public static String getOriginalURI(HttpServletRequest req) {
        return (String) req.getAttribute(JAVAX_FORWARD_SERVLET_PATH);
    }

    /**
     * Returns the decoded URI of the current request, without the context path.
     * @param req request
     * @return request URI without servlet context
     */
    private static String getDecodedURI(HttpServletRequest req) {
        String encoding = StringUtils.defaultString(req.getCharacterEncoding(), ENCODING_DEFAULT);
        String decodedURL = null;
        try {
            decodedURL = URLDecoder.decode(req.getRequestURI(), encoding);
        }
        catch (UnsupportedEncodingException e) {
            decodedURL = req.getRequestURI();
        }
        return StringUtils.substringAfter(decodedURL, req.getContextPath());
    }

    public static String getExtension(HttpServletRequest req) {
        return StringUtils.substringAfterLast(req.getRequestURI(), "."); //$NON-NLS-1$
    }

//    public static String getUniqueLabel(HierarchyManager hierarchyManager, String parent, String label) {
//        if (parent.equals("/")) { //$NON-NLS-1$
//            parent = StringUtils.EMPTY;
//        }
//        while (hierarchyManager.isExist(parent + "/" + label)) { //$NON-NLS-1$
//            label = createUniqueName(label);
//        }
//        return label;
//    }

    private static boolean isAbsolute(String path) {
        if (path == null) {
            return false;
        }

        if (path.startsWith("/") || path.startsWith(File.separator)) { //$NON-NLS-1$
            return true;
        }

        // windows c:
        if (path.length() >= 3 && Character.isLetter(path.charAt(0)) && path.charAt(1) == ':') {
            return true;
        }

        return false;
    }

    /**
     * <p>
     * Replace illegal characters by [_] [0-9], [A-Z], [a-z], [-], [_]
     * </p>
     * @param label label to validate
     * @return validated label
     */
    public static String getValidatedLabel(String label) {
        StringBuffer s = new StringBuffer(label);
        StringBuffer newLabel = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            int charCode = s.charAt(i);
            // charCodes: 48-57: [0-9]; 65-90: [A-Z]; 97-122: [a-z]; 45: [-]; 95:[_]
            if (((charCode >= 48) && (charCode <= 57))
                || ((charCode >= 65) && (charCode <= 90))
                || ((charCode >= 97) && (charCode <= 122))
                || charCode == 45
                || charCode == 95) {
                newLabel.append(s.charAt(i));
            }
            else {
                newLabel.append("-"); //$NON-NLS-1$
            }
        }
        if (newLabel.length() == 0) {
            newLabel.append(DEFAULT_UNTITLED_NODE_NAME);
        }
        return newLabel.toString();
    }

    /**
     * @param baseName
     * @return
     */
    private static String createUniqueName(String baseName) {
        int pos;
        for (pos = baseName.length() - 1; pos >= 0; pos--) {
            char c = baseName.charAt(pos);
            if (c < '0' || c > '9') {
                break;
            }
        }
        String base;
        int cnt;
        if (pos == -1) {
            if (baseName.length() > 1) {
                pos = baseName.length() - 2;
            }
        }
        if (pos == -1) {
            base = baseName;
            cnt = -1;
        }
        else {
            pos++;
            base = baseName.substring(0, pos);
            if (pos == baseName.length()) {
                cnt = -1;
            }
            else {
                cnt = new Integer(baseName.substring(pos)).intValue();
            }
        }
        return (base + ++cnt);
    }

    public static String getAbsolutePath(String path, String label) {
        if (StringUtils.isEmpty(path) || (path.equals("/"))) { //$NON-NLS-1$
            return "/" + label; //$NON-NLS-1$
        }

        return path + "/" + label; //$NON-NLS-1$
    }

//    public static String getAbsolutePath(String path) {
//        if (!path.startsWith("/")) { //$NON-NLS-1$
//            return "/" + path; //$NON-NLS-1$
//        }
//        return path;
//    }

    public static String getNodePath(String path, String label) {
        if (StringUtils.isEmpty(path) || (path.equals("/"))) { //$NON-NLS-1$
            return label;
        }
        return getNodePath(path + "/" + label); //$NON-NLS-1$
    }

    public static String getNodePath(String path) {
        if (path.startsWith("/")) { //$NON-NLS-1$
            return path.replaceFirst("/", StringUtils.EMPTY); //$NON-NLS-1$
        }
        return path;
    }

    public static String getParentPath(String path) {
        int lastIndexOfSlash = path.lastIndexOf("/"); //$NON-NLS-1$
        if (lastIndexOfSlash > 0) {
            return StringUtils.substringBefore(path, "/"); //$NON-NLS-1$
        }
        return "/"; //$NON-NLS-1$
    }
}

