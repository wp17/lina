package com.github.wp17.lina.client.module.msg;

import com.github.wp17.lina.common.msg.CommMsgId;

public class ClientMsgId extends CommMsgId {
    public static final int g2c_handshake_syn = 0;
    public static final int c2g_handshake_ack = 1;
    
    public static final int c2g_login_req = 10001;
    public static final int g2c_login_resp = 10002;
}
