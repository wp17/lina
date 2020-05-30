package com.github.wp17.lina.game.auth;

public interface IAuth {
    /**
     * 验证接口，0表示验证成功，>0表示错误码
     * @param username
     * @param pwd
     * @return
     */
    AuthResult auth(String username, String pwd);
}
