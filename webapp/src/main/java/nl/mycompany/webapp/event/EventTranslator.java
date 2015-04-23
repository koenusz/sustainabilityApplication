package nl.mycompany.webapp.event;

import nl.mycompany.webapp.abstracts.View;

import org.activiti.engine.delegate.event.ActivitiEvent;

public interface EventTranslator {

	AnswerQuestionStatusEvent translate(ActivitiEvent event, View view);
	
}
