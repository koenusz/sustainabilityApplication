package nl.mycompany.webapp.ui.home;

import javax.annotation.PostConstruct;

import nl.mycompany.webapp.abstracts.ViewEvent;
import nl.mycompany.webapp.ui.questionairebuilder.QuestionaireBuilderView;
import nl.mycompany.webapp.ui.task.TaskView;
import nl.mycompany.webapp.ui.user.UserView;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.peholmst.i18n4vaadin.annotations.Message;
import com.github.peholmst.i18n4vaadin.annotations.Messages;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Link;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@SpringView(name = HomeView.VIEW_NAME)
public class HomeViewComponent extends Panel implements HomeView {

	private static final Logger LOG = Logger
			.getLogger(HomeViewComponent.class);

	
	HomeViewComponentBundle bundle = new HomeViewComponentBundle();
	
	@Autowired
	HomePresenter presenter;
		
	
	@Messages({
		@Message(key="caption.taskLink", value ="Manage Tasks"),
		@Message(key="caption.userLink", value ="Manage Users"),
		@Message(key="caption.questionaireLink", value ="Manage Questionaire")
	})
	@PostConstruct
    void init() {
		VerticalLayout layout = new VerticalLayout();
		
		Link taskLink  = new Link(bundle.caption_taskLink(),new ExternalResource("#!" + TaskView.VIEW_NAME));
		Link userLink  = new Link(bundle.caption_userLink(), new ExternalResource("#!" + UserView.VIEW_NAME));
		Link questionaireLink  = new Link(bundle.caption_questionaireLink(), new ExternalResource("#!" + QuestionaireBuilderView.VIEW_NAME));
		
		
		layout.addComponent(taskLink);
		layout.addComponent(userLink);
		layout.addComponent(questionaireLink);
		
		this.setContent(layout);
		
    }

	

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
	}

	
	@Messages({
		@Message(key="loginFailed.caption" , value = "Login failed."),
		@Message(key="loginFailed.description" , value = "Please try again.")
	})
	


	@Override
	public void fireViewEvent(ViewEvent event) {
		// TODO Auto-generated method stub
		
	}


}
