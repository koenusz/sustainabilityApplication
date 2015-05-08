package nl.mycompany.questionaire.service.repository;

import java.util.List;
import java.util.Optional;

import nl.mycompany.questionaire.domain.Client;
import nl.mycompany.questionaire.exception.NoClientException;
import nl.mycompany.questionaire.identity.Groups;
import nl.mycompany.questionaire.repository.ClientRepository;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService{
	
	@Autowired 
	private ClientRepository clientRepository;
	
	@Autowired
	private IdentityService identityService;

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

	@Override
	public List<User> findUsersByClient(Client client) {
		
		
		
		//the clientname should be added as group to the engine.
		return identityService.createUserQuery().memberOfGroup(client.getName()).list();	
	}
	
	public Client findClientByUserName(String username) throws NoClientException {
		List<Group> membership = identityService.createGroupQuery()
				.groupMember(username).list();

		for (Group group : membership) {
			Optional<Client> client = clientRepository.findOne(group.getId());
			if (client.isPresent()) {
				return client.get();
			}
			// only admins are allowed to enter the application with access to
			// multiple clients.
			if (Groups.GROUP_ADMINS.equals(group.getId())) {
				return new Client();
			}

		}

		throw new NoClientException(username);
	}

}
