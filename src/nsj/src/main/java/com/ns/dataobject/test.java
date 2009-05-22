package com.ns.dataobject;


public class test{
	/*
	public class FetcherA  implements DOFetcher {
		int count = 100;
		public DataObject fetch(Attribute Key){
		
			if (Key == null)
				{
					Log.error(this+"::fetch(fetch): invalid parameter");
					return null;
				}

			DataObject ret = new DataObject("aa");
			try{
			

			ret.addAttr(Key);
			ret.addAttr(new Attribute("data", Attribute.ATTR_DT_INT, new Integer(count)));
			count++;
			}catch (Exception e){
				Log.log(e);
			}

			return ret;
		}
		public DataObjectList fetchAll(){
			return null;
		}

	}

*/
static public void main(String args[])throws Exception{
	//FetcherA f = new test().new FetcherA(); 
//	DataObjectCache c = new DataObjectCache(10, "key", f, 50000);
//	DataObject ob = c.get(new Attribute("key", Attribute.ATTR_DT_INT, new Integer(1)), true);
//	//Log.log(ob.print());
//	ob = c.get(new Attribute("key", Attribute.ATTR_DT_INT, new Integer(1)));
//	Log.log(ob.print());
//	ob = c.get(new Attribute("key", Attribute.ATTR_DT_INT, new Integer(1)));
//	Log.log(ob.print());
//	ob = c.get(new Attribute("key", Attribute.ATTR_DT_INT, new Integer(1)), true);
//	ob = c.get(new Attribute("key", Attribute.ATTR_DT_INT, new Integer(1)));
//	Log.log(ob.print());

//	Thread.sleep(10000);
//	Log.trace("notify");
//	c.NotifyRefresh();

	DataObject a = new DataObject("father");
	DataObject b = new DataObject("child");
	DataObjectList do_list = new  DataObjectList();
	do_list.add(b);
	a.addChildren(do_list);
	System.out.println(a.printXML());
	
	
}


}
