/*
 * Created on 2005-7-20
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ns.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;

import com.ns.log.Log;

/**
 * @author I027910
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class IO {
		public static int bufInitSize = 10000;
		public static String InputStreamToString(InputStream is, String encode) throws Exception{
			return new String(readInputStream(is), encode);
		}
		public static String InputStreamToUTF8Str(InputStream is) throws Exception{
			return new String(readInputStream(is), "UTF-8");
		}
		public static byte[] readInputStream(InputStream is) throws Exception
		{
			byte[] nbuffer = new byte[0];
			int size = 0;
			while (true) {

				byte[] buffer = new byte[bufInitSize];
				
				int readNumber = is.read(buffer);

				//Log.trace(new String(buffer));

				if (readNumber >= buffer.length) {
					// enlarge buffer
					nbuffer = new byte[size + buffer.length];
					// copy
					for (int j = 0; j < readNumber; j++) {
						nbuffer[j + size] = buffer[j];
					}
					size += buffer.length;
				} else if (readNumber > 0){
					//  		 enlarge buffer
					nbuffer = new byte[size + readNumber];
					// copy
					for (int j = 0; j < readNumber; j++) {
						nbuffer[j + size] = buffer[j];
					}
					size += readNumber;
					//Log.trace("size =" + size);
					break;
				}else
					break;
			}
			return nbuffer;
		
		};
		
		public static InputStream getInputStreamASUTF8Str(String s) throws Exception{
			StringBuffer StringBuffer1 = new StringBuffer(s);			
			ByteArrayInputStream Bis1 = new ByteArrayInputStream(StringBuffer1.toString().getBytes("UTF-8"));
			return Bis1;
		}
		public static InputStream getInputStream(String s, String encode) throws Exception{
			StringBuffer StringBuffer1 = new StringBuffer(s);			
			ByteArrayInputStream Bis1 = new ByteArrayInputStream(StringBuffer1.toString().getBytes(encode));
			return Bis1;
		}
		
		static public byte[] objectToByteArray(Object o) throws Exception{
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			(new ObjectOutputStream(bos)).writeObject(o);
			byte[] val = bos.toByteArray();
			return val;
		}
		
		static public Object byteArrayToObject(byte[] data) throws Exception{
		
			Object o;
			ContextObjectInputStream ois =
				new ContextObjectInputStream( new ByteArrayInputStream( data ), null );
			try {
				o = ois.readObject();
				Log.log("++++ deserializing " + o.getClass());
			}
			catch (ClassNotFoundException e) {
				Log.error(e);
				Log.error("++++ ClassNotFoundException thrown while trying to deserialize ");
				throw new Exception("+++ failed while trying to deserialize", e);
			}
			return o;
		}

		
}
