package nl.mycompany.webapp.main;

import nl.mycompany.webapp.SustainabilityApplicationUI;
import nl.mycompany.webapp.ui.login.LoginView;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.event.EventRouter;

public class MainViewImplementation extends Panel implements MainView {
   
    private Button logOut;

    public MainViewImplementation(final Navigator navigator) {

        VerticalLayout layout = new VerticalLayout();

        
        Label label = new Label("this is the main window");
        
        layout.addComponent(label);
        
        /*
        Link lnk = new Link("Count", (Resource) new ExternalResource("#!" + CountView.NAME));
        layout.addComponent(lnk);

        lnk = new Link("Message: Hello", new ExternalResource("#!"
                + MessageView.NAME + "/Hello"));
        layout.addComponent(lnk);

        lnk = new Link("Message: Bye", new ExternalResource("#!"
                + MessageView.NAME + "/Bye/Goodbye"));
        layout.addComponent(lnk);

        lnk = new Link("Private message: Secret", new ExternalResource("#!"
                + SecretView.NAME + "/Secret"));
        layout.addComponent(lnk);

        lnk = new Link("Private message: Topsecret", new Resource("#!"
                + SecretView.NAME + "/Topsecret"));
        layout.addComponent(lnk);
*/
        logOut = new Button("Logout", new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {

            	//((SustainabilityApplicationUI)UI.getCurrent()).setLoggedInUser(null);
            	logOut.setCaption("Login");
            	//navigator.navigateTo(TaskView.NAME);
                
            }
        });
        
    
        layout.addComponent(logOut);
        setContent(layout);
    }
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
