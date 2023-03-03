package com.juanmuscaria.cursed.cursed_tomcat;

import org.apache.catalina.startup.Tomcat;

public class WrappedTomcat implements ITomcatContainer {
    Tomcat tomcat;
    public WrappedTomcat(Tomcat tomcat) {
        this.tomcat = tomcat;
    }

    @Override
    public void start() throws Exception {
        tomcat.start();
    }

    @Override
    public void stopAndAwait() throws Exception {
        tomcat.stop();
        await();
    }

    @Override
    public void await() {
        tomcat.getServer().await();
    }

    @Override
    public Object get() {
        return tomcat;
    }
}
