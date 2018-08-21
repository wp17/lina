package com.github.wp17.lina.protomessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.wp17.lina.common.module.IModule;
import com.github.wp17.lina.util.ClassUtil;
import com.google.protobuf.MessageLite;

public class ProtoMessageModule implements IModule {
	
	private ProtoMessageModule(){}
	private static final ProtoMessageModule instance = new ProtoMessageModule();
	public static ProtoMessageModule getInstance(){
		return instance;
	}

	private final Map<Class<? extends MessageLite>, Short> messages = new HashMap<Class<? extends MessageLite>, Short>();
	private final Map<Short, IProtobufMsgProcessor> processors = new HashMap<Short, IProtobufMsgProcessor>();
	@Override
	public void init() {
		List<Class<?>> clazzes = null;
		try {
			clazzes = ClassUtil.getClasses("com.github.wp17.lina.protomessage.processor", false);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		if(null != clazzes){
			for (Class<?> clazz : clazzes) {
				try {
					MessageID messageID = clazz.getAnnotation(MessageID.class);
					short msgID = messageID.value();
					IProtobufMsgProcessor processor = (IProtobufMsgProcessor) clazz.newInstance();
					processors.put(msgID, processor);
					
					MessageClazz messageClazz = clazz.getAnnotation(MessageClazz.class);
					Class<? extends MessageLite> mseeageClazz = messageClazz.value();
					messages.put(mseeageClazz, msgID);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public IProtobufMsgProcessor getProcessor(int msgID){
		return processors.get((short)msgID);
	}
	
	public short getMessageID(Class<? extends MessageLite> clazz){
		Short id = messages.get(clazz);
		if (null != id) {
			return id.shortValue();
		}
		return (short)-1;
	}

	@Override
	public void destory() {

	}
}
