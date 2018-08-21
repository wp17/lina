package com.github.wp17.lina.server.message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.github.wp17.lina.common.message.AMsgProcessor;
import com.github.wp17.lina.common.message.IMessage;
import com.github.wp17.lina.common.message.IMsgProcessor;
import com.github.wp17.lina.server.util.SpringUtil;
import com.github.wp17.lina.util.ClassUtil;

@Component
public class MessageFactory {
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageFactory.class);
	
	private final ThreadLocal<String> path = new ThreadLocal<String>();
	public void putPath(String value){
		path.set(value);
	}
	
	private final Map<Short, Class<? extends IMessage>> messages = new HashMap<Short, Class<? extends IMessage>>();
	private final Map<Short, IMsgProcessor> processors = new HashMap<Short, IMsgProcessor>();
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		List<Class<?>> clazzes = null;
		try {
			clazzes = ClassUtil.getClasses(path.get(), true);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		if(null != clazzes){
			for (Class<?> clazz : clazzes) {
				Class<?>[] intefaces = clazz.getInterfaces();
				for (int i = 0; i < intefaces.length; i++) {
					if (intefaces[i] == IMessage.class) {
						try {
							short id = ((IMessage)clazz.newInstance()).getMsgID();
							messages.put(id, (Class<? extends IMessage>)clazz);
							AMsgProcessor msgProcessor = clazz.getAnnotation(AMsgProcessor.class);
							if (null != msgProcessor) {
								processors.put(id, SpringUtil.getBean(msgProcessor.processor()));//msgProcessor.processor().newInstance()
							}
							break;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	public IMessage getMessage(short messageId){
		Class<? extends IMessage> clazz = messages.get(messageId);
		if (null != clazz) {
			try {
				return clazz.newInstance();
			} catch (Exception e) {
				LOGGER.error("无法创建消息{}的实例", messageId, e.getStackTrace());
			}
		}
		
		return null;
	}
	
	IMsgProcessor getMsgProcessor(short messageId){
		return processors.get(messageId);
	}
}
