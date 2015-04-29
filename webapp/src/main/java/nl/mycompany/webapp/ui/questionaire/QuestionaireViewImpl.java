package nl.mycompany.webapp.ui.questionaire;

import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import nl.mycompany.questionaire.domain.Question;
import nl.mycompany.questionaire.domain.Questionaire;
import nl.mycompany.webapp.abstracts.ViewEvent;
import nl.mycompany.webapp.event.AnswerQuestionStatusEvent;
import nl.mycompany.webapp.exception.ComponentNotFoundException;
import nl.mycompany.webapp.ui.process.answerquestion.SingleQuestionComponentFactory;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Component;
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


	private TabSheet tabSheet;

	@PostConstruct
	void init() {
		questionairePresenter.setView(this);

		VerticalLayout layout = new VerticalLayout();
		tabSheet = new TabSheet();
		layout.addComponent(tabSheet);

		Questionaire questionaire = questionairePresenter.getQuestionaire();

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