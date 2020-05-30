package com.github.wp17.lina.game.logic.manager.login;

import com.github.wp17.lina.game.auth.AuthModule;
import com.github.wp17.lina.game.auth.AuthResult;
import com.github.wp17.lina.game.auth.IAuth;
import com.github.wp17.lina.game.logic.Role;
import com.github.wp17.lina.proto.msg.LoginProto;

import java.util.Objects;

public class RoleLoginManager {
    private Role role;

    public RoleLoginManager(Role role) {
        this.role = role;
    }

    public void processLogin(LoginProto.C2GLoginReq req) {
        String username = req.getUsername();
        String pwd = req.getPassword();
        int platform = req.getPlatform();
        PlatformType platformType = PlatformType.getByType(platform);
        IAuth auth = AuthModule.getInstance().getByType(platformType);
        if (Objects.isNull(auth)) {
            LoginProto.G2CLoginResp.Builder resp = LoginProto.G2CLoginResp.newBuilder();
            resp.setResult(1);
            resp.setTips("渠道验证失败");
            role.sendMsg(resp.build());
            role.downLine();
            return;
        }

        AuthResult result = auth.auth(username, pwd);
        if (result.getResult() != 0) {
            LoginProto.G2CLoginResp.Builder resp = LoginProto.G2CLoginResp.newBuilder();
            resp.setResult(result.getResult());
            resp.setTips(result.getTips());
            role.sendMsg(resp.build());
            role.downLine();
            return;
        }
        role.verified(true);
    }
}
