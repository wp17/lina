package com.github.wp17.lina.server.db.provider;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.org.wgpg.lina.db.model.user.User;

@Component
public class UseTest {
	
	@Autowired
	private UserDataProvider provider;
	
//	@PostConstruct
	public void test() {
		for (int i = 0; i < 10; i++) {
			User user =new User();
			user.setId((long)i);
			user.setName("name_"+i);
			provider.insert(user);
		}
	}

}
