package com.ngus.servlet;

import java.util.Enumeration;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.ngus.um.http.HttpServletSession;
import com.ngus.um.http.SessionManager;
import com.ns.log.Log;

public class SessionListener implements HttpSessionListener,
  ServletContextListener,ServletContextAttributeListener, ServletRequestListener
{

	//private ServletContext context = null;
	

	public void requestDestroyed(ServletRequestEvent arg0) {
		Log.trace("enter requestDestroyed");
		SessionManager.removeSession();
		
	}
	public void requestInitialized(ServletRequestEvent arg0) {
		Log.trace("enter requestInitialized");
		try{
			HttpServletSession hss = new HttpServletSession(arg0.getServletRequest());
			if (hss != null)
				SessionManager.putSession(hss);
		}catch(Exception e){
			Log.error(e);
		}

		
	}
	//����һ��sessionʱ����
	public void sessionCreated(HttpSessionEvent se) 
	{
		Log.trace("enter sessionCreated");
		//setContext(se);
	
		Enumeration e = se.getSession().getAttributeNames();
		while (e.hasMoreElements()){
			Log.trace(e.nextElement());
		}
		e = se.getSession().getServletContext().getAttributeNames();
		while (e.hasMoreElements()){
			Log.trace(e.nextElement());
		}
	
	}
	//��һ��sessionʧЧʱ����
	public void sessionDestroyed(HttpSessionEvent se) 
	{
		Log.trace("enter sessionDestroyed");
		setContext(se);
	}
	
	//����context�����ԣ����attributeReplaced��attributeAdded����
	public void setContext(HttpSessionEvent se)
	{
		Log.trace("enter setContext");
	//	se.getSession().getServletContext().setAttribute("onLine",new Integer(count));
	}
	
	 //���һ���µ�����ʱ����
	public void attributeAdded(ServletContextAttributeEvent event) {
		Log.trace("enter attributeAdded");
	log("attributeAdded('" + event.getName() + "', '" +
	    event.getValue() + "')");
	Log.trace("attributeAdded('" + event.getName() + "', '" +
		    event.getValue() + "')");
	Log.trace("leave attributeAdded");

    }
    
   //ɾ��һ���µ�����ʱ����
    public void attributeRemoved(ServletContextAttributeEvent event) {
    	Log.trace("enter attributeRemoved");
	log("attributeRemoved('" + event.getName() + "', '" +
	    event.getValue() + "')");

    }

	//���Ա����ʱ����
    public void attributeReplaced(ServletContextAttributeEvent event) {

    	Log.trace("enter attributeReplaced");
		log("attributeReplaced('" + event.getName() + "', '" +
		    event.getValue() + "')");
    }
    //contextɾ��ʱ����
     public void contextDestroyed(ServletContextEvent event) {
    		Log.trace("enter contextDestroyed");
		
	//	this.context = null;

    }

    //context��ʼ��ʱ����
    public void contextInitialized(ServletContextEvent event) {
    	Enumeration e = event.getServletContext().getAttributeNames();
		while (e.hasMoreElements()){
			Log.trace(e.nextElement());
		}
	//	this.context = event.getServletContext();
		log("contextInitialized()");

    }
    private void log(String message) {

	    System.out.println("ContextListener: " + message);
    }   
}
