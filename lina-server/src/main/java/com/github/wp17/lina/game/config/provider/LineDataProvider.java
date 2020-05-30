package com.github.wp17.lina.game.config.provider;

import java.util.ArrayList;
import java.util.List;

import com.github.wp17.lina.config.Reload;
import com.github.wp17.lina.game.config.ConfigLoadModule;
import org.slf4j.Logger;

import com.github.wp17.lina.common.log.LoggerProvider;
import com.github.wp17.lina.config.Loadable;
import com.github.wp17.lina.config.data.LineData;
import com.github.wp17.lina.common.csv.CSVReader;

@Reload
public class LineDataProvider implements Loadable{
	private static final Logger LOGGER = LoggerProvider.getLogger(LineDataProvider.class);

	private LineDataProvider() {}
	private static LineDataProvider instance = new LineDataProvider();
	public static final LineDataProvider getInstance(){
		return instance;
	}
	
	private List<LineData> datas = new ArrayList<LineData>();
	
	@Override
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
		return "/csv/line.csv";
	}
}
