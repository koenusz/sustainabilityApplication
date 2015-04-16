package nl.mycompany.webapp;



import nl.mycompany.webapp.ui.login.LoginPresenter;
import nl.mycompany.webapp.ui.login.LoginView;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@Theme("mytheme")
@SpringUI
@Widgetset("nl.mycompany.webapp.MyAppWidgetset")
public class SustainabilityApplicationUI extends UI {
	
	 private static final Logger LOG = Logger
	 .getLogger(SustainabilityApplicationUI.class);

	private String loggedInUser;
	
	@Autowired
    private SpringViewProvider viewProvider;
	
	private Navigator navigator;

	private String fragmentAndParameters;
	
	@Override
    protected void init(VaadinRequest vaadinRequest) {
		
		final VerticalLayout root = new VerticalLayout();
        root.setSizeFull();
        root.setMargin(true);
        root.setSpacing(true);
        setContent(root);

        final CssLayout navigationBar = new CssLayout();
        navigationBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        navigationBar.addComponent(createNavigationButton("View Scoped View",
                ScopedView.VIEW_NAME));
        navigationBar.addComponent(createNavigationButton("View default View",
                DefaultView.VIEW_NAME));
        navigationBar.addComponent(createNavigationButton("View login View",
        		LoginView.VIEW_NAME));
        root.addComponent(navigationBar);

        final Panel viewContainer = new Panel();
        viewContainer.setSizeFull();
        root.addComponent(viewContainer);
        root.setExpandRatio(viewContainer, 1.0f);

        navigator = new Navigator(this, viewContainer);
        navigator.addProvider(viewProvider);
			
		 // we'll handle permissions with a listener here, you could also do
        // that in the View itself.
        navigator.addViewChangeListener(new ViewChangeListener() {

            @Override
            public boolean beforeViewChange(ViewChangeEvent event) {
            	
            	
            	LOG.debug("view: " + event.getViewName());
            	if (SustainabilityApplicationUI.getCurrent().getLoggedInUser() == null && !event.getViewName().equals("login")) {
            		// Show to LoginView instead, pass intended view
            		fragmentAndParameters = event.getViewName();
            		if (event.getParameters() != null) {
            			fragmentAndParameters += "/";
            			fragmentAndParameters += event.getParameters();
            		}
            		LOG.debug("fragmentAndParameters: " + fragmentAndParameters);
            		
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
	
	 private Button createNavigationButton(String caption, final String viewName) {
	        Button button = new Button(caption);
	        button.addStyleName(ValoTheme.BUTTON_SMALL);
	        // If you didn't choose Java 8 when creating the project, convert this to an anonymous listener class
	        button.addClickListener(event -> getUI().getNavigator().navigateTo(viewName));
	        return button;
	    }

	
	public static SustainabilityApplicationUI getCurrent() {
		return (SustainabilityApplicationUI)UI.getCurrent();
	}

	public String getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(String loggedInUser) {
		this.loggedInUser = loggedInUser;
	}
	
	public void navigateToFragmentAndParameters()
	{
		this.navigateTo(fragmentAndParameters);
	}
	
	public void navigateTo(String viewName)
	{
		navigator.navigateTo(viewName);
	}

}
