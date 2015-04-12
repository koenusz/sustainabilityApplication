package nl.mycompany.questionaire;

import javax.annotation.PostConstruct;

import nl.mycompany.questionaire.identity.Groups;

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

	 private static final Logger LOG = Logger
	 .getLogger(ProcessInitializer.class);

	@PostConstruct
	public void setUp() {
		setUpGroups();
		setUpUsers();
		deployProcess();
	}

	public void setUpUsers() {
		 LOG.info("Creating users for Activiti");
		addUser("client", "Tom", "Traveler", "tom@foobar.net", GROUP_CLIENTS);
		addUser("manager", "Mike", "Manager", "mike@foobar.net", GROUP_MANAGERS);
		addUser("auditor", "Steven", "Secretary", "steven@foobar.net",
				GROUP_AUDITORS);

	}

	private void addUser(String username, String firstName, String lastName,
			String email, String group) {
		if (!userExists(username)) {

			 LOG.info("Adding user {" + username + "} (firstName = {"
			 + firstName + "}, lastName = {" + lastName + "}, email = {"
			 + email + "}, group = {" + group + "})");
			User user = identityService.newUser(username);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmail(email);
			user.setPassword("p");
			identityService.saveUser(user);
			if (group != null) {
				identityService.createMembership(username, group);
			}
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