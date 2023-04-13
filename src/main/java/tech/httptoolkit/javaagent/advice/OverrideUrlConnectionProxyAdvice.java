package tech.httptoolkit.javaagent.advice;

import net.bytebuddy.asm.Advice;

import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;

public class OverrideUrlConnectionProxyAdvice {

//    @Advice.OnMethodExit
//    public static void getHost(
//        @Advice.Return String host) {
//        System.out.println("HOST: " + host);
//    }

    @Advice.OnMethodExit
    public static void getHost(
            @Advice.FieldValue(value = "protocol") String urlProtocol,
            @Advice.FieldValue(value = "port") int port,
            @Advice.FieldValue(value = "path") String path,
            @Advice.FieldValue(value = "query") String query,
            @Advice.Return String host) {
        if (host!=null && !host.equalsIgnoreCase("")) {
            System.out.println("HOST: " + host + " PROTOCOL: " + urlProtocol + " PORT: " + port + " PATH: " + path
                    + " QUERY: " + query);
        }
    }
}
