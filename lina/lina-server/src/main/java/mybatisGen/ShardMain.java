package mybatisGen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ShardMain {
	private static final String src = "/src/main/java/";
	
	public static void main(String[] args) throws ClassNotFoundException, IOException, DocumentException {
		String genPath = "D:/selfworkspace/simple/core/src/main/java/mybatisGen/roleGeneratorConfig.xml";
		shard(genPath);
	}
	public static void shard(String genPath) throws IOException, DocumentException, ClassNotFoundException {
		SAXReader reader = new SAXReader();
		Document doc = reader.read(new File(genPath));
		
		Element element = doc.getRootElement();
		Element context = element.element("context");
		
		Element javaModelGenerator = context.element("javaModelGenerator");
		String modelPackage = javaModelGenerator.attributeValue("targetPackage");
		
		Element sqlMapGenerator = context.element("sqlMapGenerator");
		String mapperPackage = sqlMapGenerator.attributeValue("targetPackage");
		
		@SuppressWarnings("unchecked")
		Iterator<Element> tables =  context.elementIterator("table");
		
		while (tables.hasNext()) {
			Element table = tables.next();
			String tableName = table.attributeValue("tableName");
			String domainObjectName = table.attributeValue("domainObjectName");
			
			String mapperPath = System.getProperty("user.dir")+src+mapperPackage.replace('.', '/')+"/"+domainObjectName+"Mapper.xml";
			File mapper = new File(mapperPath);
			
			if (mapper.isFile()) {
				FileInputStream inputStream = new FileInputStream(mapper);
				
				StringBuilder builder = new StringBuilder();
				byte[] bytes = new byte[1024 * 64];
				int i = 0;
				while ((i = inputStream.read(bytes)) != -1) {
					String string = new String(bytes, 0, i, "UTF-8");
					builder.append(string);
				}
				inputStream.close();
				
				FileOutputStream outputStream = new FileOutputStream(mapper);
				outputStream.write(builder.toString().replaceAll(tableName, tableName+"_#{tableId}").replaceAll("parameterType=\"java\\.lang.*?\"", "").getBytes("UTF-8"));
				outputStream.flush();
				outputStream.close();
				
				String className = domainObjectName+"Sharding";
				String type = modelPackage+"."+domainObjectName;
				StringBuilder logicBuilder = new StringBuilder();
				
				logicBuilder
				.append("package ").append(modelPackage).append(";\n\n")
				.append("import ")
				.append(type).append(";\n\n")
				.append("public class ")
				.append(className).append(" extends ").append(domainObjectName)
				.append(" {\n    private int tableId;\n\n")
				.append("    public int getTableId() {\n        return tableId;\n    }\n\n")
				.append("    public void setTableId(int tableId) {\n        this.tableId = tableId;\n    }\n")
				.append("}");
				
				String LogicPathname = System.getProperty("user.dir")+src+modelPackage.replace('.', '/')+"/"+className+".java";
				File logicFile = new File(LogicPathname);
				if (logicFile.exists()) {
					logicFile.delete();
				}
				
				if(logicFile.createNewFile()){
					FileOutputStream outputStream2 = new FileOutputStream(logicFile);
					outputStream2.write(logicBuilder.toString().getBytes("UTF-8"));
					outputStream2.flush();
					outputStream2.close();
				}
			}
		}
	}
		
}
