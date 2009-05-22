package com.ngus.message;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import org.apache.commons.lang.StringUtils;



import com.ngus.dataengine.DBConnection;
import com.ngus.dataengine.DEException;
import com.ngus.resengine.JCRContentRepository;
import com.ns.exception.NSException;

public class MessageEngine implements IMessageEngine{
	//showStatus:0 都不显示，可以删除 ； 1 receiver不显示 ； 2 poster不显示； 3 都显示
	static Connection con = null;
	
	static private MessageEngine _instance = null;
	
	synchronized static public MessageEngine instance() throws NSException {
		if (_instance == null) {

			_instance = new MessageEngine();

			_instance.init();

		}

		return _instance;
	}
	
	
	synchronized static public void destroy() throws Throwable {
		if (_instance != null) {
			_instance = null;
			con.close();
		}
	}
	
	public void init() throws DEException {
		try {
			con=DBConnection.getConnection();
		} catch (Exception e) {
			throw new DEException("create db connection failed", e);
		}
	}
	
	private String getJcrPath(String receiveUerId , int MessageId) throws Exception{
		String strMessId = Integer.toString(MessageId);
		int mlength = strMessId.length();
		int bufferLen = 19 + mlength;
		StringBuffer buffer = new StringBuffer(bufferLen);
		buffer.append(MessageId);
		
		buffer.insert(0 , "/");
		
		buffer.insert(0,receiveUerId);
		
		String str = buffer.toString();
		return str;
		
	}
	
	private int getReceiveId(String jcrPath){
		//int length = jcrPath.length();
		int pos = jcrPath.indexOf('/',1);
		String id = jcrPath.substring(pos+1);
		int receveId = Integer.parseInt(id);
		return receveId;
		
	}
	
	private List<String> getStringList(String searchText){
		String newSearch = searchText.trim();
		String[] stringArray = newSearch.split(" ");
		int t = stringArray.length;
		List<String> stringList = new ArrayList<String>();
		for(int i =0; i< t ; i++){
			
			stringList.add(stringArray[i]);
			stringList.remove("");
		}
		
		return stringList;
		
	}
	
public static Node createPath(Node node, String path) throws Exception {
		
		path = StringUtils.removeStart(path, "/");

		String[] names = path.split("/");
		for (int i = 0; i < names.length; i++) {
			String name = names[i];
			if (name == null || name.length() == 0)
				continue;

			if (node.hasNode(name)) {
				node = node.getNode(name);
			} else {
				node = node.addNode(name);
			}
		}
		return node;
	}
	
	
	public List<MessageObject> listPostMsg(String postUserId,int start, int count) throws NSException, Exception {
		List<MessageObject> messageList = new ArrayList<MessageObject>();
		Statement stmt = null;
		MessageObject newMessage = null;
		Session session = null;
		
		
		try{
			String sql = "select messageId , receiveUserId,createTime , isReaded,postShow , receiveShow  from message where postUserId ='" + postUserId +"' and postShow = true order by createTime DESC limit "+start+", "+count;
			stmt = con.createStatement();
			ResultSet rs = null;
			try{
				rs = stmt.executeQuery(sql);
				while ( rs.next()){
					int id = rs.getInt(1);
					String receiveUserId = rs.getString(2);
					String date = rs.getString(3);
					String createTime = date.substring(0,19);
					boolean isReaded = rs.getBoolean(4);
					boolean postShow = rs.getBoolean(5);
					boolean receiveShow = rs.getBoolean(6);
					
					
					String jcrPath = MessageEngine.instance().getJcrPath(receiveUserId,id);
					
					
					try{
						session =JCRContentRepository.instance().getSession("messages");
						Node root = session.getRootNode();
						
						// reach the parent
						Node node = root.getNode(jcrPath);
						
					
			
						// get data
						Property contentProperty = node.getProperty(JCRContentRepository.NAMESPACE_PREFIX
								+ ":content");
						
						Property titleProperty = node.getProperty(JCRContentRepository.NAMESPACE_PREFIX
								+ ":title");
						
						String content = contentProperty.getString();
						String title = titleProperty.getString();
					
					
					
						newMessage = new MessageObject();
						newMessage.setCreateTime(createTime);
						newMessage.setReaded(isReaded);
						newMessage.setContent(content);
						newMessage.setTitle(title);
						newMessage.setMessageId(id);
						newMessage.setPostUserId(postUserId);
						newMessage.setReceiveUserId(receiveUserId);
						newMessage.setPostShow(postShow);
						newMessage.setReceiveShow(receiveShow);
						messageList.add(newMessage);
					}catch(Exception e){}
					finally{
						JCRContentRepository.instance().releaseSession(session,"messages");
					}		
					
				}
			}catch(SQLException e){System.err.println(e);}
			finally{
				rs.close();
			}		
		}catch(SQLException e){System.err.println(e);}
		finally{
			stmt.close();
		}
		return messageList;
	}

