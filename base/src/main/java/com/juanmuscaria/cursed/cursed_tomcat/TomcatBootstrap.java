package com.juanmuscaria.cursed.cursed_tomcat;

import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.TomcatURLStreamHandlerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class TomcatBootstrap {
    private static final Isolator isolator;

    static {
        Isolator iClassloader = null;
        try {
            iClassloader = new Isolator();
        } catch (IllegalArgumentException e) {
            System.err.println("[CursedTomcat] Unable to figure our jar and create an isolated classloader!");
            e.printStackTrace();
        }
        isolator = iClassloader;
    }

    public static ITomcatContainer configure(Path installDir) throws IOException {
        return configure(installDir, false);
    }
    public static ITomcatContainer configure(Path installDir, boolean isolate) throws IOException {
        return configure(installDir, isolate, false);
    }

    public static ITomcatContainer configure(Path installDir, boolean isolate, boolean disableUrlFacotry) throws IOException {
        // Unless overwritten by the user, set where tomcat should be configured to look for things to load.
        if (System.getProperty("catalina.base","").isEmpty()) {
            System.setProperty("catalina.base", installDir.toString());
            System.setProperty("catalina.home", installDir.toString());
        } else {
            // User changed the default dir, assume all files are already present
            installDir = null;
        }
        // Unpack the files needed for a default tomcat instance if there's nothing on the target directory
        if (installDir != null )
            unpackResources(installDir);

        ITomcatContainer container;
        if (isolate) {
            // Completely isolate us from weird classloaders
            container = isolator.createIsolatedContainer(disableUrlFacotry);
        } else {
            Tomcat tomcat = new Tomcat();
            // Should fix weird issues under custom classloaders
            Thread.currentThread().setContextClassLoader(TomcatBootstrap.class.getClassLoader());
            if (disableUrlFacotry) {
                TomcatURLStreamHandlerFactory.disable();
            }
            // Initialize a default tomcat instance, it will look for the server.xml for configurations
            tomcat.init(null);
            container = new WrappedTomcat(tomcat);
        }

        return container;
    }

    private static void unpackResources(Path installDir) throws IOException {
        // Return if it's not suitable for installation
        if (Files.exists(installDir) &&  Files.list(installDir).findAny().isPresent())
            return;
        Files.createDirectories(installDir);
        InputStream embeddedResource = TomcatBootstrap.class.getResourceAsStream("/tomcat_default.zip");
        if (embeddedResource == null)
            throw new IOException("Embedded default tomcat is missing and cannot be extracted!");
        safeUnzip(embeddedResource, installDir);
    }

    // General reusable unzip code with some safety in place
    private static void safeUnzip(InputStream source, Path target) throws IOException {
        try (ZipInputStream zip = new ZipInputStream(source)) {
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {

                // Prevent against traversal path attacks
                Path filePath = target.resolve(entry.getName()).normalize();
                if (!filePath.startsWith(target)) {
                    throw new IOException("Transversal path detected: " + entry.getName());
                }

                if (entry.isDirectory()) {
                    Files.createDirectories(filePath);
                } else {
                    if (filePath.getParent() != null && Files.notExists(filePath.getParent())) {
                        Files.createDirectories(filePath.getParent());
                    }
                    Files.copy(zip, filePath);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            throw new IllegalArgumentException("Please specify the installation directory for tomcat.");
        }
        ITomcatContainer tomcat = configure(Paths.get(args[0]));
        tomcat.start();
        tomcat.await();
    }
}
