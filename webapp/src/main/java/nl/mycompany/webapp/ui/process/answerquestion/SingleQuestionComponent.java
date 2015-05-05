package nl.mycompany.webapp.ui.process.answerquestion;

import javax.annotation.PostConstruct;

import nl.mycompany.questionaire.domain.Question;
import nl.mycompany.webapp.ui.questionaire.QuestionairePresenter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.peholmst.i18n4vaadin.annotations.Message;
import com.github.peholmst.i18n4vaadin.annotations.Messages;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

@SuppressWarnings("serial")
public class SingleQuestionComponent extends CustomComponent {

	SingleQuestionComponentBundle bundle = new SingleQuestionComponentBundle();
	
	@Autowired
	QuestionairePresenter presenter;

	private Label statusLabel;
	private Button startButton;
	
	private Question question;

	private static final Logger LOG = Logger
			.getLogger(SingleQuestionComponent.class);

	public SingleQuestionComponent(Question question) {
		this.question = question;
	}

	@Messages({ @Message(key = "questionId.value", value = "question: {0}"),
			@Message(key = "questionStatus.value", value = "Status is: {0}"),
			@Message(key = "start.caption", value = "Start") })
	@PostConstruct
	public void init() {
		LOG.debug("initializing singleQuestion Component");
		presenter.addComponent(question, this);
		
		Panel panel = new Panel();
		HorizontalLayout layout = new HorizontalLayout();
		panel.setContent(layout);
		layout.setSpacing(true);
		// Compose from multiple components

		Label questionText = new Label(question.getQuestionText());
		startButton = new Button(bundle.start_caption(), this::startProcess);

		
		statusLabel = new Label(bundle.questionStatus_value(presenter.getQuestionStatus(this)));
		statusLabel.setSizeUndefined(); // Shrink
		layout.addComponent(questionText);
		layout.addComponent(startButton);
		layout.addComponent(statusLabel);
		// Set the size as undefined at all levels
		panel.getContent().setSizeUndefined();
		panel.setSizeUndefined();
		setSizeUndefined();
		// The composition root MUST be set
		setCompositionRoot(panel);
	}


	private void startProcess(ClickEvent event) {
		//SustainabilityApplicationUI.navigateTo(NewAnswerQuestionProcessForm.VIEW_NAME);
		presenter.startAnswerQuestionProcess(this);
	}

	public void updateQuestionStatus(String status) {

		statusLabel.setValue(bundle.questionStatus_value(status));
		if (!status.equals("pending")) {
			startButton.setEnabled(false);
		}
	}


}
