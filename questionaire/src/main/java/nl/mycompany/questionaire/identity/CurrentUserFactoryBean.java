package nl.mycompany.questionaire.identity;

import org.activiti.engine.impl.identity.Authentication;

public class CurrentUserFactoryBean {
        
    public String getCurrentUsername() {
        return Authentication.getAuthenticatedUserId();
    }
    
    public void setCurrentUsername(String username) {
        Authentication.setAuthenticatedUserId(username);
    }
}
