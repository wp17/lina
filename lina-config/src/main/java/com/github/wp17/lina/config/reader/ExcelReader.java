package com.github.wp17.lina.config.reader;

import com.github.wp17.lina.config.common.ConfigMetadata;
import com.github.wp17.lina.config.excel.ExcelSheet;
import com.github.wp17.lina.config.excel.ExcelWorkbook;
import com.squareup.javawriter.JavaWriter;
import gnu.trove.impl.sync.TSynchronizedIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.EnumSet;

import static javax.lang.model.element.Modifier.*;

public class ExcelReader {

    public static TSynchronizedIntObjectMap load(Class<?> clazz, File baseDir) throws Exception {
        String fileName = clazz.getAnnotation(ConfigMetadata.class).path();
        File file = new File(baseDir, fileName);

        int sheetIndex = 0;
        int fieldRow = 4;
        int row = 5;
        int col = 0;

        Field[] fields = clazz.getDeclaredFields();
        ExcelWorkbook workbook = new ExcelWorkbook(file);
        ExcelSheet st = workbook.getSheet(sheetIndex);

        TSynchronizedIntObjectMap templates = new TSynchronizedIntObjectMap(new TIntObjectHashMap());
        do {
            String idStr = st.getString(row, col);
            if (StringUtils.isEmpty(idStr)) {
                break;
            }
            Object instance = clazz.newInstance();
            Field idField = clazz.getDeclaredField("Id");
            idField.setAccessible(true);
            idField.setInt(instance, Integer.parseInt(idStr));
            for (int j = col + 1; j < fields.length; j++) {
                Field field = fields[j];
                field.setAccessible(true);
                if (!field.getName().equals(st.getString(fieldRow, j).trim())) {
                    throw new RuntimeException(String.format("%s类与表结构不匹配,请重新生成类文件!!!", clazz.getSimpleName()));
                }
                switch (field.getType().getSimpleName()) {
                    case "int":
                        field.setInt(instance, st.getInt(row, j));
                        break;
                    case "float":
                        field.setFloat(instance, st.getFloat(row, j));
                        break;
                    case "double":
                        field.setDouble(instance, st.getDouble(row, j));
                        break;
                    case "String":
                        field.set(instance, st.getString(row, j));
                        break;
                    case "int[]":
                        field.set(instance, st.getInts(row, j));
                        break;
                    case "float[]":
                        field.set(instance, st.getFloats(row, j));
                        break;
                    case "String[]":
                        field.set(instance, st.getStrings(row, j, ","));
                        break;
                    default:
                        throw new RuntimeException("不支持的类型!!!");
                }
            }
            row++;
            templates.put(Integer.parseInt(idStr), instance);
        } while (true);
        workbook.close();
        return templates;
    }

    /**
     * 根据excel配置文件生成java类
     *
     * @param baseDir   文件目录
     * @param fileName  文件名
     * @param className 生成类名
     */
    public static void generateClass(File baseDir, String fileName, String className) throws IOException {
        int sheetIndex = 0;
        int docRow = 2;
        int typeRow = 3;
        int fieldRow = 4;
        int col = 0;

        StringWriter sw = new StringWriter();
        JavaWriter writer = new JavaWriter(sw);
        String basePackage = className.substring(0, className.lastIndexOf("."));
        writer.emitPackage(basePackage);
        writer.emitImports(ConfigMetadata.class);
        writer.emitImports(Data.class);
        writer.emitJavadoc("auto generate, do not modify");
        writer.emitAnnotation(Data.class);
        writer.emitAnnotation(ConfigMetadata.class, "path = \"" + fileName + "\"");
        writer.beginType(className, "class", EnumSet.of(PUBLIC));
        ExcelWorkbook workbook = new ExcelWorkbook(new File(baseDir, fileName));
        ExcelSheet st = workbook.getSheet(sheetIndex);
        do {
            String doc = st.getString(docRow, col);
            String name = st.getString(fieldRow, col);
            String type = st.getString(typeRow, col);
            if (StringUtils.isEmpty(name) || StringUtils.isEmpty(type)) {
                break;
            }
            if (type.equals("string")) {
                type = "String";
            }
            if (type.equals("string[]")) {
                type = "String[]";
            }
            if (!StringUtils.isEmpty(doc)) {
                doc = doc.replaceAll("%", "%%");
                writer.emitJavadoc(doc);
            }
            writer.emitField(type, name, EnumSet.of(PRIVATE));
            col++;
        } while (true);

        writer.endType();
        workbook.close();

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
