package ru.proto.samp.guice;

import com.google.inject.servlet.ServletModule;

/**
 * User: fmv
 * Date: 11.03.13
 * Time: 13:59
 */
public class WebModule extends ServletModule {

    protected void configureServlets() {
        serve("/*").with(BaseServlet.class);
    }

}
