package com.ngus.comment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.Session;

import org.apache.commons.lang.StringUtils;

import com.ngus.dataengine.DBConnection;
import com.ngus.dataengine.DEException;
import com.ngus.resengine.JCRContentRepository;
import com.ns.exception.NSException;



public class CommentEngine implements ICommentEngine{
	
	static Connection con = null; //@Todo using DataEngine.getConn...
	
	static private CommentEngine _instance = null;
	
	synchronized static public CommentEngine instance() throws NSException {
		if (_instance == null) {

			_instance = new CommentEngine();

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
	
	
	private String getJcrPath(long comId) throws Exception
	{
		StringBuffer buffer = new StringBuffer(23);
		buffer.append(comId);
		int strlen = buffer.length();
		for ( int f = 0 ; f< 18-strlen ; f++)
		{
			buffer.insert(0,"0");
		}
		int i ;
		for ( i = 1; i <= 5 ; i++)
		{
			int temp = 4*i -1 ;
			buffer.insert(temp , "/");
		}
		String str = buffer.toString();
		return str;
		
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
		
	
	
	
	public CommentObject getCommentById(long comId) throws Exception {
		// TODO Auto-generated method stub
		
		// convert commentid from integer to jcr path
		String jcrPath = CommentEngine.instance().getJcrPath(comId);
	
		// TODO put db operation in try-catch-finally block
		
		CommentObject newCom = null;
		Statement stmt = null;
		Session session = null;
		try{	
			// TODO write columns name instead of '*'
			String sql = "select resId , user ,createTime from comment where id ='" + comId +"'";
			stmt = con.createStatement() ;
			ResultSet rs = null;
			try{
				rs = stmt.executeQuery(sql);
				while (rs.next())
				{
					
					String resId = rs.getString(1);// use int as argument
					String user = rs.getString(2); // use int as argument
					String date = rs.getString(3); // use int as argument
					String truedate = date.substring(0,19);
				
					// get comments content from JCR repository
	
					// get workspace root
					try{
						session =JCRContentRepository.instance().getSession("comments");
						Node root = session.getRootNode();
						
						
						// reach the parent
						//Node node = createPath(root, jcrPath);
						Node node = root.getNode(jcrPath);
			
						// get data
						Property p = node.getProperty(JCRContentRepository.NAMESPACE_PREFIX
								+ ":content");
						
						String content = p.getString();
						
						// create comment object and set data
						newCom = new CommentObject();
						newCom.setCommentId(comId);
						newCom.setResourceId(resId); 
						newCom.setUser(user);		
						newCom.setContent(content);
						newCom.setCreateTime(truedate);
					}catch(Exception e){}
					finally{
						JCRContentRepository.instance().releaseSession(session,"comments");
					}
				}
			}catch(SQLException e){}
			finally{
				rs.close();
			}
			
		}catch(SQLException e){}
		finally
		{			
			stmt.close();
		}
		
		return newCom;
	}

	public List<CommentObject> listCommentsForRes(String resId , int position , int count) throws Exception{
		// TODO Auto-generated method stub
		List<CommentObject> comList = new ArrayList<CommentObject>();
		Statement stmt = null;
		CommentObject newCom = null;
		Session session = null;
		try{
			
			String sql = "select id , user , createTime from comment where resId ='" + resId +"' order by id desc limit "+position+", "+count;
			stmt = con.createStatement();
			ResultSet rs = null;
			try{
				rs = stmt.executeQuery(sql);
				while ( rs.next()){
					
					long id = rs.getLong(1);// use int as argument
					String user = rs.getString(2); // use int as argument
					String date = rs.getString(3); // use int as argument
					String truedate = date.substring(0,19);
					
					//get jcrPath by comment id
					String jcrPath = CommentEngine.instance().getJcrPath(id);
					
					//get workspace root
					try{
						session =JCRContentRepository.instance().getSession("comments");
						Node root = session.getRootNode();
						
						// reach the parent
						Node node = root.getNode(jcrPath);
			
						// get data
						Property p = node.getProperty(JCRContentRepository.NAMESPACE_PREFIX
								+ ":content");
						
						String content = p.getString();
						
						newCom = new CommentObject();
						newCom.setCommentId(id);
						newCom.setResourceId(resId);
						newCom.setUser(user);
						newCom.setContent(content);
						newCom.setCreateTime(truedate);
					    comList.add(newCom);
					}catch(Exception e){}
					finally{
						JCRContentRepository.instance().releaseSession(session,"comments");
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

		return comList;
	}

	public List<CommentObject> listCommentsForUser(String users , int position,int count) throws Exception{
//		 TODO Auto-generated method stub
		List<CommentObject> comList = new ArrayList<CommentObject>();
		Statement stmt = null;
		long id;
		String resId ;
		String jcrPath;
		String content;
		String date;
		CommentObject newCom = null;
		String truedate;
		Session session = null;
		try{
			stmt = con.createStatement() ;
			String sql = "select id , resId, createTime from comment where user ='" + users+"' order by id desc limit "+ position+", "+count;
			ResultSet rs = null;
			try{
				rs = stmt.executeQuery(sql);
				while ( rs.next()){
					
					id = rs.getLong(1);// use int as argument
					resId = rs.getString(2); // use int as argument
					date = rs.getString(3); // use int as argument
					truedate = date.substring(0,19);
					
			
					
					jcrPath = CommentEngine.instance().getJcrPath(id);
					
					//get workspace root
					try{
						session =JCRContentRepository.instance().getSession("comments");
						Node root = session.getRootNode();
					
						// reach the parent
						Node node = createPath(root,jcrPath);
					
						// get data
						Property p = node.getProperty(JCRContentRepository.NAMESPACE_PREFIX
								+ ":content");
					
						content = p.getString();
						System.out.println(content);
					
						newCom = new CommentObject();
						newCom.setCommentId(id);
						//System.out.println("id:"+newCom.getCommentId());
						newCom.setResourceId(resId);
						//System.out.println("resId:"+newCom.getResourceId());
						newCom.setUser(users);
						//System.out.println("id:"+newCom.getUser());
						newCom.setContent(content);
						newCom.setCreateTime(truedate);
					    comList.add(newCom);
						}catch(Exception e){}
						finally{
							JCRContentRepository.instance().releaseSession(session,"comments");
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
		
		return comList;
	}

	public void addComment(String content,String resId,String userId) throws Exception{
		// get user Message
		//User user = (User) UserManager.getCurrentUser();
		//String userId = user.getUserId();
//		String userId = "sean";
		//long start = System.currentTimeMillis();
		String sql = "insert into comment ( resId , user, createTime) values ('"+resId+"','"+userId+"',now())";
		Statement stmt = null; 
		Session session = null;
		try{
			stmt = con.createStatement() ;
			stmt.executeUpdate(sql);
			
			
			String selectId = "select max(id) from comment where resId ='"+resId+"'";
			
			ResultSet rs = null;
			try{
				rs = stmt.executeQuery(selectId);
				if(rs.next()){
				
					long id = rs.getLong(1);
					
					String jcrPath = CommentEngine.instance().getJcrPath(id);
					
					try{
						session =JCRContentRepository.instance().getSession("comments");
						Node root = session.getRootNode();
				
						// reach the parent
						Node node = createPath(root,jcrPath);
						
						node.setProperty(JCRContentRepository.NAMESPACE_PREFIX
								+ ":content",content);
						node.getSession().save();
					}catch(Exception e){}
					finally{
						JCRContentRepository.instance().releaseSession(session,"comments");
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

	}

	public int getResTotalnum(String value) throws Exception {
		String sql ="select count(id) from comment where resId='"+value+"'";
		Statement stmt = null;
		int num = -1;//if return -1 means error
		try{
			stmt=con.createStatement();
			ResultSet rs = null;
			try{
				rs = stmt.executeQuery(sql);
				while ( rs.next() )
				{
					num = rs.getInt(1);// use int as argument
				}
			}catch(SQLException e ){ System.out.println(e.getMessage());}
			finally{
				rs.close();
				
			}
		}catch(SQLException e ){System.out.println(e);}
		finally{
			stmt.close();
			
		}
		return num;
	}

	public int getUserTotalnum(String value) throws Exception {
		String sql ="select count(id) from comment where user='"+value+"'";
		Statement stmt = null;
		int num = -1;//if return -1 means error
		try{
			stmt=con.createStatement();
			ResultSet rs = null;
			try{
				rs = stmt.executeQuery(sql);
				while ( rs.next() )
				{
					num = rs.getInt(1);// use int as argument
				}
			}catch(SQLException e ){ System.out.println(e.getMessage());}
			finally{
				rs.close();
				
			}
		}catch(SQLException e ){System.out.println(e);}
		finally{
			stmt.close();
			
		}
		return num;
	}

	public List<CommentObject> getNewComment(int count) throws Exception {
		List<CommentObject> comList = new ArrayList<CommentObject>();
		Statement stmt = null;
		CommentObject newCom = null;
		Session session = null;
		try{
			
			String sql = "select id ,resId, user , createTime from comment order by createTime desc limit 0, "+count;
			stmt = con.createStatement();
			ResultSet rs = null;
			try{
				rs = stmt.executeQuery(sql);
				while ( rs.next()){
					
					long id = rs.getLong(1);// use int as argument
					String resId = rs.getString(2); // use int as argument
					String user = rs.getString(3);
					String date = rs.getString(4); // use int as argument
					String truedate = date.substring(0,19);
					
					//get jcrPath by comment id
					String jcrPath = CommentEngine.instance().getJcrPath(id);
					
					//get workspace root
					try{
						session =JCRContentRepository.instance().getSession("comments");
						Node root = session.getRootNode();
						
						// reach the parent
						Node node = root.getNode(jcrPath);
			
						// get data
						Property p = node.getProperty(JCRContentRepository.NAMESPACE_PREFIX
								+ ":content");
						
						String content = p.getString();
						String content2 = new String(content.getBytes("ISO8859_1"),"UTF-8");
						newCom = new CommentObject();
						newCom.setCommentId(id);
						newCom.setResourceId(resId);
						newCom.setUser(user);
						newCom.setContent(content2);
						newCom.setCreateTime(truedate);
					    comList.add(newCom);
					}catch(Exception e){}
					finally{
						JCRContentRepository.instance().releaseSession(session,"comments");
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

		return comList;
	}


}
