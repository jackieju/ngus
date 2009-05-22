package com.ngus.myweb.webservices;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ngus.myweb.dataobject.BookMark;
import com.ngus.myweb.dataobject.Folder;
import com.ngus.myweb.dataobject.MyWebRes;
import com.ngus.myweb.dataobject.ObjectTree;
import com.ngus.myweb.services.MyWebResService;
import com.ngus.myweb.webservices.dataobject.MyWebResObject;
import com.ngus.myweb.webservices.dataobject.ObjectTreeObject;
import com.ngus.um.IUser;
import com.ngus.um.UMClient;
import com.ngus.um.UMSession;
import com.ngus.um.http.Session;
import com.ngus.um.http.SessionManager;
import com.ngus.um.http.WebServiceSession;
import com.ns.log.Log;
import com.ns.util.DateTime;

public class WebServices {	
	private UMClient um = new UMClient();
	/**
	 * copy a bookmark\folder to another place
	 * @param from
	 * @param to
	 * @return
	 */
	/*public String copy(String srcId, String targetParentId){
		try{			
			MyWebResService.instance().copy(srcId,targetParentId);
			return "true";
		}
		catch(Exception e){
			return "false";
		}
	}
	/**
	 * move a bookmark\folder to another place
	 * @param from
	 * @param to
	 * @return
	 */
	/*public String move(String srcId, String targetParentId){
		try{
			MyWebResService.instance().move(srcId,targetParentId);
			return "true";
		}
		catch(Exception e){
			return "false";
		}
	}*/
	
	/**
	 * ���id�õ���Դ���󣬿�����Folder����BookMark 
	 * @param id 
	 * @return
	 */
	public MyWebResObject getInstanceByID(String id){
		Log.trace("Enter getInstanceByID", id);
		MyWebResObject ret = null;
		try{			
			MyWebRes tmp = MyWebResService.instance().getInstanceByID(id);
			ret = resToObject(tmp);
		}
		catch(Exception e){
			Log.error(e);
		}
		return ret;
	}
	
	/**
	 * @param id
	 * @return
	 */
	public String deleteByID(String id, String sessionID){
		try{
			WebServiceSession us = putSession(sessionID);
			if(us==null){
				return "false";
			}
			MyWebResService.instance().deleteByID(id);
			return "true";
		}
		catch(Exception e){
			Log.error(e);
		}
		return "false";
	}
	
