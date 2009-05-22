package com.ns.mail;

public class MailSettings {

	// Set smtpHost to the hostname for your mail server
	public  String smtpHost="mail.example.com";
	public  String smtpPort="25";
	public  String mailDebug="true";
	
	// For SMTP authentication, set the next two lines to your username and password for the mail server
	public  String smtpUsername="yoursmtpusername";
	public  String smtpPassword="yoursmtppassword";
	
	// Set toAddress to the address you want the email to go to
	public  String toAddress="fred@example.com";
	
	public  String[] ccAddresses=new String[] { "someone@example.com", "other@example.com" };
	
	public  String fromAddress="me@example.com";
	public  String fromName="Mail Example App";
	
	public  String messageSubject="This is a test mail";
	
	public  String messageBody ="This is a test mail sent by a mail example";
	
	public  String inlineImage="file:uk-builder-com.gif";
	
}
