package ru.proto.util;

import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.TestContainerException;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.options;
import static org.ops4j.pax.exam.CoreOptions.workingDirectory;

/**
 * User: fmv
 * Date: 16.03.13
 * Time: 15:15
 */
public class OSGIUtils {
    public static void waitForService(BundleContext ctx, Class clazz, Integer wait){
        waitForService(ctx, clazz.getName(), wait);
    }
    public static void waitForService(BundleContext ctx, String servicename, Integer wait){
        if (wait == null) {
            wait = 1000;
        }
        ServiceTracker tracker = new ServiceTracker(ctx, servicename, null);
        tracker.open(true);
        long start = System.currentTimeMillis();

        while (((tracker.getTrackingCount()) == 0) && (start + wait > System.currentTimeMillis())) {
            try {
                      Thread.sleep(5);
                  } catch (InterruptedException e) {
                      // Interrupted
                  }
        }
        int c = tracker.getTrackingCount();
        tracker.close();
        if (c == 0) {
            throw new TestContainerException("Service " + servicename + " has not been available in time.");
        }
    }

    public static Option[] serverOptions() {
        String gogoVersion = "0.8.0";
        return options(
                mavenBundle().groupId("org.ops4j.pax.logging").artifactId("pax-logging-api").version("1.6.1"),
                mavenBundle().groupId("org.osgi").artifactId("org.osgi.compendium").version("4.3.1"),
                mavenBundle().groupId("org.apache.felix").artifactId("org.apache.felix.ipojo").version("1.8.6"),
                mavenBundle().groupId("org.apache.felix").artifactId("org.apache.felix.scr").version("1.6.0"),

                mavenBundle().groupId("org.apache.felix").artifactId("org.apache.felix.gogo.runtime").version(gogoVersion),
                mavenBundle().groupId("org.apache.felix").artifactId("org.apache.felix.gogo.shell").version(gogoVersion),
                mavenBundle().groupId("org.apache.felix").artifactId("org.apache.felix.gogo.command").version(gogoVersion),
                mavenBundle().groupId("org.apache.felix").artifactId("org.apache.felix.webconsole").version("3.1.8"),
                mavenBundle().groupId("org.apache.felix").artifactId("org.apache.felix.ipojo.webconsole").version("1.6.0"),

                mavenBundle().groupId("org.apache.felix").artifactId("org.apache.felix.configadmin").version("1.4.0"),
                mavenBundle().groupId("org.apache.felix").artifactId("org.apache.felix.deploymentadmin").version("0.9.0"),
                mavenBundle().groupId("org.apache.felix").artifactId("org.apache.felix.dependencymanager").version("3.0.0"),
                mavenBundle().groupId("org.apache.felix").artifactId("org.apache.felix.bundlerepository").version("1.6.6"),
                mavenBundle().groupId("org.apache.felix").artifactId("org.apache.felix.metatype").version("1.0.6"),
//                mavenBundle().groupId("org.apache.felix").artifactId("org.apache.felix.shell").version("1.4.3"),
                workingDirectory(System.getProperty("user.dir") + "/target/server")
        );
    }
}
