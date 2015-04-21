package nl.mycompany.webapp.ui.question;

import nl.mycompany.questionaire.domain.Question;


public interface SingleQuestionComponentFactory {

	SingleQuestionComponent createSingleQuestionComponent(Question question);
	
}
