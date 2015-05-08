package nl.mycompany.webapp.ui.user;

import java.util.List;

import nl.mycompany.webapp.abstracts.View;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;

public interface UserView extends View {
	
	public static final String VIEW_NAME = "user";
	
	public void updateClient();
	
	public void setUsers(List<User> users);
	
	public void setGroups(List<Group> groups);
	

}
