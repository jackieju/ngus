/*
 * Created on Aug 10, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ns.dataobject;

import java.util.ArrayList;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Attributes {
	ArrayList list = new ArrayList();
	
	public void addAttr(Attribute attr){
		list.add(attr);
	}
	
	public Attribute get(int i ){
		return (Attribute)(list.get(i));
	}
	
	public int size(){
		return list.size();
	}
	
	public String getName(){
		String ret = "";
		for (int i = 0; i< list.size(); i++){
			ret += get(i).getName()+";";
		}
		return ret;
	}
	
	public String getStringValue(){
		String ret = "";
		for (int i = 0; i< list.size(); i++){
			ret += get(i).getStringValue();
		}
		return ret;
	}
	
	public String printHtml(){
		String ret = "";
		for (int i = 0; i< list.size(); i++){
			ret += get(i).printHtml();
		}
		return ret;
	}
	
	public static void main(String[] args) {
	}
}
