package nl.mycompany.webapp.ui.task;

import java.util.Iterator;

import javax.annotation.PostConstruct;

import nl.mycompany.webapp.SustainabilityApplicationUI;
import nl.mycompany.webapp.abstracts.ViewEvent;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.peholmst.i18n4vaadin.annotations.Message;
import com.github.peholmst.i18n4vaadin.annotations.Messages;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.Tree.TreeDragMode;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@SpringView(name = TaskView.VIEW_NAME)
public class TaskViewComponent extends Panel implements TaskView {

	private static final Logger LOG = Logger
			.getLogger(TaskViewComponent.class);

	
	private Tree taskTree;
	
	TaskViewComponentBundle bundle = new TaskViewComponentBundle();
	
	@Autowired
	TaskPresenter presenter;
		
	
	@Messages({
		@Message(key="caption.userLabel", value ="User"),
		@Message(key="caption.processLabel", value ="Process"),
		
		@Message(key="caption.taskLabel", value ="Task")
	})
	@PostConstruct
    void init() {
		VerticalLayout layout = new VerticalLayout();
	
		Label userLabel = new Label(SustainabilityApplicationUI.getCurrent().getLoggedInUser().getName());
		userLabel.setCaption(bundle.caption_userLabel());
	
		layout.addComponent(userLabel);
		
		
		HorizontalLayout taskLayout = new HorizontalLayout();
		
		taskTree = new Tree();
		taskTree.setDragMode(TreeDragMode.NONE);

		// Expand all nodes
		for (Iterator<?> it = taskTree.rootItemIds().iterator(); it.hasNext();) {
			taskTree.expandItemsRecursively(it.next());
		}
	
		updateTaskTree();
		taskLayout.addComponent(taskTree);
		
		VerticalLayout taskForm = new VerticalLayout();
		Label placeholder = new Label("placeholder");
		taskForm.addComponent(placeholder);
		taskLayout.addComponent(taskForm);
		
		layout.addComponent(taskLayout);
		
		this.setContent(layout);
    }
	
	private void updateTaskTree()
	{
		taskTree.setContainerDataSource(presenter.getTreeData());
		taskTree.setItemCaptionPropertyId(TaskPresenter.ITEMCAPTUREPROPERTYID);
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
