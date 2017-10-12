package com.github.wp17.lina.config.data.line;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.github.wp17.lina.config.Loadable;
import com.github.wp17.lina.config.csv.CSVReader;
import com.github.wp17.lina.log.LoggerProvider;

public class LineDataProvider implements Loadable{
	private static final Logger LOGGER = LoggerProvider.getLogger(LineDataProvider.class);
	
	private LineDataProvider(){}
	private static LineDataProvider instance = new LineDataProvider();
	public static LineDataProvider getInstance() {
		return instance;
	}
	
	List<LineData> datas = new ArrayList<LineData>();
	
	@Override
	public void init() {
		datas.clear();
		
		try {
			datas.addAll(CSVReader.getBean(LineData.class));
		} catch (Exception e) {
			LoggerProvider.addExceptionLog("LineDataProvider", e);;
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
