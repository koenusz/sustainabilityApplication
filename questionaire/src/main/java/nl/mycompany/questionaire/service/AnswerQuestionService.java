package nl.mycompany.questionaire.service;

import org.activiti.engine.runtime.ProcessInstance;

import nl.mycompany.questionaire.domain.Question;

public interface AnswerQuestionService {
	
	public Question startQuestionProcess(Question question);

	public void answerQuestion(Question question, String answer);
	
	public void auditQuestion(Question question, String result);
	
	public Question findQuestionByProcessInstance(String processInstanceId);
	
	public Question findQuestionById(Long Id);
	
	public ProcessInstance getProcessInstanceForQuestion(Question question);

	public void claimCurrentTaskByCurrentUser(String processInstanceId); 
	
}
