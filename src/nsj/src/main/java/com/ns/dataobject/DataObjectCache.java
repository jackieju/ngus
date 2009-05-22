/*
 * Created on Dec 10, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ns.dataobject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import com.ns.log.Log;
/**
 * @author i027910
 * One general cache manager for data object
 */
public class DataObjectCache {
    
	private HashMap fg_list; // list to read only if bLoadAll = true, this node value si DataObject and key is Attribute
	 
	// list to read/write if bLaodAll = false
	private ArrayList timely_list = new ArrayList();       // node list order by time
	private DOFetcher dataFetcher;                         // data fetcher, must implement interface DOFetcher
	private boolean bCacheAll = false;                     // cache all data ?

	//private HashMap dual_buffer[];
	private HashMap bg_list;                               // list to write if bLoadAll = true
	private DOCacheWatcher m_watcher = null;               // the watcher object, who will monitor and update the data list

	//	private final static int DEFAULT_REFRESH_INTERVAL = 10000;
	
	private int max_size;                      // max size of node list
	private String[] key;                        // the name of key attributes

	private boolean m_bSync = false;           // the data is sync with source or not
	private boolean m_bReloadAll = true;       // the indicator to reload all data for each node

	private String DOName;


	/**
	 * consturctor
	 * @param max_length	max cache size
	 * @param Key	the name of key attribute
	 * @param f		data fetcher
	 * @throws DOException
	 */
	public DataObjectCache(
			String doName,
		int max_length,
		String[] Key,
		DOFetcher f,
		int refreshInterval)
		 {
		Log.trace(getClass(), "DataObject", this +"::DataObjectCache(int, String, DOFetcher): enter");

		if (f == null)
			throw new IllegalArgumentException("The DOFetcher object is null");
		
		doName = this.DOName;
		bCacheAll = max_length < 0;
		max_size = max_length;
		fg_list = new HashMap();

		key = Key;
		dataFetcher = f;
		//	dual_buffer = new HashMap[2];

		//list = dual_buffer[0];

		m_watcher = new DOCacheWatcher(this, refreshInterval);
		m_watcher.start();
		Log.trace(getClass(), "DataObject", this +"::DataObjectCache(int, String, DOFetcher): leave");
	}

	public void setRefrshInterval(int interval) {
		m_watcher.setInterval(interval);
	}

	/**
	 * release reousece and stop watching thread
	 * @throws Throwable
	 */
	public void destroy() throws Throwable {
		if (m_watcher != null) {

			Log.log(getClass(), "DataObject", "destroy watcher " + m_watcher + "...");

			m_watcher.destroy();
			
			m_watcher.join();
            
			//m_watcher.stop();

			Log.log(getClass(), "DataObject", "destroy watcher " + m_watcher + " successfully.");
		}
	}

	protected void finalize() throws Throwable {
		destroy();
	}

	/**
	 * get data by key, if can not get data in cache, will get data from data source
	 * @param Key
	 * @return
	 */
	public DataObject get(Attributes Keys) {

		Log.trace(getClass(), "DataObject", this +"::get(): enter ");

		DataObject ret;

		// find in list
		ret = (DataObject) fg_list.get(Keys.getStringValue());

		// if not found, search it outside cache and add it to cache
		if (ret == null && !bCacheAll) {

			Log.trace(getClass(), "DataObject", this +"::get(): get from outside cache ");
			DataObject new_ob = getNew(Keys);

			if (new_ob == null)
				ret = null;
			else {
				Log.trace(getClass(), "DataObject", new_ob.printXML());
				try {
					ret = new_ob;
					put(new_ob, true, true);
				} catch (Exception e) {
					e.printStackTrace();
					Log.error(getClass(), "DataObject", e);
					ret = null;
				}

			}
		}
		if (ret != null && !bCacheAll) {
			// mark it as the lastest accessed node
			updateTimeList(ret);

		}
		Log.trace(getClass(), "DataObject", this +"::get(): leave ");
		return ret;
	}

