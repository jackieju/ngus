package com.ns.util;

import java.io.File;

import com.ns.log.Log;

public class FileUtil {

	public static void main(String v[]) {
		createPath("d:\\a\\b\\c");
	}

	static public void createPath(String path) {
		//Log.trace("create Path:" + path);
		File f = new File(path);
		String p = f.getAbsolutePath();
		if (f.exists())
			return;

		if (!f.mkdir()) {
			createPath(f.getParentFile().getAbsolutePath());
			f.mkdir();
		}

	}
}
