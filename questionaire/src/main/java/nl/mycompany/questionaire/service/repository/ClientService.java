package nl.mycompany.questionaire.service.repository;

import java.util.List;
import java.util.Optional;

import nl.mycompany.questionaire.domain.Client;

public interface ClientService {
	
	public Client saveClient(Client client);
	
	public Optional<Client> findClient(String name);
	
	public List<Client> findAllClients();
	
	public void deleteClient(String name);

}
