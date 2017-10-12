package com.github.wp17.lina.config.data.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.github.wp17.lina.config.Loadable;
import com.github.wp17.lina.config.csv.CSVReader;
import com.github.wp17.lina.log.LoggerProvider;

public class CommonDataProvider implements Loadable {
	
	private static final Logger LOGGER = LoggerProvider.getLogger(CommonDataProvider.class);
	
	private CommonDataProvider(){}
	private static CommonDataProvider instance = new CommonDataProvider();
	public static CommonDataProvider getInstance() {
		return instance;
	}
	
	@Override
	public String path() {
		return "/config/common.csv";
	}
	
	Map<Integer, Integer> datas = new HashMap<Integer, Integer>();
	
	@Override
	public void init() {
		datas.clear();
		try {
			List<CommonData> dataList = CSVReader.getBean(CommonData.class);
			for (CommonData data : dataList) {
				datas.put(data.getId(), data.getValue());
			}
		} catch (Exception e) {
			LOGGER.info("CommonDataProvider.init()", e);
		}
	}
	
	public int getValue(Integer key){
		return datas.get(key);
	}
}
