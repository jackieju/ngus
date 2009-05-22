package com.ngus.myweb.servlet.taobao;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.ngus.myweb.util.ParamUtils;
import com.ns.log.Log;
/**
 * Servlet implementation class for Servlet: TaobaoFBServlet
 *
 */
 public class TaobaoFBServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public TaobaoFBServlet() {
		super();
	}   	
	
	HttpServletResponse response = null;
	public void trace(Object o){
		try{
		if (response != null)
				response.getWriter().println(String.valueOf(o));
		}catch(Exception e){
			Log.error(e);
		}
		Log.trace(o);
	}
	  public  static SimpleDateFormat SIP_TIMESTAMP_FORMATER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//时间格式
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.response = response;
		String userId = ParamUtils.getParameter(request, "user_id");
		String appInstanceId = ParamUtils.getParameter(request, "app_instance_id");
		String token = ParamUtils.getParameter(request, "token");
		String appId = ParamUtils.getParameter(request, "app_id");
		String sip_sessionid = request.getSession(true).getId();
		String sip_appkey = "10934";
		String sip_appsecret = "863344802cc111ddb7e3d08d7d701322";
		String sip_apiname = "alisoft.validateUser";
		String sip_timestamp = SIP_TIMESTAMP_FORMATER.format(new Date());
		Log.trace("taobao entry: " + userId+","+appInstanceId+","+token);
		response.getWriter().println(userId+","+appInstanceId+","+token);
		
		 Map<String, String> map = new HashMap<String, String>();//在map中输入各个参数
         
         map.put("token", token);
         map.put("appId", appId);
         map.put("userId", userId);
         map.put("appInstanceId", appInstanceId);
         map.put("sip_sessionid",sip_sessionid);
         map.put("sip_appkey",sip_appkey);//注册软件时所得
         map.put("sip_appsecret",sip_appsecret);//注册软件时所得
         map.put("sip_apiname", sip_apiname);
         map.put("sip_timestamp", sip_timestamp);
         String sign = SignatureUtil.Signature(map,map.get("sip_appsecret"));//签名,生成sip_sign
         map.put("sip_sign", sign);                 
		
		Iterator s= request.getParameterMap().keySet().iterator();
		while (s.hasNext()){	
			String n = (String)s.next();
			Object o = request.getParameterMap().get(n);
			if (o == null)
				continue;
			trace("type="+o.getClass());
			if (!(o instanceof java.lang.String))
				continue;
			java.lang.String v = (java.lang.String)o;
			trace(n+"="+ v );
		}
	
         StringBuffer buffer = new StringBuffer();
         boolean notFirst = false;
         for (Map.Entry<String, ?> entry : map.entrySet()) {
                 if (notFirst) {
                         buffer.append("&");
                 } else {
                         notFirst = true;
                 }
                 Object value = entry.getValue();
                 buffer.append(entry.getKey()).append("=").append(
                                 encodeURL(value) );
         }
         String queryString=buffer.toString(); 
         trace("queryString="+queryString);
         
//         String queryString="userId="+encodeURL(userId)
//         +"&appInstanceId="+encodeURL(appInstanceId)
//         +"&token="+encodeURL(token)
//         +"&appId="+encodeURL(appId)
//         +"&sip_sessionid="+encodeURL(sip_sessionid)
//         +"&sip_appkey="+encodeURL(sip_appkey)
//         +"&sip_appsecret="+encodeURL(sip_appsecret)
//         +"&sip_apiname="+encodeURL(sip_apiname)
//         +"&sip_timestamp="+encodeURL(sip_timestamp)
//         +"&sip_sign="+encodeURL(sip_sign)
//         ;               
           /*
          * *第三步，发送访问请求
          */
         String result = null, code = null;
         try{
	         HttpURLConnection conn = (HttpURLConnection) new URL ("http://sipdev.alisoft.com/sip/rest").openConnection();
	         conn.setRequestMethod("POST");
	         conn.setDoOutput(true);
	         conn.connect();
	         conn.getOutputStream().write(queryString.getBytes());
	         String charset = this.getChareset(conn.getContentType());
	         BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream (), charset));//设置编码
	         StringBuffer outbuffer = new StringBuffer();
	         String line;
	         while ((line = reader.readLine()) != null) {
	                 outbuffer.append(line);
	         }
	         reader.close();
	         code = conn.getHeaderField("sip_status");//返回的状态码
	         conn.disconnect();
	         result=outbuffer.toString();//返回内容
	         trace("result="+result);
         }catch(Exception e){
        	 e.printStackTrace();
        	 trace(e);
         }
         
         /*
             *第四步，分析返回内容
               */
         if (result!=null) {
                 // 请求返回的status为成功状态
                 if (code.equals("9999")) {
                 Map<String, Object> returnMap = ApiUtil.getCode(
                                         DemoConstants.VALIDATE_USER, result);
                 String outcode = (String) returnMap.get("String");
                 trace("outcode="+outcode);
                 // 已订购或者使用的用户
                 if (DemoConstants.IS_APP_USER.equals(outcode) || DemoConstants.IS_APP_SUBSC_USER.equals(outcode)) {
                     response.getWriter().println("ok");
                     request.getSession().setAttribute("tb_user", userId);
                     response.sendRedirect("taobao/taobaoreg.jsp");
                	 return;//指向成功访问页面
                         }
                 }
         }
         response.getWriter().println("error");
         return;
 }
	
	
