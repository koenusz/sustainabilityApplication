package nl.mycompany.webapp.ui.dashboard;

import java.util.List;

import nl.mycompany.webapp.SustainabilityApplicationUI;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
public class DashboardPresenter {
	
	private static final Logger LOG = Logger.getLogger(DashboardPresenter.class);
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	IdentityService identityService;
	
	@Autowired
	RepositoryService repositoryService;
	
	
	
	public void assignTaskToCurrentUser(String taskId)
	{
		taskService.claim(taskId, getLoggedInUser());
	}
	
	
    /*public void assignTaskToCurrentUserAndComplete(String taskId) {
        assignTaskToCurrentUser(taskId);
        completeTask(taskId);
    }*/
    
 /*   public void completeTask(String taskId) {
        final Task task = getTaskById(taskId);
        if (formViewService.hasTaskFormView(task)) {
            View formView = formViewService.getTaskFormView(task);
            showFormView(formView);
        } else {
            taskService.complete(taskId);
            getView().showTaskCompletedMessage(task.getName());
        }
    }*/
    
 /*   protected View getCurrentFormView() {
        return currentFormView;
    }  */          
    
  /*  protected void showFormView(View view) {
       currentFormView = view;
       currentFormView.addListener(formCloseListener);
       getView().showProcessView(view);
    }*/
    
  /*  protected void hideFormView() {
        if (currentFormView != null) {
            currentFormView.removeListener(formCloseListener);
        }
        currentFormView = null;
        getView().hideProcessView();
    }*/
    
  /*  protected void updateTaskListsInView() {
        final List<Task> assignedTasks = getAssignedTasks();
        if (currentNumberOfAssignedTasks < assignedTasks.size()) {
            getView().showNewTasksMessage();
        }
        currentNumberOfAssignedTasks = assignedTasks.size();
        getView().setAssignedTasks(assignedTasks);
        
        final List<Task> claimableTasks = getClaimableTasks();
        if (currentNumberOfClaimableTasks < claimableTasks.size()) {
            getView().showNewClaimableTasksMessage();
        }
        currentNumberOfClaimableTasks = claimableTasks.size();
        getView().setClaimableTasks(claimableTasks);
        
        getView().pushChangesToClient();
    }*/
    
   /* protected List<Task> getClaimableTasks() {
        final TaskQuery query = taskService.createTaskQuery();        
        query.taskCandidateUser(currentUsername);
        return query.list();
    }*/
    
  /*  protected List<Task> getAssignedTasks() {
        return taskService.createTaskQuery().taskAssignee(currentUsername).list();
    }*/
    
   /* protected List<Group> getGroupsOfCurrentUser() {
        return identityService.createGroupQuery().groupMember(currentUsername).list();
    }*/
    
    protected List<ProcessDefinition> getAvailableProcesses() {
        return repositoryService.createProcessDefinitionQuery().orderByProcessDefinitionName().asc().list();
    }
    
    protected ProcessDefinition getProcessDefinitionByKey(String key) {
        return repositoryService.createProcessDefinitionQuery().processDefinitionKey(key).latestVersion().singleResult();
    }
    
    protected Task getTaskById(String id) {
        return taskService.createTaskQuery().taskId(id).singleResult();
    }

    private String getLoggedInUser()
    {
    	return SustainabilityApplicationUI.getCurrent().getLoggedInUser().getName();
    }
   
}
