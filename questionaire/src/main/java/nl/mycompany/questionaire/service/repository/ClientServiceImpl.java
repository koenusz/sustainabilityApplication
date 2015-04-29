package nl.mycompany.questionaire.service.repository;

import java.util.List;
import java.util.Optional;

import nl.mycompany.questionaire.domain.Client;
import nl.mycompany.questionaire.repository.ClientRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class ClientServiceImpl implements ClientService{
	
	@Autowired 
	private ClientRepository clientRepository;

	@Override
	public Client saveClient(Client client) {
		return clientRepository.save(client);
	}

	@Override
	public Optional<Client> findClient(String name) {
		return clientRepository.findOne(name);
	}

	@Override
	public void deleteClient(String name) {
		clientRepository.delete(clientRepository.findOne(name).get());
		
	}

	@Override
	public List<Client> findAllClients() {
		return clientRepository.findAll();
	}

}
