package nl.mycompany.webapp.ui.dashboard;


import java.util.Collection;
import java.util.List;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;

import com.vaadin.navigator.View;

public interface DashboardView extends View {
	
	public final String VIEW_NAME = "dashboard";
    
    void setNameOfCurrentUser(String firstName, String lastName);
    
    void setGroupsOfCurrentUser(Collection<String> groups);
    
    void setClaimableTasks(List<Task> tasks);
    
    void setAssignedTasks(List<Task> tasks);
    
    void setAvailableProcesses(List<ProcessDefinition> processes);
    
    void showProcessView(View view);
    
    void hideProcessView();
    
    void showProcessStartedMessage(String processName);
    
    void showTaskCompletedMessage(String taskName);
    
    void showNewClaimableTasksMessage();
    
    void showNewTasksMessage();
    
    void startProcessEnginePolling();
    
    void stopProcessEnginePolling();    
    
    void pushChangesToClient();
    
}