	/**
	 * update a bookmark\folder
	 * @param res
	 * @return
	 */
	public String update(MyWebResObject res, String sessionID){		
		//MyWebRes tmp = change(res);		
		try{
			WebServiceSession us = putSession(sessionID);
			if(us==null){
				return "false";
			}
			MyWebRes tmp = objectToRes(res, us.getUser().getSUserId());
			MyWebResService.instance().update(tmp, us.getUser().getSUserId());
			return "true";
		}
		catch(Exception e){
			Log.error(e);
		}
		return "false";
	}
	/**
	 * private method to convert webservice object to resource object 
	 * @param res
	 * @return
	 * @throws Exception
	 */
	private MyWebRes objectToRes(MyWebResObject res) throws Exception{
		MyWebRes tmp;
		ArrayList<String> al = new ArrayList<String>();
		String[] tags = res.getTags();
		if(tags!=null){
			for(int i=0; i<tags.length; i++){
				al.add(tags[i]);
			}
		}
		if("folder".equalsIgnoreCase(res.getResType())){
			tmp = new Folder(res.getName(), res.getParentId(), res.getDescription(), al);
			tmp.setID(res.getId());
		}
		else{
			//BookMarkObject bk = (BookMarkObject)res;
			tmp = new BookMark(res.getName(), res.getURL(), res.getRtype(), res.getParentId(), res.getDescription(), al);
			tmp.setID(res.getId());
		}
		return tmp;
	}
	private MyWebRes objectToRes(MyWebResObject res, String userID) throws Exception{
		MyWebRes tmp;
		ArrayList<String> al = new ArrayList<String>();
		String[] tags = res.getTags();
		if(tags!=null){
			for(int i=0; i<tags.length; i++){
				al.add(tags[i]);
			}
		}
		if("folder".equalsIgnoreCase(res.getResType())){
			tmp = new Folder(res.getName(), res.getParentId(), res.getDescription(), al, 0, userID);
			tmp.setID(res.getId());
		}
		else{
			//BookMarkObject bk = (BookMarkObject)res;
			tmp = new BookMark(res.getName(), res.getURL(), res.getRtype(), res.getParentId(), res.getDescription(), al, 0, userID);
			tmp.setID(res.getId());
		}
		return tmp;
	}
	/**
	 * private method to convert resource object to web service object
	 * @param tmp
	 * @return
	 * @throws Exception
	 */
	private MyWebResObject resToObject(MyWebRes tmp) throws Exception{
		MyWebResObject ret;
		
		String[] tags = null;
		if(tmp.getTags()!=null){
			tags = new String[tmp.getTags().size()];
			Iterator iter = tmp.getTags().iterator();
			int i = 0;
			while(iter.hasNext()){
				tags[i++]=(String)iter.next();
			}
		}
		if(tmp instanceof Folder){
			ret = new MyWebResObject(tmp.getID(), tmp.getName(), tmp.getParentID(), tmp.getResType(), tmp.getDescription(), tags);
		}
		else{
			BookMark bk = (BookMark)tmp;
			ret = new MyWebResObject(bk.getID(), bk.getName(), bk.getParentID(), bk.getResType(), bk.getRtype(), bk.getPtype(), bk.getURL(), tmp.getDescription(), tags);
		}
		return ret;
	}
	
	/**
	 * store the resource to harddisk\database
	 * @param res
	 * @return
	 */
	public String makePersistent(MyWebResObject res, String sessionID){
		Log.trace("Enter makePersistent:"+res + ", session id = " + sessionID);
		String ret = null;
		//MyWebRes tmp = change(res);
		try{
			WebServiceSession us = putSession(sessionID);
			if(us==null){
				Log.error("put session failed");
				return null;
			}
			MyWebRes tmp = objectToRes(res, us.getUser().getSUserId());
			Log.trace("objectToRes return "+ tmp);
			ret = MyWebResService.instance().makePersistent(tmp, us.getUser().getSUserId());
			Log.trace("makePersistent return "+ ret);
		}
		catch(Exception e){
			Log.error(e);
		}
		Log.trace("Leave makePersistent:"+res);
		return ret;
	}
	
	public String insertTree(ObjectTreeObject ot, String sessionID){
		String ret = "false";
		if(ot!=null){
			if((ret = makePersistent(ot.getNode(), sessionID))==null){
				return ret;
			}
			if(ot.getChildren()!=null){
				for(int i = 0; i < ot.getChildren().length; i++){
					(ot.getChildren()[i]).getNode().setParentId(ret);
					if(insertTree(ot.getChildren()[i], sessionID)==null){
						return null;
					}
				}
			}
		}		
		return "true";
	}
	/**
	 * getTree of it
	 * @param id
	 * @return
	 */
	public ObjectTreeObject getTree(String id, int depth, String sessionID){
		ObjectTreeObject ot = null;
		try{
			WebServiceSession us = putSession(sessionID);
			if(us==null){
				return null;
			}
			ObjectTree tmp = MyWebResService.instance().getTree(id, depth);	
			ot = convert(tmp);
		}
		catch(Exception e){
			Log.error(e);
		}
		return ot;
	}
	/**
	 * convert a tree from resource object to web service object
	 * @param ot
	 * @return
	 * @throws Exception
	 */
	private ObjectTreeObject convert(ObjectTree ot) throws Exception{				
		ObjectTreeObject ret = new ObjectTreeObject(resToObject(ot.getNode()));
		ObjectTreeObject[] children = new ObjectTreeObject[ot.getChildren().size()];
		Iterator iter = ot.getChildren().iterator();
		int i = 0;
		while(iter.hasNext()){
			children[i++]=convert((ObjectTree)iter.next());
		}
		ret.setChildren(children);
		return ret;
	}
	
