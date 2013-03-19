package ru.proto.samp;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import ru.proto.samp.core.TranslateAggregator;
import ru.proto.samp.guice.MainModule;
import ru.proto.samp.guice.WebModule;

import javax.inject.Inject;

import static com.google.inject.Guice.createInjector;
import static org.ops4j.peaberry.Peaberry.osgiModule;

/**
 * User: fmv
 * Date: 18.03.13
 * Time: 14:28
 */
public class WebActivator implements BundleActivator, ServiceTrackerCustomizer<TranslateAggregator, TranslateAggregator> {
    @Inject
    TranslateAggregator aggregator;

    BundleContext context;

    @Override
    public void start(BundleContext bc) throws Exception {
        context = bc;
        ServiceTracker<TranslateAggregator, TranslateAggregator> tracker = new ServiceTracker<TranslateAggregator, TranslateAggregator>(bc, TranslateAggregator.class, this);
        tracker.open();
    }

    /**
     * Called when the OSGi framework stops our bundle
     */
    public void stop(BundleContext bc) throws Exception {
        System.out.println("WebActivator stop");
    }

    @Override
    public TranslateAggregator addingService(ServiceReference<TranslateAggregator> reference) {
        createInjector(osgiModule(context), new MainModule(), new WebModule()).injectMembers(this);
        System.out.println("WebActivator service added." + context.getService(reference).getTranslators().size());
        return context.getService(reference);
    }

    @Override
    public void modifiedService(ServiceReference<TranslateAggregator> reference, TranslateAggregator service) {
        System.out.println("WebActivator modifiedService." + service);
    }

    @Override
    public void removedService(ServiceReference<TranslateAggregator> reference, TranslateAggregator service) {
        System.out.println("WebActivator modifiedService." + service);
    }
}
