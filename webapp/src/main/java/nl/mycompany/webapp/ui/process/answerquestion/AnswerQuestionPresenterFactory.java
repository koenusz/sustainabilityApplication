package nl.mycompany.webapp.ui.process.answerquestion;

import nl.mycompany.questionaire.domain.Question;

public interface AnswerQuestionPresenterFactory {
	
	AnswerQuestionPresenter createAnswerQuestionPresenter(Question question);

}
