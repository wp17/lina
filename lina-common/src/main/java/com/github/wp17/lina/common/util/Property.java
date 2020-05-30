package com.github.wp17.lina.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Property {
    public Property(String filePath){
        this.filePath = filePath;
    }

    private final String filePath;
    private final Properties properties = new Properties();

    public void init() throws IOException {
        InputStream inputStream;
        if (null != this.getClass()) {
            inputStream = this.getClass().getResourceAsStream("/"+filePath);
        }else {
            inputStream = ClassLoader.getSystemResourceAsStream("/"+filePath);
        }

        if (null != inputStream) {
            properties.load(inputStream);
        }else {
            throw new NullPointerException(filePath);
        }
    }

    public Properties getProperties(){
        return properties;
    }
}
