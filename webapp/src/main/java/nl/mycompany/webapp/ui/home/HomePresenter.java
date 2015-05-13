package nl.mycompany.webapp.ui.home;

import nl.mycompany.questionaire.service.repository.ClientService;
import nl.mycompany.webapp.abstracts.AbstractPresenter;

import org.activiti.engine.IdentityService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
public class HomePresenter extends AbstractPresenter<HomeView> {

	private static final Logger LOG = Logger.getLogger(HomePresenter.class);

	@Autowired
	protected transient IdentityService identityService;

	@Autowired
	private transient ClientService clientService;

	private transient HomeView loginView;

	public void setLoginView(HomeView loginView) {
		this.loginView = loginView;
	}

	
	

	

}
