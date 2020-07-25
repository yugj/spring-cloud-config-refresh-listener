# spring-cloud-config-refresh-listener 

spring cloud 监听特定配置变化  
spring cloud config refresh listener  

## 使用说明

引入依赖

```
<dependency>
    <groupId>org.github.yugj</groupId>
    <artifactId>config-refresh-listener-core</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

启动类添加注解

```
   @SpringBootApplication
   @EnableConfigRefreshListener
   public class ConfigListenerDemoStarter {
       public static void main(String[] args) {
           SpringApplication application = new SpringApplication(ConfigListenerDemoStarter.class);
           application.run(args).start();
       }
   }
```

监听关注的配置变化（这边直接采用原生el表达式匹配事件）

```
    @EventListener(condition = "#event.key eq 'sys.log.root'")
    void handleConditionalListener(ConfigRefreshEvent event) {
        // 业务逻辑 balabala
        System.out.println("handleConditionalListener event key :" + event.getKey()
        + ", before :" + event.getBeforeRefresh()
        + ", after :" + event.getAfterRefresh());
    }
```

## 版本兼容

| springboot版本 | spring-cloud-config-refresh-listener |
| -------------- | ------------------------------------ |
| 1.x            | 1.0.0                                |
| 2.x            | 2.0.0                                |

