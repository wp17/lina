package com.github.wp17.lina.server.db.provider;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.org.wgpg.lina.db.model.role.UserAuth;

@Component
public class UserAuthTest {
	@Autowired private UserAuthDataProvider provider;
	
//	@PostConstruct
	public void test() {
		for (int i = 0; i < 20; i++) {
			UserAuth userAuth = new UserAuth();
			userAuth.setId((long) i);
			userAuth.setPlatform(0);
			userAuth.setUsername("username"+i);
			provider.insert(userAuth);
		}
	}
}
