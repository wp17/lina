package com.github.wp17.lina.zone.register;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RegisterItem {
    private ServerTye serverTye;
    private String host;
    private int port;

    public String genKey() {
        return host+":"+port;
    }
}
