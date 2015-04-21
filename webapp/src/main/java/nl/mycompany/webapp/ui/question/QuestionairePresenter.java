package nl.mycompany.webapp.ui.question;

import nl.mycompany.questionaire.domain.Question;
import nl.mycompany.questionaire.service.AnswerQuestionService;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.peholmst.i18n4vaadin.annotations.Message;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
public class QuestionairePresenter {

	@Autowired
	TaskService taskService;

	@Autowired
	IdentityService identityService;

	@Autowired
	AnswerQuestionService answerQuestionService;
	
	@Autowired
	RuntimeService runtimeService;

	
	QuestionairePresenterBundle bundle = new QuestionairePresenterBundle();
	
	SingleQuestionComponent compenent;

	

	public void startAnswerQuestionProcess(Question question)
	{
		Question returned = answerQuestionService.startQuestionProcess(question);
		
		String status = answerQuestionService.getProcessInstanceForQuestion(returned).getActivityId();
		this.compenent.updateQuestionComponent(status);
	}
	
	@Message(key = "defaultStatus", value = "pending")
	public String getQuestionStatus(Question question) {

		String status = answerQuestionService
				.getProcessInstanceForQuestion(question) != null ? answerQuestionService
				.getProcessInstanceForQuestion(question).getActivityId()
				: bundle.defaultStatus();

		return status;
	}

	public SingleQuestionComponent getCompenent() {
		return compenent;
	}

	public void setCompenent(SingleQuestionComponent compenent) {
		this.compenent = compenent;
	}
}
