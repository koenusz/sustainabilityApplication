package nl.mycompany.webapp.ui.login;

import nl.mycompany.webapp.abstracts.View;
import nl.mycompany.webapp.abstracts.ViewEvent;



public class UserLoggedInEvent extends ViewEvent {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1004455277630750508L;
	private final String username;

    public UserLoggedInEvent(View source, String username) {
        super(source);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
