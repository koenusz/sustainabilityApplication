package nl.mycompany.webapp.ui.login;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@SpringView(name = LoginView.VIEW_NAME)
public class LoginViewComponent extends Panel implements LoginView {

	private static final Logger LOG = Logger
			.getLogger(LoginViewComponent.class);

	private TextField username;
	private PasswordField password;
	
	@Autowired
	LoginPresenter presenter;
		
	@PostConstruct
    void init() {
		
		LOG.debug("init loginviewcomponent");
		presenter.setLoginView(this);
		Layout layout = new VerticalLayout();

		username = new TextField("Username");
		layout.addComponent(username);

		password = new PasswordField("Password");
		layout.addComponent(password);

		final Button login = new Button("Login", this::login);
		layout.addComponent(login);
		setContent(layout);
    }

	
	private void login(ClickEvent event)
	{
		presenter.attemptLogin(username.getValue(), password.getValue());
	}


	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
	}

	@Override
	public void showLoginFailed() {
		Notification.show("Login failed.",
                "Please try again.",
                Notification.Type.WARNING_MESSAGE);
	}

	@Override
	public void clearForm() {
		username.setValue("");
		password.setValue("");

	}




}
