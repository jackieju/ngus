/*
 * Created on 2005-4-5
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ns.exception;

/**
 * @author I027910
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NSException extends Exception{
		private int err_code = 0;
//		private String msg;
//		private Throwable cause;
		
		public NSException(){
			
		}
		public NSException (int err, String msg){
			super(msg);
			this.err_code = err;
			
		}
		public NSException(String msg){
//			this.msg = msg;
			super(msg);
		}
		public NSException(String msg, Throwable e){
			//this.msg = msg;
			super(msg, e);
//			cause = e;
		}
		
		public NSException (int err, Throwable e){
			super(e);
			this.err_code = err;
			
		}
		public NSException (int err, String msg,  Throwable e){
			super(msg, e);
			this.err_code = err;
			
		}
		public NSException(Throwable e){
			super(e);
		}
}
