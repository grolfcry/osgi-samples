package ru.proto.samp.tr;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.*;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.PaxExamRuntime;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerMethod;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;
import ru.proto.util.OSGIUtils;

import javax.inject.Inject;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.ops4j.pax.exam.CoreOptions.*;
import static org.ops4j.pax.exam.OptionUtils.combine;
import static ru.proto.util.MavenUtil.mvnBundle;
import static ru.proto.util.MavenUtil.packageReference;

/**
 * User: fmv
 * Date: 16.03.13
 * Time: 14:35
 */
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerMethod.class)
public class TranslateIT {
    @Inject
    BundleContext ctx;
    @Inject
    Translate translate;


    @Configuration
    public Option[] configuration() {
        return options(
                junitBundles(),
                mavenBundle().groupId("org.apache.felix").artifactId("org.apache.felix.ipojo").version("1.8.6"),
                mvnBundle("translate-api"),
                mvnBundle("test-utils"),
                bundle(packageReference())
        );
    }

    @Before
    public void validate() {
        assertNotNull(ctx);
        assertNotNull(translate);
    }

    @Test
    public void simpleTest() {
        assertNotNull(translate.translate("ddd"));
    }

    @Test
    public void bundleTest() throws BundleException {
        //get service
        ServiceReference<Translate> ref = ctx.getServiceReference(Translate.class);
        assertNotNull(ref);
        Translate tr = ctx.getService(ref);
        assertNotNull(tr);
        //stop bundle and check
        Bundle bundle = ref.getBundle();
        bundle.stop();
        //полученные ссылки на инстансы работают
        assertNotNull(tr.translate("dfsd"));
        assertNotNull(translate.translate("sdfsdf"));
        //а новый не получить
        assertNull(ctx.getService(ref));
        //start and get
        bundle.start();
        OSGIUtils.waitForService(ctx, Translate.class, 3000);
    }


    public static void main(String[] args) throws TimeoutException, IOException {
        //go to http://localhost:8080/system/console/
        ExamSystem system = PaxExamRuntime.createServerSystem(
                combine(new TranslateIT().configuration(), OSGIUtils.serverOptions()));
        TestContainer container = PaxExamRuntime.createContainer(system);
        container.start();
    }
}
