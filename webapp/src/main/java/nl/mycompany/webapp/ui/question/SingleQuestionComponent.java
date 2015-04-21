package nl.mycompany.webapp.ui.question;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import nl.mycompany.questionaire.domain.Question;
import nl.mycompany.webapp.ui.login.LoginViewComponent;

import com.github.peholmst.i18n4vaadin.annotations.Message;
import com.github.peholmst.i18n4vaadin.annotations.Messages;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Button.ClickEvent;

@SuppressWarnings("serial")
public class SingleQuestionComponent extends CustomComponent{
	
	SingleQuestionComponentBundle bundle = new SingleQuestionComponentBundle();
	
	QuestionairePresenter presenter;
	
	Question question;
	
	String questionStatus; 
	
	Label statusLabel;
	
	Button startButton;
	
	private static final Logger LOG = Logger
			.getLogger(SingleQuestionComponent.class);
	
	
	
	@Messages({
		@Message(key="questionId.caption", value ="question: {0}"),
		@Message(key="questionStatus.caption", value ="Status is {0}"),
		@Message(key="start.caption", value ="Start")
	})
	public SingleQuestionComponent(Question question, QuestionairePresenter presenter) {
		
		this.presenter = presenter;
		this.presenter.setCompenent(this);
		this.question = question;
		// A layout structure used for composition
		Panel panel = new Panel();
		HorizontalLayout layout = new HorizontalLayout();
		panel.setContent(layout);
		layout.setSpacing(true);
		// Compose from multiple components
		
		Label questionText = new Label(question.getQuestionText());
		startButton= new Button(bundle.start_caption(), this::startProcess);
		
		questionStatus = "pending";
		statusLabel = new Label(bundle.questionStatus_caption(questionStatus) );
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
	
		private void startProcess(ClickEvent event)
		{
			
			presenter.startAnswerQuestionProcess(question);
		}
		
		public void updateQuestionComponent(String status)
		{
			statusLabel.setCaption(bundle.questionStatus_caption(status));
			if(!status.equals("pending"))
			{
				startButton.setEnabled(false);
			}
		}

}
