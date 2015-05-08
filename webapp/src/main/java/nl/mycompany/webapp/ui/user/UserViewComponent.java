package nl.mycompany.webapp.ui.user;

import java.util.List;

import javax.annotation.PostConstruct;

import nl.mycompany.webapp.SustainabilityApplicationUI;
import nl.mycompany.webapp.abstracts.ViewEvent;
import nl.mycompany.webapp.event.ClientChangedEvent;
import nl.mycompany.webapp.ui.client.ClientPresenter;
import nl.mycompany.webapp.ui.client.ClientView;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.peholmst.i18n4vaadin.annotations.Message;
import com.github.peholmst.i18n4vaadin.annotations.Messages;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@SpringView(name = UserView.VIEW_NAME)
public class UserViewComponent extends VerticalLayout implements UserView {

	public UserViewComponentBundle bundle = new UserViewComponentBundle();

	@Autowired
	private UserPresenter presenter;

	@Autowired
	private ClientPresenter clientPresenter;

	PopupView changeClientpopup;

	PopupView userPopup;

	// components
	Label clientNameLabel;

	ListSelect userList;

	ListSelect groupList;

	private static final String ID = "id";

	@PostConstruct
	@Messages({ @Message(key = "clientLabel", value = "Client {0}"),
			@Message(key = "newString", value = "New {0}"),
			@Message(key = "select", value = "Select a new {0}"),
			@Message(key = "edit", value = "Edit {0}"),
			@Message(key = "user", value = "User"),
			@Message(key = "group", value = "Group"),
			@Message(key = "client", value = "Client"),
			@Message(key = "add", value = "Add {0}"),
			@Message(key = "remove", value = "Remove {0}"),
			@Message(key = "delete", value = "Delete {0}"),
			@Message(key = "change", value = "Change {0}") })
	private void init() {
		presenter.setView(this);
		this.setMargin(true);
		this.setSpacing(true);

		String clientName;

		clientName = null == SustainabilityApplicationUI.getCurrent()
				.getLoggedInUser().getClient()
				|| null == SustainabilityApplicationUI.getCurrent()
						.getLoggedInUser().getClient().getName() ? bundle
				.select(bundle.client()) : SustainabilityApplicationUI
				.getCurrent().getLoggedInUser().getClient().getName();

		// clientbar header
		HorizontalLayout clientBar = new HorizontalLayout();
		clientBar.setWidth("100%");

		clientNameLabel = new Label();
		clientNameLabel.setCaption(bundle.client());
		clientNameLabel.setValue(clientName);

		clientBar.addComponent(clientNameLabel);
		this.addComponent(clientBar);

		HorizontalLayout contendsPanel = new HorizontalLayout();
		contendsPanel.setWidth("100%");

		VerticalLayout userPanel = new VerticalLayout();
		userPanel.setWidth("50%");
		userList = new ListSelect("Users");
		userPanel.addComponent(userList);
		userPanel.setComponentAlignment(userList, Alignment.MIDDLE_RIGHT);
		presenter.populateUserviewWithUsers();
		userList.addValueChangeListener(event -> setGroups(presenter
				.findGroupsByUser((User) userList.getValue())));
		contendsPanel.addComponent(userPanel);

		VerticalLayout groupPanel = new VerticalLayout();
		groupPanel.setWidth("50%");
		groupList = new ListSelect("Groups");
		groupPanel.addComponent(groupList);
		groupPanel.setComponentAlignment(groupList, Alignment.MIDDLE_LEFT);
		contendsPanel.addComponent(groupPanel);

		this.addComponent(clientBar);
		this.addComponent(contendsPanel);

		// add the administrator tools
		if (SustainabilityApplicationUI.getCurrent().getLoggedInUser()
				.isAdmin()) {

			// first the popups
			changeClientpopup = new PopupView(null, new ChangeClientPopup(
					clientPresenter));
			this.addComponent(changeClientpopup);

			// the admin panel for the client
			HorizontalLayout clientAdminPanel = new HorizontalLayout();
			Button changeClientButton = new Button(bundle.change(bundle
					.client()),
					click -> changeClientpopup.setPopupVisible(true));
			Button newClientButton = new Button(bundle.newString(bundle
					.client()), this::newClient);
			clientAdminPanel.addComponent(changeClientButton);
			clientAdminPanel.addComponent(newClientButton);
			clientBar.addComponent(clientAdminPanel);
			clientBar.setComponentAlignment(clientAdminPanel,
					Alignment.MIDDLE_RIGHT);

			HorizontalLayout userAdminPanel = new HorizontalLayout();
			Button editUserButton = new Button(bundle.edit(bundle.user()),
					this::editUserPopup);
			Button newUserButton = new Button(bundle.newString(bundle.user()),
					this::newUserPopup);
			Button deleteUserButton = new Button(bundle.delete(bundle.user()),
					this::deleteUser);
			userAdminPanel.addComponent(editUserButton);
			userAdminPanel.addComponent(newUserButton);
			userAdminPanel.addComponent(deleteUserButton);
			userPanel.addComponent(userAdminPanel);
			userAdminPanel.setWidth("100%");
			userPanel.setComponentAlignment(userAdminPanel,
					Alignment.BOTTOM_CENTER);

			HorizontalLayout groupAdminPanel = new HorizontalLayout();
			Button addGroupButton = new Button(bundle.add(bundle.group()));
			Button removeGroupButton = new Button(bundle.remove(bundle.group()), this::deleteMembership);
			groupAdminPanel.addComponent(addGroupButton);
			groupAdminPanel.addComponent(removeGroupButton);
			groupPanel.addComponent(groupAdminPanel);
			groupAdminPanel.setWidth("100%");
			groupPanel.setComponentAlignment(groupAdminPanel,
					Alignment.BOTTOM_CENTER);

		}

	}

