package com.ngus.myweb.dataobject;

import java.util.ArrayList;
import java.util.List;

public class ObjectTree {
	private MyWebRes node;
	private List<ObjectTree> children; 
	public ObjectTree(MyWebRes node){
		this.node = node;
		this.children = new ArrayList<ObjectTree>();
	}
	public void insertChild(ObjectTree child){
		children.add(child);
	}
	public void insertAll(List<ObjectTree> child){
		children.addAll(child);
	}
	public void delete(ObjectTree child){
		children.remove(child);
	}
	public void deleteAll(List child){
		children.removeAll(child);
	}
	public List<ObjectTree> getChildren(){
		return this.children;
	}
	public MyWebRes getNode(){
		return this.node;
	}
}
