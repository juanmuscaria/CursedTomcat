package net.minestom.server.extensions;

//TODO: Actually use minestom as dependency instead of faking it
public abstract class Extension {
    public abstract void initialize();
    public abstract void terminate();
    public java.nio.file.Path getDataDirectory() {
        throw new UnsupportedOperationException(":P");
    }
    public org.slf4j.Logger getLogger() {
        throw new UnsupportedOperationException(":P");
    }
}
