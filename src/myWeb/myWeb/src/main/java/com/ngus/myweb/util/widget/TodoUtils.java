package com.ngus.myweb.util.widget;

import com.ngus.dataengine.DataEngine;
import com.ngus.dataengine.ResourceObject;
import com.ns.dataobject.Attribute;
import com.ns.dataobject.DataObject;
import java.util.ArrayList;
import java.util.List;

public class TodoUtils
{

    public TodoUtils()
    {
    }

    private static String CheckDone(ResourceObject ro)
    {
        DataObject dt = ro.getChild("rdo", 0);
        return dt.getAttr("done").getStringValue();
    }

    public static String getTodoList(String uid)
    {
        List list = new ArrayList();
        String todolist = "[";
        try
        {
            String model = "todo";
            list = DataEngine.instance().listROByUserModel(uid, model, 0, -1, true, new Attribute("total", 1, Integer.valueOf(1)));
            ResourceObject temp = null;
            String resID = null;
            String content = null;
            String isDone = "false";
            if(list != null)
            {
                for(int i = 0; i < list.size(); i++)
                {
                    temp = (ResourceObject)list.get(i);
                    resID = temp.getResId();
                    content = temp.getStringValue();
                    content = content.replace('\n', ' ');
                    isDone = CheckDone(temp);
                    todolist = (new StringBuilder()).append(todolist).append("{\"isDone\":").append(isDone).append(",\"content\":\"").append(content).append("\",\"resourceId\":\"").append(resID).append("\"}").toString();
                    if(i + 1 < list.size())
                        todolist = (new StringBuilder()).append(todolist).append(",").toString();
                }

            }
            todolist = (new StringBuilder()).append(todolist).append("]").toString();
        }
        catch(Exception e)
        {
            todolist = "[]";
        }
        return todolist;
    }

}