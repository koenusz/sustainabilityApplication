package nl.mycompany.webapp.ui.user;

import javax.annotation.PostConstruct;

import nl.mycompany.questionaire.domain.Client;
import nl.mycompany.questionaire.identity.Groups;
import nl.mycompany.webapp.SustainabilityApplicationUI;
import nl.mycompany.webapp.abstracts.ViewEvent;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.peholmst.i18n4vaadin.annotations.Message;
import com.github.peholmst.i18n4vaadin.annotations.Messages;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@SpringView(name = UserView.VIEW_NAME)
public class UserViewComponent extends VerticalLayout implements UserView {

	public UserViewComponentBundle bundle = new UserViewComponentBundle();

	@Autowired
	private UserPresenter presenter;

	private BeanFieldGroup<Client> binder;

	@PostConstruct
	@Messages({ @Message(key = "clientLabel", value = "Client {0}"),
			@Message(key = "newString", value = "New {0}"),
			@Message(key = "select", value = "Select a new {0}"),
			@Message(key = "edit", value = "Edit {0}"),
			@Message(key = "user", value = "User"),
			@Message(key = "group", value = "Group"),
			@Message(key = "client", value = "Client"),
			@Message(key = "change", value = "Change {0}") })
	private void init() {

		Client client = SustainabilityApplicationUI.getCurrent()
				.getLoggedInUser().getClient();

		// clientbar header
		HorizontalLayout clientBar = new HorizontalLayout();
		clientBar.setWidth("100%");
		
		String clientName = client.getName() == null ? bundle.select(bundle.client()) : client.getName() ;
		Label nameLabel = new Label(bundle.clientLabel(clientName));

		HorizontalLayout contendsPanel = new HorizontalLayout();
		contendsPanel.setWidth("100%");

		VerticalLayout userPanel = new VerticalLayout();
		userPanel.setWidth("50%");
		ListSelect userList = new ListSelect("Users");
		userList.addItems("Kees", "Venus", "Piet");
		userPanel.addComponent(userList);
		contendsPanel.addComponent(userPanel);

		VerticalLayout groupPanel = new VerticalLayout();
		groupPanel.setWidth("50%");
		ListSelect groupList = new ListSelect("Groups");
		groupList.addItems(Groups.GROUP_ADMINS, Groups.GROUP_AUDITORS);
		groupPanel.addComponent(groupList);
		contendsPanel.addComponent(groupPanel);

		this.addComponent(clientBar);
		this.addComponent(contendsPanel);

		// add the administrator tools
		if (SustainabilityApplicationUI.getCurrent().getLoggedInUser()
				.isAdmin()) {
			// the admin panel for the client
			HorizontalLayout clientAdminPanel = new HorizontalLayout();
			Button changeClientButton = new Button(bundle.change(bundle
					.clientLabel()));
			Button newClientButton = new Button(bundle.newString(bundle
					.clientLabel()));
			clientAdminPanel.addComponent(changeClientButton);
			clientAdminPanel.addComponent(newClientButton);
			clientBar.addComponent(clientAdminPanel);
			clientBar.setComponentAlignment(clientAdminPanel,
					Alignment.MIDDLE_RIGHT);

			HorizontalLayout userAdminPanel = new HorizontalLayout();
			Button editUserButton = new Button(bundle.edit(bundle.user()));
			Button newUserButton = new Button(bundle.newString(bundle.user()));
			userAdminPanel.addComponent(editUserButton);
			userAdminPanel.addComponent(newUserButton);
			userPanel.addComponent(userAdminPanel);
			userAdminPanel.setWidth("100%");
			userPanel.setComponentAlignment(userAdminPanel,
					Alignment.BOTTOM_CENTER);

			HorizontalLayout groupAdminPanel = new HorizontalLayout();
			Button editGroupButton = new Button(bundle.edit(bundle.group()));
			Button newGroupButton = new Button(bundle.newString(bundle.group()));
			groupAdminPanel.addComponent(editGroupButton);
			groupAdminPanel.addComponent(newGroupButton);
			groupPanel.addComponent(groupAdminPanel);
			groupAdminPanel.setWidth("100%");
			groupPanel.setComponentAlignment(groupAdminPanel,
					Alignment.BOTTOM_CENTER);

		}
	}

	@Message(key = "message", value = "Saving failed")
	private void saveButton(ClickEvent event) {
		try {
			binder.commit();

		} catch (CommitException e) {
			Notification
					.show(bundle.message(), Notification.Type.ERROR_MESSAGE);
			e.printStackTrace();
		}
		presenter.save();
	}

	@Override
	public void fireViewEvent(ViewEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