	public List<MessageObject> listReceiveMsg(String receiveUserId,int start,int count) throws NSException, Exception {
		List<MessageObject> messageList = new ArrayList<MessageObject>();
		Statement stmt = null;
		MessageObject newMessage = null;
		Session session = null;
		try{
			String sql = "select messageId , postUserId ,createTime , isReaded , postShow , receiveShow from message where receiveUserId ='" + receiveUserId +"' and receiveShow = true order by createTime DESC limit "+start+", "+count;
			stmt = con.createStatement();
			ResultSet rs = null;
			try{
				rs = stmt.executeQuery(sql);
				while ( rs.next()){
					int id = rs.getInt(1);
					String postUserId = rs.getString(2);
					String date = rs.getString(3);
					String createTime = date.substring(0,19);
					boolean isReaded = rs.getBoolean(4);
					boolean postShow = rs.getBoolean(5);
					boolean receiveShow = rs.getBoolean(6);
					
					
					String jcrPath = MessageEngine.instance().getJcrPath(receiveUserId,id);
					
					
					try{
						session =JCRContentRepository.instance().getSession("messages");
						Node root = session.getRootNode();
						
						// reach the parent
						Node node = root.getNode(jcrPath);
			
						// get data
						Property contentProperty = node.getProperty(JCRContentRepository.NAMESPACE_PREFIX
								+ ":content");
						
						Property titleProperty = node.getProperty(JCRContentRepository.NAMESPACE_PREFIX
								+ ":title");
						
						String content = contentProperty.getString();
						String title = titleProperty.getString();
					
					
					
						newMessage = new MessageObject();
						newMessage.setCreateTime(createTime);
						newMessage.setReaded(isReaded);
						newMessage.setContent(content);
						newMessage.setTitle(title);
						newMessage.setMessageId(id);
						newMessage.setPostUserId(postUserId);
						newMessage.setReceiveUserId(receiveUserId);
						newMessage.setPostShow(postShow);
						newMessage.setReceiveShow(receiveShow);
						messageList.add(newMessage);
					}catch(Exception e){}
					finally{
						JCRContentRepository.instance().releaseSession(session,"messages");
					}		
				}
			}catch(SQLException e){System.err.println(e);}
			finally{
				rs.close();
			}		
		}catch(SQLException e){System.err.println(e);}
		finally{
			stmt.close();
		}
		return messageList;
	}

