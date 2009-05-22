package com.ngus.myweb.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.ngus.message.MessageObject;

public class PageForm extends ActionForm{
	
	//----------------Variables
	private int totalPage;	//����
	private int totalNum; 	//����Ŀ
	private int currPage;	//��ǰ��
	private int pageCount = 5; 	//������
	private int pageStart; 	//��ʵ������
	private int pageEnd;	//����������
	private boolean hasNextPage;	//�Ƿ�����
	private boolean hasPrePage;		//�Ƿ�����
	List<MessageObject> messageList = new ArrayList<MessageObject>();
	private int page;		//�����
	
	//---------------Method
	public PageForm(){}
	
	
	//��ʼ��page
	public PageForm(List<MessageObject> messages){
		this.messageList=messages;
		this.totalNum = messages.size();
		this.hasPrePage=false;
		this.currPage=1;
		if((totalNum%pageCount)==0){
			this.totalPage = totalNum/pageCount;
		}
		else{
			this.totalPage = totalNum/pageCount+1;
		}
		
		//�ж��Ƿ�����
		if(currPage >= totalPage){
			this.hasNextPage = false;
		}
		else{
			this.hasNextPage = true;
		}
		
		//��
		if(totalNum < pageCount){
			this.pageStart = 0;
			this.pageEnd = totalNum ;
		}
		else{
			this.pageStart = 0;
			this.pageEnd = pageCount;
		}
		
	}
	
	public int getPage() {
		return page;
	}


	public void setPage(int page) {
		this.page = page;
	}


	public int getCurrPage() {
		return currPage;
	}


	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}


	public boolean isHasNextPage() {
		return hasNextPage;
	}


	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}


	public boolean isHasPrePage() {
		return hasPrePage;
	}


	public void setHasPrePage(boolean hasPrePage) {
		this.hasPrePage = hasPrePage;
	}


	public int getPageEnd() {
		return pageEnd;
	}


	public void setPageEnd(int pageEnd) {
		this.pageEnd = pageEnd;
	}


	public int getPageStart() {
		return pageStart;
	}


	public void setPageStart(int pageStart) {
		this.pageStart = pageStart;
	}


	public int getTotalNum() {
		return totalNum;
	}


	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}


	public int getTotalPage() {
		return totalPage;
	}


	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
	public List<MessageObject> getMessages(){
		if(this.currPage*this.pageCount < this.totalNum ){//�������һҳ
			this.pageStart =(this.currPage-1)*this.pageCount;
			this.pageEnd = this.currPage*this.pageCount;
		}
		else{
			this.pageStart = (this.currPage-1)*this.pageCount;
			this.pageEnd = this.totalNum;
		}
		
		List<MessageObject> messages = new ArrayList<MessageObject>();
		
		for(int  i = this.pageStart ; i < this.pageEnd ; i++){
			messages.add(this.messageList.get(i));
		}
		
		return messages;
	}
	
	
	public List<MessageObject> getNextPage(){
		List<MessageObject> messages = new ArrayList<MessageObject>();
		this.currPage = this.currPage+1;
		
		this.hasPrePage = true;
		
		if(currPage >= totalPage ){
			this.hasNextPage = false;
		}
		else{
			this.hasNextPage = true;
		}
		
		messages = getMessages();
		
		return messages;
		
	}
	
	public List<MessageObject> getPrePage(){
		List<MessageObject> messages = new ArrayList<MessageObject>();
		this.currPage = this.currPage-1;
		
		this.hasNextPage = true;
		
		if(currPage <= 1  ){
			this.hasPrePage = false;
		}
		else{
			this.hasPrePage = true;
		}
		
		messages = getMessages();
		
		return messages;
		
	}
	
	public List<MessageObject> getSpecPage(int page){
		List<MessageObject> messages = new ArrayList<MessageObject>();
		if(page > 0 && page < totalPage){
			this.currPage = page;
			
			if(currPage <=1){
				this.hasPrePage = false;
			}
			else{
				this.hasPrePage = true;
			}
			
			if(currPage >= totalPage){
				this.hasNextPage = false;
			}
			else{
				this.hasNextPage = true;
			}
				
			messages = getMessages();
			
			
		}
		
		return messages;
		
	}
	

	
}
