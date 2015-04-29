package nl.mycompany.webapp.ui.process.answerquestion;

import nl.mycompany.questionaire.domain.Question;
import nl.mycompany.questionaire.service.process.AnswerQuestionService;
import nl.mycompany.webapp.abstracts.AbstractPresenter;
import nl.mycompany.webapp.aspect.EngineLogin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AnswerQuestionPresenter extends
		AbstractPresenter<NewAnswerQuestionProcessForm> {

	@Autowired
	NewAnswerQuestionProcessFormFactory startAnswerQuestionFormFactory;

	@Autowired
	AnswerQuestionService answerQuestionService;
	
	public AnswerQuestionPresenter(Question question)
	{
		this.question = question;
	}

	Question question;
	
	//the activityId of the task of hte answer question process
	String questionStatus;

	@EngineLogin
	public void startAnswerQuestionProcess() {
		startAnswerQuestionFormFactory.startAnswerQuestionForm();
		answerQuestionService.startQuestionProcess(question);
	}

	public String getQuestionStatus() {

		return answerQuestionService.getProcessInstanceForQuestion(question) != null ? answerQuestionService
				.getProcessInstanceForQuestion(question).getActivityId() : null;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public void setQuestionStatus(String questionStatus) {
		this.questionStatus = questionStatus;
	}

}
