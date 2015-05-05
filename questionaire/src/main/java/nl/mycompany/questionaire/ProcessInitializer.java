package nl.mycompany.questionaire;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import nl.mycompany.questionaire.domain.Client;
import nl.mycompany.questionaire.identity.Groups;
import nl.mycompany.questionaire.repository.ClientRepository;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcessInitializer implements Groups {

	@Autowired
	private IdentityService identityService;
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private ClientRepository clientRepo;

	 private static final Logger LOG = Logger
	 .getLogger(ProcessInitializer.class);

	@PostConstruct
	public void setUp() {
		setUpGroups();
		setUpClients();
		setUpUsers();
		deployProcess();
		
	}
	
	@PreDestroy
	private void kill(){
	 deleteUsers();	
	}
	
	private void deleteUsers()
	{
		identityService.deleteUser("client");
		identityService.deleteUser("manager");
		identityService.deleteUser("auditor");
	}

	public void setUpUsers() {
		 LOG.info("Creating users for Activiti");
		addUser("client", "Tom", "Traveler", "tom@foobar.net", new String[] {GROUP_CLIENTS, "companyA"});
		addUser("manager", "Mike", "Manager", "mike@foobar.net", new String[] {GROUP_MANAGERS});
		addUser("auditor", "Steven", "Secretary", "steven@foobar.net",
				new String[] {GROUP_AUDITORS});
		addUser("admin", "Heman", "Master of the Universe", "heman@masters.net",
				new String[] {GROUP_ADMINS});

	}

	private void addUser(String username, String firstName, String lastName,
			String email, String[] groups) {
		if (!userExists(username)) {

			 LOG.info("Adding user {" + username + "} (firstName = {"
			 + firstName + "}, lastName = {" + lastName + "}, email = {"
			 + email + "}, groups = {" + groups + "})");
			User user = identityService.newUser(username);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmail(email);
			user.setPassword("p");
			identityService.saveUser(user);
			
			for(String group : groups)
			{
			if (group != null) {
				identityService.createMembership(username, group);
			}}
		}
	}
	
	private void setUpClients()
	{
		List<Client> clients = clientRepo.findAll();
		for(Client client : clients)
		{
		addGroup(client.getName(), client.getName());
		}
	}

	private boolean userExists(String username) {
		return identityService.createUserQuery().userId(username)
				.singleResult() != null;
	}

	private void setUpGroups() {
		 LOG.info("Creating user groups for Activiti");
		addGroup(GROUP_CLIENTS, "Clients");
		addGroup(GROUP_AUDITORS, "Auditors");
		addGroup(GROUP_MANAGERS, "Managers");
		addGroup(GROUP_ADMINS, "Administrators");
		addGroup(GROUP_CLIENTADMIN, "ClientAdministrators");
		addGroup(GROUP_EMPLOYEE, "Employees");
		
		
	}

	private void addGroup(String groupId, String name) {
		if (!groupExists(groupId)) {
			 LOG.info("Adding group {" + groupId + "} (name = {" + name +
			 "}");
			Group group = identityService.newGroup(groupId);
			group.setName(name);
			identityService.saveGroup(group);
		}
	}

	private boolean groupExists(String groupId) {
		return identityService.createGroupQuery().groupId(groupId)
				.singleResult() != null;
	}
	
	private void deployProcess() {
		if (!isDeployed()) {
			 LOG.info("Deploying process");
			repositoryService.createDeployment()
					.addClasspathResource("diagrams/AnswerQuestion.bpmn")
					.deploy();
		}
	}

	private boolean isDeployed() {
		return repositoryService.createProcessDefinitionQuery()
				.processDefinitionKey("answerQuestionProcess").singleResult() != null;
	}
}
