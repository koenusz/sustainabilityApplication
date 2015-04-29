package nl.mycompany.webapp.ui.process.answerquestion;

import javax.annotation.PostConstruct;

import nl.mycompany.questionaire.domain.Question;
import nl.mycompany.webapp.abstracts.ViewEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@SpringView(name = NewAnswerQuestionProcessForm.VIEW_NAME)
public class NewAnswerQuestionProcessFormImpl extends FormLayout implements NewAnswerQuestionProcessForm {
	
	
	@Autowired
	AnswerQuestionPresenter presenter;
	
	public NewAnswerQuestionProcessFormImpl()
	{
		
	}
	
	@PostConstruct
	private void init()
	{
		BeanItem<Question> item = new BeanItem<>(presenter.getQuestion());
		
		
		this.setCaption(presenter.getQuestion().getQuestionText());
		this.setSizeUndefined();
		
		TextField startRemark = new TextField("the questions startRemark");
		this.addComponent(startRemark);
		
		FieldGroup binder = new FieldGroup(item);
		binder.bind(startRemark, "startRemark");
		
		
	}

	@Override
	public void fireViewEvent(ViewEvent event) {
		// nothing happends
		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// nothing happends
		
	}

}
