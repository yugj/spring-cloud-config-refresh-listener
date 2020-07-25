package org.github.yugj.config.refresh.listener.core.listener;

import org.github.yugj.config.refresh.listener.core.event.ConfigRefreshEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * 配置刷新监听
 * @author yugj
 * @since 1.0-SNAPSHOT
 */
@Service
public class ConfigRefreshListener implements Ordered {

    @Autowired
    private Environment environment;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @EventListener(EnvironmentChangeEvent.class)
    public void listener(EnvironmentChangeEvent event) {
        for (String refreshKey : event.getKeys()) {
            Object afterRefreshed = environment.getProperty(refreshKey);
            eventPublisher.publishEvent(new ConfigRefreshEvent(this,refreshKey, afterRefreshed, afterRefreshed));
        }
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE - 1;
    }
}
