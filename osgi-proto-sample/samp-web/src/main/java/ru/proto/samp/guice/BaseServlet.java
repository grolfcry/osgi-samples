package ru.proto.samp.guice;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.vaadin.server.*;

/**
 * User: fmv
 * Date: 11.03.13
 * Time: 13:33
 */
@Singleton
public class BaseServlet extends VaadinServlet implements SessionInitListener {
	/**
	 * Cannot use constructor injection. Container expects servlet to have no-arg public constructor
	 */
	@Inject
	private UIProvider basicProvider;

	@Override
	protected void servletInitialized() {
		getService().addSessionInitListener(this);
	}

	@Override
	public void sessionInit(SessionInitEvent event) throws ServiceException {
		event.getSession().addUIProvider(basicProvider);
	}

}