package nl.mycompany.questionaire.service;

import java.util.HashMap;

import nl.mycompany.core.domain.validation.ValidationUtil;
import nl.mycompany.questionaire.domain.Question;
import nl.mycompany.questionaire.identity.Groups;
import nl.mycompany.questionaire.identity.RequireGroup;
import nl.mycompany.questionaire.repository.QuestionRepository;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AnswerQuestionServiceImpl extends AbstractServiceImpl implements AnswerQuestionService{
	
	@Autowired
	QuestionRepository questionRepository;
	
    @Override
    @Transactional
    @RequireGroup(Groups.GROUP_CLIENTS)
    public Question startQuestionProcess(Question question) {
    	//TODO validation
        ValidationUtil.validateAndThrow(validator, question);
    	//TODO: authorization
        //question.setRequesterUserId(Authentication.getAuthenticatedUserId());
        //question.setRequesterUserName(getFullNameOfUser(question.getRequesterUserId()));
        question = questionRepository.save(question);
        String businessKey = Long.toString(question.getId());
        
        // Also set the request as process-variable
        HashMap<String, Object> variables = new HashMap<String, Object>();
        variables.put("request", question);
        
        runtimeService.startProcessInstanceByKey("answerQuestionProcess", businessKey, variables); 
        return question;
    }
	

	@Override
	@RequireGroup(Groups.GROUP_CLIENTS)
	public void answerQuestion(Question question, String answer) {
		question.setAnswer(answer);
		questionRepository.save(question);
		 final ProcessInstance processInstance = getProcessInstanceForQuestion(question);
	     final Task answerQuestionTask = findTaskByDefinitionKey(processInstance, "answerQuestionTask");
	        taskService.complete(answerQuestionTask.getId());
		
		
	}

	@Override
	@RequireGroup(Groups.GROUP_AUDITORS)
	public void auditQuestion(Question question, String result) {
		
		question.setAuditResult(result);
		questionRepository.save(question);
		 final ProcessInstance processInstance = getProcessInstanceForQuestion(question);
	     final Task auditQuestionTask = findTaskByDefinitionKey(processInstance, "auditQuestionTask");
	     runtimeService.setVariable(processInstance.getProcessInstanceId(), "question", question);
	        taskService.complete(auditQuestionTask.getId());
		
	}

	@Override
	public Question findQuestionByProcessInstance(String processInstanceId) {
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (processInstance == null || processInstance.getBusinessKey() == null) {
            return null;
        }
        return findQuestionById(Long.parseLong(processInstance.getBusinessKey()));
	}

	@Override
	public Question findQuestionById(Long id) {
		
		return questionRepository.findOne(id).get();
	}
	
    public ProcessInstance getProcessInstanceForQuestion(Question question) {
        return runtimeService.createProcessInstanceQuery().
                processInstanceBusinessKey(Long.toString(question.getId()), "answerQuestionProcess").
                singleResult();
    }

}
