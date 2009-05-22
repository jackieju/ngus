package com.ngus.myweb.servlet;

import java.util.Date;
import java.util.List;

import com.ngus.dataengine.ResourceObject;
import com.ns.util.DateTime;

// for free mark
public class ROWrapper  {
	private String resId;

	private String user;

	private String createTime="";

	private String updateTime="";

	private int shareLevel;

	private String title;

	private List<String> tags;
	
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getShareLevel() {
		return shareLevel;
	}

	public void setShareLevel(int shareLevel) {
		this.shareLevel = shareLevel;
	}

	

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ROWrapper(ResourceObject ro) throws Exception {
		value = ro.getStringValue();
		if (ro.getCreateTime() != null)
			this.setCreateTime(DateTime.toUTCTime(ro.getCreateTime().getTime()));
		if (ro.getUpdateTime() != null)
			this.setUpdateTime(DateTime.toUTCTime(ro.getUpdateTime().getTime()));

		// this.setDesc(ro.getDesc());

		this.setResId(ro.getResId());

		this.setShareLevel(ro.getShareLevel());
		this.setTags(ro.getTags());
		this.setTitle(ro.getTitle());


		this.setUser(ro.getUser());

	}

	

	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}

	
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
}
