package com.github.wp17.lina.config.csv;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.wp17.lina.util.ClassUtil;

public class CSVReader {
	private static Map<String, Model> configs = new HashMap<String, Model>();
	
	public static void init(List<String> packagePaths, String parentPath) throws ClassNotFoundException, IOException{
		for (String pn : packagePaths) {
			List<Class<?>> classes = ClassUtil.getClasses(pn, true);
			
			classes.stream().forEach(clazz -> {
				Path path = clazz.getAnnotation(Path.class);
				if (null != path) {
					String p = path.value();
					if (null != parentPath) {
						p = parentPath+p;
					}
					
					Charset charset = clazz.getAnnotation(Charset.class);
					Model model = new Model(clazz, p, (null == charset ? "UTF-8" : charset.value()));
					configs.put(clazz.getName(), model);
				}
			});
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> getBean(Class<T> clazz) throws Exception{
		Model model = configs.get(clazz.getName());
		if (null == model) {
			return Collections.emptyList();
		}
		clazz = (Class<T>) model.clazz;
		List<T> result = new ArrayList<T>();
		try(FileReader fileReader = new FileReader(new File(model.csvpath));BufferedReader reader = new BufferedReader(fileReader);) {
			String title = reader.readLine();
			String[] titles = title.split(",");
			
			reader.readLine();
			reader.readLine();
			
			String line = "";
			while((line = reader.readLine()) != null){
				
				line = new String(line.getBytes(), model.charset);
				String[] datas = line.split(",");
				if (datas.length != titles.length) {
					throw new Exception("atas.length != titles.length");
				}
				
				T t = clazz.newInstance();
				for (int i = 0; i < datas.length; i++) {
					String col = titles[i].trim().toLowerCase();
					if (!col.equals("note")) {
						Field field = clazz.getDeclaredField(col);
						Class<?> ft = field.getType();
						
						field.setAccessible(true);
						
						String data = datas[i].trim();
						Object value = cast(ft, data);
						field.set(t, value);
					}
				}
				result.add(t);
			}
		} 
		
		return result;
	}
	
	private static Object cast(Class<?> type, String value){
		if (type == Byte.class || type == byte.class) {
			return Byte.valueOf(value);
		}
		
		if (type == Short.class || type == short.class) {
			return Short.valueOf(value);
		}
		
		if (type == Integer.class || type == int.class) {
			return Integer.valueOf(value);
		}
		
		if (type == Long.class || type == long.class) {
			return Long.valueOf(value);
		}
		
		if (type == Float.class || type == float.class) {
			return Float.valueOf(value);
		}
		
		if (type == Double.class || type == double.class) {
			return Double.valueOf(value);
		}
		
		if (type == Character.class || type == char.class) {
			return value.charAt(0);
		}
		
		return value;
	}
	
	private static final class Model {
		private Class<?> clazz;
		private String csvpath;
		private String charset;
		
		public Model(Class<?> clazz, String csvpath, String charset) {
			this.clazz = clazz;
			this.csvpath = csvpath;
			this.charset = charset;
		}
	}
}

