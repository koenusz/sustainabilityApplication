package nl.mycompany.webapp.ui.questionaire;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import javax.annotation.PostConstruct;

import nl.mycompany.questionaire.domain.Question;
import nl.mycompany.questionaire.domain.Questionaire;
import nl.mycompany.webapp.SustainabilityApplicationUI;
import nl.mycompany.webapp.abstracts.ViewEvent;
import nl.mycompany.webapp.event.AnswerQuestionStatusEvent;
import nl.mycompany.webapp.exception.ComponentNotFoundException;
import nl.mycompany.webapp.ui.process.answerquestion.SingleQuestionComponentFactory;
import nl.mycompany.webapp.ui.user.UserView;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.peholmst.i18n4vaadin.annotations.Message;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@SpringView(name = QuestionaireView.VIEW_NAME)
public class QuestionaireViewImpl extends VerticalLayout implements
		QuestionaireView {

	private static final Logger LOG = Logger
			.getLogger(QuestionaireViewImpl.class);

	@Autowired
	private transient QuestionairePresenter questionairePresenter;

	@Autowired
	private transient SingleQuestionComponentFactory componentFactory;
	
	QuestionaireViewImplBundle bundle = new QuestionaireViewImplBundle();

	private TabSheet tabSheet;

	@PostConstruct
	@Message(key = "message", value = "No client found for user {0}")
	void init() {
		questionairePresenter.setView(this);

		VerticalLayout layout = new VerticalLayout();
		tabSheet = new TabSheet();
		layout.addComponent(tabSheet);

		Questionaire questionaire = questionairePresenter.getQuestionaire();

		if (questionaire == null) {
			Notification.show(bundle.message(SustainabilityApplicationUI.getCurrent().getLoggedInUser().getName()), Notification.Type.WARNING_MESSAGE);
			//TODO likely due to https://dev.vaadin.com/ticket/17477. fix in vaadin 7.4.5
			try{
			SustainabilityApplicationUI.navigateTo(UserView.VIEW_NAME);
			} catch (ConcurrentModificationException e)
			{
				//do nothing, the functionality is correct.
				//TODO remove this try catch on update to vaadin 7.4.5
				//maybe the code needs to change using a viewchangelistener.
			}
			return;
		}

		for (Question question : questionaire.getQuestions()) {
			this.addQuestion(question);
		}

		this.addComponent(layout);

	}

	private void addQuestion(Question question) {

		boolean foundTab = false;
		Tab tab = null;

		Iterator<Component> i = tabSheet.iterator();
		while (i.hasNext()) {
			Component c = (Component) i.next();
			tab = tabSheet.getTab(c);
			if (question.getDomain().equals(tab.getCaption())) {
				foundTab = true;
				break;
			}
		}
		if (!foundTab) {
			tab = addTab(question.getDomain());
		}

		((VerticalLayout) tab.getComponent()).addComponent(componentFactory
				.createSingleQuestionComponent(question));

	}

	private Tab addTab(String caption) {
		// Create the first tab
		VerticalLayout layout = new VerticalLayout();
		return tabSheet.addTab(layout, caption);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// the view is constructed in the init() method()
	}

	@Override
	public void fireViewEvent(ViewEvent ev) {
		if (ev instanceof AnswerQuestionStatusEvent) {
			AnswerQuestionStatusEvent event = (AnswerQuestionStatusEvent) ev;
			try {
				questionairePresenter.findCompenentByQuestion(
						event.getQuestion()).updateQuestionStatus(
						event.getStatus());
			} catch (ComponentNotFoundException e) {
				LOG.error("Error when processing event " + event.getClass(), e);
				e.printStackTrace();
			}
		}

	}

}