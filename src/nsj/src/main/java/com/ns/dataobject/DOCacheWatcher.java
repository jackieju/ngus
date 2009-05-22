/*
 * Created on Dec 31, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ns.dataobject;
import com.ns.log.Log;


/**
 * @author Jackie Ju
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class DOCacheWatcher extends Thread{
	
		protected DataObjectCache m_cache;
		protected int nIntervalTime = 100;
		
		public DOCacheWatcher(DataObjectCache cache, int interval){
			m_cache = cache;
			nIntervalTime = interval;
		}
		
		public void run(){
			while (true) {
				try {
				m_cache.RefreshData();
				
				
					sleep(nIntervalTime);
				} catch (Exception e) {
					Log.log(e);
				}


				
				// for test
						// for test
		//DataObjectList testlist = m_cache.FetchAll();
		/*
		if (testlist != null)
		{
			Log.trace(this+"---------\n");
			Log.trace("size="+testlist.size());
			for (int i = 0; i < testlist.size(); i++)
				Log.trace(testlist.get(i).print());
			Log.trace("---------\n");
		}*/
			}
		}
		
		public void setInterval(int interval){
			Log.log(this+"::setInterval(int) set interval time to " + interval);
			nIntervalTime = interval;
		}
		
}
