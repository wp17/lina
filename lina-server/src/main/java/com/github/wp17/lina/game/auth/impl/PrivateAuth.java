package com.github.wp17.lina.game.auth.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.wp17.lina.game.auth.AuthResult;
import com.github.wp17.lina.game.auth.AuthType;
import com.github.wp17.lina.game.auth.IAuth;
import com.github.wp17.lina.common.log.LoggerProvider;
import com.github.wp17.lina.game.logic.manager.login.PlatformType;
import com.github.wp17.lina.util.encrypt.EncryptUtils;

import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@AuthType(PlatformType.Private)
public class PrivateAuth implements IAuth {
    private final String host = "http://127.0.0.1";
    private final int port = 8080;
    private final String path = "/user/auth/verify";
    private String appId = "";

    private String appToken = "";

    @Override
    public AuthResult auth(String username, String pwd) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(host + ":" + port + path);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            String authInfo = buildAuthInfo(username, pwd);
            connection.setConnectTimeout(30000);

            connection.setDoOutput(true);
            connection.getOutputStream().write(authInfo.getBytes(StandardCharsets.UTF_8));
            connection.getOutputStream().close();

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] buff = new byte[1024];
            int len = -1;
            while ((len = connection.getInputStream().read(buff)) != -1) {
                buffer.write(buff, 0, len);
            }
            connection.getInputStream().close();

            String resultString = buffer.toString();

            JSONObject result = JSONObject.parseObject(resultString);
            int resultCode = result.getInteger("code");
            if (resultCode == 0) {
                return new AuthResult(0, "success");
            }else {
                return new AuthResult(1, "fail");
            }

        } catch (Exception e) {
            LoggerProvider.addExceptionLog("自有渠道验证异常", e);
        }finally {
            if (Objects.nonNull(connection)) connection.disconnect();
        }
        return new AuthResult(3, "exception");
    }

    private String buildAuthInfo(String username, String pwd) throws Exception {
        JSONObject object = new JSONObject();
        object.put("username", username);
        object.put("token", pwd);
        object.put("appId", appId);

        long tick = System.currentTimeMillis();
        object.put("tick", tick);

        String checkSumString = new StringBuilder()
                .append(username).append(appId).append(tick).append(appToken).toString();
        String checksum = EncryptUtils.SHAAndBase64(checkSumString);
        object.put("checksum", checksum);

        return object.toJSONString();
    }
}