	/**
	 * get data by key
	 * @param Key	key attribute
	 * @param bNew	force to get newest data or not
	 * @return
	 */
	public DataObject get(Attributes Key, boolean bNew) throws DOException {

		Log.trace(getClass(), "DataObject", this +"enter ");

		if (!bNew) {
			Log.trace(getClass(), "DataObject", this +"leave ");
			return get(Key);
		}

		DataObject ret;

		// get new data
		DataObject new_ob = getNew(Key);
		if (new_ob == null) {
			Log.error(getClass(), "DataObject", "get fresh data failed");
			return null;
		}

		//		// find in list
		//		ret = (DataObject) fg_list.get(Key.getStringValue());
		//
		//		if (ret != null) { // if already exists, remove it
		//			// remove the old one
		//			remove(ret);
		//
		//		}

		// put the new one
		put(new_ob, true, true);

		Log.trace(getClass(), "DataObject", this +"leave ");
		return new_ob;
	}

	/**
	 * remove one  element from cache
	 *
	 */
	protected void removeOne() {
		// remove last one
		DataObject ob = null;
		synchronized (timely_list) {
			synchronized (fg_list) {
				if (timely_list.size() > 0) {
					ob =
						(DataObject) timely_list.remove(timely_list.size() - 1);
					if (ob != null)
						fg_list.remove(ob.getAttr(key).getStringValue());
				} else {
					Log.warning(getClass(), "DataObject", 
						"timely_list is alreay empty. But force to remove one on the header");
					fg_list.remove(fg_list.keySet().iterator().next());
				}
			}
		}
	}

	/**
	 * remove one  element from cache
	 *
	 */
	protected void remove(DataObject ob) {
		if (ob == null) {
			Log.error(getClass(), "DataObject", "null point parameter");
			return;
		}

		synchronized (fg_list) {
			// remove last one
			fg_list.remove(ob.getAttr(key).getStringValue());
		}
		synchronized (timely_list) {
			timely_list.remove(ob);
		}
	}

	/**
	 * put data into cache
	 * @param e
	 * @throws DOException
	 */
	private void put(DataObject e, boolean bFg, boolean bBg) throws DOException {

		if (bFg == false && bBg == false)
			return;

		if (!bCacheAll) { // if no load all, remove the oldest one
			synchronized (fg_list) {
				while (fg_list.size() > max_size && fg_list.size() > 0)
					removeOne();
			}
		}

		if (e == null) {
			Log.error(getClass(), "DataObject", this +"::put() parameter e is null");
			return;
		}

		Attributes KeyAttribute = e.getAttr(key);
		if (KeyAttribute == null) {
			Log.error(getClass(), "DataObject", this +"::put() get attribute by name " + key + " failed");
			return;
		}

		// for testing
		Log.trace(getClass(), "DataObject", e.printHtml());

		if (fg_list != null && bFg)
			synchronized (fg_list) {

				fg_list.put(KeyAttribute.getStringValue(), e);
			}

		if (bg_list != null && bBg)
			synchronized (bg_list) {
				bg_list.put(KeyAttribute.getStringValue(), e);
			}

		if (!bCacheAll)
			updateTimeList(e);
	}

	/**
	 * Get DataObject by key outside of cache, this method should be overridden by child class
	 * @param key
	 * @return
	 */
	protected DataObject getNew(Attributes Keys) {
		DataObject ob = dataFetcher.fetch(Keys);
		//Log.trace(getClass(), "DataObject", ob.printHtml());
		return ob;
	}

