package nl.mycompany.webapp.ui.process.answerquestion;

import nl.mycompany.questionaire.domain.Question;

public interface SingleQuestionComponentFactory {

	SingleQuestionComponent createSingleQuestionComponent(Question question);
	
}
