package org.feathercoin.monitoring;

import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.util.time.Duration;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 * 
 * @see org.radics.mining.Start#main(String[])
 */
public class WicketApplication extends WebApplication
{    	
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage()
	{
		return HomePage.class;
	}

    @Override
    public void init()
    {
        super.init();


        getComponentInstantiationListeners().add(new SpringComponentInjector(this));
    }
}
