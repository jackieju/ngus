package com.ngus.myweb.spider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.ngus.config.SystemProperty;
import com.ns.log.Log;
import com.ns.util.DateTime;

public class PageSnapshot {
	static class StreamGobbler extends Thread {
		InputStream is;

		String type;

		OutputStream os;

		StreamGobbler(InputStream is, String type) {
			this(is, type, null);
		}

		StreamGobbler(InputStream is, String type, OutputStream redirect) {
			this.is = is;
			this.type = type;
			this.os = redirect;
		}

		public void run() {
			try {
				PrintWriter pw = null;
				if (os != null)
					pw = new PrintWriter(os);

				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				String line = null;
				while ((line = br.readLine()) != null) {
					if (pw != null)
						pw.println(line);
					System.out.println(type + ">" + line);
				}
				if (pw != null)
					pw.flush();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	public static void main(String v[]) {
		 try {
				FileOutputStream fos = new FileOutputStream(
				"d:\\html2image.o", true);
		 //Runtime.getRuntime().exec("cmd /c start excel \"" + path + "\"");
		 Process proc = Runtime.getRuntime().exec("cmd /c dir f:\\");
			// any error message?
			StreamGobbler errorGobbler = new StreamGobbler(proc
					.getErrorStream(), "ERROR", fos);

			// any output?
			StreamGobbler outputGobbler = new StreamGobbler(proc
					.getInputStream(), "OUTPUT", fos);

			// kick them off
			errorGobbler.start();
			outputGobbler.start();

			// any error???
			int exitVal = proc.waitFor();
			Log.trace("daemon.webpage", "ExitValue: " + exitVal);
			fos.flush();
			fos.close();
		 } catch (Exception e) {
		 e.printStackTrace();
		 }
//		String a = "a//bc\\";
//		a = a.replaceAll("\\\\", "");
//		a = a.replaceAll("/", "");
//		a = a.replaceAll("bc", "23");
//		System.out.println(a);

	}

	static public String working_dir = "";
	static public String command = "html2pic.sh";

	static public void setWorkingDir(String path) {
		working_dir = path;
	}

	static public void genImage(String url, int width, int height,
			String outputFile, String display) {
		try {
			GregorianCalendar gc = new GregorianCalendar();
			FileOutputStream fos = new FileOutputStream(
					"html2image.output."+ gc.get(Calendar.YEAR) + gc.get(Calendar.MONTH) + gc.get(Calendar.DAY_OF_MONTH) + gc.get(Calendar.HOUR_OF_DAY)+".log", true);

			// Runtime.getRuntime().exec("cmd /c start excel \"" + path + "\"");
			// DISPLAY=ts-test-03:1 ./html2image www.sina.com.cn /root/sina.jpg
			// -W 100 -H 100
			if (display.startsWith("DISPLAY=")) {
				display = display.substring(8, display.length());
			}
			Log.trace("daemon.webpage", "DISPLAY=" + display);
			Log.trace("daemon.webpage", "working folder=" + working_dir);
//			String cmdmsg = working_dir+"/"+ command + " \"" + url + "\" \"" + outputFile
//				+ "\" -W " + width + " -H " + height + " -t 600000 "+display+" "+ working_dir;
//			String cmd[] ={ working_dir+"/"+ command,  url,  outputFile,
//                     "-W ", ""+width, "-H",""+ height,"-t", "600000", display,  working_dir};
			
			String cmd[] ={ working_dir+"/"+ command,  width+"", height+"", url, outputFile, "600000", display, working_dir};
			
//			Log.trace("daemon.webpage", "execute shell command:" + cmdmsg);
//			Process proc = Runtime.getRuntime().exec(cmd,
//					new String[] { "DISPLAY=" + display },
//					new File(working_dir));
			Process proc = Runtime.getRuntime().exec(cmd,
					null,
					new File(working_dir));
			// any error message?
			StreamGobbler errorGobbler = new StreamGobbler(proc
					.getErrorStream(), "ERROR", fos);			
			errorGobbler.start();
			
			// any output?
			if (SystemProperty.getProperty("ngus.log.trace").equalsIgnoreCase("enable"))
			{
				StreamGobbler outputGobbler = new StreamGobbler(proc			
					.getInputStream(), "OUTPUT", fos);
				outputGobbler.start();
			}
			
			// any error???
			int exitVal = proc.waitFor();
			Log.trace("daemon.webpage", "ExitValue: " + exitVal);
			fos.flush();
			fos.close();

		} catch (Exception e) {
			Log.error("daemon.webpage", "", e);
		}
	}

	public static String getCommand() {
		return command;
	}

	public static void setCommand(String command) {
		PageSnapshot.command = command;
	}


}
