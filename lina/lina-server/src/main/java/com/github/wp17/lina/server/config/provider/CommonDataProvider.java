package com.github.wp17.lina.server.config.provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.github.wp17.lina.common.log.LoggerProvider;
import com.github.wp17.lina.config.Loadable;
import com.github.wp17.lina.config.data.CommonData;
import com.github.wp17.lina.server.config.ConfigLoadModule;
import com.github.wp17.lina.util.csv.CSVReader;

@Component
public class CommonDataProvider implements Loadable {
	private static final Logger LOGGER = LoggerProvider.getLogger(CommonDataProvider.class);
	
	Map<Integer, Integer> datas = new HashMap<Integer, Integer>();
	
	@Override
	@PostConstruct
	public void init() {
		datas.clear();
		try {
			List<CommonData> dataList = CSVReader.getBean(CommonData.class, ConfigLoadModule.parentPath);
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
	
	@Override
	public String path() {
		return "/config/common.csv";
	}
}
