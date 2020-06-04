package com.github.wp17.lina.game.module.db;

import java.io.*;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ShardMain {
    public static void main(String[] args) throws ClassNotFoundException, IOException, DocumentException {
        String genPath = "D:\\lina\\lina\\lina-server\\src\\main\\resources\\mybatisGen\\generatorConfig.xml";
        shard(genPath);
    }

    public static void shard(String genPath) throws IOException, DocumentException, ClassNotFoundException {
        SAXReader reader = new SAXReader();
        Document doc = reader.read(new File(genPath));
        Element element = doc.getRootElement();
        List<Element> contexts = element.elements("context");
        for (Element context : contexts) {
            shard(context);
        }
    }

    public static void shard(Element context) throws IOException, DocumentException, ClassNotFoundException {
        Element modelGenerator = context.element("javaModelGenerator");
        String modelPackage = modelGenerator.attributeValue("targetPackage");
        String modelTargetProject = modelGenerator.attributeValue("targetProject");

        Element xmlGenerator = context.element("sqlMapGenerator");
        String xmlPackage = xmlGenerator.attributeValue("targetPackage");
        String xmlTargetProject = xmlGenerator.attributeValue("targetProject");

        Element javaClientGenerator = context.element("javaClientGenerator");
        String javaClientPackage = javaClientGenerator.attributeValue("targetPackage");
        String javaClientTargetProject = javaClientGenerator.attributeValue("targetProject");

        @SuppressWarnings("unchecked")
        Iterator<Element> tables = context.elementIterator("table");

        while (tables.hasNext()) {
            Element table = tables.next();

            String tableName = table.attributeValue("tableName");
            String objectName = table.attributeValue("domainObjectName");

//            buildShardingModel(objectName, modelPackage, modelTargetProject);

            String xmlPath = System.getProperty("user.dir") + "/lina-server/" + xmlTargetProject +"/"+
                    xmlPackage.replace('.', '/') + "/" + objectName + "Mapper.xml";
            File xmlFile = new File(xmlPath);
            modifyXml(xmlFile, xmlPath, tableName);

            String javaClientPath = System.getProperty("user.dir") + "/lina-server/" + javaClientTargetProject +"/"+
                    javaClientPackage.replace('.', '/') + "/" + objectName + "Mapper.java";
            File javaClientFile = new File(javaClientPath);
            modifyJavaClient(javaClientFile, javaClientPath, tableName);
        }
    }

    private static void modifyJavaClient(File javaClientFile, String javaClientPath, String tableName) throws IOException {
        if (javaClientFile.isFile()) {
            BufferedReader reader = new BufferedReader(new FileReader(javaClientFile));
            StringBuilder builder = new StringBuilder();
            String code = null;
            while ((code = reader.readLine()) != null) {
                if (code.contains("()"))
                    code = code.replace("()", "(@Param(\"tableId\") Integer tableId)");
                else if (code.contains("Long id"))
                    code =  code.replace("Long id",
                            "@Param(\"id\") Long id, @Param(\"tableId\") Integer tableId");
                builder.append(code).append(System.lineSeparator());

                if (code.contains("package "))
                    builder.append("import org.apache.ibatis.annotations.Param;").append(System.lineSeparator());
            }
            reader.close();
            if (javaClientFile.exists()) javaClientFile.delete();

            FileOutputStream outputStream = new FileOutputStream(new File(javaClientPath));

            String newCode = builder.toString();
            outputStream.write(newCode.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
        }
    }

    private static void modifyXml(File xmlFile, String xmlPath, String tableName) throws IOException, DocumentException, ClassNotFoundException {
        if (xmlFile.isFile()) {
            FileInputStream inputStream = new FileInputStream(xmlFile);

            StringBuilder builder = new StringBuilder();
            byte[] bytes = new byte[1024 * 64];
            int i = 0;
            while ((i = inputStream.read(bytes)) != -1) {
                String string = new String(bytes, 0, i, "UTF-8");
                builder.append(string);
            }
            inputStream.close();

            if (xmlFile.exists()) xmlFile.delete();

            FileOutputStream outputStream = new FileOutputStream(new File(xmlPath));
            String newCode = builder.toString().replaceAll(tableName, tableName + "_#{tableId}")
                    .replaceAll("parameterType=\"java\\.lang.*?\"", "")
                    ;
            outputStream.write(newCode.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
        }
    }

    private static void buildShardingModel(String domainObjectName, String modelPackage, String modelTargetProject) throws IOException {
        String className = domainObjectName + "Sharding";
        String type = modelPackage + "." + domainObjectName;
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

        String LogicPathname = System.getProperty("user.dir") + "/lina-server/" +
                modelTargetProject + "/" + modelPackage.replace('.', '/') + "/" + className + ".java";

        File logicFile = new File(LogicPathname);
        if (logicFile.exists())  logicFile.delete();

        if (logicFile.createNewFile()) {
            FileOutputStream outputStream2 = new FileOutputStream(logicFile);
            outputStream2.write(logicBuilder.toString().getBytes("UTF-8"));
            outputStream2.flush();
            outputStream2.close();
        }
    }
}
