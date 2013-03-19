package ru.proto.samp.launcher;

import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.ExamSystem;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.TestContainer;
import org.ops4j.pax.exam.spi.PaxExamRuntime;
import ru.proto.util.OSGIUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.ops4j.pax.exam.CoreOptions.*;
import static org.ops4j.pax.exam.OptionUtils.combine;
import static ru.proto.util.MavenUtil.mvnBundle;

/**
 * User: fmv
 * Date: 16.03.13
 * Time: 20:20
 */
public class StdLauncher {
    @Configuration
    public static Option[] configuration() {
        return combine(
                options(
                        systemProperty("org.osgi.service.http.port").value("8081"),
                        systemProperty("org.ops4j.pax.logging.DefaultServiceLog.level").value("DEBUG"),
                        mavenBundle().groupId("org.ops4j.pax.web").artifactId("pax-web-jetty-bundle").version("3.0.0.M2"),
                        //--mavenBundle().groupId("org.ops4j.pax.web").artifactId("pax-web").version("3.0.0.M2"),
                        mavenBundle().groupId("org.ops4j.pax.web").artifactId("pax-web-extender-war").version("3.0.0.M2"),
                        mavenBundle().groupId("org.ops4j.pax.web").artifactId("pax-web-jsp").version("3.0.0.M2"),
                        mavenBundle().groupId("org.apache.xbean").artifactId("xbean-finder").version("3.12"),
                        mavenBundle().groupId("org.apache.xbean").artifactId("xbean-bundleutils").version("3.12"),
                        mavenBundle().groupId("org.eclipse.jetty.orbit").artifactId("org.objectweb.asm").version("3.3.1.v201105211655")

                ),
                projectBundles()
        );
    }

    private static Option[] projectBundles() {
        return options(
                mvnBundle("translate-api"),
                mvnBundle("translate-google"),
                mvnBundle("translate-simple"),
                mvnBundle("core-api"),
                mvnBundle("core"),
                //bundle("war:mvn:ru.proto.samp/samp-web/0.1-SNAPSHOT/war")
//                bundle("war:file:C:\\work\\osgi\\org.ops4j.pax.web\\samples\\war-simple\\target\\war-simple-3.0.0-SNAPSHOT.war"),
                //mvnBundle("simple-web").type("war").startLevel(2),
                mvnBundle("samp-web").type("war").startLevel(2)
        );
    }

    public static void main(String[] args) throws TimeoutException, IOException {
        ExamSystem system = PaxExamRuntime.createServerSystem(
                combine(configuration(), OSGIUtils.serverOptions()));
        TestContainer container = PaxExamRuntime.createContainer(system);
        container.start();
    }
}
