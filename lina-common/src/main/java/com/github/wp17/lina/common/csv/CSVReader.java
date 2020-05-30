package com.github.wp17.lina.common.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

public class CSVReader {
	public static <T> List<T> getBean(Class<T> clazz, String parentPath) throws Exception{
		ConfigData configData = clazz.getAnnotation(ConfigData.class);
		if (null == configData) {
			return Collections.emptyList();
		}
		
		String path = configData.path();
		if (null != parentPath) {
			path = parentPath + path;
		}
		String charset = configData.charset();
		
		List<T> result = new ArrayList<T>();
		
		BufferedReader reader = null;
		try {
			
			FileReader fileReader = new FileReader(new File(path)); 
			reader = new BufferedReader(fileReader);
//			String cs = reader.readLine();
//			String[] css = cs.split(",");
			
			String title = reader.readLine();
			String[] titles = title.split(",");
			
			reader.readLine();
			reader.readLine();
			
			String line = "";
			while((line = reader.readLine()) != null){
				line = new String(line.getBytes(), charset);
				String[] datas = line.split(",");
				if (datas.length != titles.length) {
					continue;
				}
				
				JSONObject object = new JSONObject();
				for (int i = 0; i < datas.length; i++) {
					String key = titles[i].trim();
					if (key.equals("remarks")) {
						continue;
					}
					
//					String readable = css[i].trim();
//					if (!readable.contains("s") && !readable.contains("S")) {
//						continue;
//					}
					
					String value = datas[i].trim();
					object.put(key, value);
				}
				T t = JSONObject.toJavaObject(object, clazz);
				result.add(t);
			}
		} finally {
			if(null != reader) {
				reader.close();
			}
		}
		return result;
	}
}

