package com.juanmuscaria.cursed.cursed_tomcat;

import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.TomcatURLStreamHandlerFactory;

import java.net.URL;

public class IsolatedTomcat implements ITomcatContainer {
    Tomcat tomcat;
    public IsolatedTomcat(boolean disableUrlFactory) {
        this.tomcat = new Tomcat();
        Thread initializer = new Thread(() -> {
            if (disableUrlFactory) {
                TomcatURLStreamHandlerFactory.disable();
            }
            tomcat.init(null);
        });
        initializer.setContextClassLoader(this.getClass().getClassLoader());
        initializer.start();
        try {
            initializer.join();
        } catch (InterruptedException e) {
        }
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
