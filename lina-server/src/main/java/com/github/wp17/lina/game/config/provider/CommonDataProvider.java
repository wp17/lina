package com.github.wp17.lina.game.config.provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.wp17.lina.config.Reload;
import com.github.wp17.lina.game.config.ConfigLoadModule;
import org.slf4j.Logger;

import com.github.wp17.lina.common.log.LoggerProvider;
import com.github.wp17.lina.config.Loadable;
import com.github.wp17.lina.config.data.CommonData;
import com.github.wp17.lina.common.csv.CSVReader;

@Reload
public class CommonDataProvider implements Loadable {
	private static final Logger LOGGER = LoggerProvider.getLogger(CommonDataProvider.class);

	private CommonDataProvider() {}
	private static CommonDataProvider instance = new CommonDataProvider();
	public static final CommonDataProvider getInstance(){
		return instance;
	}
	
	Map<Integer, Integer> datas = new HashMap<Integer, Integer>();
	
	@Override
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
		return "/csv/common.csv";
	}
}
