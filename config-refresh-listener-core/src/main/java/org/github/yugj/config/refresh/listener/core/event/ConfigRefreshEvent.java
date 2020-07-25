package org.github.yugj.config.refresh.listener.core.event;

import org.springframework.context.ApplicationEvent;

/**
 * 配置刷新事件
 * @author yugj
 * @since 1.0-SNAPSHOT
 */
public class ConfigRefreshEvent extends ApplicationEvent {

    private String key;

    /**
     * 1.x版本beforeRefresh = afterRefresh
     */
    private Object beforeRefresh;
    private Object afterRefresh;

    public ConfigRefreshEvent(Object source, String key, Object beforeRefresh, Object afterRefresh) {
        super(source);
        this.key = key;
        this.beforeRefresh = beforeRefresh;
        this.afterRefresh = afterRefresh;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getBeforeRefresh() {
        return beforeRefresh;
    }

    public void setBeforeRefresh(String beforeRefresh) {
        this.beforeRefresh = beforeRefresh;
    }

    public Object getAfterRefresh() {
        return afterRefresh;
    }

    public void setAfterRefresh(String afterRefresh) {
        this.afterRefresh = afterRefresh;
    }
}
