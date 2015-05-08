package nl.mycompany.questionaire.identity;

import nl.mycompany.questionaire.domain.Client;

public class AuthenticatedUser {

	private String name;

	private Client client;

	private boolean isClientAdmin;

	private boolean isAdmin;

	public AuthenticatedUser(String name, Client client, boolean isAdmin, boolean isClientAdmin) {
		this.name = name;
		this.client = client;
		this.isAdmin = isAdmin;
		this.isClientAdmin = isClientAdmin;		
	}

	public String getName() {
		return name;
	}

	public Client getClient() {
		return client;
	}
	
	public void setClient(Client client) {
		this.client = client;
	}

	public boolean isClientAdmin() {
		return isClientAdmin;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

}
