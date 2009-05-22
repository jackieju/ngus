package com.ns.mail;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.URLDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.ns.exception.NSException;
import com.ns.log.Log;

public class MailSender {
	
	public static void main(String[] args) throws Exception {
		
		Log.trace("start sending email");
		MailSender.initialize("211.144.87.138", "25", null, null);
		
		MailSettings mail = new MailSettings();
		mail.ccAddresses = null;
		mail.fromAddress = "ju.weihua@adways.net";
		mail.fromName = "test";
		
		// mail.inlineImage =
		mail.toAddress = "jackie_juju@Hotmail.com";
		mail.messageSubject = "test";
		//mail.smtpHost = "localhost";
//		mjm.sendMail(mail);

	
		MailSender.sendMailThreaded(mail);
		Log.trace("ok)");
		MailSender.destroy();
	}

	public MailSender() {
	};
	static public Session getSession(){
		Session session = Session.getDefaultInstance(props,
				(Authenticator) new ForcedAuthenticator(userName, pwd));
		return session;
	}
	
	
	private static Properties props = new Properties();
	private static String userName;
	private static String pwd;
	public static void initialize(String host, String port, String user, String pwd1){
		//props.put("mail.smtp.starttls.enable","true");
		Log.trace("mailSender", "Init..." );
		Log.trace("mailSender", "port:"+port );
		Log.trace("mailSender", "host:"+host );
		Log.trace("mailSender", "userName:"+userName );
		Log.trace("mailSender", "pwd:"+pwd1 );
		props.put("mail.smtp.port", port);	
		props.put("mail.smtp.host", host);
		userName = user;
		pwd = pwd1;
		ThreadedMailSender.instance();
		Log.trace("mailSender", "Init OK" );
	}
	static public void destroy(){
		ThreadedMailSender.destroy();
	}
	
	static public void sendMailThreaded(AbstractMail mail) throws NSException {
		ThreadedMailSender.instance().sendMail(mail);
	}
	
	static public void sendMailThreaded(MailSettings mail) throws NSException {
		
		

		try {
			SimpleEmail se = new SimpleEmail();
			se
					.setFrom(new InternetAddress(mail.fromAddress,
							mail.fromName));

			se.setRecipient(Message.RecipientType.TO, new InternetAddress(
					mail.toAddress));

			ArrayList<InternetAddress> ccs = new ArrayList<InternetAddress>();

			if (mail.ccAddresses != null && mail.ccAddresses.length > 0)
				for (String s : mail.ccAddresses)
					ccs.add(new InternetAddress(s));

			se.setRecipients(Message.RecipientType.CC,
					(InternetAddress[]) ccs.toArray(new InternetAddress[ccs
							.size()]));

			se.setSubject(mail.messageSubject);

			se.setText(mail.messageBody);

			se.setSentDate(new Date());

			ThreadedMailSender.instance().sendMail(se);
		

		} catch (AddressException e) {
			e.printStackTrace();
			Log.error(e);
		} catch (MessagingException e) {		
			e.printStackTrace();
			Log.error(e);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			Log.error(e);
		}catch(Exception e){
			throw new NSException(e);
		}

	}

	static public void sendMail(MailSettings mail) {
		Properties props = new Properties();

		props.put("mail.smtp.host", mail.smtpHost);

		Session session = Session.getDefaultInstance(props,
				(Authenticator) new ForcedAuthenticator(userName, pwd));

		Message message = new MimeMessage(session);

		try {
			message
					.setFrom(new InternetAddress(mail.fromAddress,
							mail.fromName));

			message.setRecipient(Message.RecipientType.TO, new InternetAddress(
					mail.toAddress));

			ArrayList<InternetAddress> ccs = new ArrayList<InternetAddress>();

			if (mail.ccAddresses != null && mail.ccAddresses.length > 0)
				for (String s : mail.ccAddresses)
					ccs.add(new InternetAddress(s));

			message.setRecipients(Message.RecipientType.CC,
					(InternetAddress[]) ccs.toArray(new InternetAddress[ccs
							.size()]));

			message.setSubject(mail.messageSubject);

			message.setText(mail.messageBody);

			message.setSentDate(new Date());

			Transport.send(message);

		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.error(e);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.error(e);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.error(e);
		}

	}

	static public void sendSimpleHtmlMail(MailSettings mail) {
		Properties props = new Properties();

		props.put("mail.smtp.host", mail.smtpHost);
		props.put("mail.smtp.port", mail.smtpPort);
		props.put("mail.debug", mail.mailDebug);

		Session session = Session.getDefaultInstance(props,
				new ForcedAuthenticator(userName, pwd));

		Message message = new MimeMessage(session);

		try {
			message
					.setFrom(new InternetAddress(mail.fromAddress,
							mail.fromName));

			message.setRecipient(Message.RecipientType.TO, new InternetAddress(
					mail.toAddress));

			message.setSubject(mail.messageSubject);

			MimeMultipart multipart = new MimeMultipart();

			BodyPart msgBodyPart = new MimeBodyPart();
			msgBodyPart.setContent(
					"<H1>Hi! From HtmlJavaMail</H1><img src=\"cid:logo\">",
					"text/html");

			BodyPart embedImage = new MimeBodyPart();
			DataSource ds = new URLDataSource(new URL(mail.inlineImage));
			embedImage.setDataHandler(new DataHandler(ds));
			embedImage.setHeader("Content-ID", "<logo>");

			multipart.addBodyPart(msgBodyPart);
			multipart.addBodyPart(embedImage);

			message.setContent(multipart);

			message.setSentDate(new Date());

			Transport.send(message);

		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.error(e);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.error(e);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.error(e);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.error(e);
		}

	}

}
