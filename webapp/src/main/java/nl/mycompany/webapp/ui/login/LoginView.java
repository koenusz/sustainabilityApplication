package nl.mycompany.webapp.ui.login;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.navigator.View;


@VaadinView(LoginView.NAME)
public interface LoginView extends View{
	
	public static final String NAME = "login";
	
    void showLoginFailed();
    
    void clearForm();
    
}
