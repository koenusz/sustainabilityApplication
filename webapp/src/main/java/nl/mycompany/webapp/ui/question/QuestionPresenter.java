package nl.mycompany.webapp.ui.question;

import nl.mycompany.questionaire.domain.Question;
import nl.mycompany.questionaire.service.repository.QuestionService;
import nl.mycompany.webapp.abstracts.AbstractPresenter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class QuestionPresenter extends AbstractPresenter<QuestionView> {
	
	private static final Logger LOG = Logger
			.getLogger(QuestionPresenter.class);

	@Autowired
	QuestionService service;

	private Question question;

	//TODO test
	public void init()
	{
		question = service.findAllQuestions().get(0);
	}

	public void save() {
		service.saveQuestion(question);
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

}
