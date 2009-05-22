package com.ns.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Title: Base64-encoding and decoding Description: Copyright: Copyright (c)
 * 2002 Company: SAP AG
 * 
 * @author @created 18. November 2002
 * @version 1.0
 */

public class Base64 {
	/**
	 * encode from a string to string (add by jackie)
	 * 
	 * @param data
	 * @return
	 */
	public static String encode(String data) {
		return encode(data.getBytes());

	}

	/**
	 * Returns a base64-encoded string to represent the passed data array.
	 * 
	 * @param data
	 *            the array of bytes to encode
	 * @return base64-coded string.
	 */
	public static String encode(byte[] data) {
		return new String(encodeAsArray(data));
	}

	/**
	 * decode a string to string (add by jackie)
	 * 
	 * @param data
	 *            the base64-encoded string
	 * @return decoded string
	 */
	public static String decodeToString(String data) {
		byte[] bData = decode(data);
		return new String(bData);
	}

	/**
	 * Returns an array of bytes which were encoded in the passed string
	 * 
	 * @param data
	 *            the base64-encoded string
	 * @return decoded data array
	 */
	public static byte[] decode(String data) {
		      char[] tdata = new char[data.length()];
		      data.getChars(0, data.length(), tdata, 0);
		      return decode(tdata);
//		return decode(data.toCharArray());
	}

	/**
	 * Returns an array of base64-encoded characters to represent the passed
	 * data array.
	 * 
	 * @param data
	 *            the array of bytes to encode
	 * @return base64-coded character array.
	 */
	public static char[] encodeAsArray(byte[] data) {
		char[] out = new char[((data.length + 2) / 3) * 4];

		//
		// 3 bytes encode to 4 chars. Output is always an even
		// multiple of 4 characters.
		//
		for (int i = 0, index = 0; i < data.length; i += 3, index += 4) {
			boolean quad = false;
			boolean trip = false;

			int val = (0xFF & (int) data[i]);
			val <<= 8;
			if ((i + 1) < data.length) {
				val |= (0xFF & (int) data[i + 1]);
				trip = true;
			}
			val <<= 8;
			if ((i + 2) < data.length) {
				val |= (0xFF & (int) data[i + 2]);
				quad = true;
			}
			out[index + 3] = b64code[(quad ? (val & 0x3F) : 64)];
			val >>= 6;
			out[index + 2] = b64code[(trip ? (val & 0x3F) : 64)];
			val >>= 6;
			out[index + 1] = b64code[val & 0x3F];
			val >>= 6;
			out[index + 0] = b64code[val & 0x3F];
		}
		return out;
	}

	/**
	 * Returns an array of bytes which were encoded in the passed character
	 * array.
	 * 
	 * @param data
	 *            the array of base64-encoded characters
	 * @return decoded data array
	 */
	public static byte[] decode(char[] data) {
		int len = ((data.length + 3) / 4) * 3;
		if (data.length > 0 && data[data.length - 1] == '=')
			--len;
		if (data.length > 1 && data[data.length - 2] == '=')
			--len;
		byte[] out = new byte[len];

		int shift = 0;
		// # of excess bits stored in accum
		int accum = 0;
		// excess bits
		int index = 0;

		for (int ix = 0; ix < data.length; ix++) {
			int value = b64icode[data[ix] & 0xFF];
			// ignore high byte of char
			if (value >= 0) {
				// skip over non-code
				accum <<= 6;
				// bits shift up by 6 each time thru
				shift += 6;
				// loop, with new bits being put in
				accum |= value;
				// at the bottom.
				if (shift >= 8) {
					// whenever there are 8 or more shifted in,
					shift -= 8;
					// write them out (from the top, leaving any
					out[index++] =
					// excess at the bottom for next iteration.
					(byte) ((accum >> shift) & 0xff);
				}
			}
		}
		if (index != out.length)
			throw new Error("miscalculated data length!");

		return out;
	}

	//
	// code characters for values 0..63
	//
	private static char[] b64code = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/="
			.toCharArray();

	//
	// lookup table for converting base64 characters to value in range 0..63
	//
	private static byte[] b64icode = new byte[256];
	static {
		for (int i = 0; i < 256; i++)
			b64icode[i] = -1;
		for (int i = 0; i < b64code.length; i++)
			b64icode[b64code[i]] = (byte) i;
		b64icode['='] = -1;
	}
//	字符到字节转换
	public static byte[] charToByte(char ch){
	int temp=(int)ch;
	byte[] b=new byte[2];
	for (int i=b.length-1;i>-1;i--){
	b[i] = new Integer(temp&0xff).byteValue(); //将最高位保存在最低位
	temp = temp >> 8; //向右移8位
	}
	return b;
	}

//	字节到字符转换

	public static char byteToChar(byte[] b) {
		int s = 0;
		if (b[0] > 0)
			s += b[0];
		else
			s += 256 + b[0];
		s *= 256;
		if (b[1] > 0)
			s += b[1];
		else
			s += 256 + b[1];
		char ch = (char) s;
		return ch;
	}

	static public void main(String v[]) throws Exception {
		//   	String source = "<sample>aa f阿阿f</sample>";
		//	System.out.println(source);
		//   	System.out.println(encode(source));
		//	System.out.println(new String(decode(encode(source))));
		DataInputStream is = new DataInputStream(new FileInputStream(
				"c:\\Water lilies.jpg"));
		byte[] data = new byte[100000];
		int size = is.read(data);
		is.close();

		byte[] data2 = new byte[size];
		for (int i = 0; i < size; i++) {
			data2[i] = data[i];
		}

		String out = encode(data2);
		DataOutputStream a = new DataOutputStream(new FileOutputStream(
				"d:\\encode.txt", false));
		a.writeBytes(out);
		a.flush();
		a.close();

		byte[] decoded_data = decode(out);

		a = new DataOutputStream(new FileOutputStream("d:\\decode.jpg", false));
		a.write(decoded_data);
		a.flush();
		a.close();

//		DataInputStream is = new DataInputStream(new FileInputStream("d:\\a"));
//		byte[]  data = new byte[600000];
//		int size = is.read(data);
//		is.close();
//		byte[] data3 = new byte[size];
//		for (int i = 0; i < size; i++) {
//			data3[i] = data[i];
//		}
//		data = decode(new String(data3));
//		DataOutputStream a = new DataOutputStream(new FileOutputStream("d:\\decode.jpg2", false));
//		a.write(data);
//		a.flush();
//		a.close();

		return;
	}

}