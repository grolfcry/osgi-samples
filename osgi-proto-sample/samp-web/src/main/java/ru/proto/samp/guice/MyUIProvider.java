package ru.proto.samp.guice;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UICreateEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.ui.UI;

/**
 * User: fmv
 * Date: 11.03.13
 * Time: 13:30
 */
public class MyUIProvider extends UIProvider {
    @Inject
    private Provider<UI> provider;

    @Inject
    Injector injector;

    @Override
    public UI createInstance(UICreateEvent event) {
        return injector.getInstance(provider.get().getClass());
    }
    @Override
    public Class<? extends UI> getUIClass(UIClassSelectionEvent event) {
        return provider.get().getClass();
    }
}