	public ObjectTreeObject[] getRoot(int depth, String sessionID){
		ArrayList ot = new ArrayList();
		try{
			WebServiceSession us = putSession(sessionID);
			if(us==null){
				return null;
			}
			List<ObjectTree> tmp = MyWebResService.instance().listAll(depth, us.getUser().getSUserId());
			if (tmp == null || tmp.size() == 0)
				return null;
			for(int i = 0; i < tmp.size(); i++){
				ot.add(convert(tmp.get(i)));
			}					
		}
		catch(Exception e){
			Log.error(e);
		}
		
		if (ot == null)
			return null;
		
		ObjectTreeObject[] ret = new ObjectTreeObject[ot.size()];
		Iterator iter = ot.iterator();
		// add by jackie
		if (iter == null)
			return ret;
		// == 
		int i = 0;		
		while(iter.hasNext()){
			ret[i++]=(ObjectTreeObject)iter.next();
		}
		return ret;
	}
	public MyWebResObject[] listByTag(String tag, int start, int number){
		MyWebResObject[] ret = null;
		try{
			List tmp = MyWebResService.instance().listByTag(tag, start, number, null, null);
			Iterator iter = tmp.iterator();
			ret = new MyWebResObject[tmp.size()];
			int i = 0;
			while(iter.hasNext()){
				ret[i++]=this.resToObject((MyWebRes)iter.next());
			}
		}
		catch(Exception e){
			Log.error(e);
		}
		return ret;
	}
	
	public MyWebResObject[] fullTextSearch(String key, int start, int number, String sessionID) {
		
		
		MyWebResObject[] ret =null;
		try{
			WebServiceSession us = putSession(sessionID);
			if(us==null){
				return null;
			}
			//TODO Change WebService interface??
			List tmp = MyWebResService.instance().search(key, start, number, null, null);
			Iterator iter = tmp.iterator();
			int i = 0;
			ret = new MyWebResObject[tmp.size()];
			while(iter.hasNext()){
				ret[i++]=this.resToObject((MyWebRes)iter.next());
			}		
		}		
		catch(Exception e){
			Log.error(e);
		}
		return ret;
	}
	
	public String[] listAllTag(){
		String[] ret = null;
		try{
			List tmp = MyWebResService.instance().listAllTag();
			Iterator iter = tmp.iterator();
			int i = 0;
			ret = new String[tmp.size()];
			while(iter.hasNext()){
				ret[i++]=(String)iter.next();
			}
		}
		catch(Exception e){
			Log.error(e);
		}
		return ret;
	}
	public MyWebResObject[] listMostRecentRes(int number, String sessionID){
		MyWebResObject[] ret = null;
		try{
			WebServiceSession us = putSession(sessionID);
			if(us==null){
				return null;
			}
			List tmp = MyWebResService.instance().listMostRecentRes(number);
			Iterator iter = tmp.iterator();
			int i = 0;
			ret = new MyWebResObject[tmp.size()];
			while(iter.hasNext()){
				ret[i++]=this.resToObject((MyWebRes)iter.next());
			}
		}
		catch(Exception e){
			Log.error(e);
		}
		return ret;
	}
	public String[] mostPopularTag(int count){
		String[] ret = null;
		try{
			List tmp = MyWebResService.instance().mostPopularTag(count);
			Iterator iter = tmp.iterator();
			int i = 0;
			ret = new String[tmp.size()];
			while(iter.hasNext()){
				ret[i++]=(String)iter.next();
			}
		}
		catch(Exception e){
			Log.error(e);
		}
		return ret;
	}
	public String[] listMostPopularURL(int count){
		String[] ret = null;
		try{
			List tmp = MyWebResService.instance().listMostPopularRes(count);
			Iterator iter = tmp.iterator();
			int i = 0;
			ret = new String[tmp.size()];
			while(iter.hasNext()){
				ret[i++]=(String)iter.next();
			}
		}
		catch(Exception e){
			Log.error(e);
		}
		return ret;
	}
	public String[] listUserTag(String sid){
		String[] ret = null;
		try{
			//UMClient um = new UMClient();
			WebServiceSession ums = putSession(sid);
			List tmp = MyWebResService.instance().listUserTag(ums.getUser().getSUserId());
			Iterator iter = tmp.iterator();
			int i = 0;
			ret = new String[tmp.size()];
			while(iter.hasNext()){
				ret[i++]=(String)iter.next();
			}
		}
		catch(Exception e){
			Log.error(e);
		}
		return ret;
	}
	
