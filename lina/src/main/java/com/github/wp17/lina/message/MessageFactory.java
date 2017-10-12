package com.github.wp17.lina.message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.wp17.lina.util.ClassUtil;

public class MessageFactory {
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageFactory.class);
	
	private MessageFactory(){}
	private static final MessageFactory INSTANCE = new MessageFactory();
	public static MessageFactory getFactory(){
		return INSTANCE;
	}

	private final ThreadLocal<String> path = new ThreadLocal<String>();
	public void putPath(String value){
		path.set(value);
	}
	
	private final Map<Short, Class<? extends IMessage>> messages = new HashMap<Short, Class<? extends IMessage>>();
	private final Map<Short, IMsgProcessor> processors = new HashMap<Short, IMsgProcessor>();
	
	@SuppressWarnings("unchecked")
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
								processors.put(id, msgProcessor.processor().newInstance());
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
	
	IMessage getMessage(short messageId){
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