	public MessageObject sendMsg(String postUserId, String receiveUserId,String title , String content) throws NSException, Exception {
		String sql = "insert into message ( postUserId , receiveUserId , createTime, isReaded , postShow , receiveShow) values ('"+postUserId+"','"+receiveUserId+"', now() , false , true, true)";
		Statement stmt = null; 
		MessageObject newMessage = null;
		Session session=null;
		try{
			stmt = con.createStatement() ;
			stmt.executeUpdate(sql);
			
			String selectId = "select last_insert_id() from message";
			
			ResultSet rs = null;
			try{
				rs = stmt.executeQuery(selectId);
				if(rs.next()){
				
					int id = rs.getInt(1);
					
					
					String jcrPath = MessageEngine.instance().getJcrPath(receiveUserId,id);
				
					
					try{
						
						session =JCRContentRepository.instance().getSession("messages");
						
						Node root = session.getRootNode();
					
						Node node = createPath(root,jcrPath);
						
						node.setProperty(JCRContentRepository.NAMESPACE_PREFIX
								+ ":content",content);
						
						
						
						node.setProperty(JCRContentRepository.NAMESPACE_PREFIX
								+ ":title",title);
						
						node.getSession().save();
					
						newMessage = new MessageObject();
						
						newMessage.setContent(content);
						
						newMessage.setTitle(title);
						
						newMessage.setMessageId(id);
						newMessage.setPostUserId(postUserId);
						newMessage.setReceiveUserId(receiveUserId);
					}catch(Exception e){}
					finally{
						JCRContentRepository.instance().releaseSession(session,"messages");
					}
					
				}
			}catch(SQLException e ){System.err.println(e);}
			finally{
				rs.close();
			}
		}catch(SQLException e){System.err.println(e); }
		finally{
			stmt.close();
		}
		
		
		return newMessage;
	}

	
	public MessageObject searchMsg(int messageId) throws Exception {
		
		String sql = "select  postUserId ,receiveUserId,createTime , isReaded from message where messageId ='"+messageId+"'";
		Statement stmt = null; 
		MessageObject newMessage = null;
		Session session = null;
		try{
			stmt = con.createStatement();
			
			ResultSet rs = null;
			try{
				rs = stmt.executeQuery(sql);
			
				
				
				if( rs.next()){
					String postUserId = rs.getString(1);
					
					String receiveUserId = rs.getString(2);
					String date = rs.getString(3);
					String createTime = date.substring(0,19);
					boolean isReaded = rs.getBoolean(4);
					
					
					try{
						String jcrPath = MessageEngine.instance().getJcrPath(receiveUserId,messageId);
						
						session =JCRContentRepository.instance().getSession("messages");
					
						Node root = session.getRootNode();
					
						// reach the parent
						Node node = root.getNode(jcrPath);
						
						// get data
						Property contentProperty = node.getProperty(JCRContentRepository.NAMESPACE_PREFIX
								+ ":content");
						
						Property titleProperty = node.getProperty(JCRContentRepository.NAMESPACE_PREFIX
								+ ":title");
						
						String content = contentProperty.getString();
						String title = titleProperty.getString();
						
						
						
						newMessage = new MessageObject();
						newMessage.setCreateTime(createTime);
						newMessage.setReaded(isReaded);
						newMessage.setContent(content);
						newMessage.setTitle(title);
						newMessage.setMessageId(messageId);
						newMessage.setPostUserId(postUserId);
						newMessage.setReceiveUserId(receiveUserId);
						
						
					}catch(Exception e){}
					finally{
						JCRContentRepository.instance().releaseSession(session,"messages");
					}		
					
					
					
					
				}
			}catch(SQLException e){System.err.println(e);}
			finally{
				rs.close();
			}	
		}catch(SQLException e){System.err.println(e);}
		finally{
			stmt.close();
		}
		return newMessage;
	}

	public MessageObject reply(int messageId,String content,String title) throws Exception {
		MessageObject newMessage =
			MessageEngine.instance().searchMsg(messageId);
		
		String receiveUserId = newMessage.getPostUserId();//because it's reply , the form poster will be receiver here. 
		String postUserId = newMessage.getReceiveUserId();//because it's reply , the form poster will be receiver here.
		
		
		
		MessageObject replyMessage = MessageEngine.instance().sendMsg(postUserId,receiveUserId,title,content);
		
		return replyMessage;
	}

	public MessageObject sendInvitation(String postUserId, String receiveUserId) throws NSException, Exception{
		String sql = "insert into message ( postUserId , receiveUserId ,createTime,isReaded ,postShow , receiveShow) values ('"+postUserId+"','"+receiveUserId+"',now(),false,true,true)";
		Statement stmt = null; 
		MessageObject newMessage = null;
		Session session=null;
		String title = "邀请";
		String content ="你是否愿意接受邀请？";
		try{
			stmt = con.createStatement() ;
			stmt.executeUpdate(sql);
			
			String selectId = "select max(messageId) from message where postUserId ='"+postUserId+"' and receiveUserId ='" +receiveUserId+"'";
			
			ResultSet rs = null;
			try{
				rs = stmt.executeQuery(selectId);
				if(rs.next()){
				
					int id = rs.getInt(1);
					
					String jcrPath = MessageEngine.instance().getJcrPath(receiveUserId,id);
					
					try{
						session =JCRContentRepository.instance().getSession("messages");
						Node root = session.getRootNode();
						
						Node node = createPath(root,jcrPath);
						
						node.setProperty(JCRContentRepository.NAMESPACE_PREFIX
								+ ":content",content);
						
						node.setProperty(JCRContentRepository.NAMESPACE_PREFIX
								+ ":title",title);
						
						node.getSession().save();
					
						newMessage = new MessageObject();
						newMessage.setContent(content);
						newMessage.setTitle(title);
						newMessage.setMessageId(id);
						newMessage.setPostUserId(postUserId);
						newMessage.setReceiveUserId(receiveUserId);
						
					}catch(Exception e){}
					finally{
						JCRContentRepository.instance().releaseSession(session,"messages");
					}
					
				}
			}catch(SQLException e ){System.err.println(e);}
			finally{
				rs.close();
			}
		}catch(SQLException e){System.err.println(e); }
		finally{
			stmt.close();
		}
		
		
		return newMessage;
	}


