package nl.mycompany.webapp.ui.user;

import nl.mycompany.questionaire.domain.Client;
import nl.mycompany.webapp.abstracts.View;
import nl.mycompany.webapp.abstracts.ViewEvent;
import nl.mycompany.webapp.ui.client.ClientPresenter;

import org.activiti.engine.identity.User;

import com.github.peholmst.i18n4vaadin.annotations.Message;
import com.github.peholmst.i18n4vaadin.annotations.Messages;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.VerticalLayout;

public class AddClientPopup extends VerticalLayout implements View{

	private static final long serialVersionUID = -5706202138398241947L;

	private UserPresenter userPresenter;
	
	private ClientPresenter clientPresenter;
	
	private User user;

	private ChangeClientPopupBundle bundle = new ChangeClientPopupBundle();
	
	private Client selectedClient;
	
	public AddClientPopup(UserPresenter userPresenter, ClientPresenter clientPresenter, User selectedUser) {
		super();
		this.userPresenter = userPresenter;
		this.clientPresenter = clientPresenter;
		this.user = selectedUser;
		this.init();
	}


	@Messages({ @Message(key = "combo.caption", value = "Select a Client"),
			@Message(key = "button.caption", value = "Save") })
	public void init() {
		
		BeanItemContainer<Client> container = new BeanItemContainer<>(
				Client.class);

		container.addAll(clientPresenter.findAllClients());

		ComboBox selectCombo = new ComboBox(bundle.combo_caption(), container);
		selectCombo.setItemCaptionMode(ItemCaptionMode.PROPERTY);
		selectCombo.setItemCaptionPropertyId("name");
		this.addComponent(selectCombo);
		selectCombo.addValueChangeListener(event -> selectedClient = (Client) selectCombo
				.getValue());

		this.addComponent(new Button(bundle.button_caption(),
				this::buttonClick ));
		
	}
	
	private void buttonClick(ClickEvent event)
	{
		userPresenter.createMembership(user, selectedClient.getName());
		userPresenter.getView().setGroups(userPresenter.findGroupsByUser(user));
	}

	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fireViewEvent(ViewEvent event) {
		// TODO Auto-generated method stub
		
	}
}