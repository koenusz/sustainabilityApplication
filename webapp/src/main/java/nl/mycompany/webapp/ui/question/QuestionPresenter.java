package nl.mycompany.webapp.ui.question;

import javax.annotation.PostConstruct;

import nl.mycompany.questionaire.domain.Question;
import nl.mycompany.questionaire.service.repository.QuestionService;
import nl.mycompany.webapp.abstracts.AbstractPresenter;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
public class QuestionPresenter extends AbstractPresenter<QuestionView> {

	@Autowired
	QuestionService service;

	private Question question;
	
	//TODO
	@PostConstruct
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
