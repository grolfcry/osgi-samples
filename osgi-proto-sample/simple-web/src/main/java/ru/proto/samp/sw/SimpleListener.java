package ru.proto.samp.sw;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * User: fmv
 * Date: 19.03.13
 * Time: 1:14
 */
@WebListener
public class SimpleListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("SimpleListener contextInitialized, "+servletContextEvent);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("SimpleListener contextDestroyed, "+servletContextEvent);
    }
}
