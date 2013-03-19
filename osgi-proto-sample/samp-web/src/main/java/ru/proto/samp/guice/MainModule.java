package ru.proto.samp.guice;

import com.google.gwt.thirdparty.guava.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.servlet.ServletScopes;
import com.vaadin.server.UIProvider;
import com.vaadin.ui.UI;
import ru.proto.samp.MyVaadinUI;
import ru.proto.samp.core.TranslateAggregator;

import static org.ops4j.peaberry.Peaberry.service;

/**
 * User: fmv
 * Date: 11.03.13
 * Time: 13:57
 */
public class MainModule extends AbstractModule {

    protected void configure() {
        bind(UIProvider.class).to(MyUIProvider.class);
        bind(UI.class).to(MyVaadinUI.class).in(ServletScopes.SESSION);
        bind(TranslateAggregator.class).toProvider(service(TranslateAggregator.class).single());
        bind(EventBus.class).in(ServletScopes.SESSION);
    }

}