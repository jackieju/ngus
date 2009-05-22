package com.ngus.myweb.servlet;

import java.util.Date;
import java.util.List;

public class Wrapper{
	String url;
	String name;
	Integer time;
	Date createTime;
	String userName;
	String description;
	List<String> tags;
	String snapshot;
	public void setUrl(String url){this.url = url;}
	public String getUrl(){return url;}
	public int getTime(){return time;}
	public void setTime(int time){this.time = time;}
	public String getName(){return name;}
	public void setName(String name){this.name = name;}
	public void setCreateTime(Date createTime){this.createTime = createTime;}
	public Date getCreateTime(){return this.createTime;}
	public String getUserName(){return this.userName;}
	public void setUserName(String userName){this.userName = userName;}
	public void setTags(List<String> tags) {this.tags = tags;}
	public List<String> getTags() {return tags;}
	public void setDescription(String description) {this.description = description;}
	public String getDescription() {return description;}
	public void setSnapshot(String snapshot) {this.snapshot = snapshot;}
	public String getSnapshot() {return snapshot;}
}
