package nl.mycompany.webapp.ui.login;

import nl.mycompany.webapp.SustainabilityApplicationUI;
import nl.mycompany.webapp.abstracts.AbstractPresenter;

import org.activiti.engine.IdentityService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
public class LoginPresenter extends AbstractPresenter<LoginView>  {

	private static final Logger LOG = Logger.getLogger(LoginPresenter.class);

	@Autowired
	protected transient IdentityService identityService;

	
	private transient LoginView loginView;

	public void setLoginView(LoginView loginView) {
		this.loginView = loginView;
	}
	
	public void attemptLogin(String username, String password) {
		LOG.debug("logging in: " + username);
		if (identityService.checkPassword(username, password)) {
			LOG.debug("login succesfull");
			loginView.clearForm();
			SustainabilityApplicationUI.getCurrent().setLoggedInUser(username);
			SustainabilityApplicationUI.getCurrent().navigateToFragmentAndParameters();
			
		} else {
			LOG.debug("login failed");
			loginView.clearForm();
			loginView.showLoginFailed();
		}
	}


}
