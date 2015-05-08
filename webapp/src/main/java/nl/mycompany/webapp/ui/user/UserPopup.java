package nl.mycompany.webapp.ui.user;

import nl.mycompany.webapp.abstracts.View;
import nl.mycompany.webapp.abstracts.ViewEvent;

import org.activiti.engine.identity.User;

import com.github.peholmst.i18n4vaadin.annotations.Message;
import com.github.peholmst.i18n4vaadin.annotations.Messages;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;

public class UserPopup extends FormLayout implements View{

	

	UserPopupBundle bundle = new UserPopupBundle();
	
	UserPresenter presenter;


	public UserPopup(User user, UserPresenter presenter) {
		super();
		this.presenter = presenter;
		this.init(user);
	}
	
	
	BeanFieldGroup<User> binder;

	
	@Messages({ @Message(key = "caption.form", value = "Fill out the user form"),
			@Message(key = "caption.saveButton", value = "Save") })
	public void init(User user) {
		
		this.
			
		binder = new BeanFieldGroup<>(User.class);
		binder.setItemDataSource(user);
		this.addComponent(binder.buildAndBind("Id", "id"));
		this.addComponent(binder.buildAndBind("First Name", "firstName"));
		this.addComponent(binder.buildAndBind("Last Name", "lastName"));
		this.addComponent(binder.buildAndBind("Email", "email"));
		
		binder.setBuffered(true);
		
		
			this.addComponent(new Button(bundle.caption_saveButton(), this::save));
		
		
		
		this.setCaption(bundle.caption_form());
		this.setSizeUndefined();
	

	}

	private void save(ClickEvent event)
	{
		try {
			binder.commit();
			presenter.saveUser(binder.getItemDataSource().getBean());
		} catch (CommitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		presenter.populateUserviewWithUsers();
		this.setVisible(false);
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
