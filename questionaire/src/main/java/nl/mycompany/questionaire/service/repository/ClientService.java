package nl.mycompany.questionaire.service.repository;

import java.util.List;
import java.util.Optional;

import nl.mycompany.questionaire.domain.Client;
import nl.mycompany.questionaire.exception.NoClientException;

import org.activiti.engine.identity.User;

public interface ClientService {
	
	public Client saveClient(Client client);
	
	public Optional<Client> findClient(String name);
	
	public List<Client> findAllClients();
	
	public void deleteClient(String name);
	
	public List<User> findUsersByClient(Client client);
	
	public Client findClientByUserName(String username) throws NoClientException;

}
