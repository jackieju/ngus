package com.ns.mail;



import java.util.HashMap;

import javax.mail.Session;


/**
 * Date: Mar 30, 2006 Time: 1:01:01 PM
 * @author <a href="mailto:niko@macnica.com">Nicolas Modrzyk</a>
 */
public class SimpleEmail extends AbstractMail {

    public SimpleEmail(Session s) throws Exception {
        super(s);
        this.setHeader(CONTENT_TYPE, TEXT_PLAIN_UTF);
    }
    
    public SimpleEmail() throws Exception {
        super(MailSender.getSession());
        this.setHeader(CONTENT_TYPE, TEXT_PLAIN_UTF);
    }
    public void setBody(String body, HashMap parameters) throws Exception {
        this.setContent(body, TEXT_PLAIN_UTF);
    }
}
