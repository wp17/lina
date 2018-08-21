package com.github.wp17.lina.util;

import java.io.File;

public class FileUtil {
	public static long lastModifyTime(String pathname){
		File file = new File(pathname);
		return file.lastModified();
	}
}
