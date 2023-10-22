//package com.arc.ws;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.net.InetAddress;
//import java.time.LocalDateTime;
//import java.util.Enumeration;
//import java.util.HashMap;
//import java.util.Map;
//
////可以很容易地将 WebSocket 集成到 Spring Boot 项目中。以下是如何在 Spring Boot 项目中集成 WebSocket 的一般步骤：
//@RestController
//@SpringBootApplication
//public class SpringWebSocketServMain3 {
//
//    private static final Logger log = LoggerFactory.getLogger(SpringWebSocketServMain3.class);
//
//    public static void main(String[] args) {
//        SpringApplication.run(SpringWebSocketServMain3.class, args);
//    }
//
////    @Bean
////    public WebMvcConfigurer corsConfigurer() {
////        return new WebMvcConfigurer() {
////            @Override
////            public void addCorsMappings(CorsRegistry registry) {
////                registry.addMapping("/**").allowedOrigins("*");
////            }
////        };
////    }
//
//    @Value("${spring.profiles.active:unknown}")
//    public String activeProfile;
//
//    @Value("${server.port:80}")
//    private int port;
//
//    @Value("${server.servlet.context-path:/}")
//    private String contextPath;
//
//    @RequestMapping("/info")
//    @ResponseBody
//    public Object info(HttpServletRequest request, HttpServletResponse response) {
//        log.info("################################");
//        log.info("request ServerName={} ", request.getServerName());
//        log.info("request ServerName={} ", request.getRemoteAddr());
//
//        String ipAddress = getIpAddress(request);
//        log.info("测试 ipAddress={}", ipAddress);
//
//        log.info("时间={},请求方法为 info ={},", LocalDateTime.now(), request);
//        //arcRuntimeEnvironment.setPort()
//
//        log.info("################################");
//
//        Map<String, String> requestParamMap = new HashMap<>(16);
//        Enumeration<?> temp = request.getParameterNames();
//        if (null != temp) {
//            while (temp.hasMoreElements()) {
//                String en = (String) temp.nextElement();
//                String value = request.getParameter(en);
//                requestParamMap.put(en, value);
//            }
//        }
//
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("info", request.getContextPath() + "  " + request.getRequestURI() + "  " + request.getRemoteHost() + "  ");
//        map.put("requestParamMap", requestParamMap);
//        map.put("now", System.currentTimeMillis());
//        response.setHeader("server_response_time", "" + System.currentTimeMillis());
//        response.setHeader("test", "true");
//        return map;
//    }
//
//    /**
//     * 获取用户ip
//     *
//     * @return String
//     */
//    public static String getIpAddress(HttpServletRequest request) {
//
//        try {
//            String ipAddress = request.getHeader("x-forwarded-for");
//            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
//                ipAddress = request.getHeader("Proxy-Client-IP");
//            }
//            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
//                ipAddress = request.getHeader("WL-Proxy-Client-IP");
//            }
//            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
//                ipAddress = request.getRemoteAddr();
//                if (ipAddress.equals("127.0.0.1")) {
//                    // 根据网卡取本机配置的IP
//                    ipAddress = InetAddress.getLocalHost().getHostAddress();
//                }
//            }
//            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
//            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
//                // = 15
//                if (ipAddress.indexOf(",") > 0) {
//                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
//                }
//            }
//            return (ipAddress != null && !"".equals(ipAddress.trim())) ? ipAddress : "unknown";
//
//
//        } catch (Exception e) {
//            return "unknown";
//        }
//    }
//
//}
//
