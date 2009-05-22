package com.ngus.myweb.util.widget;

import com.ngus.dataengine.DataEngine;
import com.ngus.dataengine.ResourceObject;
import com.ns.dataobject.Attribute;
import com.ns.dataobject.DataObject;
import java.util.ArrayList;
import java.util.List;

public class StockUtils
{

    public StockUtils()
    {
    }

    private static String CheckDone(ResourceObject ro)
    {
        DataObject dt = ro.getChild("rdo", 0);
        return dt.getAttr("done").getStringValue();
    }

    public static String getStockList(String uid)
    {
        List list = new ArrayList();
        String stockList = "[";
        try
        {
            String model = "stock";
            list = DataEngine.instance().listROByUserModel(uid, model, 0, -1, true, new Attribute("total", 1, Integer.valueOf(1)));
            ResourceObject temp = null;
            String resID = null;
            String code = null;
            String isChoose = "false";
            if(list != null)
            {
                for(int i = 0; i < list.size(); i++)
                {
                    temp = (ResourceObject)list.get(i);
                    resID = temp.getResId();
                    code = temp.getStringValue();
                    code = code.replace('\n', ' ');
                    code = code.trim();
                    isChoose = CheckDone(temp);
                    stockList = (new StringBuilder()).append(stockList).append("{\"isChoose\":").append(isChoose).append(",\"code\":\"").append(code).append("\",\"resId\":\"").append(resID).append("\"}").toString();
                    if(i + 1 < list.size())
                        stockList = (new StringBuilder()).append(stockList).append(",").toString();
                }

            }
            stockList = (new StringBuilder()).append(stockList).append("]").toString();
        }
        catch(Exception e)
        {
            stockList = "[]";
        }
        return stockList;
    }
}