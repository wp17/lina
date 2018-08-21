package com.github.wp17.lina.server.line;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.github.wp17.lina.common.module.IModule;
import com.github.wp17.lina.common.module.ModuleInitOrder;
import com.github.wp17.lina.config.data.LineData;
import com.github.wp17.lina.server.config.provider.LineDataProvider;
import com.github.wp17.lina.server.logic.Role;
import com.github.wp17.lina.server.util.SpringUtil;

@Component
@Order(ModuleInitOrder.lineServerModule)
public class LineServerModule implements IModule, CommandLineRunner{
	private final AtomicInteger num = new AtomicInteger();
	private final Map<Long, Role> roles = new ConcurrentHashMap<Long, Role>();
	private final Map<Integer, LineServer> lineServers = new HashMap<Integer, LineServer>();
	@Autowired private LineDataProvider lineDataProvider;
	
	@Override
	public void run(String... args) throws Exception {
		List<LineData> dataList = new ArrayList<LineData>();
		List<LineData> datas = lineDataProvider.getDatas();
		for (LineData data : datas) {
//			if (ServerHolder.server().getId() == data.getServer_id()) {
				dataList.add(data);
				num.incrementAndGet();
//			}
		}
		
		CountDownLatch latch = new CountDownLatch(num.get());
		for (LineData data : dataList) {
			LineServer lineServer = SpringUtil.getBean(LineServer.class);
			lineServer.setCountDownLatch(latch);
			lineServer.setLineData(data);
			lineServer.startup();
			lineServers.put(lineServer.getId(), lineServer);
		}
	}
	
	@Override
	public void init() {
		
	}

	@Override
	@PreDestroy
	public void destory() {
		for (LineServer lineServer : lineServers.values()) {
			lineServer.close();
		}
	}
	
	public void addRole(Role role){
		roles.put(role.getRoleUuid(), role);
		LineServer lineServer = selectServer(role.getRoleUuid());
		lineServer.addRole(role);
		role.setLineServer(lineServer);
	}
	
	public void remRole(Role role){
		roles.remove(role.getRoleUuid());
		role.getLineServer().remRole(role);
		role.setLineServer(null);
	}
	
	public Role getRoleById(Long roleId){
		return roles.get(roleId);
	}
	
	private LineServer selectServer(Long roleId){
		int index = (int) (roleId % num.get());
		return lineServers.get(index);
	}
}
