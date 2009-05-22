/*
 * Created on 2005-3-24
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ns.dataobject;

import com.ns.exception.NSException;



/**
 * @author I027910
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DOException extends NSException{

    public DOException(String msg, Throwable cause){
        super(msg, cause);
    }
    
    public DOException(String msg){
        super(msg);
    }
        /*
    public DOException(Throwable cause){
        super(cause);
    }
            
    public DOException(String patternKey){
        super(patternKey);
    }
        
    public DOException(String patternKey, Object[] args){
        super(patternKey, args);
    }
        
    public DOException(String patternKey, Throwable cause){
        super(patternKey, cause);
    }
        
    public DOException(String patternKey, Object[] args, Throwable cause){
        super(patternKey, args, cause); 
    }
    */
}
