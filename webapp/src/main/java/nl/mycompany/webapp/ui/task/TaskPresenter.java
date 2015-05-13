package nl.mycompany.webapp.ui.task;

import nl.mycompany.questionaire.service.repository.ClientService;
import nl.mycompany.webapp.abstracts.AbstractPresenter;

import org.activiti.engine.IdentityService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
public class TaskPresenter extends AbstractPresenter<TaskView> {

	private static final Logger LOG = Logger.getLogger(TaskPresenter.class);

	@Autowired
	protected transient IdentityService identityService;

	@Autowired
	private transient ClientService clientService;

	private transient TaskView loginView;

	public void setLoginView(TaskView loginView) {
		this.loginView = loginView;
	}

	
	

	

}
