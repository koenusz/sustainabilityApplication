package nl.mycompany.webapp.ui.question;

import javax.annotation.PostConstruct;

import nl.mycompany.questionaire.domain.Question;
import nl.mycompany.webapp.abstracts.ViewEvent;
import nl.mycompany.webapp.event.AnswerQuestionStatusEvent;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@SpringView(name = QuestionaireViewImpl.VIEW_NAME)
public class QuestionaireViewImpl extends VerticalLayout implements
		QuestionaireView {

	private static final Logger LOG = Logger
			.getLogger(QuestionaireViewImpl.class);
	
	@Autowired
	QuestionairePresenter questionairePresenter;

	@Autowired
	SingleQuestionComponentFactory componentFactory;

	@PostConstruct
	void init() {
		questionairePresenter.setView(this);

		Question question = new Question();
		// question.setId(120);
		question.setQuestionText("Wie kan mij de weg naar Hamelen vertellen?");
		question.setDomain("Domme vragen");

		Question question2 = new Question();
		// question.setId(120);
		question2
				.setQuestionText("Wie kan mij de weg naar Rotterdam vertellen?");
		question2.setDomain("Domme vragen");

		addComponent(componentFactory.createSingleQuestionComponent(question));
		addComponent(componentFactory.createSingleQuestionComponent(question2));

	}

	private SingleQuestionComponent getCompenentByQuestion(Question question) {
		for (Component component : this.components) {
			if (component instanceof SingleQuestionComponent) {
				SingleQuestionComponent sq = (SingleQuestionComponent) component;
				if (sq.getQuestionId() == question.getId()) {
					return sq;
				}
			}
		}
		return null;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// the view is constructed in the init() method()
	}

	@Override
	public void fireViewEvent(ViewEvent event) {
		if (event instanceof AnswerQuestionStatusEvent) {
			this.getCompenentByQuestion(
					((AnswerQuestionStatusEvent) event).getQuestion())
					.updateQuestionStatus(
							((AnswerQuestionStatusEvent) event).getStatus());
		}

	}
}