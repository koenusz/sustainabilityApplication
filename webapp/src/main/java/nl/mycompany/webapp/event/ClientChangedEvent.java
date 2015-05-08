package nl.mycompany.webapp.event;

import nl.mycompany.questionaire.domain.Client;
import nl.mycompany.webapp.abstracts.View;
import nl.mycompany.webapp.abstracts.ViewEvent;

public class ClientChangedEvent extends ViewEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Client client;

	public ClientChangedEvent(View source, Client client) {
		super(source);
		this.client = client;
	}

	public Client getClient() {
		return client;
	}

}
