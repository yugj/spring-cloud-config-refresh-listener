package org.github.yugj.config.refresh.listener.demo;

import org.github.yugj.config.refresh.listener.core.event.ConfigRefreshEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author yugj
 */
@Component
public class DemoEventListener {

    @EventListener(condition = "#event.key eq 'sys.log.root'")
    void handleConditionalListener(ConfigRefreshEvent event) {
        // 业务逻辑 balabala
        System.out.println("handleConditionalListener event key :" + event.getKey()
        + ", before :" + event.getBeforeRefresh()
        + ", after :" + event.getAfterRefresh());
    }

    @EventListener(ConfigRefreshEvent.class)
    void baseListener(ConfigRefreshEvent event) {

        System.out.println("base listener key :" + event.getKey()
                + ", before :" + event.getBeforeRefresh()
                + ", after :" + event.getAfterRefresh());
    }
}
