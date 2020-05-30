package com.github.wp17.lina.util;

import java.util.Objects;
import java.util.Properties;

public class PropertyUtil {
    public static Properties copy(Properties src, String prefix) {
        if (StringUtil.isEmpty(prefix)) return src;
        String prefix1 = prefix + ".";
        Properties prop = new Properties();
        if (Objects.nonNull(src)) {
          src.stringPropertyNames().forEach(key ->{
              if (key.startsWith(prefix1)) {
                  prop.put(key.replaceFirst(prefix1, ""), src.getProperty(key));
              }
          });
        }
        return prop;
    }
}
