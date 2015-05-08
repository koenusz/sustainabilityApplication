package nl.mycompany.webapp.ui.client;

import java.util.List;

import nl.mycompany.questionaire.domain.Client;
import nl.mycompany.questionaire.service.repository.ClientService;
import nl.mycompany.webapp.abstracts.AbstractPresenter;
import nl.mycompany.webapp.abstracts.View;
import nl.mycompany.webapp.event.ClientChangedEvent;
import nl.mycompany.webapp.event.EventBus;
import nl.mycompany.webapp.ui.user.ChangeClientPopup;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class ClientPresenter extends AbstractPresenter<ClientView> {
	
	private static final Logger LOG = Logger.getLogger(ClientPresenter.class);
	
	private Client client = new Client();
	
	private ChangeClientPopup popup;
	
	@Autowired
	private EventBus eventBus;
	
	@Autowired
	private ClientService service;
	
	public void save() {
		service.saveClient(client);
	}

	public List<Client> findAllClients()
	{
		return service.findAllClients();
	}

	public Client getClient() {
		return client;
	}
	
	public void setChangeClientPopup(ChangeClientPopup popup)
	{
		this.popup = popup;
	}

	public void setClient(Client client) {
		
		LOG.debug("setting client " + client);
		this.client = client;
		
		if(!(client.getName()== null)){
			
		View view = popup == null ? getView() : popup;	
		eventBus.fireViewEvent(new ClientChangedEvent(view, client));
		}
	}

}
