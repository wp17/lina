package com.github.wp17.lina.line;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import com.github.wp17.lina.config.data.line.LineData;
import com.github.wp17.lina.executor.ExecutorModule;
import com.github.wp17.lina.log.LoggerProvider;
import com.github.wp17.lina.logic.Role;
import com.github.wp17.lina.message.IMessage;
import com.github.wp17.lina.message.MessageModule;

public class LineServer {
	
	private final LineData lineData;
	private final CountDownLatch countDownLatch;
	private final AtomicBoolean running = new AtomicBoolean();
	private final AtomicBoolean closed = new AtomicBoolean();
	private final Map<Long, Role> roles = new ConcurrentHashMap<Long, Role>();
	private final FrameUpdateService frameUpdateService = new FrameUpdateService();
	
	public LineServer(LineData lineData, CountDownLatch countDownLatch){
		this.lineData = lineData;
		this.countDownLatch = countDownLatch;
	}
	
	public void startup(){
		running.compareAndSet(false, true);
		ExecutorModule.getInstance().executeFUService(frameUpdateService);
	}
	
	public void close(){
		if (closed.get() || !running.get()) {
			return;
		}
		
		running.compareAndSet(true, false);
		//TODO
		closed.compareAndSet(false, true);
	}

	public int getId(){
		return lineData.getId();
	}
	
	public void addRole(Role role){
		roles.put(role.getRoleUuid(), role);
	}
	
	public void remRole(Role role){
		roles.remove(role.getRoleUuid());
	}
	
	public Role getRoleById(Long roleId){
		return roles.get(roleId);
	}
	
	private class FrameUpdateService implements Runnable{
		@Override
		public void run() {
			countDownLatch.countDown();
			while(running.get()){
				for (Role role : roles.values()) {
					processMsg(role);
				}
				
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					LoggerProvider.addExceptionLog("", e);
				}
			}
		}
	}
	
	private void processMsg(Role role){
		IMessage message = null;
		while ((message = role.getSession().pollMsg()) != null) {
			MessageModule.getInstance().process(role.getSession(), message);
		}
	}
}
