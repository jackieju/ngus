package com.ngus.myweb.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class TagUtils {
	
	public static List<String> tagAnalyse(String tags){		
		return Arrays.asList(tags.split("\\s"));
	}
	
	public static String tagsToString(List<String> tags){
		//List<String> s = Arrays.asList(tags);
		StringBuffer buffer = new StringBuffer();
        Iterator iter = tags.iterator();
        while (iter.hasNext()) {
            buffer.append(iter.next());
            if (iter.hasNext()) {
                buffer.append(" ");
            }
        }
        return buffer.toString();
	}
}
