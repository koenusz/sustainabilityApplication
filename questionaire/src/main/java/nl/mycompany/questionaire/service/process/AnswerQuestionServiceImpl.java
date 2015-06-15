package nl.mycompany.questionaire.service.process;

import java.util.HashMap;

import nl.mycompany.core.domain.validation.ValidationUtil;
import nl.mycompany.questionaire.domain.Question;
import nl.mycompany.questionaire.identity.Groups;
import nl.mycompany.questionaire.identity.RequireGroup;
import nl.mycompany.questionaire.repository.QuestionRepository;
import nl.mycompany.questionaire.service.repository.QuestionService;

import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLinkType;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnswerQuestionServiceImpl extends AbstractProcessServiceImpl
		implements AnswerQuestionService {

	@Autowired
	QuestionService service;

	@Override
	@Transactional
	@RequireGroup(Groups.GROUP_CLIENTS)
	public String startQuestionProcess(Question question) {

		ValidationUtil.validateAndThrow(validator, question);

		question = service.saveQuestion(question);
		String businessKey = Long.toString(question.getId());

		// Also set the question as process-variable
		HashMap<String, Object> variables = new HashMap<String, Object>();
		variables.put("question", question);

		ProcessInstance instance = runtimeService.startProcessInstanceByKey(
				"answerQuestionProcess", businessKey, variables);
		
		setCurrentUserAsOwner(instance.getId());
		claimCurrentTaskByCurrentUser(instance.getId());
		return instance.getId();
	}

	
	
	@Override
	public void assignQuestionToUser(Question question, String userId) {
		
		ProcessInstance instance = findProcessInstanceForQuestion(question);
		Task task = findActiveTask(instance);
		taskService.setAssignee(task.getId(), userId);
	}

	@Override
	@RequireGroup(Groups.GROUP_CLIENTS)
	public void answerQuestion(Question question, String answer) {
		question.setAnswer(answer);
		service.saveQuestion(question);
		final ProcessInstance processInstance = findProcessInstanceForQuestion(question);
		final Task answerQuestionTask = findTaskByDefinitionKey(
				processInstance, "answerQuestionTask");
		taskService.complete(answerQuestionTask.getId());

	}

	@Override
	@RequireGroup(Groups.GROUP_AUDITORS)
	public void auditQuestion(Question question, String result) {

		question.setAuditResult(result);
		service.saveQuestion(question);
		final ProcessInstance processInstance = findProcessInstanceForQuestion(question);
		final Task auditQuestionTask = findTaskByDefinitionKey(processInstance,
				"auditQuestionTask");
		runtimeService.setVariable(processInstance.getProcessInstanceId(),
				"question", question);
		taskService.complete(auditQuestionTask.getId());

	}

	@Override
	public Question findQuestionByProcessInstance(String processInstanceId) {
		ProcessInstance processInstance = runtimeService
				.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		if (processInstance == null || processInstance.getBusinessKey() == null) {
			return null;
		}
		return findQuestionById(Long
				.parseLong(processInstance.getBusinessKey()));
	}

	@Override
	public Question findQuestionById(Long id) {

		return service.findQuestion(id).get();
	}

	public ProcessInstance findProcessInstanceForQuestion(Question question) {
		return runtimeService
				.createProcessInstanceQuery()
				.processInstanceBusinessKey(Long.toString(question.getId()),
						"answerQuestionProcess").singleResult();
	}

	

}
