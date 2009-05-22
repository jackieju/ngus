/*
 * Created on 2005-4-5
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ngus.dataengine;

import com.ns.exception.NSException;

/**
 * @author I027910
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DEException extends NSException {
	public DEException(String msg){
		super(msg);
	}
	public DEException(String msg, Exception e){
		super(msg, e);
	}
}
