package ru.proto.samp.sw;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import ru.proto.samp.core.TranslateAggregator;

/**
 * User: fmv
 * Date: 19.03.13
 * Time: 1:05
 */
public class WebActivator implements BundleActivator, ServiceTrackerCustomizer<TranslateAggregator,TranslateAggregator> {
    BundleContext context;
    TranslateAggregator aggregator;
    @Override
    public void start(BundleContext context) throws Exception {
        this.context=context;
        ServiceTracker<TranslateAggregator,TranslateAggregator> tracker = new ServiceTracker<TranslateAggregator,TranslateAggregator>(context, TranslateAggregator.class, this);
        tracker.open();
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("WebActivator stop");
    }

    @Override
    public TranslateAggregator addingService(ServiceReference<TranslateAggregator> reference) {
        System.out.println("WebActivator service added."+ context.getService(reference).getTranslators().size());
        return null;  //TODO implement

    }
    @Override
    public void modifiedService(ServiceReference<TranslateAggregator> reference, TranslateAggregator service) {
        System.out.println("WebActivator modifiedService."+ service);
    }

    @Override
    public void removedService(ServiceReference<TranslateAggregator> reference, TranslateAggregator service) {
        System.out.println("WebActivator modifiedService."+ service);
    }
}
