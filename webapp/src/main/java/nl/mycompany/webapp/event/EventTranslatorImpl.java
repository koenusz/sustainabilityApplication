package nl.mycompany.webapp.event;

import nl.mycompany.questionaire.domain.Question;
import nl.mycompany.questionaire.service.AnswerQuestionService;
import nl.mycompany.webapp.abstracts.View;

import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent("eventTranslator")
public class EventTranslatorImpl implements EventTranslator {

	@Autowired
	private TaskService taskService;

	@Autowired
	private AnswerQuestionService answerQuestionService;

	public AnswerQuestionStatusEvent translate(ActivitiEvent event,
			View view) {
		Question question = answerQuestionService
				.findQuestionByProcessInstance(event.getProcessInstanceId());
		String activityId = answerQuestionService
				.getProcessInstanceForQuestion(question).getActivityId();
		return new AnswerQuestionStatusEvent(view, activityId, question);

	}

}
