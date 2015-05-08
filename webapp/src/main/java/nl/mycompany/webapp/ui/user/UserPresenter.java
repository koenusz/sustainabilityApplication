package nl.mycompany.webapp.ui.user;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import nl.mycompany.questionaire.domain.Client;
import nl.mycompany.questionaire.service.repository.ClientService;
import nl.mycompany.webapp.SustainabilityApplicationUI;
import nl.mycompany.webapp.abstracts.AbstractPresenter;
import nl.mycompany.webapp.abstracts.ViewEvent;
import nl.mycompany.webapp.event.ClientChangedEvent;
import nl.mycompany.webapp.event.EventBus;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.peholmst.i18n4vaadin.annotations.Message;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Notification;

@SpringComponent
@UIScope
public class UserPresenter extends AbstractPresenter<UserView> {

	UserPresenterBundle bundle = new UserPresenterBundle();

	@Autowired
	private EventBus eventBus;

	@Autowired
	private ClientService clientService;

	@Autowired
	private IdentityService identityService;

	@PostConstruct
	public void init() {
		eventBus.subscribe(this);
	}

	@Message(key = "error.noClient", value = "Current user has no client set.")
	public void populateUserviewWithUsers() {
		Client client = SustainabilityApplicationUI.getCurrent()
				.getLoggedInUser().getClient();

		List<User> users;
		if (null == client.getName()
				&& SustainabilityApplicationUI.getCurrent().getLoggedInUser()
						.isAdmin()) {
			users = identityService.createUserQuery().list();
		} else if (null == client.getName()) {
			Notification.show(bundle.error_noClient(),
					Notification.Type.ERROR_MESSAGE);
			return;
		} else {
			users = clientService.findUsersByClient(client);
		}

		getView().setUsers(users);

	}

	public User newUser() {
		Client client = SustainabilityApplicationUI.getCurrent()
				.getLoggedInUser().getClient();
		UUID id = UUID.randomUUID();
		User user = identityService.newUser(client.getName() + id);

		return user;
	}

	public void saveUser(User user) {
		Client client = SustainabilityApplicationUI.getCurrent()
				.getLoggedInUser().getClient();
		if (client.getName() == null) {
			Notification.show(bundle.error_noClient(),
					Notification.Type.ERROR_MESSAGE);
			return;
		}

		identityService.saveUser(user);
		identityService.createMembership(user.getId(), client.getName());
	}

	public void deleteUser(User user) {
		identityService.deleteUser(user.getId());
	}

	public void createMembership(User user, String groupId) {
		identityService.createMembership(user.getId(), groupId);
	}

	public void deleteMembership(User user, Group group) {
		identityService.deleteMembership(user.getId(), group.getId());
	}

	/**
	 * returns an empty list if user is null
	 * 
	 * @param user
	 * @return
	 */
	public List<Group> findGroupsByUser(User user) {

		if (user == null) {
			return new ArrayList<Group>();
		}

		return identityService.createGroupQuery().groupMember(user.getId())
				.list();
	}

	@Override
	public void fireViewEvent(ViewEvent event) {
		if (event instanceof ClientChangedEvent) {
			SustainabilityApplicationUI.getCurrent().getLoggedInUser()
					.setClient(((ClientChangedEvent) event).getClient());
			populateUserviewWithUsers();
			getView().fireViewEvent(event);
		}
	}

	@PreDestroy
	public void tearDown() {
		eventBus.unSubscribe(this);
	}

}
