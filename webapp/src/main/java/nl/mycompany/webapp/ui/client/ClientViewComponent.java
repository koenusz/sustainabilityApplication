package nl.mycompany.webapp.ui.client;

import javax.annotation.PostConstruct;

import nl.mycompany.questionaire.domain.Client;
import nl.mycompany.webapp.abstracts.ViewEvent;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.peholmst.i18n4vaadin.annotations.Message;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;

@SuppressWarnings("serial")
@SpringView(name = ClientView.VIEW_NAME)
public class ClientViewComponent extends FormLayout implements ClientView {
	
	public ClientViewComponentBundle bundle = new ClientViewComponentBundle();

	@Autowired
	private ClientPresenter presenter;

	private BeanFieldGroup<Client> binder;

	@PostConstruct
	@Message(key="newClient" , value="New Client")
	private void init() {

		// BeanFieldGroup automatically adds validators
		binder = new BeanFieldGroup<Client>(Client.class);
		binder.setItemDataSource(presenter.getClient());
		this.addComponent(binder.buildAndBind("Name", "name"));

		// Buffer the form content
		binder.setBuffered(true);
		this.addComponent(new Button("Save", this::saveButton));

	}

	@Message(key="message" , value="Saving failed")
	private void saveButton(ClickEvent event)
	{
		 try {
			binder.commit();
			
		} catch (CommitException e) {
			Notification.show(bundle.message(), Notification.Type.ERROR_MESSAGE);
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
