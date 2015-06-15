package nl.mycompany.webapp.ui.task;

import java.util.List;

import nl.mycompany.webapp.SustainabilityApplicationUI;
import nl.mycompany.webapp.abstracts.AbstractPresenter;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.peholmst.i18n4vaadin.annotations.Message;
import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
public class TaskPresenter extends AbstractPresenter<TaskView> {

	private static final Logger LOG = Logger.getLogger(TaskPresenter.class);

	@Autowired
	private TaskService taskService;

	private transient TaskView taskView;

	private TaskPresenterBundle bundle = new TaskPresenterBundle();

	public static final String ITEMCAPTUREPROPERTYID = "id";

	public void setLoginView(TaskView loginView) {
		this.taskView = taskView;
	}

	public String getLoggedInUserName() {
		return SustainabilityApplicationUI.getCurrent().getLoggedInUser()
				.getName();
	}

	public List<Task> findTasksforLoggedInUser() {
		return taskService.createTaskQuery()
				.taskAssignee(getLoggedInUserName()).list();

	}

	@SuppressWarnings("unchecked")
	@Message(key = "message.noTask", value = "No tasks found")
	public HierarchicalContainer getTreeData() {

		List<Task> taskList = findTasksforLoggedInUser();

		HierarchicalContainer tasks = new HierarchicalContainer();

		tasks.addContainerProperty(ITEMCAPTUREPROPERTYID, String.class, "");

		Item processItem = null;
		Item taskItem = null;

		//provide a message when container is empty
		if(taskList.isEmpty())
		{
			processItem = tasks.addItem(bundle.message_noTask());
			processItem.getItemProperty(ITEMCAPTUREPROPERTYID).setValue(
					bundle.message_noTask());
		}
		
		// adds questions as items to the questions with the question's domain
		// as parent
		for (Task task : taskList) {
			String processId = task.getProcessDefinitionId();

			// returns null if the item is already present, in which case it
			// needs to be retrieved.
			processItem = tasks.addItem(processId);
			if (processItem == null) {
				processItem = tasks.getItem(processId);
			}
			processItem.getItemProperty(ITEMCAPTUREPROPERTYID).setValue(
					processId);
			tasks.setChildrenAllowed(processId, true);

			taskItem = tasks.addItem(task);
			taskItem.getItemProperty(ITEMCAPTUREPROPERTYID).setValue(
					"" + task.getId());

			tasks.setParent(task, processId);
			tasks.setChildrenAllowed(task, false);

		}

		return tasks;
	}

}
