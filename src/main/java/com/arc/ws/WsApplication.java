package com.arc.ws;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@SpringBootApplication
public class WsApplication {

    static ApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(WsApplication.class, args);

    }

    @ResponseBody
    @RequestMapping("info")
    public ResponseEntity info() {
        try {

            Map<Object, Object> map = new HashMap<>();
            map.put("Env", ArcRuntimeEnvironment.getArcRuntimeEnvironment());
            map.put("context", context);
            return ResponseEntity.ok(map);
        } catch (Exception exception) {
            return ResponseEntity.ok(exception);
        }
    }
}
//java -server -Xms256m -Xmx256m -XX:PermSize=64m -XX:MaxPermSize=128m -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=4004 -jar /data/project/java/ws/ws-0.0.1-SNAPSHOT.jar

