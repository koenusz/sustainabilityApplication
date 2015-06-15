package nl.mycompany.questionaire.service.process;

import nl.mycompany.questionaire.domain.Question;

import org.activiti.engine.runtime.ProcessInstance;

public interface AnswerQuestionService extends AbstractProcessService {
	/**
	 * 
	 * @param question
	 * @return process Id of the newly created process
	 */
	public String startQuestionProcess(Question question);
	
	public void assignQuestionToUser(Question question, String userId);

	public void answerQuestion(Question question, String answer);
	
	public void auditQuestion(Question question, String result);
	
	public Question findQuestionByProcessInstance(String processInstanceId);
	
	public Question findQuestionById(Long Id);
	
	public ProcessInstance findProcessInstanceForQuestion(Question question);

	
	
}
