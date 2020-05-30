package com.github.wp17.lina.game.module.event;

import com.alibaba.fastjson.JSONObject;
import com.github.wp17.lina.common.event.EventListener;
import com.google.common.eventbus.Subscribe;

@EventListener
public class TestEventListener {

    @Subscribe
    public void TestProcessor(TestEvent event) {
        System.out.println(JSONObject.toJSONString(event));
    }
}
