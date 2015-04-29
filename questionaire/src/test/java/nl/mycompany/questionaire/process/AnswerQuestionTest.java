package nl.mycompany.questionaire.process;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import nl.mycompany.questionaire.conf.ApplicationConfiguration;
import nl.mycompany.questionaire.domain.Question;
import nl.mycompany.questionaire.identity.AccessDeniedException;
import nl.mycompany.questionaire.identity.Groups;
import nl.mycompany.questionaire.repository.QuestionRepository;
import nl.mycompany.questionaire.service.Result;
import nl.mycompany.questionaire.service.process.AnswerQuestionService;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
public class AnswerQuestionTest {

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private IdentityService identityService;

	@Autowired
	QuestionRepository repo;

	@Autowired
	@Rule
	public ActivitiRule activitiSpringRule;

	@Autowired
	public AnswerQuestionService service;

	private static final Logger LOG = Logger
			.getLogger(AnswerQuestionTest.class);

	@Test
	@Deployment(resources = "diagrams/AnswerQuestion.bpmn")
	public void acceptedAuditQuestionTaskTest() {
		Question question = createQuestion();

		// set the authenticated user for authorization
		Authentication.setAuthenticatedUserId("testClient");

		service.startQuestionProcess(question);

		// check if the process is there

		ProcessInstance processInstance = service
				.getProcessInstanceForQuestion(question);
		assertNotNull(processInstance);

		// claim the task
		service.claimCurrentTaskByCurrentUser(processInstance.getId());

		service.answerQuestion(question, "de kaboutertjes");

		// set the authenticated user for authorization
		Authentication.setAuthenticatedUserId("testAuditor");

		service.auditQuestion(question, Result.RESULT_ACCEPTED);

		// check the database
		String remark = repo.findOne(question.getId()).get().getAuditResult();
		assertEquals(Result.RESULT_ACCEPTED, remark);

	}

	@Test
	@Deployment(resources = "diagrams/AnswerQuestion.bpmn")
	public void deniedAuditQuestionTaskTest() {
		Question question = createQuestion();

		// set the authenticated user for authorization
		Authentication.setAuthenticatedUserId("testClient");

		service.startQuestionProcess(question);

		// check if the process is there

		ProcessInstance processInstance = service
				.getProcessInstanceForQuestion(question);
		assertNotNull(processInstance);

		// claim the task
		service.claimCurrentTaskByCurrentUser(processInstance.getId());

		service.answerQuestion(question, "de kaboutertjes");

		// set the authenticated user for authorization
		Authentication.setAuthenticatedUserId("testAuditor");

		service.auditQuestion(question, Result.RESULT_DENIED);

		// check the database
		String remark = repo.findOne(question.getId()).get().getAuditResult();
		assertEquals(Result.RESULT_DENIED, remark);

		// Check if a answer question task is available
		Task theAuditTask = taskService.createTaskQuery()
				.processInstanceId(processInstance.getId()).singleResult();
		assertNotNull(theAuditTask);
		assertEquals(processInstance.getId(),
				theAuditTask.getProcessInstanceId());
		LOG.debug("Task:  " + theAuditTask.getName());
		assertEquals("answerQuestionTask", theAuditTask.getName());
	}

	@Test
	@Deployment(resources = "diagrams/AnswerQuestion.bpmn")
	public void answerQuestionTaskTest() {
		Question question = createQuestion();

		// set the authenticated user for authorization
		Authentication.setAuthenticatedUserId("testClient");

		service.startQuestionProcess(question);

		// check if the process is there

		ProcessInstance processInstance = service
				.getProcessInstanceForQuestion(question);
		assertNotNull(processInstance);

		// claim the task

		Task theAnswerTask = taskService.createTaskQuery()
				.processInstanceId(processInstance.getId())
				.taskCandidateGroup("clients").singleResult();

		taskService.claim(theAnswerTask.getId(), "testClient");

		service.answerQuestion(question, "de kaboutertjes");

		// Check if a task is available, queued for an auditor
		Task theAuditTask = taskService.createTaskQuery()
				.taskCandidateGroup("auditors").singleResult();
		assertNotNull(theAuditTask);
		assertEquals(processInstance.getId(),
				theAuditTask.getProcessInstanceId());
		LOG.debug("Task:  " + theAuditTask.getName());
		assertEquals("auditQuestionTask", theAuditTask.getName());

	}

	@Test(expected = AccessDeniedException.class)
	@Deployment(resources = "diagrams/AnswerQuestion.bpmn")
	public void unauthorizedAnswerQuestionTaskTest() {
		Question question = createQuestion();

		// set the authenticated user for authorization
		Authentication.setAuthenticatedUserId("testManager");

		service.startQuestionProcess(question);

	}

	@Test
	@Deployment(resources = "diagrams/AnswerQuestion.bpmn")
	public void simpleProcessTest() {
		ProcessInstance processInstance = runtimeService
				.startProcessInstanceByKey("answerQuestionProcess");
		Assert.assertNotNull(processInstance);

		// Check if a task is available for the given process
		Assert.assertEquals(
				1,
				taskService.createTaskQuery()
						.processInstanceId(processInstance.getId()).count());
	}

	@Before
	public void setUp() {
		User client = identityService.newUser("testClient");
		client.setFirstName("Vincent");
		client.setLastName("testClient");
		identityService.saveUser(client);
		identityService.createMembership("testClient", Groups.GROUP_CLIENTS);

		User manager = identityService.newUser("testManager");
		client.setFirstName("Willem");
		client.setLastName("testManager");
		identityService.saveUser(manager);
		identityService.createMembership("testManager", Groups.GROUP_MANAGERS);

		User auditor = identityService.newUser("testAuditor");
		client.setFirstName("Wendelien");
		client.setLastName("testAuditor");
		identityService.saveUser(auditor);
		identityService.createMembership("testAuditor", Groups.GROUP_AUDITORS);

	}

	@After
	public void tearDown() {

		deleteQuestions();
		try {
			identityService.deleteUser("testClient");
			identityService.deleteUser("testManager");
			identityService.deleteUser("testAuditor");

		} catch (Throwable t) {
			// ignore
		}
	}

	private Question createQuestion() {
		Question q = new Question();
		q.setDomain("test");
		q.setQuestionText("Wie heeft er in de prullenbak gepoept?");

		LOG.debug("Question: " + q.getId() + " is created");

		repo.save(q);
		LOG.debug("Question: " + q.getId() + " is saved");

		return q;

	}

	private void deleteQuestions() {
		// clean up database
		List<Question> questions = repo.findAll();
		for (Question question : questions) {
			LOG.debug("deleting question: " + question.getId());
			repo.delete(question);
		}
	}

}