	@Message(key = "message.noselectedgroup", value = "No group selected.")
	private void deleteMembership(ClickEvent event) {
		User user = (User) userList.getValue();
		Group group = (Group) groupList.getValue();

		if (user == null) {
			Notification.show(bundle.message_noselecteduser(),
					Notification.Type.WARNING_MESSAGE);
		} else if (group == null) {
			Notification.show(bundle.message_noselectedgroup(),
					Notification.Type.WARNING_MESSAGE);
		} else {
			presenter.deleteMembership(user, group);
		}
		setGroups(presenter
				.findGroupsByUser((User) userList.getValue()));
		
	}

	private void addMembership(String groupId) {
		User user = (User) userList.getValue();

		if (user == null) {
			Notification.show(bundle.message_noselecteduser(),
					Notification.Type.WARNING_MESSAGE);
		} else if (groupId == null) {
			Notification.show(bundle.message_noselectedgroup(),
					Notification.Type.WARNING_MESSAGE);
		} else {
			presenter.createMembership(user, groupId);
		}

	}

	@Message(key = "message.userdeleted", value = "User {0} is deleted.")
	private void deleteUser(ClickEvent event) {
		if (userList.getValue() == null) {
			Notification.show(bundle.message_noselecteduser(),
					Notification.Type.WARNING_MESSAGE);
			return;
		}

		User user = (User) userList.getValue();
		userList.setValue(null);
		presenter.deleteUser(user);
		presenter.populateUserviewWithUsers();

		Notification.show(
				bundle.message_userdeleted(user.getFirstName() + " "
						+ user.getLastName()),
				Notification.Type.ASSISTIVE_NOTIFICATION);

	}

	@Message(key = "message.noselecteduser", value = "No user selected.")
	private void editUserPopup(ClickEvent event) {

		if (userList.getValue() == null) {
			Notification.show(bundle.message_noselecteduser(),
					Notification.Type.WARNING_MESSAGE);
			return;
		}

		userPopup = new PopupView(null, new UserPopup(
				(User) userList.getValue(), presenter));
		this.addComponent(userPopup);
		userPopup.setPopupVisible(true);

	}

	@Messages({ @Message(key = "default.user.firstname", value = "First Name"),
			@Message(key = "default.user.lastname", value = "Last Name"),
			@Message(key = "default.user.email", value = "email@emal.com") })
	private void newUserPopup(ClickEvent event) {

		User user = presenter.newUser();
		user.setFirstName(bundle.default_user_firstname());
		user.setLastName(bundle.default_user_lastname());
		user.setEmail(bundle.default_user_email());
		userPopup = new PopupView(null, new UserPopup(user, presenter));
		this.addComponent(userPopup);
		userPopup.setPopupVisible(true);
		

	}

	public void newClient(ClickEvent event) {
		clientPresenter.setPreviousView(VIEW_NAME);
		SustainabilityApplicationUI.navigateTo(ClientView.VIEW_NAME);
	}

	@Override
	public void fireViewEvent(ViewEvent event) {
		if (event instanceof ClientChangedEvent) {
			updateClient();
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateClient() {

		clientNameLabel.setValue(clientPresenter.getClient().getName());
		changeClientpopup.setPopupVisible(false);

	}

	@Override
	public void setUsers(List<User> users) {

		IndexedContainer userContainer = new IndexedContainer();

		userContainer.addContainerProperty(ID, String.class, "");

		for (User user : users) {
			Item userItem = userContainer.addItem(user);
			userItem.getItemProperty(ID).setValue(
					user.getFirstName() + " " + user.getLastName());
		}
		userList.setContainerDataSource(userContainer);
		userList.setItemCaptionPropertyId(ID);
		userList.setNullSelectionAllowed(false);
	}

	@Override
	@Messages({
			@Message(key = "caption.groupList", value = "Groups for user {0}"),
			@Message(key = "caption.null", value = "Select a user to show groups.") })
	public void setGroups(List<Group> groups) {

		IndexedContainer userContainer = new IndexedContainer();

		userContainer.addContainerProperty(ID, String.class, "");

		for (Group group : groups) {
			Item userItem = userContainer.addItem(group);
			userItem.getItemProperty(ID).setValue(group.getName());
		}
		groupList.setContainerDataSource(userContainer);
		groupList.setItemCaptionPropertyId(ID);

		User selectedUser = (User) userList.getValue();
		String fullName;
		if (selectedUser == null) {

			groupList.setCaption(bundle.caption_null());
		} else {
			fullName = selectedUser.getFirstName() + " "
					+ selectedUser.getLastName();
			groupList.setCaption(bundle.caption_groupList(fullName));
		}

		groupList.setNullSelectionAllowed(false);

	}

}
