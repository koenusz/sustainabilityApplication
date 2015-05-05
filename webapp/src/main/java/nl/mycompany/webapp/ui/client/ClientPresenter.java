package nl.mycompany.webapp.ui.client;

import nl.mycompany.questionaire.domain.Client;
import nl.mycompany.questionaire.service.repository.ClientService;
import nl.mycompany.webapp.abstracts.AbstractPresenter;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class ClientPresenter extends AbstractPresenter<ClientView> {
	
	private Client client = new Client();
	
	@Autowired
	private ClientService service;
	
	public void save() {
		service.saveClient(client);
	}


	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

}
