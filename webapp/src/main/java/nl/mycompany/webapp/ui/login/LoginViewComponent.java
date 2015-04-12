package nl.mycompany.webapp.ui.login;

import nl.mycompany.questionaire.identity.CurrentUserFactoryBean;
import nl.mycompany.webapp.SustainabilityApplicationUI;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Component
public class LoginViewComponent extends Panel implements LoginView {

	private static final Logger LOG = Logger
			.getLogger(LoginViewComponent.class);

	private HorizontalLayout viewLayout;
	private TextField username;
	private PasswordField password;
	private Button login;
	private String fragmentAndParameters;

	private Navigator navigator;

	@Autowired
	private transient CurrentUserFactoryBean currentUserFactoryBean;

	@Autowired
	private transient LoginPresenter loginPresenter;

	public LoginViewComponent() {
		super();
		Layout layout = new VerticalLayout();

		username = new TextField("Username");
		layout.addComponent(username);

		password = new PasswordField("Password");
		layout.addComponent(password);

		final Button login = new Button("Login", this::login);
		layout.addComponent(login);
		setContent(layout);

	}

	public void setFragmentAndParameters(String fragmentAndParameters) {
		this.navigator = SustainabilityApplicationUI.getCurrent()
				.getNavigator();
		this.fragmentAndParameters = fragmentAndParameters;
	}

	private void login(ClickEvent event) {

		if (currentUserFactoryBean == null) {
			LOG.debug("currentUserFactoryBean is null");
		}

		if (loginPresenter == null) {
			LOG.debug("presenter is null");
		}

		loginPresenter.attemptLogin(username.getValue(), password.getValue());

		// navigate back to the intended place
		navigator.navigateTo(fragmentAndParameters);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showLoginFailed() {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearForm() {
		// TODO Auto-generated method stub

	}
}
