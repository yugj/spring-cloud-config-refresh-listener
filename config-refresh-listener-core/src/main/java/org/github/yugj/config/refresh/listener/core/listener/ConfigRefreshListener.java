package org.github.yugj.config.refresh.listener.core.listener;

import org.github.yugj.config.refresh.listener.core.event.ConfigRefreshEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.cloud.endpoint.event.RefreshEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.env.*;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.StandardServletEnvironment;

import java.util.*;

/**
 * 配置刷新监听
 * @author yugj
 * @since 1.0-SNAPSHOT
 */
@Service
public class ConfigRefreshListener implements Ordered {

    @Autowired
    private ContextRefresher refresh;
    @Autowired
    private Environment environment;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private ConfigurableApplicationContext context;

    /**
     * 拷贝自
     * org.springframework.cloud.context.refresh.ConfigRefreshListener
     * 私有方法无法引用，直接拷贝出来
     *
     */
    private Set<String> standardSources = new HashSet<>(
            Arrays.asList(StandardEnvironment.SYSTEM_PROPERTIES_PROPERTY_SOURCE_NAME,
                    StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME,
                    StandardServletEnvironment.JNDI_PROPERTY_SOURCE_NAME,
                    StandardServletEnvironment.SERVLET_CONFIG_PROPERTY_SOURCE_NAME,
                    StandardServletEnvironment.SERVLET_CONTEXT_PROPERTY_SOURCE_NAME,
                    "configurationProperties"));

    private static final Logger log = LoggerFactory.getLogger(ConfigRefreshListener.class);

    @EventListener(RefreshEvent.class)
    public void listener(RefreshEvent event) {

        Map<String, Object> before = extract(this.context.getEnvironment().getPropertySources());
        Set<String> refreshed = refresh.refresh();

        for (String refreshKey : refreshed) {
            Object beforeRefreshed = before.get(refreshKey);
            Object afterRefreshed = environment.getProperty(refreshKey);
            eventPublisher.publishEvent(new ConfigRefreshEvent(this,refreshKey, beforeRefreshed, afterRefreshed));
        }
    }

    /**
     * 拷贝自
     * org.springframework.cloud.context.refresh.ConfigRefreshListener
     * 私有方法无法引用，直接拷贝出来
     *
     */
    private Map<String, Object> extract(MutablePropertySources propertySources) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<PropertySource<?>> sources = new ArrayList<PropertySource<?>>();
        for (PropertySource<?> source : propertySources) {
            sources.add(0, source);
        }
        for (PropertySource<?> source : sources) {
            if (!this.standardSources.contains(source.getName())) {
                extract(source, result);
            }
        }
        return result;
    }

    /**
     * 拷贝自
     * org.springframework.cloud.context.refresh.ConfigRefreshListener
     * 私有方法无法引用，直接拷贝出来
     *
     */
    private void extract(PropertySource<?> parent, Map<String, Object> result) {
        if (parent instanceof CompositePropertySource) {
            try {
                List<PropertySource<?>> sources = new ArrayList<PropertySource<?>>();
                for (PropertySource<?> source : ((CompositePropertySource) parent)
                        .getPropertySources()) {
                    sources.add(0, source);
                }
                for (PropertySource<?> source : sources) {
                    extract(source, result);
                }
            }
            catch (Exception e) {
                return;
            }
        }
        else if (parent instanceof EnumerablePropertySource) {
            for (String key : ((EnumerablePropertySource<?>) parent).getPropertyNames()) {
                result.put(key, parent.getProperty(key));
            }
        }
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE - 1;
    }
}
