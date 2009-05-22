package com.ns.mail;


import java.util.ArrayList;
import java.util.Arrays;

import javax.mail.Transport;

import com.ns.log.Log;



/**
 * This class is meant to allow async emails ... The mail is posted and send. Note that mail success or failure is only
 * displayed in the logs. Date: Mar 31, 2006 Time: 5:51:38 PM
 * @author <a href="mailto:niko@macnica.com">Nicolas Modrzyk</a>
 */
public class ThreadedMailSender {

  //  Logger log = LoggerFactory.getLogger(ThreadedMailHandler.class);

    ArrayList emails = new ArrayList();

    MailThread thread; // can be replaced with a pool of thread someday

    static public ThreadedMailSender _inst = null;
    static public ThreadedMailSender instance(){
    	if (_inst == null)
    		_inst = new ThreadedMailSender();
    	return _inst;
    }
    static public void destroy(){
    	_inst = null;
    }
    private ThreadedMailSender() {
        this.thread = new MailThread();
        Thread bb = new Thread(this.thread);
        bb.start();
    }

    protected void finalize() throws Throwable {
        this.thread.setStop(true);
        super.finalize();
    }



    /**
     * Send the email as is, without touching it
     * @param email the email to send
     * @throws Exception if fails
     */
    public void sendMail(AbstractMail email)  {
        synchronized (this) {
            this.emails.add(email);
        }
        synchronized (this){
        notify();
        }
      
    }

    /**
     * Thread doing all the job for sending emails and formatting the body
     */
    class MailThread implements Runnable {

        boolean stop = false;

        public void run() {
        	Log.log("mailSender", "working thread started");        	
            while (!this.stop) {
            	
                if (ThreadedMailSender.this.emails.size() == 0) { // nothing to do just sleep
                	
                    synchronized (this) {
                        try {
                        	//Log.trace("waiting...");
                            this.wait(100);
                           // Log.trace("wakeup...");
                        }
                        catch (InterruptedException e) {
                            Log.trace("mailSender", "Mail Thread was interrupted");
                        }
                    }
                }
                else {
                    AbstractMail email = null;
                    synchronized (this) {
                    	
                        if (ThreadedMailSender.this.emails.size() > 0) {
                            email = (AbstractMail) ThreadedMailSender.this.emails.remove(0);
                        }
                    }
                    if (email != null) {
                        try {
//                            if (email.isBodyNotSetFlag()) {
//                                email.setBody();
//                            }
                            try {                            	
                                Transport.send(email);
                                Log.trace("mailSender", "Mail has been sent to: ["
                                    + Arrays.asList(email.getAllRecipients())
                                    + "]");                              
                            }
                            catch (Exception e) {
                                Log.error("mailSender", "Email to: ["
                                    + Arrays.asList(email.getAllRecipients())
                                    + "] was not sent because of an error", e);
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                            Log.error("mailSender", "error", e);
                        }
                    }
                }

            }
            Log.trace("mailSender", "leave mail sending roop.");
        }

        public boolean isStop() {
            return this.stop;
        }

        public void setStop(boolean stop) {
            this.stop = stop;
            this.notify();
        }
    }
}
