package nl.mycompany.webapp.ui.login;

import com.vaadin.navigator.View;


public interface LoginView extends View {
	
	public static final String VIEW_NAME = "login";
	
	void showLoginFailed();
    
    void clearForm();
   
    

}