	/**
	 * Update all data in cache
	 *
	 */
	public void RefreshData() {

		
		synchronized (this) {
			m_bSync = true;

		}

		Log.trace(getClass(), "DataObject", 
			this
				+ "Starting Refresh Data...(key="
				+ key
				+ ", max_size="
				+ max_size
				+ ", cacheAll="
				+ bCacheAll);
		//int i  = 0;
		if (!bCacheAll) { 
            
            // only cache visited item
			synchronized (fg_list) {
				try {
					Vector remove_list = new Vector();
					Vector put_list = new Vector();

					Iterator it = fg_list.entrySet().iterator();
					while (it.hasNext()) {

						DataObject ob =
							(DataObject) ((Map.Entry) it.next()).getValue();

						// get key attr
						Attributes attrs = ob.getAttr(key);

						// get update
						DataObject e = getNew(attrs);

						if (e == null) { // if not available							
							//this.remove(ob);	
							remove_list.add(ob);
						} else {							
							// put to cache
							//list.put(attr.getStringValue(), e);
							put_list.add(e);

						}
						
					}
                    
					// remove node which not exist
					for (int i = 0; i < remove_list.size(); i++) {
						Log.trace(getClass(), "DataObject", "remove node " + i);
						this.remove((DataObject) remove_list.get(i));
					}

					// update node
					for (int i = 0; i < put_list.size(); i++) {
						DataObject ob = (DataObject) put_list.get(i);
						Attributes attrs = ob.getAttr(key);
						if (attrs == null) {
							Log.error(getClass(), "DataObject", "get key attribute failed");
							continue;
						}
						Log.trace(getClass(), "DataObject", "update node " + i);
						fg_list.put(attrs.getStringValue(), ob);
					}

				} catch (Exception e) {
					Log.error(getClass(), "DataObject", e);
				}
				//	Log.trace(getClass(), "DataObject", this+"::RefreshData(): refresh " + i + "node");
			}
			Log.trace(getClass(), "DataObject", this +"Refreshing Data stopped.");
		} else // if cache all items, then fetch all data
			{
                
			// get the header list with key
			DataObjectList headerList = null;

            headerList = dataFetcher.fetchKeyList();
			if (headerList == null)
				return;

	

			//HashMap bg_list = new HashMap();
			bg_list = new HashMap();
			Log.trace(getClass(), "DataObject", "bg_list = " + bg_list + "; fg_list = " + fg_list);
			
//print all node, for debug			
//          Iterator it = fg_list.values().iterator();
//			DataObject ob1;
//			while (it.hasNext()) {
//				ob1 = (DataObject) (it.next());
//				Log.trace(getClass(), "DataObject", ob1.print());
//			}	
			
			// put into bg cache
			for (int i = 0; i < headerList.size(); i++) {
				// get one node from header list
				DataObject ob = headerList.get(i);
                // get its key attribute
				Attributes KeyAttributes = ob.getAttr(key);
				if (KeyAttributes == null) {
					Log.error(getClass(), "DataObject", 
						this +" get attribute by name " + key + " failed");
					continue;
				}
				DataObject new_ob = null;

				// reload or update data of  this node
				try {
					if (m_bReloadAll) { // load all nodes in header list								
						 Log.trace(getClass(), "DataObject", "reloading node " + i);
						//	new_ob = get(KeyAttribute, true); } // update fg and bg list
						new_ob = dataFetcher.fetch(KeyAttributes);
                        if (new_ob == null){
                            Log.warning(getClass(), "get object by attribute " + KeyAttributes.printHtml()+" return null");
                            continue;
                        }
						synchronized (bg_list) {
						bg_list.put(KeyAttributes.getStringValue(), new_ob);
						}

						Log.trace(getClass(), "DataObject", 
							"load object with KeyAttribute: "
								+ KeyAttributes.getName()
								+ "="
								+ KeyAttributes.getStringValue());
						//Log.trace(getClass(), "DataObject", new_ob.print());
					} else { // update

						// to see whether it is a new one
						DataObject obFg = null;
						if (fg_list != null)
							obFg =
								(DataObject) fg_list.get(
									KeyAttributes.getStringValue());
						else
							Log.trace(getClass(), "DataObject", "fg_list is null");
						if (obFg == null) // new added, reload all of its data
							{
							Log.trace(getClass(), "DataObject", 
								"node "
									+ i
									+ " is new one, key: "
									+ KeyAttributes.getName()
									+ "="
									+ KeyAttributes.getStringValue());
							new_ob = dataFetcher.fetch(KeyAttributes);
							//Log.trace(getClass(), "DataObject", new_ob.print());
							if (new_ob == null) {
								Log.error(getClass(), "DataObject", "fetch data faild");
								continue;
							}
							synchronized (bg_list) {
							bg_list.put(KeyAttributes.getStringValue(), new_ob);
							}
						} else { // existing node, refresh partial data
							Log.trace(getClass(), "DataObject", "update node " + i);
							new_ob = dataFetcher.refresh(KeyAttributes);
							
							if (new_ob == null) {
								//Log.error(getClass(), "DataObject", "get refresh data faild");								
							}
							else
							{
								//Log.trace(getClass(), "DataObject", new_ob.print());
								obFg.update(new_ob);
								//Log.trace(getClass(), "DataObject", new_ob.print());
							}						
							synchronized (bg_list) {	
							bg_list.put(KeyAttributes.getStringValue(), obFg);
							}
						}

					}

				} catch (Exception e) {
					Log.error(getClass(), "DataObject", e);
				}

				//	Log.trace(getClass(), "DataObject", new_ob.printHtml());

			}
			Log.trace(getClass(), "DataObject", "bg_list = " + bg_list + "; fg_list = " + fg_list);
            
			// flip buffer
			fg_list = bg_list;
			bg_list = null;

	
            
			if (m_bReloadAll)
				m_bReloadAll = false;

			Log.trace(getClass(), "DataObject", this +"Refreshing Data stopped.");
		}

		//Log.trace(getClass(), "DataObject", this+"::RefreshData(): leave ");
		

	}

