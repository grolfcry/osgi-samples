package ru.proto.samp.tr;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.ExamSystem;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.TestContainer;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.PaxExamRuntime;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerMethod;
import org.ops4j.pax.exam.util.Filter;
import org.osgi.framework.*;
import ru.proto.samp.core.TranslateAggregator;
import ru.proto.util.OSGIUtils;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.*;
import static org.ops4j.pax.exam.CoreOptions.*;
import static org.ops4j.pax.exam.OptionUtils.combine;
import static ru.proto.util.MavenUtil.mvnBundle;
import static ru.proto.util.MavenUtil.packageReference;

/**
 * User: fmv
 * Date: 14.03.13
 * Time: 16:56
 */
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerMethod.class)
public class CoreIT {
    @Inject
    TranslateAggregator aggregator;

    @Inject
    @Filter(("(instance.name=SimpleTranslate)"))
    Translate simpleTranslate;

    @Inject
    BundleContext ctx;

    @Configuration
    public Option[] configuration() {
        return combine(
                options(
                        junitBundles(),
                        mavenBundle().groupId("org.apache.felix").artifactId("org.apache.felix.ipojo").version("1.8.6")
                ),
                projectBundles()
        );
    }

    private Option[] projectBundles() {
        return options(
                mvnBundle("translate-api"),
                mvnBundle("translate-google"),
                mvnBundle("translate-simple"),
                mvnBundle("core-api"),
                mvnBundle("test-utils"),
                bundle(packageReference())
        );
    }

    @Before
    public void validate() {
        assertNotNull(ctx);
        assertNotNull(aggregator);
        assertNotNull(simpleTranslate);
    }

    @Test
    public void test1() throws BundleException, InvalidSyntaxException, InterruptedException {
        assertNotNull(aggregator.getTranslators());
        assertTrue(aggregator.getTranslators().size() > 0);
        assertNotNull(simpleTranslate.translate("aaa"));
    }
    @Test
    public void trackingTest() throws InvalidSyntaxException, BundleException {
        Collection<ServiceReference<Translate>> refs = ctx.getServiceReferences(Translate.class, null);
        assertNotNull(refs);
        int size = aggregator.getTranslators().size();
        Bundle bundle = null;
        for (ServiceReference<Translate> ref : refs) {
            bundle = ref.getBundle();
            bundle.stop();
            //динамический резолвинг/update работает?
            assertEquals(--size, aggregator.getTranslators().size());
        }
        //остановили все
        assertEquals(0, aggregator.getTranslators().size());
        //"закешированный" класс по прежнему работает
        System.out.println(simpleTranslate.translate("dsfsfs"));
        //старуем
        bundle.start();
        //сервиc появился?
        OSGIUtils.waitForService(ctx, Translate.class.getName(), 3000);
        //и тут? injected field tracking - auto update
        assertEquals(1, aggregator.getTranslators().size());
    }

    public static void main(String[] args) throws TimeoutException, IOException {
           ExamSystem system = PaxExamRuntime.createServerSystem(
                   combine(new CoreIT().configuration(), OSGIUtils.serverOptions()));
           TestContainer container = PaxExamRuntime.createContainer(system);
           container.start();
       }

}
