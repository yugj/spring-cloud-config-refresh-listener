package org.github.yugj.config.refresh.listener.demo;

import org.github.yugj.config.refresh.listener.core.autoconfigure.EnableConfigRefreshListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yugj
 */
@SpringBootApplication
@EnableConfigRefreshListener
public class ConfigListenerDemoStarter {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ConfigListenerDemoStarter.class);
        application.run(args).start();
    }
}
