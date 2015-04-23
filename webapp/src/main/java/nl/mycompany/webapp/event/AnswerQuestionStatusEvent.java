package nl.mycompany.webapp.event;

import nl.mycompany.questionaire.domain.Question;
import nl.mycompany.webapp.abstracts.View;
import nl.mycompany.webapp.abstracts.ViewEvent;
import nl.mycompany.webapp.ui.question.AnswerQuestionStatusTranslator;


public class AnswerQuestionStatusEvent extends ViewEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8019763917698063401L;
	private String status;
	private Question question;
	
	public AnswerQuestionStatusEvent(View source, String activityId, Question question) {
		super(source);
		
		this.status = AnswerQuestionStatusTranslator.translate(activityId);
		this.question = question;
	}
	
	public String getStatus()
	{
		return this.status;
	}
	
	public Question getQuestion()
	{
		return this.question;
	}

}