	/*public MyWebResObject[] listByType(String type, int start, int number){
		MyWebResObject[] ret = null;
		try{
			List tmp = MyWebResService.instance().getByType(type, start, number);
			Iterator iter = tmp.iterator();
			int i = 0;
			ret = new MyWebResObject[tmp.size()];
			while(iter.hasNext()){
				ret[i++]=this.resToObject((MyWebRes)iter.next());
			}
		}
		catch(Exception e){
			Log.error(e);
		}
		return ret;
	}*/
	
	public MyWebResObject[] listByType(String type, int start, int number, String sessionID){
		MyWebResObject[] ret = null;
		try{
			WebServiceSession us = putSession(sessionID);
			if(us==null){
				return null;
			}
			List tmp = MyWebResService.instance().getByType(type, start, number, us.getUser().getSUserId());
			Iterator iter = tmp.iterator();
			int i = 0;
			ret = new MyWebResObject[tmp.size()];
			while(iter.hasNext()){
				ret[i++]=this.resToObject((MyWebRes)iter.next());
			}
		}
		catch(Exception e){
			Log.error(e);
		}
		return ret;
	}
	
	public long getUserAppUpdateTime(String sessionID, String app){
		Log.trace("enter");
		
		java.sql.Statement st = null;
		java.sql.Connection c = null;
		java.sql.ResultSet rs = null;
		int userId = putSession(sessionID).getUser().getUserId();
		try {
			c = com.ngus.dataengine.DBConnection.getConnection();
			String sql = "select updatetime from updatetime where userid="+userId+" and app='" + app + "'";
			Log.trace(sql);
			st = c.createStatement();
			rs = st.executeQuery(sql);
			long curTime = DateTime.currentUTCTimeStamp().getTime();
			if (rs != null && rs.next()){ // update
				Log.trace("leave");
				return rs.getLong(1);
			}
			else { // insert
				sql = "insert into updatetime values("+userId+", '"+app+"', "+ curTime +")";
				Log.trace(sql);
				st.executeUpdate(sql);
			}
		} catch(Exception e){
			Log.error(e);
		}
		finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				if (c != null)
					c.close();
			} catch (SQLException e) {
				Log.error(e);
			}
		}
		Log.trace("leave");
		return -1;

	}
	
	private WebServiceSession putSession(String sessionId){
		WebServiceSession session = null;
		session = new WebServiceSession(sessionId);		
		SessionManager.putSession(session);
		return session;
	}

	public MyWebResObject[] getBeforeTimeList(long  time, String sessionID) {
		MyWebResObject[] ret = null;
		try {
			String[] list = MyWebResService.instance().getBeforeTimeList(time, sessionID);
			int num = list.length;
			ret = new MyWebResObject[num];
			Log.trace("size = " + num);
			for(int i =0 ; i<num;i++){
				String resid = list[i];
				MyWebRes tmp = MyWebResService.instance().getInstanceByID(resid);
				
				ret[i] = this.resToObject(tmp);
			}
		} catch (Exception e) {
			Log.error(e);
		}
		
		return ret;
	}

	public String[] getDeletedRes(long time , String sessionID){
		String[] ret = null;
		try{
			ret = MyWebResService.instance().getDeletedRes(time,sessionID);
		}catch(Exception e)
		{ 
			Log.error(e);
		}
		
		return ret;
	}
}
