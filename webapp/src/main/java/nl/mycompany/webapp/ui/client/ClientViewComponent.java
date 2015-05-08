package nl.mycompany.webapp.ui.client;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import nl.mycompany.questionaire.domain.Client;
import nl.mycompany.webapp.abstracts.ViewEvent;
import nl.mycompany.webapp.ui.user.UserPresenter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.peholmst.i18n4vaadin.annotations.Message;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;

@SuppressWarnings("serial")
@SpringView(name = ClientView.VIEW_NAME)
public class ClientViewComponent extends FormLayout implements ClientView {
	

	private static final Logger LOG = Logger.getLogger(ClientViewComponent.class);
	
	public ClientViewComponentBundle bundle = new ClientViewComponentBundle();
	
	@Autowired
	private ClientPresenter presenter;
	
	@Autowired
	private UserPresenter userPresenter;

	private BeanFieldGroup<Client> binder;
	

	@PostConstruct
	@Message(key="newClient" , value="New Client")
	private void init() {
		presenter.setView(this);
		// BeanFieldGroup automatically adds validators
		binder = new BeanFieldGroup<Client>(Client.class);
		binder.setItemDataSource(presenter.getClient());
		this.addComponent(binder.buildAndBind("Name", "name"));

		// Buffer the form content
		binder.setBuffered(true);
		this.addComponent(new Button("Save", this::saveButton));
		
		presenter.getClient().setName(bundle.newClient());
		
	}

	@Message(key= "message", value = "Saving failed")
	private void saveButton(ClickEvent event) {
		try {
			binder.commit();

		} catch (CommitException e) {
			Notification
					.show(bundle.message(), Notification.Type.ERROR_MESSAGE);
			e.printStackTrace();
		}
		presenter.save();
		presenter.navigateBack();
	}

	@Override
	public void fireViewEvent(ViewEvent event) {

		
		
	}


	
	@Override
	public void enter(ViewChangeEvent event) {
		
	}

	@PreDestroy
	public void tearDown()
	{
		presenter.setChangeClientPopup(null);
	}

}
