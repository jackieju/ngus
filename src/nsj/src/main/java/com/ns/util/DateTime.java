package com.ns.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateTime {
	static public void main(String v[]){
		//System.out.print(getCurrentUTCTime().toString());
	
//		GregorianCalendar liftOffApollo11 = new GregorianCalendar(1969, Calendar.JULY, 16, 9, 32);
//		GregorianCalendar liftOffApollo11 = new GregorianCalendar(1969, Calendar.JULY, 16, 9, 32);
//
//		Date d = liftOffApollo11.getTime();
//
//		DateFormat df1 = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
//
//		DateFormat df2 = DateFormat.getTimeInstance(DateFormat.SHORT);
//
//		String s1 = df1.format(d);
//
//		String s2 = df2.format(d);
//
//		System.out.println(s1);
//
//		System.out.println(s2);
//
//		
//		long time = System.currentTimeMillis();
//		time = time-TimeZone.getDefault().getOffset(time);
//		System.out.print(new Timestamp(time).toString());
//	System.out.print(""+currentUTCTimeStamp().getTime());
		System.out.print(toUTCTime(System.currentTimeMillis()));
		} 
	
	static final public String DATE_FORMAT_MYSQL = "yyyyMMdd hh:mm:ss SSS";
	
	static public String currentUTCTime(){
		//Timestamp tm = new Timestamp(System.currentTimeMillis());
		//GregorianCalendar gc = currentTime();
		java.util.Date l_datetime = new java.util.Date(); 
		SimpleDateFormat l_date_format = new SimpleDateFormat("yyyyMMdd hh:mm:ss SSS"); 
		TimeZone l_timezone = TimeZone.getTimeZone("GMT-0"); 
		l_date_format.setTimeZone(l_timezone); 
		String l_utc_date = l_date_format.format(l_datetime);
		return l_utc_date;
	}
	
	static public Timestamp currentUTCTimeStamp(){
		long time = System.currentTimeMillis();
		time = time-TimeZone.getDefault().getOffset(time);
		return new Timestamp(time);
	}
	
	static public String toMySQLTimestamp(Date time){
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy.MM.dd hh:mm:ss SSS");
		return formatter.format(time);		
	}
	
	static public String toUTCTime(long time){
		Date d = new Date(time);
		SimpleDateFormat l_date_format = new SimpleDateFormat("yyyyMMdd hh:mm:ss SSS"); 
		TimeZone l_timezone = TimeZone.getTimeZone("GMT-0"); 
		l_date_format.setTimeZone(l_timezone); 
		String l_utc_date = l_date_format.format(d);
		return l_utc_date;		
	}

	static public String toLocalTime(long time, TimeZone l_timezone){
		Date d = new Date(time);
		SimpleDateFormat l_date_format = new SimpleDateFormat("yyyyMMdd hh:mm:ss SSS");	 
		l_date_format.setTimeZone(l_timezone); 
		String l_utc_date = l_date_format.format(d);
		return l_utc_date;		
	}
	
	static public Date createTime(int year, int month, int day, int hour, int minute, int second){
		return new GregorianCalendar(year, month, day, hour, minute, second).getTime();	
	}
	
	
	static public Date fromUTCTime(String s) throws Exception{
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMdd hh:mm:ss SSS");				
		return formatter.parse(s); 
	}
	
//	static public String currentTimestamp(){
//		Timestamp tm = new Timestamp(System.currentTimeMillis());
//		//tm.getTimezoneOffset()
//	}

	static public Date currentTime(){
		return new GregorianCalendar().getTime();
	}
	
	static public GregorianCalendar currentGregorianCalendar(){
		return new GregorianCalendar();
	}
	
	static public Timestamp currentTimestamp(){
		return new Timestamp(System.currentTimeMillis());
	}

//	static public int currentTimeZoneOffset(){
//		return (new Calendar().get(Calendar.ZONE_OFFSET) + new  Calendar()..get(Calendar.DST_OFFSET)) / (60 * 1000);
//	}
	

	
	
	
}
