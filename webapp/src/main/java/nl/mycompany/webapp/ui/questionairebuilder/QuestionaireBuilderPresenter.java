package nl.mycompany.webapp.ui.questionairebuilder;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import nl.mycompany.questionaire.domain.Question;
import nl.mycompany.questionaire.domain.Questionaire;
import nl.mycompany.questionaire.service.repository.QuestionService;
import nl.mycompany.questionaire.service.repository.QuestionaireService;
import nl.mycompany.webapp.abstracts.AbstractPresenter;
import nl.mycompany.webapp.abstracts.ViewEvent;
import nl.mycompany.webapp.event.ActivitiEventSubscriber;
import nl.mycompany.webapp.ui.question.QuestionairePresenterBundle;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

@SpringComponent()
@UIScope
public class QuestionaireBuilderPresenter extends
		AbstractPresenter<QuestionaireBuilderView> implements
		ActivitiEventSubscriber {

	private static final Logger LOG = Logger
			.getLogger(QuestionaireBuilderPresenter.class);

	@Autowired
	private QuestionaireService service;

	@Autowired
	private QuestionService questionService;

	private Questionaire questionaire;

	QuestionairePresenterBundle bundle = new QuestionairePresenterBundle();

	public static final String NAME = "name";

	public List<Question> getQuestions() {
		//make sure only to return the questions that are not already part of the questionaire
		List<Question> questions = questionService.findNotInQuestionaire(questionaire);
		
		return questions;
	}

	@SuppressWarnings("unchecked")
	public HierarchicalContainer getTreeData() {

		// TODO test
		questionaire = service.findAllQuestionaires().get(0);

		HierarchicalContainer questions = new HierarchicalContainer();
		

		questions.addContainerProperty(NAME, String.class, "");

		Item domainItem = null;
		Item questionItem = null;

		// adds questions as items to the questions with the question's domain
		// as parent
		for (Question question : questionaire.getQuestions()) {
			String domain = question.getDomain();

			// returns null if the item is already present, in which case it
			// needs to be retrieved.
			domainItem = questions.addItem(domain);
			if (domainItem == null) {
				domainItem = questions.getItem(domain);
			}
			domainItem.getItemProperty(NAME).setValue(domain);
			questions.setChildrenAllowed(domain, true);

			questionItem = questions.addItem(question);
			questionItem.getItemProperty(NAME).setValue("" + question.getId());
		
			questions.setParent(question, domain);
			questions.setChildrenAllowed(question, false);

		}

		return questions;
	}
		

	@PostConstruct
	public void init() {
	}

	@PreDestroy
	public void tearDown() {

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

	};

}
