package com.github.wp17.lina.common.msg;

import com.github.wp17.lina.common.net.AbstractPacket;
import com.github.wp17.lina.common.net.AbstractSession;

/**stateless*/
public interface IMsgProcessor {
	void process(AbstractSession session, AbstractPacket packet);
}
