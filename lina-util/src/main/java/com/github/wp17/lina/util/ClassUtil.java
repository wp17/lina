package com.github.wp17.lina.util;

import com.google.common.collect.Lists;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassUtil {

    public static List<Class<?>> getClassesByAnnotation(String paths, boolean deep, Class aClass) throws IOException, ClassNotFoundException {
        List<Class<?>> result = getClasses(paths, deep);
        List<Class<?>> result1 = Lists.newArrayList();
        for (Class<?> clazz : result) {
            Annotation annotation = clazz.getAnnotation(aClass);
            if (Objects.nonNull(annotation)) result1.add(clazz);
        }
        return result1;
    }

    public static List<Class<?>> getClasses(String paths, boolean deep) throws IOException, ClassNotFoundException {
        if (null == paths)  return Collections.emptyList();

        String[] paths1 = paths.split(",");
        List<Class<?>> result = new ArrayList<Class<?>>();
        for (int i = 0; i < paths1.length; i++) {
            String path = paths1[i];
            if (path.endsWith(".jar")) {
                JarFile jarFile = new JarFile(path);
                return getJarClasses(jarFile, "", deep);
            }
            if (path.endsWith(".class")) {
                Class<?> clazz = null;
                try {
                    clazz = Class.forName(path.substring(0, path.lastIndexOf(".")));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (null == clazz) return Collections.emptyList();

                List<Class<?>> list = new ArrayList<Class<?>>();
                list.add(clazz);
                return list;
            }

            String temp = path.replace('.', '/');
            URL url = Thread.currentThread().getContextClassLoader().getResource(temp);
            result.addAll(getClasses(url, path, deep));
        }

        return result;
    }

    static List<Class<?>> getClasses(URL url, String packageName, boolean childPackage) throws IOException, ClassNotFoundException {
        String protocol = url.getProtocol();
        if ("file".equals(protocol)) {
            String path = url.getPath().substring(1);
            File file = new File(path);
            return getFileClasses(file, packageName, childPackage);

        } else if ("jar".equals(protocol)) {
            JarURLConnection jarConn = (JarURLConnection) url.openConnection();
            JarFile jarFile = jarConn.getJarFile();
            return getJarClasses(jarFile, packageName, childPackage);
        }

        return Collections.emptyList();
    }

    static List<Class<?>> getFileClasses(File file, String packageName, boolean childPackage) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<Class<?>>();

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory() && !childPackage) {
                    continue;
                }
                classes.addAll(getFileClasses(files[i], packageName, childPackage));
            }
        } else if (file.isFile()) {
            if (file.getName().endsWith(".class")) {
                String path = file.getPath();
                path = path.replace(Const.file_separator, ".");

                String subName = path.split(packageName)[1];
                subName = subName.substring(0, subName.length() - 6);

                String classPath = packageName + subName;
                Class<?> clazz = Class.forName(classPath);
                classes.add(clazz);
            }
        }

        return classes;
    }

    static List<Class<?>> getJarClasses(JarFile jarFile, String packageName, boolean childPackage) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<Class<?>>();

        Enumeration<JarEntry> entrys = jarFile.entries();

        while (entrys.hasMoreElements()) {
            JarEntry entry = entrys.nextElement();
            String name = entry.getName();
            if (name.endsWith(".class")) {
                String className = name.replace('/', '.');
                if (childPackage) {
                    if (className.startsWith(packageName)) {
                        Class<?> clazz = Class.forName(className.substring(0, className.lastIndexOf(".")));
                        classes.add(clazz);
                    }
                } else {
                    String classPath = className.substring(0, name.lastIndexOf("/"));
                    if (classPath.equals(packageName)) {
                        Class<?> clazz = Class.forName(className.substring(0, className.lastIndexOf(".")));
                        classes.add(clazz);
                    }
                }
            }
        }
        return classes;
    }
}
