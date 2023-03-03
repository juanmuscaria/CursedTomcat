package com.juanmuscaria.cursed.cursed_tomcat;

public interface ITomcatContainer {
    void start() throws Exception;
    void stopAndAwait() throws Exception;
    void await();
    Object get();
}
