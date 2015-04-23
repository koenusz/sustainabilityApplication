package nl.mycompany.webapp.ui.login;

import nl.mycompany.webapp.abstracts.View;



public interface LoginView extends View {
	
	public static final String VIEW_NAME = "login";
	
	void showLoginFailed();
    
    void clearForm();
   
    

}
