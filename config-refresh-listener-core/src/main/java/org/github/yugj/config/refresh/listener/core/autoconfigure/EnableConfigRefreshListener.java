package org.github.yugj.config.refresh.listener.core.autoconfigure;

import org.github.yugj.config.refresh.listener.core.listener.ConfigRefreshListener;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author yugj
 * @since 1.0-SNAPSHOT
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({ConfigRefreshListener.class})
@Documented
public @interface EnableConfigRefreshListener {


}
