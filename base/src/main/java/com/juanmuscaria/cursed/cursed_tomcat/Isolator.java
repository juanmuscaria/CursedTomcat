package com.juanmuscaria.cursed.cursed_tomcat;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class Isolator extends URLClassLoader {
    public Isolator() {
        super(figureOutClasspath(), ClassLoader.getSystemClassLoader());
    }

    public static URL[] figureOutClasspath() {
        URL jar = JarFinder.getJarUrl(JarFinder.getLocation(Isolator.class));
        return new URL[]{jar};
    }

    public ITomcatContainer createIsolatedContainer(boolean disableUrlFactory) {
        try {
            return (ITomcatContainer) loadClass("com.juanmuscaria.cursed.cursed_tomcat.IsolatedTomcat").getDeclaredConstructor(boolean.class).newInstance(disableUrlFactory);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Class<?> loadClass(String name, boolean load) throws ClassNotFoundException {
        if (ITomcatContainer.class.getName().equals(name)) {
            return ITomcatContainer.class;
        } else
            return super.loadClass(name, load);
    }
}
