package nl.mycompany.webapp;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import nl.mycompany.questionaire.domain.Question;
import nl.mycompany.webapp.ui.question.QuestionairePresenter;
import nl.mycompany.webapp.ui.question.SingleQuestionComponent;
import nl.mycompany.webapp.ui.question.SingleQuestionComponentFactory;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@SpringView(name = DefaultView.VIEW_NAME)
public class DefaultView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "";
    
	@Autowired
	QuestionairePresenter presenter;
	
	@Autowired
	SingleQuestionComponentFactory componentFactory;
	

    @PostConstruct
    void init() {
    	
    	Question question = new Question();
    	//question.setId(120);
    	question.setQuestionText("Wie kan mij de weg naar Hamelen vertellen?");
    	question.setDomain("Domme vragen");
    	
    	Question question2 = new Question();
    	//question.setId(120);
    	question2.setQuestionText("Wie kan mij de weg naar Rotterdam vertellen?");
    	question2.setDomain("Domme vragen");
    	
        addComponent(componentFactory.createSingleQuestionComponent(question));
    	 addComponent(componentFactory.createSingleQuestionComponent(question2));

        
  
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // the view is constructed in the init() method()
    }
}