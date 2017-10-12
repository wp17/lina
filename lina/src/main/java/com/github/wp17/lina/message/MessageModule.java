package com.github.wp17.lina.message;

import com.github.wp17.lina.log.LogModule;
import com.github.wp17.lina.message.msgs.TestMessage;
import com.github.wp17.lina.module.IModule;
import com.github.wp17.lina.module.ModuleInitOrder;
import com.github.wp17.lina.net.connection.LogicSession;

public class MessageModule implements IModule {
	private MessageModule(){}
	private static MessageModule instance;
	public static MessageModule getInstance(){
		if (null == instance) {
			synchronized (MessageModule.class) {
				if (null == instance) {
					instance = new MessageModule();
				}
			}
		}
		return instance;
	}
	
	public IMessage getMessage(short messageId){
		return MessageFactory.getFactory().getMessage(messageId);
	}

	public void process(LogicSession session, IMessage message){
		IMsgProcessor processor = MessageFactory.getFactory().getMsgProcessor(message.getMsgID());
		if (null == processor) {
			throw new NullPointerException("the processor of msgid: "+message.getMsgID()+"is null");
		}
		
		processor.process(session, message);
	}
	
	@Override
	public void init() {
		MessageFactory.getFactory().putPath("com.peter.lina.message");
		MessageFactory.getFactory().init();
	}
	
	@Override
	public void destory() {
		
	}
	
	@Override
	public int order() {
		return ModuleInitOrder.msgModule;
	}
	
	public static void main(String[] args) {
		LogModule.getInstance().init();
		MessageFactory.getFactory().putPath("com.peter.lina.message");
		MessageFactory.getFactory().init();
		
		TestMessage testMessage = new TestMessage();
		testMessage.setId(1);
		testMessage.setAge((byte) 28);
		testMessage.setName("peter");
		
		MessageFactory.getFactory().getMsgProcessor((short) 1).process(null, testMessage);
	}
}
