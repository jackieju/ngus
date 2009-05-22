package com.ngus.resengine;

import com.ns.log.Log;

public class ResourceId {
	static public void main(String v[])throws Exception{
		System.out.println(new ResourceId("text://ngus/jcr/tree/myweb/-00000000000000001#27").getUser());
	}

	public String resId;

	public String storageType;

	public String storagePath;

	public long id;

	public String protocol;

	public String host;

	public String jcrWorkspace;

	public String jcrPath;

	public String user;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getJcrPath() {
		return jcrPath;
	}

	public void setJcrPath(String jcrPath) {
		this.jcrPath = jcrPath;
	}

	public String getJcrWorkspace() {
		return jcrWorkspace;
	}

	public void setJcrWorkspace(String jcrWorkspace) {
		this.jcrWorkspace = jcrWorkspace;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getStoragePath() {
		return storagePath;
	}

	public void setStoragePath(String storagePath) {
		this.storagePath = storagePath;
	}

	public String getStorageType() {
		return storageType;
	}

	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}

	public String print() {

		String s = "protocol: " + protocol + "\n" + "host:  " + host + "\n"
				+ "storageType: " + storageType + "\n" + "storagePath: "
				+ this.storagePath + "\n" + "Id: " + id + "\n"
				+ "jcrWorkspace: " + jcrWorkspace + "\n" + "jcrPath: "
				+ jcrPath + "\n";

		return s;
	}

	public String getUser() {
		return user;
	}

	public ResourceId(String rid) throws Exception {
		if (rid == null || rid.length() == 0)
			throw new Exception("invalid resource id (null or empty string)");

		// example http://ngus/jcr/tree/clipboard/67/68/#69
		resId = rid;
		int index = rid.indexOf("://"); // ://
		protocol = rid.substring(0, index);
		int index2 = rid.indexOf((int) '/', index + 3); // /jcr/tree/clipboard/67/68/#69
		host = rid.substring(index + 3, index2);
		do {
			index = index2 + 1;
		} while (rid.charAt(index) == '/'); // jcr/...

		index2 = rid.indexOf((int) '/', index); // /tree/...
		if (index2 < 0) {
			index2 = rid.indexOf((int) '#', index);
			storageType = rid.substring(index, index2);
			storagePath = "/";
		} else {
			storageType = rid.substring(index, index2);
			do {
				index = index2 + 1;
			} while (rid.charAt(index) == '/'); // tree/
			index2 = rid.indexOf((int) '#', index); // #69
			if (index2 < 0)
				throw new Exception("invalid resource id '"+rid+"'");
			storagePath = "/" + rid.substring(index, index2); // tree/clipboard/67/68
			while (storagePath.charAt(storagePath.length() - 1) == '/')
				// remove '/' on tail
				storagePath = storagePath
						.substring(0, storagePath.length() - 1);
		}
		this.id = new Long(rid.substring(index2 + 1, rid.length()).trim())
				.longValue();

		Log.trace("index id = " + this.id);
		if (storageType.equalsIgnoreCase(IResEngine.STORAGETYPE_JCR)) {
			Log.trace("storagePath=" + storagePath);
			int index3 = storagePath.indexOf((int) '/', 1);
			if (index3 < 0) // no path, like "/tree"
			{
				jcrWorkspace = storagePath.substring(1, storagePath.length());
				jcrPath = "/";
			} else { // has path, like "/tree/a/b"
				jcrWorkspace = storagePath.substring(1, index3);
				jcrPath = storagePath.substring(index3, storagePath.length());
				index3 = jcrPath.indexOf((int) '/', 1);
				if (index3 > 0) {
					user = jcrPath.substring(index3, jcrPath.length());

					index3 = user.indexOf((int) '/', 1);
					if (index3 > 0)
						user = user.substring(1, index3);
					else 
						user = user.substring(1, user.length());
				}

			}
		}
		Log.trace("jcrPath = " + this.id);
		jcrPath += "/" + this.id;

	}

	public String getResId() {
		return resId;
	}

	// static public String getJCRWorkspace(String path){
	// int index = path.indexOf((int)'/', 1);
	// return path.substring(1, index);
	// }

}
