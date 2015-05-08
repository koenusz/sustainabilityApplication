package nl.mycompany.webapp.ui.login;

import java.util.List;

import nl.mycompany.questionaire.domain.Client;
import nl.mycompany.questionaire.exception.NoClientException;
import nl.mycompany.questionaire.identity.AuthenticatedUser;
import nl.mycompany.questionaire.identity.Groups;
import nl.mycompany.questionaire.service.repository.ClientService;
import nl.mycompany.webapp.SustainabilityApplicationUI;
import nl.mycompany.webapp.abstracts.AbstractPresenter;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
public class LoginPresenter extends AbstractPresenter<LoginView> {

	private static final Logger LOG = Logger.getLogger(LoginPresenter.class);

	@Autowired
	protected transient IdentityService identityService;

	@Autowired
	private transient ClientService clientService;

	private transient LoginView loginView;

	public void setLoginView(LoginView loginView) {
		this.loginView = loginView;
	}

	public void attemptLogin(String username, String password) {
		LOG.debug("logging in: " + username);
		if (identityService.checkPassword(username, password)) {
			LOG.debug("login succesfull");
			loginView.clearForm();
			Client client = null;
			try {
				client = clientService.findClientByUserName(username);
			} catch (NoClientException e) {
				LOG.debug("login failed");
				e.printStackTrace();
				loginView.clearForm();
				loginView.showLoginFailed();
				return;
			}
			List<Group> memberships = identityService.createGroupQuery()
					.groupMember(username).list();
			boolean isAdmin = false;
			boolean isClientAdmin = false;
			
			for(Group group : memberships)
			{
				if(group.getId().equals(Groups.GROUP_ADMINS))
					isAdmin =  true;
				
				if(group.getId().equals(Groups.GROUP_CLIENTADMIN))
					isClientAdmin = true;
			}
			

			AuthenticatedUser user = new AuthenticatedUser(username, client, isAdmin, isClientAdmin);

			SustainabilityApplicationUI.getCurrent().setLoggedInUser(user);
			SustainabilityApplicationUI.getCurrent()
					.navigateToFragmentAndParameters();

		} else {
			LOG.debug("login failed");
			loginView.clearForm();
			loginView.showLoginFailed();
		}
	}

	

}
