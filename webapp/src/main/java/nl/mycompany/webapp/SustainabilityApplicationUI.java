package nl.mycompany.webapp;



import nl.mycompany.webapp.main.MainView;
import nl.mycompany.webapp.main.MainViewImplementation;
import nl.mycompany.webapp.ui.login.LoginView;
import nl.mycompany.webapp.ui.login.LoginViewComponent;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.xpoft.vaadin.DiscoveryNavigator;
import ru.xpoft.vaadin.SpringApplicationContext;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@Component
@SuppressWarnings("serial")
@Scope("prototype")
@Theme("mytheme")
@Widgetset("nl.mycompany.webapp.MyAppWidgetset")
public class SustainabilityApplicationUI extends UI {
	
	 private static final Logger LOG = Logger
	 .getLogger(SustainabilityApplicationUI.class);

	
	 @Autowired
	 private transient ApplicationContext applicationContext;
	
	// Create a navigator to control the views
	
	 Navigator navigator;
	
	private String loggedInUser;
	
	@Autowired
	LoginViewComponent loginViewComponent;

	@Override
    protected void init(VaadinRequest vaadinRequest) {
		
		if(applicationContext == null)
    	{
    	LOG.debug("applicationContext is null");
    	}
		
		if(SpringApplicationContext.getApplicationContext() == null)
		{
			LOG.debug("applicationContext 2 is null");
		}
		
		//Application questionaire = new Application();
		
		
		
		//navigator = new Navigator(this, this);
		//navigator.addView(MainView.NAME, new MainViewImplementation(navigator));
		//navigator.addView(LoginView.NAME, loginViewComponent);
		//navigator =  new DiscoveryNavigator(this, this);
		
		//navigator.getDisplay().showView(new LoginViewComponent());
		
		 // we'll handle permissions with a listener here, you could also do
        // that in the View itself.
        navigator.addViewChangeListener(new ViewChangeListener() {

            @Override
            public boolean beforeViewChange(ViewChangeEvent event) {
            	
            	
            	LOG.debug("view: " + event.getViewName());
            	if (SustainabilityApplicationUI.getCurrent().getLoggedInUser() == null && !(event.getViewName().equals("login"))) {
            		// Show to LoginView instead, pass intended view
            		String fragmentAndParameters = event.getViewName();
            		if (event.getParameters() != null) {
            			fragmentAndParameters += "/";
            			fragmentAndParameters += event.getParameters();
            		}
            		
            		LoginViewComponent login = (LoginViewComponent) applicationContext.getBean("loginViewComponent");
            		login.setFragmentAndParameters(fragmentAndParameters);
            		navigator.navigateTo("login");
            		
            		return false;

            	} else {
            		return true;
            	}
              }
                  
              @Override
              public void afterViewChange(ViewChangeEvent event) {

              }          
        });


    }

	public String getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(String loggedInUser) {
		this.loggedInUser = loggedInUser;
	}
	
	public static SustainabilityApplicationUI getCurrent() {
		return (SustainabilityApplicationUI)UI.getCurrent();
	}

	public Navigator getNavigator() {
		return navigator;
	}

	@Override
	public void markAsDirty() {
		// TODO Auto-generated method stub
		
	}

}
