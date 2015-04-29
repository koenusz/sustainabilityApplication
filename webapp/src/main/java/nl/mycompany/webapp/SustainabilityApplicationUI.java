package nl.mycompany.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PreDestroy;

import nl.mycompany.questionaire.identity.AuthenticatedUser;
import nl.mycompany.questionaire.identity.CurrentUserFactoryBean;
import nl.mycompany.webapp.event.ActivitiEventSubscriber;
import nl.mycompany.webapp.ui.login.LoginView;
import nl.mycompany.webapp.ui.question.QuestionGenerator;
import nl.mycompany.webapp.ui.question.QuestionPresenter;
import nl.mycompany.webapp.ui.question.QuestionView;
import nl.mycompany.webapp.ui.questionaire.QuestionaireView;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.peholmst.i18n4vaadin.I18N;
import com.github.peholmst.i18n4vaadin.annotations.Message;
import com.github.peholmst.i18n4vaadin.annotations.Messages;
import com.github.peholmst.i18n4vaadin.simple.I18NProvidingUIStrategy;
import com.github.peholmst.i18n4vaadin.simple.SimpleI18N;
import com.github.peholmst.i18n4vaadin.util.I18NHolder;
import com.github.peholmst.i18n4vaadin.util.I18NProvider;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ClientConnector.DetachListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@Theme("mytheme")
@SpringUI
@Widgetset("nl.mycompany.webapp.MyAppWidgetset")
@Push
public class SustainabilityApplicationUI extends UI implements I18NProvider,
		ActivitiEventListener, DetachListener {

	private static final Logger LOG = Logger
			.getLogger(SustainabilityApplicationUI.class);

	@Autowired
	protected transient CurrentUserFactoryBean currentUserFactoryBean;

	private AuthenticatedUser loggedInUser;

	@Autowired
	private SpringViewProvider viewProvider;

	private Navigator navigator;

	private String fragmentAndParameters;

	ResourceBundle i18nBundle;

	@Autowired
	private RuntimeService runtimeService;

	// TODO testcode
	@Autowired
	QuestionPresenter presenter;

	// TODO testcode
	@Autowired
	private transient QuestionGenerator generator;

	private Map<ActivitiEventSubscriber, List<ActivitiEventType>> subscriberMap = new HashMap<>();

	@Messages({
			@Message(key = "viewScopedView", value = "View Scoped View"),
			@Message(key = "viewDefaultView", value = "View Default View"),
			@Message(key = "viewLoginView", value = "View Login View"),
			@Message(key = "viewQuestionaireView", value = "View Questionaire View"),
			@Message(key = "viewQuestionView", value = "View Question View")

	})
	@Override
	protected void init(VaadinRequest vaadinRequest) {

		// TODO tesing code
		generator.cleanUp();
		generator.init();

		final VerticalLayout root = new VerticalLayout();
		root.setSizeFull();
		root.setMargin(true);
		root.setSpacing(true);
		setContent(root);

		final CssLayout navigationBar = new CssLayout();
		navigationBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		navigationBar.addComponent(createNavigationButton(
				bundle.viewScopedView(), ScopedView.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton(
				bundle.viewDefaultView(), DefaultView.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton(
				bundle.viewLoginView(), LoginView.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton(
				bundle.viewQuestionaireView(), QuestionaireView.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton(
				bundle.viewQuestionView(), QuestionView.VIEW_NAME));
		root.addComponent(navigationBar);

		final Panel viewContainer = new Panel();
		viewContainer.setSizeFull();
		root.addComponent(viewContainer);
		root.setExpandRatio(viewContainer, 1.0f);

		navigator = new Navigator(this, viewContainer);
		navigator.addProvider(viewProvider);

		// we'll handle permissions with a listener here, you could also do
		// that in the View itself.
		navigator.addViewChangeListener(new ViewChangeListener() {

			@Override
			public boolean beforeViewChange(ViewChangeEvent event) {

				LOG.debug("view: " + event.getViewName());
				if (SustainabilityApplicationUI.getCurrent().getLoggedInUser() == null
						&& !event.getViewName().equals("login")) {
					// Show to LoginView instead, pass intended view
					fragmentAndParameters = event.getViewName();
					if (event.getParameters() != null) {
						fragmentAndParameters += "/";
						fragmentAndParameters += event.getParameters();
					}
					LOG.debug("fragmentAndParameters: " + fragmentAndParameters);

					navigator.navigateTo("login");

					return false;

				} else {
					return true;
				}
			}

			@Override
			public void afterViewChange(ViewChangeEvent event) {

			}
		});
		// add the ui as listener to the activiti engine
		this.runtimeService.addEventListener(this);
		// add a detach listener for cleanup
		addDetachListener(this);

	}

	private Button createNavigationButton(String caption, final String viewName) {
		Button button = new Button(caption);
		button.addStyleName(ValoTheme.BUTTON_SMALL);
		button.addClickListener(event -> getUI().getNavigator().navigateTo(
				viewName));
		return button;
	}

	public static SustainabilityApplicationUI getCurrent() {
		return (SustainabilityApplicationUI) UI.getCurrent();
	}

	public AuthenticatedUser getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(AuthenticatedUser loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	public CurrentUserFactoryBean getCurrentUserFactoryBean() {

		return currentUserFactoryBean;
	}

	public void navigateToFragmentAndParameters() {
		this.navigateTo(fragmentAndParameters);
	}

	public static void navigateTo(String viewName) {
		getCurrent().navigator.navigateTo(viewName);
	}

	private I18N i18n = new SimpleI18N(Arrays.asList(new Locale("en"),
			new Locale("nl")));
	private SustainabilityApplicationUIBundle bundle = new SustainabilityApplicationUIBundle();

	// used for language support
	static {
		I18NHolder.setStrategy(new I18NProvidingUIStrategy());
	}

	@Override
	public I18N getI18N() {
		return i18n;
	}

	// start
	// These methods are in place to make sure events end up at the components.
	// The structure may seem a bit elaborate but is this way to make use of the
	// push functionality of the vaadin framework.
	@Override
	public void onEvent(ActivitiEvent event) {

		LOG.debug("Recieved: " + event.getType());
		access(() -> notifySubscribers(event));

	}

	private void notifySubscribers(ActivitiEvent event) {

		for (ActivitiEventSubscriber sub : subscriberMap.keySet()) {
			if (subscriberMap.get(sub).contains(event.getType())) {
				LOG.debug("notifying: " + event.getType());
				sub.onEvent(event);
			}
		}
	}

	public static void addActivityEventSubscriber(ActivitiEventSubscriber sub,
			List<ActivitiEventType> typeList) {

		if (!(SustainabilityApplicationUI.getCurrent() == null)) {
			SustainabilityApplicationUI.getCurrent().subscriberMap.put(sub,
					typeList);
		}
	}

	public static void removeActivityEventSubscriber(ActivitiEventSubscriber sub) {
		SustainabilityApplicationUI.getCurrent().subscriberMap.remove(sub);
	}

	@Override
	public boolean isFailOnException() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void detach(DetachEvent event) {
		LOG.debug("detatching UI: " + this.getUIId());
		// unregister the ui as listener on the activiti engine
		runtimeService.removeEventListener(this);
	}
	
	@PreDestroy
	public void tearDown() {

		// TODO tesing code
		generator.cleanUp();
	}

}
