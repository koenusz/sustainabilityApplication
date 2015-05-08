package nl.mycompany.webapp.ui.user;

import nl.mycompany.questionaire.domain.Client;
import nl.mycompany.webapp.abstracts.View;
import nl.mycompany.webapp.abstracts.ViewEvent;
import nl.mycompany.webapp.ui.client.ClientPresenter;

import com.github.peholmst.i18n4vaadin.annotations.Message;
import com.github.peholmst.i18n4vaadin.annotations.Messages;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.VerticalLayout;

public class ChangeClientPopup extends VerticalLayout implements View{

	private static final long serialVersionUID = -5706202138398241947L;

	ClientPresenter presenter;

	public ChangeClientPopup(ClientPresenter presenter) {
		super();
		this.presenter = presenter;
		this.init();
	}

	ChangeClientPopupBundle bundle = new ChangeClientPopupBundle();

	@Messages({ @Message(key = "combo.caption", value = "Select a Client"),
			@Message(key = "button.caption", value = "Save") })
	public void init() {
		presenter.setChangeClientPopup(this);
		BeanItemContainer<Client> container = new BeanItemContainer<>(
				Client.class);

		container.addAll(presenter.findAllClients());

		ComboBox selectCombo = new ComboBox(bundle.combo_caption(), container);
		selectCombo.setItemCaptionMode(ItemCaptionMode.PROPERTY);
		selectCombo.setItemCaptionPropertyId("name");
		this.addComponent(selectCombo);
		selectCombo.addValueChangeListener(event -> presenter.setClient((Client) selectCombo.getValue()));

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
