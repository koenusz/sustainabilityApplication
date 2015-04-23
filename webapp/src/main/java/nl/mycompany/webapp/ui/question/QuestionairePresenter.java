package nl.mycompany.webapp.ui.question;

import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import nl.mycompany.questionaire.domain.Question;
import nl.mycompany.questionaire.service.AnswerQuestionService;
import nl.mycompany.webapp.SustainabilityApplicationUI;
import nl.mycompany.webapp.abstracts.AbstractPresenter;
import nl.mycompany.webapp.abstracts.ViewEvent;
import nl.mycompany.webapp.aspect.EngineLogin;
import nl.mycompany.webapp.event.ActivitiEventSubscriber;
import nl.mycompany.webapp.event.EventTranslator;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.peholmst.i18n4vaadin.annotations.Message;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

@SpringComponent()
@UIScope
public class QuestionairePresenter extends AbstractPresenter<QuestionaireView> implements  ActivitiEventSubscriber {

	private static final Logger LOG = Logger
			.getLogger(QuestionairePresenter.class);

	@Autowired
	TaskService taskService;

	@Autowired
	IdentityService identityService;

	@Autowired
	AnswerQuestionService answerQuestionService;

	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	EventTranslator eventTranslator;

	QuestionairePresenterBundle bundle = new QuestionairePresenterBundle();
	
	@PostConstruct
	public void init(){
		LOG.debug("subscribing");
		SustainabilityApplicationUI.addActivityEventSubscriber(this, Arrays.asList(ActivitiEventType.TASK_CREATED));
	}
	
	@PreDestroy
	public void kill()
	{
		SustainabilityApplicationUI.removeActivityEventSubscriber(this);
	}

	@EngineLogin
	public void startAnswerQuestionProcess(Question question) {
		//SustainabilityApplicationUI.engineLogin();
		Question returned = answerQuestionService
				.startQuestionProcess(question);
		//SustainabilityApplicationUI.engineLogout();
		// String status = answerQuestionService.getProcessInstanceForQuestion(
		// returned).getActivityId();
	}

	@Message(key = "defaultStatus", value = "pending")
	public String getQuestionStatus(Question question) {

		String status = answerQuestionService
				.getProcessInstanceForQuestion(question) != null ? answerQuestionService
				.getProcessInstanceForQuestion(question).getActivityId()
				: bundle.defaultStatus();

		return status;
	}

	@Override
	public void fireViewEvent(ViewEvent event) {
		
		this.getView().fireViewEvent(event);
	}

	@Override
	public void onEvent(ActivitiEvent event) {
		LOG.debug("recieved Event");
		fireViewEvent(eventTranslator.translate(event, getView()));
		
	};

}
