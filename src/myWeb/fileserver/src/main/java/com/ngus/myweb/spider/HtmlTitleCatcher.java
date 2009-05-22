package com.ngus.myweb.spider;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

import com.ns.log.Log;



public class HtmlTitleCatcher {
	
	public static void main(String args[]) throws Exception {
//		Perl5Compiler compiler = new Perl5Compiler();
//		Pattern p = compiler.compile("<meta[^>]*content=[^>]*charset[^>]*=[^>]*/>", Perl5Compiler.CASE_INSENSITIVE_MASK);
//		PatternMatcher pm1 = new Perl5Matcher();
//		if (pm1.contains("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\" />",  p))		
//				System.out.print(pm1.getMatch().group(0).toString());
//		else
//			System.out.print("failed");
		System.out.print("title is " + getPageTitle("http://localhost:8080/myWeb/main.jsp"));
	}

	static public String getPageEncoding(InputStream in) throws Exception{
//		int status=0;
		String line;
		Perl5Compiler compiler = new Perl5Compiler();
		String p = "<meta[^>]*content=[^>]*charset[^>]*=[^>]*/>";
		Pattern pattern = compiler.compile(p, Perl5Compiler.CASE_INSENSITIVE_MASK);
		StringBuffer title= new StringBuffer();
		BufferedReader r = new BufferedReader(new InputStreamReader(in));
		while ((line = r.readLine()) != null ) {	
			PatternMatcher pm1 = new Perl5Matcher();
			if (pm1.contains(line, pattern)){
				String m = pm1.getMatch().group(0).toString();
				int n1 = m.indexOf("charset");
				int n2 = m.indexOf("=", n1);
				int n3 = m.indexOf("\"",n1);
				return m.substring(n2+1, n3).trim();
			}
		}
		return null;
	}
	
	
	static public String getPageTitle(String url) throws Exception {
		Perl5Compiler compiler = new Perl5Compiler();
		StringBuffer title= new StringBuffer();
		Pattern openTag = compiler.compile("<title>", Perl5Compiler.CASE_INSENSITIVE_MASK);
		Pattern closeTag = compiler.compile("</title>", Perl5Compiler.CASE_INSENSITIVE_MASK);
		try {
			URL urlE = new URL(url);
			URLConnection con = urlE.openConnection();
		
		
			InputStream is = con.getInputStream();
			String encoding = con.getContentEncoding();
			Log.trace("spider", "http encoding="+encoding);
			if (encoding == null)
			{
				encoding = getPageEncoding(is);			
				Log.trace("spider", "html encoding="+encoding);
			}
			try{
				is.reset();
			}catch(Exception e){
				is = urlE.openConnection().getInputStream();				
			}
			
			BufferedReader r = null;
			if (encoding == null)
				r = new BufferedReader(new InputStreamReader(is));
			else
				r = new BufferedReader(new InputStreamReader(is, encoding));
			
			String line;
			boolean foundOpenTag = false;
			boolean foundCloseTag = false;
			
			while ((line = r.readLine()) != null ) {				
				PatternMatcher pm1 = new Perl5Matcher();
				PatternMatcher pm2 = new Perl5Matcher();
//				System.out.println(line);
			
				if (!foundOpenTag)
				{
					foundOpenTag = pm1.contains(line, openTag);
					foundCloseTag = pm2.contains(line, closeTag);
					if (foundOpenTag)
					{	
						foundOpenTag = true;	
						if (foundCloseTag){
							title.append(line.substring(pm1.getMatch().endOffset(0), pm2.getMatch().beginOffset(0)));
							return title.toString();
						}else{
							title.append(line.substring(pm1.getMatch().endOffset(0), line.length()));
							continue;
						}
						
					}else
						continue;
				}
				else
				{
					foundCloseTag = pm2.contains(line, closeTag);
					if (foundCloseTag){
						{
							title.append(line.substring(0, pm2.getMatch().beginOffset(0)).trim());
							return title.toString();
						}
					}else 
						{
						title.append(line.trim());
						continue;
						}
					
				}
			}
		} catch (Exception e) {
			Log.error("spider", "", e);
		}
		return title.toString();

		// public static void main(String args[]) {
		// try {
		// URL urlE= new URL("http://www.google.com/search?");
		// URLConnection con=urlE.openConnection();
		// con.setDoOutput(true);
		// PrintWriter out = new PrintWriter(con.getOutputStream());
		// out.println("hl=da&ie=UTF-8&oe=UTF-8&q=biler+salg+import");
		// out.close();
		// BufferedReader r= new BufferedReader(new
		// InputStreamReader(con.getInputStream()));
		// String line;
		// while((line=r.readLine())!=null) {
		// System.out.println(line);
		// }
		// }
		// catch (Exception e)
		// {
		// System.err.println(e);
		// }}

	}
}
