package com.ngus.myweb.servlet;

import java.util.Date;

import com.ngus.config.SystemProperty;
import com.ngus.myweb.dataobject.MyWebRes;
import com.ns.log.Log;

public class PicChecked{
	MyWebRes res;
	Integer checked;
	String picUrl;
	Date createTime;
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public MyWebRes getRes() {
		return res;
	}
	public void setRes(MyWebRes res) {
		this.res = res;
	}
	public Integer getChecked() {
		return checked;
	}
	public void setChecked(Integer checked) {
		this.checked = checked;
	}
	
	public String getPicUrl(){
		picUrl = SystemProperty.getProperty("ngus.myweb.fileserver")+"images/";
		String tmp = null;
		try {
			tmp = this.res.getID();
			int position1 = tmp.indexOf("myweb");
			int position2 = tmp.indexOf("#");
			
			picUrl +=tmp.substring(position1,position2);
			picUrl +="/"+tmp.substring(position2+1)+"/";
			picUrl +=tmp.substring(position2+1)+"300_300.jpg";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.error(e);
		}
		return picUrl;
	}
}
