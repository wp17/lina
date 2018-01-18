package com.github.wp17.lina.line;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import com.github.wp17.lina.config.data.line.LineData;
import com.github.wp17.lina.config.data.line.LineDataProvider;
import com.github.wp17.lina.logic.Role;
import com.github.wp17.lina.module.IModule;
import com.github.wp17.lina.module.ModuleInitOrder;
import com.github.wp17.lina.server.ServerHolder;

public class LineServerModule implements IModule {
	private LineServerModule(){}
	private static LineServerModule instance = new LineServerModule();
	public static LineServerModule getInstance() {
		return instance;
	}
	
	private final AtomicInteger num = new AtomicInteger();
	private final Map<Long, Role> roles = new ConcurrentHashMap<Long, Role>();
	private final Map<Integer, LineServer> lineServers = new HashMap<Integer, LineServer>();
	
	@Override
	public void init() {
		List<LineData> dataList = new ArrayList<LineData>();
		List<LineData> datas = LineDataProvider.getInstance().getDatas();
		for (LineData data : datas) {
			if (ServerHolder.server().getId() == data.getServer_id()) {
				dataList.add(data);
				num.incrementAndGet();
			}
		}
		
		CountDownLatch latch = new CountDownLatch(num.get());
		for (LineData data : dataList) {
			LineServer lineServer = new LineServer(data, latch);
			lineServer.startup();
			lineServers.put(lineServer.getId(), lineServer);
		}
	}

	@Override
	public void destory() {
		for (LineServer lineServer : lineServers.values()) {
			lineServer.close();
		}
	}
	
	@Override
	public int order() {
		return ModuleInitOrder.lineServerModule;
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
