package nl.mycompany.webapp.ui.question;

import com.github.peholmst.i18n4vaadin.annotations.Message;

public interface AnswerQuestionStatus {
	
	AnswerQuestionStatusBundle bundle = new AnswerQuestionStatusBundle();

	@Message(key ="startAnswerQuestion" , value = "pending")
	public final String STATUS_PENDING = bundle.startAnswerQuestion();
	@Message(key ="answerQuestionTask" , value = "answer")
	public final String STATUS_ANSWER = bundle.answerQuestionTask();
	@Message(key ="auditQuestionTask" , value = "audit")
	public final String STATUS_AUDIT = bundle.auditQuestionTask();

}