	/**
	 * notify Cache to refresh data
	 *
	 */
	public void NotifyRefresh() {
		synchronized (this) {
			m_bSync = false;
			m_bReloadAll = true; // reload all data
			notifyAll();
		}
	}
	//	/**
	//	 * get all data whether in cache or not
	//	 * @return
	//	 */
	//	public DataObjectList FetchAll(){
	//		return dataFetcher.fetchAll();
	//	}

	/**
	 * get all data 
	 * @return
	 */
	public DataObjectList getAll() {
		if (!bCacheAll)
			return dataFetcher.fetchAll();

		// get from cache
		DataObjectList ret = new DataObjectList();
		synchronized (fg_list) {
			Collection col = fg_list.values();
			if (col == null)
				return null;
			Iterator it = col.iterator();

			while (it.hasNext())
				ret.add((DataObject) it.next());
		}
		return ret;
	}

	private void updateTimeList(DataObject ob) {
		synchronized (timely_list) {
			// find the obj in time list
			int i = timely_list.indexOf(ob);
			if (i >= 0) // found, remove it
				{
				timely_list.remove(i);
			}
			//			if (i < 0) // not found, simply add it to the header
			//				{
			//				timely_list.add(0, ob);
			//				return;
			//			}
			// then add it to the header
			timely_list.add(0, ob);
		}
	}

	/**
	 * 
	 * @author Jackie Ju
	 * the watcher will update the cache timely
	 */
	public class DOCacheWatcher extends Thread {

		protected DataObjectCache m_cache;
		protected int nIntervalTime = 100;
		private int toStopEvent = 0;

		public DOCacheWatcher(DataObjectCache cache, int interval)
			{
			if (cache == null) {
				throw new IllegalArgumentException("the cache for watcher is null");
			}
			nIntervalTime = interval;
			m_cache = cache;
		}

		public void run() {
			synchronized (m_cache) {
				while (true) {
					if (toStopEvent != 0)
						return;
					try {

						//update data firstly, then wait
						if (m_bSync == false) {
							Log.trace(getClass(), "DataObject", "refresh");
						
							m_cache.RefreshData();
							
						}
						Log.trace(getClass(), "DataObject", "end refresh");

						m_bSync = false;

						Log.trace(getClass(), "DataObject", "wait " + nIntervalTime);
						// wait other event to update data
						m_cache.wait(nIntervalTime);

						//	sleep(nIntervalTime);
						/*
						Log.trace(getClass(), "DataObject", "wait " + nIntervalTime);
						m_cache.wait(nIntervalTime);
						if (m_bSync == false) {
							Log.trace(getClass(), "DataObject", "refresh");
							m_cache.RefreshData();
						}
						Log.trace(getClass(), "DataObject", "end refresh");
						m_bSync = false;*/
						//	sleep(nIntervalTime);
					} catch (Exception e) {
						Log.log(getClass(), "DataObject", e);
					}
				}
				
			}
		}

		public void setInterval(int interval) {
			Log.log(getClass(), "DataObject", 
				this +"::setInterval(int) set interval time to " + interval);
			nIntervalTime = interval;
		}

		public void destroy() {
			toStopEvent = 1;
		}
		
	

	}
}
