package nl.mycompany.questionaire.identity;

import org.activiti.engine.impl.identity.Authentication;
import org.apache.log4j.Logger;

public class CurrentUserFactoryBean {
	
	private static final Logger LOG = Logger
			.getLogger(CurrentUserFactoryBean.class);
        
    public String getCurrentUsername() {
    	
        return Authentication.getAuthenticatedUserId();
    }
    
    public void setCurrentUsername(String username) {
    	LOG.debug("setting username " + username );
        Authentication.setAuthenticatedUserId(username);
    }
}
