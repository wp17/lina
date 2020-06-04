package com.github.wp17.lina.game.logic.manager.login;

import com.github.wp17.lina.game.auth.AuthModule;
import com.github.wp17.lina.game.auth.AuthResult;
import com.github.wp17.lina.game.auth.IAuth;
import com.github.wp17.lina.game.logic.Role;
import com.github.wp17.lina.proto.msg.LoginProto;

import java.util.Objects;

public class RoleLoginManager {
    private final Role role;

    public RoleLoginManager(Role role) {
        this.role = role;
    }

    public void processLogin(LoginProto.C2GLoginReq req) {

    }
}
