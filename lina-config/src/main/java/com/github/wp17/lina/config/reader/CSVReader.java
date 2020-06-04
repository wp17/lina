package com.github.wp17.lina.config.reader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import com.alibaba.fastjson.JSONObject;
import com.github.wp17.lina.config.common.ConfigMetadata;
import com.github.wp17.lina.util.StringUtil;
import com.squareup.javawriter.JavaWriter;
import lombok.Data;

import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PUBLIC;

public class CSVReader {
	public static <T> List<T> getBean(Class<T> clazz, String parentPath) throws Exception{
		ConfigMetadata configData = clazz.getAnnotation(ConfigMetadata.class);
		if (null == configData) {
			return Collections.emptyList();
		}
		
		String path = configData.path();
		if (null != parentPath) {
			path = parentPath +"csv\\" + path;
		}
		String charset = configData.charset();
		
		List<T> result = new ArrayList<T>();
		
		BufferedReader reader = null;
		try {
			
			FileReader fileReader = new FileReader(new File(path)); 
			reader = new BufferedReader(fileReader);

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

	public static void generateClass(File baseDir, String fileName, String className) throws IOException {
		FileReader fileReader = new FileReader(new File(baseDir, fileName));
		BufferedReader reader = new BufferedReader(fileReader);
		String title = reader.readLine();
		String[] titles = title.split(",");
		String typeString = reader.readLine();
		String[] types = typeString.split(",");

		StringWriter sw = new StringWriter();
		JavaWriter writer = new JavaWriter(sw);
		String basePackage = className.substring(0, className.lastIndexOf("."));
		writer.emitPackage(basePackage);
		writer.emitImports(Data.class, ConfigMetadata.class);
		writer.emitJavadoc("auto generate, do not modify");
		writer.emitAnnotation(Data.class);
		Map<String, String> map = new HashMap<>();
		map.put("path", "\"" + fileName + "\"");
		map.put("charset", "\"utf-8\"");
		writer.emitAnnotation(ConfigMetadata.class, map);
		writer.beginType(className, "class", EnumSet.of(PUBLIC));

		for (int i = 0; i < titles.length; i++) {
			String name = titles[i];
			String type = types[i];

			if (StringUtil.isEmpty(name) || StringUtil.isEmpty(type)) {
				break;
			}
			if (type.equals("string")) {
				type = "String";
			}
			if (type.equals("string[]")) {
				type = "String[]";
			}
			writer.emitField(type, name, EnumSet.of(PRIVATE));
		}
		writer.endType();

		String user_dir = "D:\\project\\lina\\lina-config\\src\\main\\java\\";
		String packageDir = className.replaceAll("\\.", "/") + ".java";
		File confFile = new File(user_dir + packageDir);
		try (OutputStreamWriter fileWriter =
					 new OutputStreamWriter(
							 new FileOutputStream(confFile.toString()), StandardCharsets.UTF_8)) {
			fileWriter.write(sw.toString());
			fileWriter.flush();
		}

	}
}

