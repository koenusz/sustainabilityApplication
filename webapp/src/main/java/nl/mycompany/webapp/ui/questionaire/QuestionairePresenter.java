package nl.mycompany.webapp.ui.questionaire;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import nl.mycompany.questionaire.domain.Question;
import nl.mycompany.questionaire.domain.Questionaire;
import nl.mycompany.questionaire.service.process.AnswerQuestionService;
import nl.mycompany.questionaire.service.repository.QuestionaireService;
import nl.mycompany.webapp.SustainabilityApplicationUI;
import nl.mycompany.webapp.abstracts.AbstractPresenter;
import nl.mycompany.webapp.abstracts.ViewEvent;
import nl.mycompany.webapp.aspect.EngineLogin;
import nl.mycompany.webapp.event.ActivitiEventSubscriber;
import nl.mycompany.webapp.event.EventTranslator;
import nl.mycompany.webapp.exception.ComponentNotFoundException;
import nl.mycompany.webapp.exception.QuestionNotFoundException;
import nl.mycompany.webapp.ui.process.answerquestion.SingleQuestionComponent;
import nl.mycompany.webapp.ui.question.QuestionairePresenterBundle;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Component;

@SpringComponent()
@UIScope
public class QuestionairePresenter extends AbstractPresenter<QuestionaireView>
		implements ActivitiEventSubscriber {

	private static final Logger LOG = Logger
			.getLogger(QuestionairePresenter.class);

	@Autowired
	private TaskService taskService;

	@Autowired
	private IdentityService identityService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private EventTranslator eventTranslator;

	@Autowired
	private QuestionaireService questionaireService;

	@Autowired
	AnswerQuestionService answerQuestionService;

	private Questionaire questionaire;

	QuestionairePresenterBundle bundle = new QuestionairePresenterBundle();

	Map<Question, SingleQuestionComponent> components = new HashMap<>();

	@PostConstruct
	public void init() {
		LOG.debug("subscribing");
		SustainabilityApplicationUI.addActivityEventSubscriber(this,
				Arrays.asList(ActivitiEventType.TASK_CREATED));

		questionaire = questionaireService
				.findLatestQuestionaireByClient(SustainabilityApplicationUI
						.getCurrent().getLoggedInUser().getClient());
	}

	@PreDestroy
	public void tearDown() {

		SustainabilityApplicationUI.removeActivityEventSubscriber(this);
	}

	@EngineLogin
	public void startAnswerQuestionProcess(SingleQuestionComponent component) {

		try {
			answerQuestionService
					.startQuestionProcess(findQuestionByComponent(component));
		} catch (QuestionNotFoundException e) {
			e.printStackTrace();
		}
	}

	public String getQuestionStatus(SingleQuestionComponent component) {

		Question question = null;

		try {
			question = findQuestionByComponent(component);
		} catch (QuestionNotFoundException e) {
			e.printStackTrace();
		}
		return answerQuestionService.getProcessInstanceForQuestion(question) != null ? answerQuestionService
				.getProcessInstanceForQuestion(question).getActivityId() : "new";
	}

	private Question findQuestionByComponent(SingleQuestionComponent component)
			throws QuestionNotFoundException {
		for (Question question : components.keySet()) {
			if (components.get(question).equals(component)) {
				return question;
			}
		}
		throw new QuestionNotFoundException("Question not found for component "
				+ component.getId());
	}

	public SingleQuestionComponent findCompenentByQuestion(Question question)
			throws ComponentNotFoundException {
		
		SingleQuestionComponent result = null;
		for (Question componentQuestion : components.keySet())
		{
			if( componentQuestion.getId() == question.getId())
			{
			 result = components.get(componentQuestion);
			}
		}
		
		if (result == null) {
			throw new ComponentNotFoundException(
					"Component not found for question " + question.getId());
		}
		return result;
	}

	public void addComponent(Question question, SingleQuestionComponent component) {
		
		this.components.put(question, component);
	}

	public void removeComponent(SingleQuestionComponent component) {
		this.components.remove(component);
	}

	public Questionaire getQuestionaire() {
		return questionaire;
	}

	public void setQuestionaire(Questionaire questionaire) {
		this.questionaire = questionaire;
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
