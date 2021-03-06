package com.ns.mail;



import java.util.ArrayList;
import java.util.HashMap;

import javax.mail.BodyPart;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;

import com.ns.exception.NSException;
import com.ns.log.Log;


/**
 * Date: Mar 30, 2006 Time: 2:12:53 PM
 * @author <a href="mailto:niko@macnica.com">Nicolas Modrzyk</a>
 */
public class HtmlEmail extends MgnlMultipartEmail {

    public static final String MAIL_ATTACHMENT = "attachment";

    public HtmlEmail(Session _session) throws Exception {
        super(_session);
        this.setHeader(CONTENT_TYPE, TEXT_HTML_UTF);
    }

    public void setBody(String body, HashMap parameters) throws Exception {
        // check if multipart
        if (!isMultipart()) { // it is not a multipart yet, just set the text for content
            this.setContent(body, TEXT_HTML_UTF);
        }
        else { // some attachment are already in this mail. Init the body part to set the main text
            // Create your new message part
            BodyPart messageBodyPart = new MimeBodyPart();
            // Set the _content of the body part
            messageBodyPart.setContent(body, TEXT_HTML_UTF);
            // Add body part to multipart
            this.multipart.addBodyPart(messageBodyPart, 0);
            this.setContent(this.multipart);
        }

        // process the attachments
        if (parameters != null && parameters.containsKey(MAIL_ATTACHMENT)) {
            ArrayList attachment = (ArrayList) parameters.get(MAIL_ATTACHMENT);
            setAttachments(attachment);
        }
    }

    private void turnOnMultipart() {
        try {
            Object o = this.getContent();
            if (o instanceof String) {
                BodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setContent(o, TEXT_HTML_UTF);
                this.multipart.addBodyPart(messageBodyPart, 0);
                this.setContent(this.multipart);
            }
        }
        catch (Exception e) {
            Log.trace("Could not turn on multipart");
        }
    }

    public MimeBodyPart addAttachment(MailAttachment attachment) throws NSException {
        if (!isMultipart()) {
            turnOnMultipart();
        }
        return super.addAttachment(attachment);
    }

}
