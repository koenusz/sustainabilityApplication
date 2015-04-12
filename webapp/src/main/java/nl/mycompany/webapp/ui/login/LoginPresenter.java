package nl.mycompany.webapp.ui.login;

import nl.mycompany.questionaire.identity.CurrentUserFactoryBean;

import org.activiti.engine.IdentityService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginPresenter {
	
	private static final Logger LOG = Logger
			.getLogger(LoginPresenter.class);

 
    @Autowired
    protected transient IdentityService identityService;
    
    @Autowired
    protected transient CurrentUserFactoryBean currentUserFactoryBean;    
    
    public void attemptLogin(String username, String password) {
       // SustainabilityApplicationUI.getCurrent().setLoggedInUser(username);
    	
    	LOG.debug("logging in: " + username );
    	
    	if (identityService.checkPassword(username, password)) {
            currentUserFactoryBean.setCurrentUsername(username);
            LOG.debug("login succesfull");
            //fireEvent(new UserLoggedInEvent(getView(), username));
        } else {
        	LOG.debug("login failed");
            //getView().clearForm();
            //getView().showLoginFailed();s
        }
    }


    
}
