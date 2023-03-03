import com.juanmuscaria.cursed.cursed_tomcat.ITomcatContainer;
import com.juanmuscaria.cursed.cursed_tomcat.TomcatBootstrap;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TestTomcat {
    @TempDir
    Path tempInstall;

    @Test
    void testDefault() throws Exception {
        ITomcatContainer tomcat = TomcatBootstrap.configure(tempInstall);
        Set<String> expectedFiles = new HashSet<String>(Arrays.asList()) {{
            add("logs");
            add("webapps");
            add("temp");
            add("conf");
            add("work");
        }};
        Assertions.assertTrue(Files.list(tempInstall).map(file -> file.getFileName().toString())
                .filter(expectedFiles::remove)
                .anyMatch(__ -> expectedFiles.isEmpty()),
                "Incomplete installation!");
        tomcat.start();
        tomcat.stopAndAwait();
    }
}