	public List<MessageObject> searchText(String receiveId , String content) throws NSException, Exception {
		
		Session session = null;
		Statement stmt = null;
		MessageObject newMessage = null;
		List<MessageObject> messageList = new ArrayList<MessageObject>();
		//try{	
		List<String> stringList = new ArrayList<String>();
		
		
		
			session =JCRContentRepository.instance().getSession("messages");
			
			QueryManager qm = session.getWorkspace().getQueryManager();
			
			Node rn = session.getRootNode();
			
			//String rnPath = rn.getCorrespondingNodePath("messages");
			//String queryStr = "//"+receiveId+"/*[jcr:contains(@"+JCRContentRepository.NAMESPACE_PREFIX+":content,'"+content+"')]";
			
			
			String str =  new String(content.getBytes("ISO8859_1"),"UTF-8");
			
			//System.out.println("test"+str);
			
			
			stringList = MessageEngine.instance().getStringList(str);
			
			int len = stringList.size();
			
			//System.out.println(content);
			//System.out.println(len);
			
			//String queryStr =  or jcr:contains(@"+JCRContentRepository.NAMESPACE_PREFIX+":content,'*g*')]";
			StringBuffer queryBuffer = new StringBuffer();
			
			for(int i = 0 ; i < len ; i++){
				if(i == 0 && i != len-1){
					queryBuffer.append("//"+receiveId+"/*[jcr:like(@"+JCRContentRepository.NAMESPACE_PREFIX+":content,'%"+stringList.get(i)+"%')");
					
				}
				else if (i ==0 && i ==len-1){
					queryBuffer.append("//"+receiveId+"/*[jcr:like(@"+JCRContentRepository.NAMESPACE_PREFIX+":content,'%"+stringList.get(i)+"%')]");
				}
				else if ( i == len-1 ){
					
					queryBuffer.append(" or jcr:like(@" + JCRContentRepository.NAMESPACE_PREFIX+":content,'%" + stringList.get(i)+"%')]");
					//System.out.println(queryBuffer.toString());
				}
				else{
					
					queryBuffer.append(" or jcr:like(@" + JCRContentRepository.NAMESPACE_PREFIX+":content,'%" + stringList.get(i)+"%')");
					//System.out.println(queryBuffer.toString());
				}
			}
			
			String queryStr = queryBuffer.toString();
			
			
			Query q =qm.createQuery(queryStr,Query.XPATH);
			
			
			QueryResult result = q.execute();
			
			NodeIterator it = result.getNodes();
			
			while (it.hasNext()) {
				
	            Node n = it.nextNode();
	           
	            String nPath = n.getCorrespondingNodePath("messages");
	            
	            int id = MessageEngine.instance().getReceiveId(nPath);
	           
	            String select = "select postUserId from message where messageId = '"+id+"' ";
	            String postId =null;
	            try{
	            	stmt = con.createStatement() ;
	            	ResultSet rs = null;
	    			try{
	    				rs = stmt.executeQuery(select);
	    				while ( rs.next()){
	    					
	    					postId = rs.getString(1);
	    					
	    				}
	    			}catch(Exception e){}
	    			finally{
	    				rs.close();
	    			}
	            }catch(Exception e){}
	            finally{
	            	stmt.close();
	            }
	            Property contentProperty = n.getProperty(JCRContentRepository.NAMESPACE_PREFIX
						+ ":content");
	            
	            Property titleProperty = n.getProperty(JCRContentRepository.NAMESPACE_PREFIX
						+ ":title");
	          
	            
	            String newContent = contentProperty.getString();
	            String newTitle = titleProperty.getString();
	            
	            newMessage = new MessageObject();
	            newMessage.setMessageId(id);
	            newMessage.setContent(newContent);
	            newMessage.setTitle(newTitle);
	            newMessage.setPostUserId(postId);
	            newMessage.setReceiveUserId(receiveId);
	            
	            messageList.add(newMessage);
	            
	       }
	//	}catch(Exception e){}
	//	finally{
		//	JCRContentRepository.instance().releaseSession(session,"messages");
		//}	
		return messageList;
	}
	
	
	public void deleteMsg(int messageId){
		String selectReceiveId = "select receiveUserId from message where messageId ='"+messageId+"'";
		String deleteMsg = "delete from message where messageId = '"+messageId+"'";
		Statement stmt = null; 
		Session session=null;
		String receiveId = null;
		try{
			//获取messageId对应的receiveId
			stmt = con.createStatement() ;
			ResultSet rs = null;
			try{
				rs = stmt.executeQuery(selectReceiveId);
				if(rs.next())
					receiveId= rs.getString(1);
				String jcrPath = null;
				jcrPath = MessageEngine.instance().getJcrPath(receiveId,messageId);
				session =JCRContentRepository.instance().getSession("messages");
				
				Node root = session.getRootNode();
				
				Node node = root.getNode(jcrPath);
				
				
				node.remove();
				
				session.save();
				
				stmt.executeUpdate(deleteMsg);
			
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
			
			finally{
				rs.close();
			}
		}catch(SQLException e){e.printStackTrace();}
		finally{
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	
	
	public void updateShowStatus(int messageId , String userShow){
		String postNotShow = "update message set postShow = false where messageId ="+messageId;
		String receiveNotShow = "update message set receiveShow = false where messageId ="+messageId;
		Statement stmt = null; 
		
		String showStatusSQL = "select count(*) from message where postShow = false and receiveShow = false and messageId = "+messageId;
		int count = 0;
		try{
			//获取messageId对应的receiveId
			stmt = con.createStatement() ;
			ResultSet rs = null;
			try{
				if(userShow.equals("post")){
					stmt.executeUpdate(postNotShow);
				}else{
					stmt.executeUpdate(receiveNotShow);
				}
				
				rs = stmt.executeQuery(showStatusSQL);
				if(rs.next()){
					count= rs.getInt(1);
				}
						
				if(count == 1 ){
					this.deleteMsg(messageId);
				}
			
				
			
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
			
			finally{
				rs.close();
			}
		}catch(SQLException e){e.printStackTrace();}
		finally{
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	
	public void updateReaded(int messageId){
		String updateReaded = "update message set isReaded = true where messageId ='"+messageId+"'";
		Statement stmt = null; 
		try{
			stmt = con.createStatement() ;
			stmt.executeUpdate(updateReaded);
		}catch(Exception e ){
			e.printStackTrace();
		}finally{
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	}
	
	
	public int getNotReadedCount(String receiveUserId){
		String getNotReadedCount = "select count(*) from message where receiveUserId='"+receiveUserId+"' and isReaded = false and receiveShow = true";
		Statement stmt = null; 
		int count = 0;
		try{
			stmt = con.createStatement() ;
			ResultSet rs = null;
			rs = stmt.executeQuery(getNotReadedCount);
			if(rs.next()){
				count = rs.getInt(1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return count;
			
	}

	public int getReceiveMsgCount(String receiveUserId){
		String getRecMsgCount = "select count(*) from message where receiveUserId='"+receiveUserId+"' and receiveShow = true";
		Statement stmt = null; 
		int count = 0;
		try{
			stmt = con.createStatement() ;
			ResultSet rs = null;
			rs = stmt.executeQuery(getRecMsgCount);
			if(rs.next()){
				count = rs.getInt(1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return count;
	}
		
	public int getPostMsgCount(String postUserId){
		String getpostMsgCount = "select count(*) from message where postUserId = '"+postUserId+"' and postShow = true";
		Statement stmt = null; 
		int count = 0;
		try{
			stmt = con.createStatement() ;
			ResultSet rs = null;
			rs = stmt.executeQuery(getpostMsgCount);
			if(rs.next()){
				count = rs.getInt(1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return count;
	}

	

}
