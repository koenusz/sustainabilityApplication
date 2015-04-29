package nl.mycompany.questionaire.identity;

import nl.mycompany.questionaire.domain.Client;

public class AuthenticatedUser {
	
	private String name;
	
	private Client client;
	
	
	public AuthenticatedUser(String name, Client client)
	{
		this.name = name;
		this.client = client;
	}

	public String getName() {
		return name;
	}
	
	public Client getClient() {
		return client;
	}
	

}
