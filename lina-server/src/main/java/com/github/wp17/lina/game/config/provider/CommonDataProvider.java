package com.github.wp17.lina.game.config.provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.wp17.lina.config.common.ConfigMetadata;
import com.github.wp17.lina.config.common.Reload;
import com.github.wp17.lina.config.template.CommonTemplate;
import com.github.wp17.lina.game.config.ConfigLoadModule;
import org.slf4j.Logger;

import com.github.wp17.lina.common.log.LoggerProvider;
import com.github.wp17.lina.config.common.Loadable;
import com.github.wp17.lina.config.reader.CSVReader;

@Reload
public class CommonDataProvider implements Loadable {
	private static final Logger LOGGER = LoggerProvider.getLogger(CommonDataProvider.class);

	private CommonDataProvider() {}
	private static CommonDataProvider instance = new CommonDataProvider();
	public static final CommonDataProvider getInstance(){
		return instance;
	}
	
	private volatile Map<Integer, Integer> map = new HashMap<Integer, Integer>();
	
	@Override
	public void init() {
		Map<Integer, Integer> temp = new HashMap();
		try {
			List<CommonTemplate> dataList = CSVReader.getBean(CommonTemplate.class, ConfigLoadModule.configDir);
			for (CommonTemplate data : dataList) {
				temp.put(data.getId(), data.getValue());
			}
		} catch (Exception e) {
			LOGGER.info("CommonDataProvider.init()", e);
		}
		map = temp;
	}
	
	public int getValue(Integer key){
		return map.get(key);
	}
	
	@Override
	public String path() {
		return CommonTemplate.class.getAnnotation(ConfigMetadata.class).path();
	}
}
