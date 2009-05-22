package com.ngus.myweb.services;

import java.util.List;

import com.ngus.myweb.dataobject.MyWebRes;
import com.ngus.myweb.dataobject.ObjectTree;

public interface IMyWebResService {
	MyWebRes getInstanceByID(String id) throws Exception;
	//boolean move(String from, String to);
	//boolean copy(String from, String to);
	String makePersistent(MyWebRes res);
	boolean deleteByID(String id);
	boolean update(MyWebRes res);
	List<ObjectTree> listAll(int depth);
	ObjectTree getTree(String id, int depth);
}
