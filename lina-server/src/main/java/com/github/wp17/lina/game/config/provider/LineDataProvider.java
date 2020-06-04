package com.github.wp17.lina.game.config.provider;

import java.util.ArrayList;
import java.util.List;

import com.github.wp17.lina.config.common.ConfigMetadata;
import com.github.wp17.lina.config.common.Reload;
import com.github.wp17.lina.game.config.ConfigLoadModule;
import org.slf4j.Logger;

import com.github.wp17.lina.common.log.LoggerProvider;
import com.github.wp17.lina.config.common.Loadable;
import com.github.wp17.lina.config.template.LineTemplate;
import com.github.wp17.lina.config.reader.CSVReader;

@Reload
public class LineDataProvider implements Loadable {
    private static final Logger LOGGER = LoggerProvider.getLogger(LineDataProvider.class);

    private LineDataProvider() {
    }

    private static LineDataProvider instance = new LineDataProvider();

    public static final LineDataProvider getInstance() {
        return instance;
    }

    private volatile List<LineTemplate> templates = new ArrayList<LineTemplate>();

    @Override
    public void init() {
        try {
            templates = (CSVReader.getBean(LineTemplate.class, ConfigLoadModule.configDir));
        } catch (Exception e) {
            LoggerProvider.addExceptionLog("LineDataProvider", e);
        }
        LOGGER.debug("LineDataProvider init");
    }

    public List<LineTemplate> getTemplates() {
        return templates;
    }

    @Override
    public String path() {
        return LineTemplate.class.getAnnotation(ConfigMetadata.class).path();
    }
}
