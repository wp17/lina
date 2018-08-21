package com.github.wp17.lina.server.config.provider;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.github.wp17.lina.common.log.LoggerProvider;
import com.github.wp17.lina.config.Loadable;
import com.github.wp17.lina.config.data.LineData;
import com.github.wp17.lina.server.config.ConfigLoadModule;
import com.github.wp17.lina.util.csv.CSVReader;

@Component
public class LineDataProvider implements Loadable{
	private static final Logger LOGGER = LoggerProvider.getLogger(LineDataProvider.class);
	
	private List<LineData> datas = new ArrayList<LineData>();
	
	@Override
	@PostConstruct
	public void init() {
		datas.clear();
		try {
			datas.addAll(CSVReader.getBean(LineData.class, ConfigLoadModule.parentPath));
		} catch (Exception e) {
			LoggerProvider.addExceptionLog("LineDataProvider", e);
		}
		LOGGER.info("LineDataProvider inited");
	}
	
	public List<LineData> getDatas() {
		return datas;
	}
	
	@Override
	public String path() {
		return "/config/line.csv";
	}
}