//	private String getUserNickName(String userId, String appInstanceId, ){
//		
//		String appInstanceId = ParamUtils.getParameter(request, "app_instance_id");
//		String token = ParamUtils.getParameter(request, "token");
//		String appId = ParamUtils.getParameter(request, "app_id");
//		String sip_sessionid = request.getSession(true).getId();
//		String sip_appkey = "10934";
//		String sip_appsecret = "863344802cc111ddb7e3d08d7d701322";
//		String sip_apiname = "alisoft.validateUser";
//		String sip_timestamp = SIP_TIMESTAMP_FORMATER.format(new Date());
//		Log.trace("taobao entry: " + userId+","+appInstanceId+","+token);
//		response.getWriter().println(userId+","+appInstanceId+","+token);
//		
//		 Map<String, String> map = new HashMap<String, String>();//在map中输入各个参数
//         
//         map.put("token", token);
//         map.put("appId", appId);
//         map.put("userId", userId);
//         map.put("appInstanceId", appInstanceId);
//         map.put("sip_sessionid",sip_sessionid);
//         map.put("sip_appkey",sip_appkey);//注册软件时所得
//         map.put("sip_appsecret",sip_appsecret);//注册软件时所得
//         map.put("sip_apiname", sip_apiname);
//         map.put("sip_timestamp", sip_timestamp);
//         String sign = SignatureUtil.Signature(map,map.get("sip_appsecret"));//签名,生成sip_sign
//         map.put("sip_sign", sign); 
//		
//		
//	}
 /*
   *当中用到的一些辅助方法
     */
 private String getChareset(String contentType) {
         int i = contentType == null ? -1 : contentType.indexOf("charset=");
         return i == -1 ? "UTF-8" : contentType.substring(i + 8);
 }
 /*
  * *编码
  */
 private String encodeURL(Object target) {
         String result = (target != null) ? target.toString() : "";
         try {
                 result = URLEncoder.encode(result, "UTF8");
         } catch (UnsupportedEncodingException e) {
                 e.printStackTrace();
         }
         return result;
 }
}



/**
* 服务请求辅助类
* @author wenchu.cenwc
*
*/
class SignatureUtil
{

/**
* 签名方法
* @param params
* @param secret
* @return
*/
@SuppressWarnings("unchecked")
public static String Signature(Map params,String secret)
{
 String result = null;
 try
 {
     Map treeMap = new TreeMap();
     treeMap.putAll(params);
     Iterator iter = treeMap.keySet().iterator();
     StringBuffer orgin = new StringBuffer(secret);
     while(iter.hasNext())
     {
         String name = (String)iter.next();
         orgin.append(name).append(params.get(name));
     }

     MessageDigest md = MessageDigest.getInstance("MD5");
     result = byte2hex(md.digest(orgin.toString().getBytes("utf-8")));
 }
 catch(Exception ex)
 {
     throw new java.lang.RuntimeException("sign error !");
 }
 return result;
}

/**
*
* 二行制转字符串
*
* @param b
*
* @return
*
*/
public static String byte2hex(byte[] b) {
 String hs = "";
 String stmp = "";
 for (int n = 0; n < b.length; n++) {
     stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
     if (stmp.length() == 1)
         hs = hs + "0" + stmp;
     else
         hs = hs + stmp;
 }
 return hs.toUpperCase();
}
	 	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}   	  	    
}

class ApiUtil {

    /**
     * 从传入的xml串中获取code
     *
     * @param s
     * @return
     */
    public static Map<String, Object> getCode(String apiName, String str) {
            Map<String, Object> map = new HashMap<String, Object>();

            if (str != null || str.length() >= 0) {
                    ByteArrayInputStream bais = new ByteArrayInputStream(str.getBytes());
                    Document document = null;

                    try {
                            DocumentBuilderFactory factory = DocumentBuilderFactory
                                            .newInstance();
                            factory.setValidating(true);
                            DocumentBuilder builder = factory.newDocumentBuilder();

                            document = builder.parse(bais);
                    } catch (ParserConfigurationException e) {
                            e.printStackTrace();
                    } catch (SAXException e) {
                            e.printStackTrace();
                    } catch (IOException e) {
                            e.printStackTrace();
                    }

                    if (document != null) {
                            map.put("String", toString(document, "String", 0));
                    }
            }

            return map;
    }
   
    /**
     * 将xml对象节点中的值转换为字符串
     *
     * @param document
     * @param tagName
     * @param item
     * @return
     */
    private static String toString(Document document, String tagName,
                    Integer item) {
            return null != document.getElementsByTagName(tagName).item(
                            null == item ? 0 : item) ? document.getElementsByTagName(
                            tagName).item(null == item ? 0 : item).getFirstChild()
                            .getNodeValue() : "";
    }
}

class DemoConstants{
	static public String VALIDATE_USER =  "alisoft.validateUser";
	static public String IS_APP_USER = "0";
	static public String IS_APP_SUBSC_USER = "1";
}
