package com.github.wp17.lina.server.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.github.wp17.lina.common.message.IMessage;
import com.github.wp17.lina.common.message.IMsgProcessor;
import com.github.wp17.lina.common.module.IModule;
import com.github.wp17.lina.common.module.ModuleInitOrder;
import com.github.wp17.lina.common.net.LogicSession;

@Component
@Order(ModuleInitOrder.msgModule)
public class MessageModule implements IModule, CommandLineRunner {
	
	private static final Logger log = LoggerFactory.getLogger(MessageModule.class);

	@Autowired private MessageFactory messageFactory;
	
	@Override
	public void run(String... args) throws Exception {
		messageFactory.putPath("com.github.wp17.lina.net.message");
		messageFactory.init();
		log.info("MessageModule inited");
	}
	
	public IMessage getMessage(short messageId){
		return messageFactory.getMessage(messageId);
	}

	public void process(LogicSession session, IMessage message){
		IMsgProcessor processor = messageFactory.getMsgProcessor(message.getMsgID());
		if (null == processor) {
			throw new NullPointerException("the processor of msgid: "+message.getMsgID()+"is null");
		}
		processor.process(session, message);
	}
	
	@Override
	public void init() {
		
	}
	
	@Override
	public void destory() {
		
	}
}
