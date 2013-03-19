package ru.proto.samp.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

/**
 * User: fmv
 * Date: 11.03.13
 * Time: 12:54
 */
//@WebListener
public class AppGuiceServletConfig extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new WebModule(), new MainModule());
    }
}
