package nl.mycompany.webapp.ui.question;

import javax.annotation.PostConstruct;

import nl.mycompany.questionaire.domain.Question;
import nl.mycompany.webapp.SustainabilityApplicationUI;
import nl.mycompany.webapp.abstracts.ViewEvent;
import nl.mycompany.webapp.ui.login.LoginView;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.peholmst.i18n4vaadin.annotations.Message;
import com.github.peholmst.i18n4vaadin.annotations.Messages;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;


@SuppressWarnings("serial")
@SpringView(name = QuestionView.VIEW_NAME)
public class QuestionViewComponent extends Panel implements QuestionView{
	
	private static final Logger LOG = Logger
			.getLogger(QuestionViewComponent.class);
	
	private boolean editable = true;
	
	@Autowired
	QuestionPresenter presenter;
	
	QuestionViewComponentBundle bundle = new QuestionViewComponentBundle();
	
	TextField questionId;
	
	TextArea area;
	
	View previousView;
	
	FieldGroup binder;
	
	
	
	@Messages({
		@Message(key="questionID.caption", value ="Question Number: "),
		@Message(key="save.caption", value ="Save"),
		@Message(key="cancel.caption", value ="Cancel")
	})
	@PostConstruct
    void init() {
		presenter.init();
		
		LOG.debug("init loginviewcomponent");
		presenter.setView(this);
		Layout layout = new VerticalLayout();
		
		
		questionId = new TextField();
		layout.addComponent(questionId);
		
		area = new TextArea();
		layout.addComponent(area);
		
		HorizontalLayout buttonBar = new HorizontalLayout();
		layout.addComponent(buttonBar);
		final Button save = new Button(bundle.save_caption(), this::save);
		buttonBar.addComponent(save);
		final Button cancel = new Button(bundle.cancel_caption(), this::cancel);
		buttonBar.addComponent(cancel);
		
		layout.setEnabled(editable);
		
		//data binding
		
		BeanItem<Question> item = new BeanItem<>(presenter.getQuestion());
		binder = new FieldGroup(item);
		binder.bind(questionId, "id");
		binder.bind(area, "questionText");
		
		setContent(layout);
    }
	
	
	

	private void save(ClickEvent event)
	{
		try {
			binder.commit();
		} catch (CommitException e) {
			e.printStackTrace();
		}
		presenter.save();
		SustainabilityApplicationUI.navigateTo("");
	}
	
	private void cancel(ClickEvent event)
	{
		SustainabilityApplicationUI.navigateTo("");
	}
	
	
	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	@Override
	public void fireViewEvent(ViewEvent event) {
		//do nothing
		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		//do nothing
		
	}

}
