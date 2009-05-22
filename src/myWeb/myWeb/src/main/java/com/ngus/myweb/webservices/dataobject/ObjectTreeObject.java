package com.ngus.myweb.webservices.dataobject;


public class ObjectTreeObject {
	private MyWebResObject node;
	private ObjectTreeObject[] children;
	public ObjectTreeObject(){
		
	}
	
	public ObjectTreeObject(MyWebResObject node){
		this.node = node;
		this.children = new ObjectTreeObject[10];
	}
	/*public void insertAll(List<ObjectTreeObject> child){
		this.children = new ObjectTreeObject[child.size()];
		Iterator iter = child.iterator();
		int i = 0;
		while(iter.hasNext()){
			children[i++]=(ObjectTreeObject)iter.next();
		}
	}*/

	public ObjectTreeObject[] getChildren(){
		return this.children;
	}
	public void setChildren(ObjectTreeObject[] children){
		this.children = children;
	}
	public MyWebResObject getNode(){
		return this.node;
	}
	public void setNode(MyWebResObject obj){
		this.node = obj; 
	}
}
