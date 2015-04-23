package nl.mycompany.webapp.ui.login;

import javax.annotation.PostConstruct;

import nl.mycompany.webapp.abstracts.ViewEvent;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.peholmst.i18n4vaadin.annotations.Message;
import com.github.peholmst.i18n4vaadin.annotations.Messages;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
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
	
	LoginViewComponentBundle bundle = new LoginViewComponentBundle();
	
	@Autowired
	LoginPresenter presenter;
		
	
	@Messages({
		@Message(key="username.caption", value ="Username"),
		@Message(key="password.caption", value ="Password"),
		@Message(key="login.caption", value ="Login")
	})
	@PostConstruct
    void init() {
		
		LOG.debug("init loginviewcomponent");
		presenter.setLoginView(this);
		Layout layout = new VerticalLayout();

		
		username = new TextField(bundle.username_caption());
		layout.addComponent(username);

		password = new PasswordField(bundle.password_caption());
		layout.addComponent(password);

		final Button login = new Button(bundle.login_caption(), this::login);
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

	
	@Messages({
		@Message(key="loginFailed.caption" , value = "Login failed."),
		@Message(key="loginFailed.description" , value = "Please try again.")
	})
	@Override
	public void showLoginFailed() {
		Notification.show(bundle.loginFailed_caption(),
               bundle.loginFailed_description(),
                Notification.Type.WARNING_MESSAGE);
	}

	@Override
	public void clearForm() {
		username.setValue("");
		password.setValue("");

	}


	@Override
	public void fireViewEvent(ViewEvent event) {
		// TODO Auto-generated method stub
		
	}




}
