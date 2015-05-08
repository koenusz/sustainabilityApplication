package nl.mycompany.webapp.ui.user;

import nl.mycompany.questionaire.identity.Groups;
import nl.mycompany.webapp.abstracts.View;
import nl.mycompany.webapp.abstracts.ViewEvent;

import org.activiti.engine.identity.User;

import com.github.peholmst.i18n4vaadin.annotations.Message;
import com.github.peholmst.i18n4vaadin.annotations.Messages;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.VerticalLayout;

public class AddGroupPopup extends VerticalLayout implements View {

	private static final long serialVersionUID = -5706202138398241947L;

	private UserPresenter presenter;

	private User user;

	private String selectedGroup;

	private static final String NAME = "name";

	public AddGroupPopup(UserPresenter presenter, User user) {
		super();
		this.presenter = presenter;
		this.init();

	}

	AddGroupPopupBundle bundle = new AddGroupPopupBundle();

	@Messages({ @Message(key = "combo.caption", value = "Select a Client"),
			@Message(key = "button.caption", value = "Save") })
	public void init() {

		String[] groups = { Groups.GROUP_ADMINS, Groups.GROUP_AUDITORS,
				Groups.GROUP_CLIENTADMIN, Groups.GROUP_CLIENTS,
				Groups.GROUP_EMPLOYEE, Groups.GROUP_MANAGERS };
		IndexedContainer container = new IndexedContainer();
		container.addContainerProperty(NAME, String.class, null);

		ComboBox selectCombo = new ComboBox(bundle.combo_caption(), container);
		selectCombo.setItemCaptionMode(ItemCaptionMode.PROPERTY);
		selectCombo.setItemCaptionPropertyId(NAME);
		selectCombo.addItems(groups);
		this.addComponent(selectCombo);
		selectCombo
				.addValueChangeListener(event -> selectedGroup = (String) selectCombo
						.getValue());
		this.addComponent(new Button(bundle.button_caption(),
				click -> presenter.createMembership(user, selectedGroup)));
		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fireViewEvent(ViewEvent event) {
		// TODO Auto-generated method stub

	}

}
